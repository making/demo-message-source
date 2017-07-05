package com.example.demo;

import java.nio.charset.Charset;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.messages")
public class MessageSourceConfigProps {
	/**
	 * Comma-separated list of basenames, each following the ResourceBundle convention.
	 * Essentially a fully-qualified classpath location. If it doesn't contain a package
	 * qualifier (such as "org.mypackage"), it will be resolved from the classpath root.
	 */
	private String basename = "messages";

	/**
	 * Message bundles encoding.
	 */
	private Charset encoding = Charset.forName("UTF-8");

	/**
	 * Loaded resource bundle files cache expiration, in seconds. When set to -1, bundles
	 * are cached forever.
	 */
	private int cacheSeconds = -1;

	/**
	 * Set whether to fall back to the system Locale if no files for a specific Locale
	 * have been found. if this is turned off, the only fallback will be the default file
	 * (e.g. "messages.properties" for basename "messages").
	 */
	private boolean fallbackToSystemLocale = true;

	/**
	 * Set whether to always apply the MessageFormat rules, parsing even messages without
	 * arguments.
	 */
	private boolean alwaysUseMessageFormat = false;

	public String getBasename() {
		return basename;
	}

	public void setBasename(String basename) {
		this.basename = basename;
	}

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(Charset encoding) {
		this.encoding = encoding;
	}

	public int getCacheSeconds() {
		return cacheSeconds;
	}

	public void setCacheSeconds(int cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}

	public boolean isFallbackToSystemLocale() {
		return fallbackToSystemLocale;
	}

	public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
		this.fallbackToSystemLocale = fallbackToSystemLocale;
	}

	public boolean isAlwaysUseMessageFormat() {
		return alwaysUseMessageFormat;
	}

	public void setAlwaysUseMessageFormat(boolean alwaysUseMessageFormat) {
		this.alwaysUseMessageFormat = alwaysUseMessageFormat;
	}
}
