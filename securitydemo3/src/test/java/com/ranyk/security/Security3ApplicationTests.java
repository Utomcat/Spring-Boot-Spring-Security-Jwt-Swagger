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

    /**
     * 进行属性置空测试,使用 ... 方式配置多个清空属性值的情况,传入指定的字符参数方式
     */
    @Test
    void testMethod0() {
		useMethod("parameter");
    }

	/**
	 * 进行属性置空测试,使用 ... 方式配置多个清空属性值的清空,传入字符数组的方式
	 */
	@Test
    void testMethod1() {
		useMethod("array");
    }

	/**
	 * 进行属性置空测试,使用 ... 方式配置多个清空属性值的清空,传入字符 List 集合
	 */
	@Test
	void testMethod2() {
		useMethod("list");
	}

	/**
	 * 测试空字符串的长度,如下的结果为 0
	 */
	@Test
	void testMethod3() {
    	log.error("空字符串的长度 ==> " + ("".length()));
	}

	/**
	 * 调用方法
	 *
	 * @param parameter 需要传入那种类型的参数
	 */
    private void useMethod(String parameter) {
        if ("parameter".equals(parameter)) {
			executeMethod(null,null,"age", "sex", "bytes", "list");
        } else if ("array".equals(parameter)) {
			executeMethod(getAttributesArray(),null,  "");
        } else if ("list".equals(parameter)) {
			executeMethod(null,getAttributesList(),"");
        } else {
            log.error("参数不合法!");
        }
    }

	/**
	 * 执行方法
	 *
	 * @param strArray 参数名数组
	 * @param list 参数名 List 集合
	 * @param attributes 属性字符串
	 */
    private void executeMethod(String[] strArray, List<String> list, String... attributes) {
        PeopleVO peopleVO = generateVO();
        log.error("清理指定属性之前的数据: " + JSON.toJSONString(peopleVO));
        try {
            log.error("正在清理中...");
            reflexMethod.clearVoParameter(peopleVO, strArray != null ? strArray : (list != null ? list.toArray(new String[0]) : attributes));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        log.error("清理指定属性之后的数据: " + JSON.toJSONString(peopleVO));
    }

    /**
     * 生成创建一VO 对象
     *
     * @return VO 对象
     */
    private PeopleVO generateVO() {
        byte[] bytes = new byte[1024];
        List<String> list = new ArrayList<String>(10);
        list.add("aaa");
        list.add("bbb");
        return new PeopleVO("张三", 20, "男", bytes, list);
    }

    /**
     * 得到属性名数组
     *
     * @return 返回VO对象的属性名数组
     */
    private String[] getAttributesArray() {
        return new String[]{"age", "sex", "bytes", "list"};
    }

    /**
     * 得到属性名的 List 集合
     *
     * @return 返回 VO 对象的属性名 List 集合
     */
    private List<String> getAttributesList() {
        List<String> strList = new ArrayList<String>(10);
        strList.add("age");
        strList.add("sex");
        strList.add("bytes");
        strList.add("list");
        return strList;
    }

}
