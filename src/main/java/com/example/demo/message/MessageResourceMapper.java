package com.example.demo.message;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageResourceMapper {
	private final JdbcTemplate jdbcTemplate;

	public MessageResourceMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public MessageResource findOne(MessageResourceKey key) {
		MessageResource messageResource = this.jdbcTemplate.queryForObject(
				"SELECT code, language, message FROM message_resource WHERE code = ? AND language = ?",
				(rs, i) -> new MessageResource(new MessageResourceKey(
						rs.getString("code"), rs.getString("language")),
						rs.getString("message")),
				key.code, key.language);
		return messageResource;
	}
}
