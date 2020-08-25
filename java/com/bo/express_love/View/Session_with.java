package com.bo.express_love.View;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Session_with {
    @RequestMapping("Client/Session")
    public JSONObject ClientwithSession(HttpServletRequest request){
        JSONObject object=new JSONObject();
        try {
            String session_email=request.getSession().getAttribute("login").toString();
            String session_name=request.getSession().getAttribute("Username").toString();
            String session_psw=request.getSession().getAttribute("Password").toString();
            object.put("status","OK");
            object.put("name",session_name);
            object.put("email",session_email);
            object.put("password",session_psw);
        }catch (NullPointerException npe){
            System.err.println("未登录访问！");
            return JSONObject.parseObject("{'status':'Error'}");
        }

        return object;
    }
}
