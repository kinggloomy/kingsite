package com.kinggloomy.site.core;

import com.kinggloomy.site.model.UserModel;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/5/16.
 */
public class Utils {
    private static Utils instance = null;
    public static Utils getInstance(){
        if(instance==null){
            instance = new Utils();
        }
        return  instance;
    }

    /**
     * 获取访问者IP
     *
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) throws Exception{
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
// 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
    public boolean validateLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        return user!=null&&user.getUserCode()!=null;
    }
    public boolean validateSelf(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        return user!=null&&user.getUserCode()!=null;
    }
}
