package com.ranyk.security;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class Security2ApplicationTests {

	@Test
	void contextLoads() {

		Map<String, Object> params = Maps.newHashMap();
		params.put("keyword","维修");
		params.put("id",null);
		params.put("regionCodes",null);

		System.out.println(JSON.toJSONString(params));

	}

}
