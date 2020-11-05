package com.ranyk.security.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ClassName:JwtLoginFilter
 * Description:登录认证触发过滤器
 *
 * @author ranyi
 * @date 2020-11-05 17:07
 * Version: V1.0
 */
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {


    /**
     * 登录拦截过滤器的构造方法,构造一个指定认证管理器的拦截器对象
     *
     * @param autManager 认证管理器对象
     */
    public JwtLoginFilter(AuthenticationManager autManager) {
        super.setAuthenticationManager(autManager);
    }

    /**
     * 覆写登录认证流程方法
     *
     * @param req   ServletRequest 对象
     * @param res   ServletResponse 对象
     * @param chain FilterChain 对象
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        //在此处可以做整个登录认证逻辑的覆写

        super.doFilter(req, res, chain);
    }


    /**
     * 对用户的登录认证请求进行处理,认证成功则会返回对已认证用户的认证令牌
     *
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     * @return 1.返回已认证的用户的认证令牌
     * 2. 返回null,表示认证还在进行中
     * 3. 认证失败,抛出异常
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //因存在对应的请求格式问题,
        // 在 UsernamePasswordAuthenticationFilter.java 过滤器中是使用的 request.getParameter(request)进行获取的用户名和密码,
        // 故无法获取到以 application/json 格式且以 post 方式进行请求传输的参数,故需要先对参数的获取

        //获得请求的 json 字符串
        String body = getBody(request);
        //将 json 字符串转换成 json 对象
        JSONObject jsonObject = JSON.parseObject(body);
        //从 json 对象中获得 用户名 和 密码 参数
        String userName = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        //判断用户名和密码是否为空

        if (StringUtils.isEmpty(userName)) {
            userName = "";
        }

        if (StringUtils.isEmpty(password)) {
            password = "";
        }

        //用户名去空格
        userName = userName.trim();

        //获得此用户的 token 令牌,默认是通过 UsernamePasswordAuthenticationToken 获得,此处使用 JWT 方式获得
        JwtAuthenticationToken authRequest = new JwtAuthenticationToken(userName,password);

        //将此 token 令牌设置此用户详情属性,可理解为将用户详细信息放到 token 中
        setDetails(request,authRequest);

        //返回验证当前的 token 令牌的结果,确定账户是否有异常状态,即禁用、锁定、身份验证被拒绝
        return this.getAuthenticationManager().authenticate(authRequest);
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //存储认证信息进 context 中
        SecurityContextHolder.getContext().setAuthentication(authResult);

        //记住我服务
        getRememberMeServices().loginSuccess(request,response,authResult);

        super.successfulAuthentication(request, response, chain, authResult);
    }

    /**
     * 解析登录的 http 请求中的用户名和密码参数
     *
     * @param request HttpServletRequest 对象
     * @return 返回解析出的整个请求对象的json字符串
     */
    private String getBody(HttpServletRequest request) {

        //从请求中获得输入流对象,从输入流对象转成输出流,再从输出流对象中获得流字符对象并返回

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.info(e.getMessage());
                }
            }

            if (null != reader) {
                try {
                    reader.close();
                }catch (IOException e){
                    logger.info(e.getMessage());
                }
            }
        }

        return sb.toString();


    }
}
