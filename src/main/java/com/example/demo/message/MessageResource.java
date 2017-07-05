package com.example.demo.message;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;

public class MessageResource implements Serializable {
	private final MessageResourceKey key;
	private final String message;

	public MessageResource(MessageResourceKey key, String message) {
		this.key = key;
		this.message = Objects.requireNonNull(message);
	}

	public MessageFormat toMessageFormat() {
		return new MessageFormat(this.message, new Locale(this.key.language));
	}
}
