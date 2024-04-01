package com.example.firebasemessaging.controller;

import java.io.IOException;

public abstract class ImageConverter<T,V> {
    public abstract V convert(T t) throws IOException;
}
