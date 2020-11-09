package com.ranyk.security.utils;

import com.ranyk.security.security.JwtAuthenticationToken;
import com.ranyk.security.service.impl.GrantedAuthorityImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * ClassName:JwtTokenUtils<br/>
 * Description: Jwt Token 工具类
 *
 * @author ranyi
 * @date 2020-11-06 9:57
 * Version: V1.0
 */
@Slf4j
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * JWT Subject 的参数名
     */
    private static final String USERNAME = Claims.SUBJECT;

    private static final String CREATED = "created";

    private static final String AUTHORITIES = "authorities";
    /**
     * 密钥/加密盐
     */
    private static final String SECRET = "abcdefgh";
    /**
     * 有效时间 12 小时,对应的毫秒数
     */
    private static final long EXPIRE_TIME = 10 * 60 * 60 * 1000;
    /**
     * Authorization 参数
     */
    private static final String AUTHORIZATION = "Authorization";

    /**
     * 通过 Authentication 对象生成对应的 token 令牌
     *
     * @param authentication Authentication 对象
     * @return 生成的 token 令牌字符串
     */
    public static String generateToken(Authentication authentication) {
        /**
         * 创建存放参数的 Map 集合,用来存放本次认证的 用户名、创建时间、授予权限列表
         */
        Map<String, Object> claims = new HashMap<>(3);
        //向 Map 中添加对应参数值
        claims.put(USERNAME, SecurityUtils.getUserName(authentication));
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, authentication.getAuthorities());

        //调用根据对应认证信息生成 token 令牌的方法,返回生成的 token 值
        return generateToken(claims);
    }

    /**
     * 通过传入的认证信息,生成此次认证成功后的 token 令牌
     *
     * @param claims 存放认证信息的 Map 集合
     * @return 返回生成的 token 值
     */
    private static String generateToken(Map<String, Object> claims) {

        //获取当前 token 的有效时间
        Date exporationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        //生成 token
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(exporationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

    }

    /**
     * 从 HTTPServletRequest 对象中获取 token 然后获取 Authentication 对象
     *
     * @param request HTTPServletRequest 对象
     * @return 返回得到的 Authentication 对象
     */
    public static Authentication getAuthenticationFromToken(HttpServletRequest request) {

        //声明返回的认证对象,同时初始化
        Authentication authentication = null;

        //从 request 中获取 token 令牌
        String token = JwtTokenUtils.getToken(request);

        //判断获得的 token 令牌是否为空
        if (!StringUtils.isEmpty(token)) {

            //获取 SecurityContextHolder 中的 Context 中的 Authentication 对象,判断是否存在(确认是否已经认证成功)
            if (null == SecurityUtils.getAuthentication()) {
                //还未认证,则先认证,再获取 Authentication 对象

                //从 token 中获取 Claims 对象
                Claims claims = getClaimsFromToken(token);

                //判断 claims 是否为空,为空则直接返回
                if (null == claims) {
                    return null;
                }

                //从 Claims 对象中获取 用户名
                String userName = claims.getSubject();

                //判断用户名是否为空,为空则直接返回
                if (null == userName) {
                    return null;
                }

                //验证 token 是否有效
                if (isTokenExpired(token)) {
                    return null;
                }

                //从 Claims 对象中获取权限列表
                Object authors = claims.get(AUTHORITIES);
                List<GrantedAuthority> authorities = new ArrayList<>();
                if (authors instanceof List) {
                    for (Object author : (List) authors) {
                        authorities.add(new GrantedAuthorityImpl(((String) ((Map) author).get("authority"))));
                    }
                }

                //通过 Jwt 进行认证 token 令牌,同时生成 Authentication 对象
                authentication = new JwtAuthenticationToken(userName,null,authorities,token);

            } else {
                //已经认证,则校验认证对象中的token是否有效,同时判断登录的用户名和token中存放的用户名是否一致
                if (validateToken(token,SecurityUtils.getUserName())) {

                    //token有效且是同一用户则获取该 认证对象
                    authentication = SecurityUtils.getAuthentication();
                }
            }

        }

        //返回获得的 Authentication 对象,当token认证失效或认证失败,此处返回的是 null
        return authentication;


    }

    /**
     * 验证 token 令牌
     * @param token token 令牌字符串
     * @param userName 用户名从 Security Context 中的认证对象中获取的用户名
     * @return 返回验证结果
     */
    private static boolean validateToken(String token, String userName) {
        //从token令牌中获取用户名
        String name = getUserNameFromToken(token);
        //判断从token中获取的用户名和 Security Context 中存放的用户名一致;同时token令牌还未失效
        //思考此处需要进行空值判断不????
        return (name.equals(userName) && !isTokenExpired(token));
    }

    /**
     * 从 token 中获得用户名
     * @param token token令牌字符串
     * @return 返回获得的用户名
     */
    private static String getUserNameFromToken(String token) {
        String userName;
        try {
            //从token中获得Claims
            Claims claims = getClaimsFromToken(token);
            //从 Claims 中获得 userName
            userName = claims.getSubject();
        }catch (Exception e){
            log.info("从Token中获取用户名出错");
            userName = null;
        }
        //返回结果
        return userName;
    }

    /**
     * 验证 token 是否过期/超时
     * @param token token 令牌字符串
     * @return 返回验证结果: 过期返回false;反之返回true;
     */
    private static boolean isTokenExpired(String token) {

        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        }catch (Exception e){
            log.info("转换Claims异常");
            return false;
        }

    }


    /**
     * 从token中获取权限列表
     * @param token token令牌字符串
     * @return 返回获得权限 Claims 对象
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }catch (Exception e){
            log.info("解析token获取Claims发生异常");
            claims = null;
        }
        return claims;
    }

    /**
     * 从请求中获取 token
     *
     * @param request HttpServletRequest 对象
     * @return 返回获得的 token 令牌的字符串
     */
    private static String getToken(HttpServletRequest request) {

        //
        String token = request.getHeader(AUTHORIZATION);

        String tokenHead = "Bearer ";

        if (StringUtils.isEmpty(token)) {
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length());
        }

        if (StringUtils.isEmpty(token)) {
            token = null;
        }

        return token;

    }
}
