package com.example.demo.message;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class MessageResourceKey implements Serializable {
	final String code;
	final String language;

	public MessageResourceKey(String code, String language) {
		this.code = Objects.requireNonNull(code);
		this.language = Objects.requireNonNull(language);
	}

	public MessageResourceKey(String code, Locale locale) {
		this(code, locale.getLanguage());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MessageResourceKey))
			return false;
		MessageResourceKey that = (MessageResourceKey) o;
		return code.equals(that.code) && language.equals(that.language);
	}

	@Override
	public int hashCode() {
		int result = code.hashCode();
		result = 31 * result + language.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return code + "_" + language;
	}
}
