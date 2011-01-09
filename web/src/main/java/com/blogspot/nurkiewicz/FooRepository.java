package com.blogspot.nurkiewicz;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 14:21
 */
@Service
public class FooRepository {

	private static final Logger log = LoggerFactory.getLogger(FooRepository.class);

	@Resource
	private JdbcOperations jdbcOperations;

	@PostConstruct
	public void init() {
		log.info("Database server time is: {}", jdbcOperations.queryForObject("SELECT CURRENT_TIMESTAMP", Date.class));
	}

	public void persistRequest(String request) {
		log.debug("Saving request: '{}'", request);
		jdbcOperations.update("INSERT INTO requests (payload) VALUES (?)", request);
	}

}
