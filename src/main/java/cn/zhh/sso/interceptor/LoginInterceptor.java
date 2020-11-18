package cn.zhh.sso.interceptor;

import cn.zhh.sso.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        String token = "";

        if (cookies == null){
            request.getSession().setAttribute("originUrl", request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        }

        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                token = cookie.getValue();
            }
        }

        if (token == null){
            request.getSession().setAttribute("originUrl", request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        }

        try{
            String userName = JWT.decode(token).getAudience().get(0);
            User user = new User();
            String password = user.userMap.get(userName);
            // 验证 token
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(password)).build();
            jwtVerifier.verify(token);

        } catch (JWTVerificationException e) {
            request.getSession().setAttribute("originUrl", request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/index.html");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
