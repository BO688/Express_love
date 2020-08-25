package com.bo.express_love.Test;

import com.bo.express_love.repository.Demo_2Mapper;
import com.bo.express_love.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestService {
    @Autowired
    Demo_2Mapper demo_2Mapper;
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/AddDemo_2")
    public void AddDemo_2(){
        HashMap map=new HashMap();
        map.put("email","6@qq.com");
        map.put("you","6@qq.com");
        map.put("me","6@qq.com");
        map.put("a1","1");

        demo_2Mapper.AddDemo_22(map);
    }
    @RequestMapping("/GetDemo_2")
    public void GetDemo_2(){

       Map LD= demo_2Mapper.SelectDemo_22();
        System.out.println(LD.get("email"));
        System.out.println(LD.get("p1"));
        System.out.println(LD.get("p2"));
    }
    @RequestMapping("TestMapping")
    public void selectUserDemo(HttpServletRequest request){
//        User user=new User();
//        user.setEmail("1");
//        user.setName("1");
//        user.setPassword("1");
//        userMapper.insertUser(user);
        HashMap map=new HashMap();
        map.put("Old_email","1");
        map.put("New_email","2");
        map.put("name","2");
        userMapper.updateUser(map);
    }

}
