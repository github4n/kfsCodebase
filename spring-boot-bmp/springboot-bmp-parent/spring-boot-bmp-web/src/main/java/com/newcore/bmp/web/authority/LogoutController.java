package com.newcore.bmp.web.authority;

import com.halo.core.web.annotation.ResponseMessage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 账户登出服务.
 *
 * @author maeagle
 * @date 2016/3/30 8:40
 */
@Controller
@RequestMapping("/exp/logout")
public class LogoutController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseMessage
    public String authCheckMethod() {
        Subject currentSubject = SecurityUtils.getSubject();
        String sessionId = "";
        Session session = currentSubject.getSession(false);
        if (session != null)
            sessionId = session.getId().toString();
        currentSubject.logout();
        return sessionId;
    }
}
