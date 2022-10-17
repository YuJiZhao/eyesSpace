package com.eyes.eyesspace.common.service.utils;

import com.eyes.eyesspace.common.tool.context.ConfigContext;
import com.eyes.eyesspace.common.service.exception.CustomException;
import com.eyes.eyesspace.common.tool.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class IdentityUtils {
    private final static String ROLE_ADMIN = ConfigContext.getString("ROLE_ADMIN");
    private final static String ROLE_USER = ConfigContext.getString("ROLE_USER");
    private final static String ROLE_HEADER = ConfigContext.getString("ROLE_HEADER");
    private final static String ID_HEADER = ConfigContext.getString("ID_HEADER");

    /**
     * 获取用户身份，不必须有
     * @return
     * @throws CustomException
     */
    public static String getRequestRole() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = request.getHeader(ROLE_HEADER);
        if (Objects.nonNull(role) && !ROLE_ADMIN.equals(role) && !ROLE_USER.equals(role)) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }
        return role;
    }

    /**
     * 获取用户身份，必须有
     * @return
     * @throws CustomException
     */
    public static String getRequestRoleNeed() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String role = request.getHeader(ROLE_HEADER);
        if (!ROLE_ADMIN.equals(role) && !ROLE_USER.equals(role)) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }
        return role;
    }

    /**
     * 获取用户id，不必须有
     * @return
     * @throws CustomException
     */
    public static Integer getRequestId() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        return Objects.isNull(request.getHeader(ID_HEADER)) ? null : Integer.valueOf(request.getHeader(ID_HEADER));
    }

    /**
     * 获取用户id，必须有
     * @return
     * @throws CustomException
     */
    public static Integer getRequestIdNeed() throws CustomException {
        HttpServletRequest request = WebUtils.getRequest();
        String sid = request.getHeader(ID_HEADER);
        if (Objects.isNull(sid)) {
            throw new CustomException(ResultCode.GATEWAY_BYPASSED);
        }
        return Integer.valueOf(sid);
    }
}