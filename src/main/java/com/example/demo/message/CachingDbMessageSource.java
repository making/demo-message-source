package com.example.demo.message;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;

public class CachingDbMessageSource extends AbstractMessageSource {
	private static final Logger log = LoggerFactory
			.getLogger(CachingDbMessageSource.class);
	private final Cache cache;
	private final MessageResourceMapper messageResourceMapper;
	private final MessageSource fallbackMessageSource;
	private Locale fallBackLocale = Locale.getDefault();

	public CachingDbMessageSource(CacheManager cacheManager,
			MessageResourceMapper messageResourceMapper,
			MessageSource fallbackMessageSource) {
		this.cache = cacheManager.getCache("messageResource");
		this.messageResourceMapper = messageResourceMapper;
		this.fallbackMessageSource = fallbackMessageSource;
	}

	public void setFallBackLocale(Locale fallBackLocale) {
		this.fallBackLocale = Objects.requireNonNull(fallBackLocale);
	}

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		MessageResourceKey key = new MessageResourceKey(code, locale);
		MessageResource messageResource = this.cache.get(key, () -> resolveFromDb(key));
		if (messageResource == null) {
			if (this.fallBackLocale.equals(locale)) {
				// not found
				return null;
			}
			log.debug("Fallback to default locale {}({})", code, this.fallBackLocale);
			// fall back to default locale
			return resolveCode(code, this.fallBackLocale);
		}
		return messageResource.toMessageFormat();
	}

	private MessageResource resolveFromDb(MessageResourceKey key) {
		try {
			return this.messageResourceMapper.findOne(key);
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	protected String getMessageInternal(String code, Object[] args, Locale locale) {
		String message = super.getMessageInternal(code, args, locale);
		if (message == null) {
			log.debug("Fallback resolving {}({})", code, locale);
			return this.fallbackMessageSource.getMessage(code, args, locale);
		}
		return message;
	}
}
