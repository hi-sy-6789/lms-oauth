package com.pranav.oauthresourceserver.util;

import javax.servlet.http.HttpServletRequest;

public class SpringSecurityClientUtil {

    public static String getApplicationUrl(HttpServletRequest httpServletRequest) {
        return "http://"
                +httpServletRequest.getServerName()
                +":"
                +httpServletRequest.getServerPort()
                +httpServletRequest.getContextPath()
                +httpServletRequest.getRequestURI();
    }
    public static String getApplicationUrlWithoutRequestURI(HttpServletRequest httpServletRequest) {
        return "http://"+
        httpServletRequest.getHeader("Host") + httpServletRequest.getContextPath();
    }

}
