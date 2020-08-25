package com.bo.express_love.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bo.express_love.Util.MailUtil;
import com.bo.express_love.entity.User;
import com.bo.express_love.repository.UserDemoMapper;
import com.bo.express_love.repository.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

import static com.bo.express_love.Util.CheckUtil.CheckStringExist;
import static com.bo.express_love.Util.MailUtil.getMailCode;

@RestController
public class Service_DBA {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDemoMapper userDemoMapper;
    @RequestMapping(value = "checkMail",method = RequestMethod.POST)
    public  JSONObject CheckEmailExistWithWeb(@RequestBody String string){
        try {
            String mail=(String) JSON.parseObject(string).get("mail");
            if(!CheckStringExist(new String[]{mail})){
                JSONObject.parseObject("{'status':' incorrect email!'}");
            }
            if(CheckEmailExist(mail)){
                return JSONObject.parseObject("{'status':'this email already exists!'}");
            }
        }catch (NullPointerException e){
            return JSONObject.parseObject("{'status':'ERROR'}");
        }
        return JSONObject.parseObject("{'status':'OK'}");
    }
    private  boolean CheckEmailExist(String email){
        if(userMapper.selectUserByEmail(email).size()>0){
            return true;
        } return false;
    }
    public void DataBaseAddUser(String name,String email, String password){
        User user=new User();
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        userMapper.insertUser(user);
    }


    @RequestMapping(value = "ClientLogin" ,method = RequestMethod.POST)
    public JSONObject LoginUser(HttpServletRequest request){
        String user_mail=request.getParameter("email");
        String user_psw=request.getParameter("password");
        if(!CheckStringExist(new String[]{user_mail,user_psw})){
            return JSONObject.parseObject("{'status':'user_mail or user_psw is null'}");
        }
        List<User> Lu =userMapper.selectUserByEmail(user_mail);
        for (User u:Lu) {
            System.out.println(u.getName()+','+u.getEmail()+','+u.getPassword());
            if(user_mail.equals(u.getEmail())&&user_psw.equals(u.getPassword())){
                request.getSession().setAttribute("login",u.getEmail());
                request.getSession().setAttribute("Username",u.getName());
                request.getSession().setAttribute("Password",u.getPassword());
                UsernamePasswordToken token=new UsernamePasswordToken(user_mail,user_psw);
                Subject subject=SecurityUtils.getSubject();
                subject.login(token);
                return JSONObject.parseObject("{'status':'OK'}");
            }
        }
        return JSONObject.parseObject("{'status':'not found'}");
    }

    @RequestMapping(value = "CancelLogin",method = RequestMethod.POST)
    public void CancleLogin(HttpServletRequest request){
        Enumeration enumeration= request.getSession().getAttributeNames();
        while (enumeration.hasMoreElements()){
            request.getSession().removeAttribute(enumeration.nextElement().toString());
        }
        Subject subject= SecurityUtils.getSubject();
        subject.logout();

    }


    @RequestMapping(value = "RegisterUser_setCode" , method =  RequestMethod.POST)
    public JSONObject RegisterUser_setCode(@RequestBody String string,HttpServletRequest request){
        try {
            String mail=(String) JSON.parseObject(string).get("mail");
            if(CheckEmailExist(mail)){
                return JSONObject.parseObject("{'status':'email already exists'}");
            }
            String name=(String) JSON.parseObject(string).get("name");
            if(!CheckStringExist(new String[]{name,mail})){
                return JSONObject.parseObject("{'status':'mail or name is null'}");
            }
            String result = getMailCode(6);
            request.getSession().setAttribute("code",result);
            //        配置信息
            //配置额外属性/*邮箱操作*/
            String mailarray[]={mail};
            String namearray[]={name};
            boolean check= MailUtil.sendEmail(mailarray,namearray,"亲爱的"+name+",欢迎你注册本站！<br/>本次验证码为：<b>"+result+"</b>","注册验证码",false);
//创建邮件
            if(!check){
                return JSONObject.parseObject("{'status':'Send Fail'}");
            }
        }catch (Exception e){
            e.printStackTrace();
            return JSONObject.parseObject("{'status':'ERROR'}");
        }
        return JSONObject.parseObject("{'status':'OK'}");

    }

    @RequestMapping(value = "RegisterUser_getCode",method = RequestMethod.POST)
    public JSONObject RegisterUser_getCode(@RequestBody String string, HttpServletRequest request){
        try {
            String code=request.getSession().getAttribute("code").toString();
            if(JSON.parseObject(string).get("code").equals(code)){

                String name=(String) JSON.parseObject(string).get("name");
                String mail=(String) JSON.parseObject(string).get("mail");
                String password=(String) JSON.parseObject(string).get("password");
                if(!CheckStringExist(new String[]{name,mail,password})){
                    return JSONObject.parseObject("{'status':'params are not enough'}");
                }
                if(CheckEmailExist(mail)){
                    return JSONObject.parseObject("{'status':'this email already exists'}");
                }
                DataBaseAddUser(name,mail,password);
                return JSONObject.parseObject("{'status':'OK'}");
            }
        }catch (Exception npe){
            npe.printStackTrace();
            return JSONObject.parseObject("{'status':'code is null'}");
        }
        return JSONObject.parseObject("{'status':'ERROR'}");



    }


}
