package cn.zhh.sso.controller;

import cn.zhh.sso.Result;
import cn.zhh.sso.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.istack.internal.NotNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/")
@CrossOrigin
public class LoginController {

    @GetMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response,
                        @NotNull String userName, @NotNull String password) throws IOException {

        Result result = new Result();
        User user = new User();
        if (!user.userMap.containsKey(userName)){
            result.code = 10004;
            result.data = null;
            result.message = "找不到该用户";
            result.success = false;
            return result;
        }

        if (!user.userMap.get(userName).equals(password)){
            result.code = 10005;
            result.data = null;
            result.message = "密码错误";
            result.success = false;
            return result;
        }

        String token = JWT.create().withAudience(userName).sign(Algorithm.HMAC256(password));
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
        String originUrl = (String) request.getSession().getAttribute("originUrl");
//        response.sendRedirect(originUrl);
        result.code = 10000;
        result.data = originUrl;
        result.message = "请求成功";
        result.success = true;
        return result;
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        Result result = new Result();
        result.code = 10003;
        result.data = null;
        result.message = "清除token";
        result.success = true;
        return result;
    }
}
