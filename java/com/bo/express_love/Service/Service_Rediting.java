package com.bo.express_love.Service;

import com.alibaba.fastjson.JSONObject;
import com.bo.express_love.Util.CheckUtil;
import com.bo.express_love.Util.MailUtil;
import com.bo.express_love.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.bo.express_love.Util.MailUtil.getMailCode;
import static com.bo.express_love.Util.MailUtil.validEmail;

@RestController
public class Service_Rediting {
    @Autowired
    private UserMapper userMapper;
    private  boolean CheckEmailExist(String email){
        if(userMapper.selectUserByEmail(email).size()>0){
            return true;
        } return false;
    }
    @RequestMapping("/Client/Reediting/name")
    public JSONObject ReeditingName(HttpServletRequest request){
        try {
            String name=request.getParameter("name");
            if(!CheckUtil.CheckStringExist(name)){
                System.err.println("异常修改用户名");
                return JSONObject.parseObject("{'status':'Param ERROR'}");
            }
            String email=request.getSession().getAttribute("login").toString();
            HashMap<String ,String> map=new HashMap<>();
            map.put("name",name);
            map.put("Old_email",email);
            userMapper.updateUser(map);
            request.getSession().setAttribute("Username",name);
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("异常修改用户名");
            return JSONObject.parseObject("{'status':'ERROR'}");
        }
        return JSONObject.parseObject("{'status':'OK'}");
    }

    @RequestMapping("/Client/Reediting/password")
    public JSONObject ReeditingPassword(HttpServletRequest request){
        try {
            String password=request.getParameter("password");
            if(!CheckUtil.CheckStringExist(password)){
                System.err.println("异常修改用户密码");
                return JSONObject.parseObject("{'status':'Param ERROR'}");
            }
            String email=request.getSession().getAttribute("login").toString();
            HashMap<String ,String> map=new HashMap<>();
            map.put("password",password);
            map.put("Old_email",email);
            userMapper.updateUser(map);
        }catch (Exception e){
//            e.printStackTrace();
            System.err.println("异常修改用户密码");
            return JSONObject.parseObject("{'status':'ERROR'}");
        }
        return JSONObject.parseObject("{'status':'OK'}");
    }

    @RequestMapping("/Client/Reediting/email_getCode")
    public JSONObject ReeditingEmailGet(HttpServletRequest request){
        try {
            String CheckCode=request.getSession().getAttribute("Recode").toString();
            String New_email=request.getParameter("email");
            String code=request.getParameter("code");
            if(!CheckUtil.CheckStringExist(New_email,code)||!validEmail(New_email)||CheckEmailExist(New_email)||!(CheckCode.equals(code))){
                System.err.println("异常修改用户邮箱");
                return JSONObject.parseObject("{'status':'Param ERROR'}");
            }
            String Old_email=request.getSession().getAttribute("login").toString();
            HashMap<String ,String> map=new HashMap<>();
            map.put("New_email",New_email);
            map.put("Old_email",Old_email);
            userMapper.updateUser(map);
            request.getSession().setAttribute("login",New_email);
        }catch (Exception e){
//            e.printStackTrace();
            System.err.println("异常修改用户邮箱");
            return JSONObject.parseObject("{'status':'ERROR'}");
        }
        return JSONObject.parseObject("{'status':'OK'}");

    }
    @RequestMapping("/Client/Reediting/email_setCode")
    public JSONObject ReeditingEmailSet(HttpServletRequest request){
        try {//获得新的email判断是否存在，不存在则继续发送验证码
            String email=request.getParameter("email");
            String name=request.getSession().getAttribute("Username").toString();
            if(!CheckUtil.CheckStringExist(email)||CheckEmailExist(email)||!validEmail(email)){
                System.err.println("异常修改用户邮箱"+email);
                return JSONObject.parseObject("{'status':'Param ERROR'}");
            }
            String result = getMailCode(6);
            System.out.println("验证码:"+result);
            request.getSession().setAttribute("Recode",result);
            //配置额外属性/*邮箱操作*/
            String mailarray[]={email};
            String namearray[]={name};
            boolean check= MailUtil.sendEmail(mailarray,namearray,"亲爱的"+name+",你正在修改本站的密码！<br/>本次验证码为：<b>"+result+"</b>","注册验证码",false);
//创建邮件
            if(!check){
                return JSONObject.parseObject("{'status':'Send Fail'}");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("异常修改用户邮箱");
            return JSONObject.parseObject("{'status':'ERROR'}");
        }
        return JSONObject.parseObject("{'status':'OK'}");
    }



}
