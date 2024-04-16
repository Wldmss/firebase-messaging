package com.example.firebasemessaging.service;

import com.google.firebase.messaging.*;
import com.example.firebasemessaging.controller.ImageConverter;
import com.example.firebasemessaging.controller.ImageToBase64;
import com.example.firebasemessaging.datasource.dto.FcmDTO;
import com.example.firebasemessaging.datasource.repository.PushMasterRepository;
import com.example.firebasemessaging.datasource.repository.PushMemberRepository;
import com.example.firebasemessaging.datasource.repository.PushQueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FcmService {

    @Autowired
    PushMasterRepository pushMasterRepository;

    @Autowired
    PushQueueRepository pushQueueRepository;

    @Autowired
    PushMemberRepository pushMemberRepository;

    @Value("classpath:static/images/icon-bio-face.png")
    private Resource resource;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 개별 메시지 발송
    public BatchResponse sendMessage() throws FirebaseMessagingException {
        // push queue 에서 메시지 데이터 가져오기
        List<FcmDTO> queueList = pushQueueRepository.getPushQueue();

        // 메시지 일괄 발송을 위한 객체
        List<Message> messages = new ArrayList<>();

        // 메시지 생성
        for(FcmDTO queue : queueList){
            String title = queue.getMsgTitle() != null ? queue.getMsgTitle() : "지니어스";
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)      // 메시지 타이틀
                            .setBody(queue.getMsgContents())    // 메시지 내용
                            .build())
                    .setToken(queue.getDeviceToken())           // 전송 device push token
                    .setAndroidConfig(androidConfig(null))
                    .setApnsConfig(apnsConfig())
                    .build();

            messages.add(message);
        }

        if(!messages.isEmpty()){
            // 메시지 일괄 전송
            BatchResponse response = FirebaseMessaging.getInstance().sendEach(messages);

            // 전송 실패 token 처리
            if(response.getFailureCount() > 0){
                List<SendResponse> responseList = response.getResponses();
                List<String> failToken = new ArrayList<>();

                for(SendResponse res : responseList){
                    if(!res.isSuccessful()) {
                        int index = responseList.indexOf(res);
                        failToken.add(queueList.get(index).getDeviceToken());
                    }
                }

                // 전송 실패 토큰 비활성화
                if(!failToken.isEmpty()) disabledDeviceToken(failToken);
            }

            return response;
        }

        return null;
    }

    public String getIcon() throws IOException {
        ImageConverter<File, String> converter = new ImageToBase64();
        return converter.convert(new File(resource.getURI()));
    }

    // 여러 사용자에게 메시지 발송
    public BatchResponse sendMultiMessage() throws FirebaseMessagingException {
        FcmDTO pushData = FcmDTO.builder()
                .msgTitle("")
                .msgContents("")
                .build();

        // 전송 device token list
        List<String> deviceTokenList = Arrays.asList(
                "",
                ""
        );

        if(!deviceTokenList.isEmpty()){
            // 메시지 전송
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(Notification.builder()
                            .setTitle(pushData.getMsgTitle())
                            .setBody(pushData.getMsgContents())
                            .build())
                    .addAllTokens(deviceTokenList)
                    .setAndroidConfig(androidConfig(null))
                    .setApnsConfig(apnsConfig())
                    .build();

            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);

            // 전송 실패 token 처리
            if(response.getFailureCount() > 0){
                List<SendResponse> responseList = response.getResponses();
                List<String> failToken = new ArrayList<>();

                for(SendResponse res : responseList){
                    if(!res.isSuccessful()) {
                        int index = responseList.indexOf(res);
                        failToken.add(deviceTokenList.get(index));
                    }
                }

//                if(!failToken.isEmpty()) disabledDeviceToken(failToken);
            }

            return response;
        }

        return null;
    }

    // topic 메시지 발송
    public BatchResponse sendTopicMessage() throws FirebaseMessagingException {
        // push queue 에서 topic 메시지 데이터 가져오기
        List<FcmDTO> queueList = pushQueueRepository.getTopicPushQueue();
        List<Message> messages = new ArrayList<>();

        for(FcmDTO queue : queueList){
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(queue.getMsgTitle())
                            .setBody(queue.getMsgContents())
                            .build())
                    .setTopic(queue.getTopic())
                    .setAndroidConfig(androidConfig("genius"))
                    .setApnsConfig(apnsConfig())
                    .build();
            messages.add(message);
        }

        if(!messages.isEmpty()){
            BatchResponse response = FirebaseMessaging.getInstance().sendEach(messages);
            return response;
        }

        return null;
    }

    // 만료된 device token 처리
    public void disabledDeviceToken(List<String> deviceToken) {
        pushMemberRepository.disableDeviceToken(LocalDateTime.now(), deviceToken);
    }

    /* android 설정
    * @channelId :: default: 알림, genius: 공지 */
    public AndroidConfig androidConfig(String channelId){
        return AndroidConfig.builder()
                .setNotification(AndroidNotification.builder()
                        .setPriority(AndroidNotification.Priority.MAX)
                        .setChannelId(channelId != null ? channelId : "default")
                        .setColor("#000000")  // push icon 배경색 변경
                        .build())
                .build();
    }

    // ios 설정
    public ApnsConfig apnsConfig() {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setBadge(42)
                        .build())
                .build();
    }
}
