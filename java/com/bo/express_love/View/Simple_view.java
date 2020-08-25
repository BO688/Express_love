package com.bo.express_love.View;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Simple_view {
    @RequestMapping("index")
    public String index_1(){
        return "main/index";
    }
    @RequestMapping("")
    public String index_2(){
        return "main/index";
    }
    @RequestMapping("Login")
    public String RegisterPage(){
        return "main/LoginOrRegisterPage";
    }
    @RequestMapping("test")
    public String test(){
        return "client/test";
    }
    @RequestMapping("demo_2")
    public String demo_2(){return "main/demo_2";}
    @RequestMapping("demo_1")
    public String demo_1(){return "main/demo_1";}
    @RequestMapping("/Client/demo_2")
    public String client_demo_2(){return "client/demo_2";}
    @RequestMapping("/Client/demo_1")
    public String client_demo_1(){return "client/demo_1";}
    @RequestMapping("/MakeDemo_1")
    public String MakeDeme_1(){return "main/MakeDemo_1";}
    @RequestMapping("/MakeDemo_2")
    public String MakeDeme_2(){return "main/MakeDemo_2";}

    @RequestMapping("Client/Reediting")
    public String Reediting(){return "main/Reediting";}



}
