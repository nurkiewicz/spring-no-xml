package com.blogspot.nurkiewicz;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tomasz Nurkiewicz
 * @since 09.01.11, 14:21
 */
@Service
public class Foo {

	@Resource
	private Bar bar;

}
