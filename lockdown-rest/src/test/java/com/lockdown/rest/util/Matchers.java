package com.lockdown.rest.util;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matcher;
import org.springframework.test.web.servlet.ResultMatcher;

public class Matchers {

	public static ResultMatcher jsonPathLong(String path, Matcher<Long> matcher) {
		return jsonPath(path).value(matcher, Long.class);
	}
}
