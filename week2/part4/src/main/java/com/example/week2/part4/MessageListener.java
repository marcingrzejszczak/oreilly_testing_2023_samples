package com.example.week2.part4;

import java.util.function.Consumer;

public interface MessageListener<T> {

	void pollForMessage(Consumer<T> doForMessage);
}



