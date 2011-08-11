package com.blogspot.nurkiewicz;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Foo {

	@Resource
	private Bar bar;

}
