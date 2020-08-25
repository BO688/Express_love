package com.bo.express_love.repository;

import com.bo.express_love.entity.User_demo;

import java.util.List;
import java.util.Map;

public interface UserDemoMapper {
    /**
     * 根据参数查询
     * @param map
     * @return void
     */
    List<User_demo> ListDemo(Map map);
    List<User_demo> ListDemo_2(Map map);
    /**
     * 根据参数查询
     * @param
     * @return void
     */
    List<User_demo> ListDemo_2();
    List<User_demo> ListDemo();

    void addDemo(User_demo user_demo);

}
