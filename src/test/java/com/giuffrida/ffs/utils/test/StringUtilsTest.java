package com.giuffrida.ffs.utils.test;

import com.giuffrida.ffs.utils.StringUtils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	public void testStripQuotes() {
		String quoted = "\"string with quotes\"";
		assertTrue(StringUtils.stripQuotes(quoted).equals("string with quotes"));

		String notQuoted = "string with quotes";
		assertTrue(StringUtils.stripQuotes(notQuoted).equals("string with quotes"));
	}
}
