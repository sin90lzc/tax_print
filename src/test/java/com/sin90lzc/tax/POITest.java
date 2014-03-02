package com.sin90lzc.tax;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

public class POITest {
	@Test
	public void testCreate() {
		/*
		 * System.out.println(System .getProperty("user.dir"));
		 */

		Properties props = System.getProperties();
		Set<Entry<Object, Object>> entries = props.entrySet();
		for (Entry<Object, Object> entry : entries) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

		System.out.println("-----------------------------------------");
		Map<String, String> envs = System.getenv();
		Set<Entry<String, String>> envsEntries = envs.entrySet();
		for (Entry<String, String> envEntry : envsEntries) {
			System.out.println(envEntry.getKey() + ":" + envEntry.getValue());
		}

	}
}
