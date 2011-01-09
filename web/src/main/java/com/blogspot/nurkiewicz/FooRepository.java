package com.blogspot.nurkiewicz;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 14:21
 */
public class FooRepository {

	private static final Logger log = LoggerFactory.getLogger(FooRepository.class);

	private JdbcOperations jdbcOperations;

	public void init() {
		log.info("Daabase server time is: {}", jdbcOperations.queryForObject("SELECT CURRENT_TIMESTAMP", Date.class));
	}

	public void persistRequest(String request) {
		log.debug("Saving request: '{}'", request);
		jdbcOperations.update("INSERT INTO requests (payload) VALUES (?)", request);
	}

	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
}
