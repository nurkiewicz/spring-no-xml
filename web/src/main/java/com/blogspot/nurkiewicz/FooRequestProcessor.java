package com.blogspot.nurkiewicz;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 14:21
 */
@Service
public class FooRequestProcessor {

	private static final Logger log = LoggerFactory.getLogger(FooRequestProcessor.class);

	@Resource
	private FooRepository fooRepository;

	@Transactional
	public void process(String payload) {
		log.debug("Processing message: '{}'", payload);
		fooRepository.persistRequest(payload);
	}

}
