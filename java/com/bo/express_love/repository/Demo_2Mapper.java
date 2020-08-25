package com.bo.express_love.repository;

import com.bo.express_love.entity.Demo_2;

import java.util.List;
import java.util.Map;

public interface Demo_2Mapper {
    List<Demo_2> SelectDemo_21(String email);
    List<Demo_2> SelectDemo_21(Map map);
    List<Demo_2> SelectDemo_21();
    void AddDemo_21(Demo_2 demo_2);
    Map SelectDemo_22(String email);
    Map SelectDemo_22(Map map);
    Map SelectDemo_22();
    void AddDemo_22(Map map);
}
