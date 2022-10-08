package com.yruns.controller;

import com.yruns.common.R;
import com.yruns.pojo.User;
import com.yruns.service.UserService;
import com.yruns.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * UserController
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();
        log.info(phone);

        if (phone != null) {
            // 随机生成4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            // 发送短信
//            SMSUtils.sendMessage("瑞吉外卖", "", phone, code);
            log.info("code: " + code);
            // 保存短信到Session
            session.setAttribute(phone, code);
            return R.success("手机验证码短信发送成功");
        }
        return R.success("手机验证码短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        String phone = (String) map.get("phone");
        String code = (String) map.get("code");

        // 取出Session中的code
        String goldCode = (String) session.getAttribute(phone);

        if (code != null && code.equals(goldCode)) {
            User user = userService.getOne(phone);
            if (user == null) {
                // 不存在，自动注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
