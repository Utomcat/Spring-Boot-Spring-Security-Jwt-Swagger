package com.ranyk.security.method.operate;

import com.alibaba.fastjson.JSONObject;
import com.ranyk.security.method.vo.PeopleVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:ReflexMethod<br/>
 * Description:反射方法
 *
 * @author ranyi
 * @date 2020-12-07 10:20
 * Version: V1.0
 */
@Log4j2
@Component
public class ReflexMethod {

    /**
     * 设置指定对象的指定属性值为空
     *
     * @param vo 操作的对象
     * @param attributes 需要操作的属性名(对应着类声明的字段名)
     * @throws NoSuchFieldException 未找到指定字段异常
     * @throws IllegalAccessException 非法访问异常
     */
    public void clearVoParameter(PeopleVO vo, String... attributes) throws NoSuchFieldException, IllegalAccessException {
        // 1. 判断是否有需要处理的属性名
        if (objectIsEmpty(attributes)) {
            log.error("不存在需要清理的属性");
            return;
        }
        // 2. 判断需要处理的对象是否存在
        if (objectIsEmpty(vo)) {
            log.error("需要清理的对象为空,无需进行清理");
            return;
        }
        // 3. 获取指定对象的class对象
        Class<? extends PeopleVO> voClass = vo.getClass();
        // 4. 利用反射获取对应类的所有字段属性(含私有属性)
        Field[] fields = voClass.getDeclaredFields();
        // 5. 排除当前对象的class对象不拥有的属性
        Set<String> realAllAttribute = getRealAllAttribute(fields, attributes);
        // 6. 判断对应的属性名数组是否存在值
        if (objectIsEmpty(realAllAttribute)) {
            log.error("没有需要进行处理的字段属性!");
            return;
        }
        // 7. 设置操作对象的指定属性的值为空
        batchSetVoAttributesForNull(vo, voClass, realAllAttribute.toArray(new String[realAllAttribute.size()]));
    }

    /**
     * 设置指定对象的指定属性为空
     *
     * @param obj        需要操作的实例对象
     * @param voClass    需要操作的实例对象的 Class 对象
     * @param attributes 需要操作的属性字段名
     * @throws NoSuchFieldException   未找到字段异常
     * @throws IllegalAccessException 非法访问异常
     */
    public void batchSetVoAttributesForNull(Object obj, Class<? extends Object> voClass, String... attributes) throws NoSuchFieldException, IllegalAccessException {
        for (String attribute : attributes) {
            setVoAttributeValue(voClass, obj, attribute, null);
        }
    }


    /**
     * 利用反射的 Field 对象,设置指定的实例对象的指定属性为指定值
     *
     * @param voClass        对应实例对象的 Class 对象
     * @param obj            实例对象
     * @param attributeName  需要设置的属性名
     * @param attributeValue 设置的属性值
     * @throws NoSuchFieldException   未找到字段异常
     * @throws IllegalAccessException 非法访问异常
     */
    public void setVoAttributeValue(Class<? extends Object> voClass, Object obj, String attributeName, Object attributeValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = voClass.getDeclaredField(attributeName);
        field.setAccessible(true);
        field.set(obj, attributeValue);
    }


    /**
     * 获取属性名列表,从给出的字段对象(Field)集合 和 字段名 中 获取
     *
     * @param fields     给出的对象的字段对象集合(Field)
     * @param attributes 给出的字段名
     * @return 需要清空的字段列表, 排除给出的字段名中不存在于对象实例中的字段, 即返回的字段是在对应的实体对象(类)中存在的字段名
     */
    public Set<String> getRealAllAttribute(Field[] fields, String... attributes) {
        // 0. 声明并创建返回结果集合,用来保存有关的字段属性名
        Set<String> resultSet = new HashSet<>(16);
        // 1. 判断字段属性集合是否存在
        if (objectIsEmpty(fields)) {
            log.error("需替换的对象的属性为空!");
            return null;
        }
        // 2. 判断给出的字段名是否存在
        if (objectIsEmpty(attributes)) {
            log.error("处理的字段属性为空!");
            return null;
        }
        // 3. 循环字段属性集合 和 给出的字段名 进行匹配,匹配成功则将该字段名添加进返回结果中,否则不做处理
        for (Field field : fields) {
            // 3.1. 得到当前的字段属性名
            String fieldName = field.getName();
            // 3.2. 判断得到的属性名是否存在
            if (objectIsEmpty(fieldName)) {
                log.error("获取的字段名为空,当前字段对象为 ==> " + JSONObject.toJSONString(field));
                continue;
            }
            //3.3. 循环给出的字段名,匹配给出的字段名是否是处理对象中的字段名
            for (String attribute : attributes) {
                //3.3.1. 判断属性名是否存在于操作对象中
                if (fieldName.equals(attribute)) {
                    // 3.3.1.1. 添加进结果集,若添加失败则输出日志
                    if (!resultSet.add(fieldName)) {
                        log.error("向最终的修改字段集合中添加修改字段名时出错!");
                    }
                }
            }
        }
        // 4. 返回结果集
        return resultSet;
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 需要进行判断的对象参数
     * @return 当对象为空时返回 true;反之返回 false;
     */
    public Boolean objectIsEmpty(Object obj) {
        // 判断对象是否为 null
        if (null == obj) {
            return true;
        }
        // 当对象是字符串时,判断是否为空
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        // 当对象是 Collection 集合时,判断是否为空
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        // 当对象是 Map 集合时,判断是否为空
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        // 当对象是数组时,判断是否为空
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (Object o : object) {
                if (!objectIsEmpty(o)) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }


}
