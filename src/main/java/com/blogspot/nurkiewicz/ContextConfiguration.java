package com.blogspot.nurkiewicz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 18:17
 */
@Configuration
public class ContextConfiguration {

	@Resource
	private Foo foo;

	@Value("#{2+3}")
	private int value;

	@Bean
	public Bar bar() {
		Assert.isTrue(value == 5, Integer.toString(value));
		return new Bar();
	}

}
