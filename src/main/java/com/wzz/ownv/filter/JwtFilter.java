package com.wzz.ownv.filter;/**
 * Created by Enzo Cotter on 2020/9/8.
 */

import com.wzz.ownv.common.constant.Constant;
import com.wzz.ownv.common.result.CheckResult;
import com.wzz.ownv.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: ownv
 * @description: token拦截器
 * @author: wzz
 * @create: 2020-09-08 14:55
 */
@Slf4j
public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        //客户端将token封装在请求头中，格式为（Bearer后加空格）：Authorization：Bearer +token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write("Missing or invalid Authorization header.".getBytes());
            outputStream.flush();
            outputStream.close();
        }

        //去除Bearer 后部分
        String token = authHeader.substring(7);
        logger.debug("token的值为：\n"+token);

        try {
            //解密token，拿到里面的对象claims
            CheckResult validateJWT = JwtUtils.validateJWT(token);
            Claims claims=null;
            // 判断是否是正确的Token
            if(validateJWT.isSuccess()){
                logger.debug("解密Token成功");
                claims=validateJWT.getClaims();
            }
            else {
                if (validateJWT.getErrCode()== Constant.JWT_ERRCODE_EXPIRE) {
                    response.setStatus(401);
                    logger.debug("解密Token失败，错误代码：" + validateJWT.getErrCode());
                    ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.write("TOKEN  EXPIRE.".getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
            //将对象传递给下一个请求
            request.setAttribute("userId", claims.getSubject());
        }
        catch (SignatureException e) {
            logger.debug("解密失败，不正确的Token");
            throw new ServletException("Invalid token.");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
