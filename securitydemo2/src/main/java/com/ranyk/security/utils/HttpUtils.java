package com.ranyk.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.ranyk.security.constant.ResponseContextType;
import com.ranyk.security.vo.HttpResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ClassName:HttpUtils<br/>
 * Description: HTTP 工具类
 *
 * @author ranyi
 * @date 2020-11-06 10:58
 * Version: V1.0
 */
public class HttpUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static void write(HttpServletResponse response, Object data) throws IOException {
        response.setContentType(ResponseContextType.APPLICATION_JSON.getContentType());
        HttpResult result = HttpResult.ok(data);
        String json = JSONObject.toJSONString(result);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();

    }


}
