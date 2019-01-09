package com.newcore.bmp.authority.util;

import org.apache.shiro.session.Session;

import com.alibaba.fastjson.JSON;
import com.newcore.bmp.models.authority.constants.Constants;
import com.newcore.bmp.models.authority.models.SessionModel;

public class SessionUtils {

    private SessionUtils() {

    }

    @Deprecated
    public static SessionModel getSessionModel(String sessionModelJson) {

        return JSON.parseObject(sessionModelJson, SessionModel.class);
    }

    public static SessionModel getSessionModel(Session session) {

        String sessionModelJson = (String) session.getAttribute(Constants.SESSION_MODEL_KEY);

        if (null == sessionModelJson || "".equals(sessionModelJson)) {
            return null;
        }

        return JSON.parseObject(sessionModelJson, SessionModel.class);
    }
}
