package com.example.demo;

import java.util.Locale;

import org.springframework.cache.CacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.example.demo.message.CachingDbMessageSource;
import com.example.demo.message.MessageResourceMapper;

@Configuration
public class I18nConfig extends WebMvcConfigurerAdapter {

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	@Bean
	public MessageSource messageSource(CacheManager cacheManager,
			MessageResourceMapper messageResourceMapper, MessageSourceConfigProps props) {
		MessageSource fallbackMessageSource = fallbackMessageSource(props);
		CachingDbMessageSource messageSource = new CachingDbMessageSource(cacheManager,
				messageResourceMapper, fallbackMessageSource);
		messageSource.setFallBackLocale(Locale.ENGLISH);
		return messageSource;
	}

	private MessageSource fallbackMessageSource(MessageSourceConfigProps props) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		if (StringUtils.hasText(props.getBasename())) {
			messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(
					StringUtils.trimAllWhitespace(props.getBasename())));
		}
		if (props.getEncoding() != null) {
			messageSource.setDefaultEncoding(props.getEncoding().name());
		}
		messageSource.setFallbackToSystemLocale(props.isFallbackToSystemLocale());
		messageSource.setCacheSeconds(props.getCacheSeconds());
		messageSource.setAlwaysUseMessageFormat(props.isAlwaysUseMessageFormat());
		return messageSource;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}