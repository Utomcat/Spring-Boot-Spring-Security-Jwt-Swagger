package com.ranyk.security;

import com.alibaba.fastjson.JSON;
import com.ranyk.security.method.operate.ReflexMethod;
import com.ranyk.security.method.vo.PeopleVO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@SpringBootTest
class Security3ApplicationTests {

	@Autowired
	private ReflexMethod reflexMethod;

	@Test
	void contextLoads() {
	}

	@Test
	void testMethod1() {

		byte[] bytes = new byte[1024];
		List list = new ArrayList();
		list.add("aaa");
		list.add("bbb");
		//PeopleVO peopleVO = new PeopleVO("张三", 20, "男",bytes,list);
		//PeopleVO peopleVO1 = new PeopleVO("张三", 20, "男",bytes,list);
		PeopleVO peopleVO2 = new PeopleVO("张三", 20, "男",bytes,list);

		//log.error("清理指定属性之前的数据: " + JSON.toJSONString(peopleVO));
		//log.error("清理指定属性之前的数据: " + JSON.toJSONString(peopleVO1));
		log.error("清理指定属性之前的数据: " + JSON.toJSONString(peopleVO2));
		try {
			log.error("正在清理中...");
			String[] strs = {"age","sex","bytes","list"};
			List<String> strList = new ArrayList<>();
			strList.add("age");
			strList.add("sex");
			strList.add("bytes");
			strList.add("list");
			//reflexMethod.clearVoParameter(peopleVO1, "age", "sex","bytes","list");
			//reflexMethod.clearVoParameter(peopleVO1, strs);
			reflexMethod.clearVoParameter(peopleVO2, "age", "sex","bytes","list");
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//log.error("清理指定属性之后的数据: " + JSON.toJSONString(peopleVO));
		//log.error("清理指定属性之后的数据: " + JSON.toJSONString(peopleVO1));
		log.error("清理指定属性之后的数据: " + JSON.toJSONString(peopleVO2));

	}

}
