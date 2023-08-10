package com.example.week2.part4.done;

import java.util.function.Consumer;

public interface MessageListener<T> {

	void pollForMessage(Consumer<T> doForMessage);
}



