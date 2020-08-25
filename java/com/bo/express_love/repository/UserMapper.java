package com.bo.express_love.repository;

import com.bo.express_love.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * 根据参数查询
     * @param map
     * @return
     */
    List<User> selectUser(Map map);
    /**
     * 根据参数查询
     * @param
     * @return
     */
    List<User> selectUser();
    /**
     * 根据参数查询
     * @param email
     * @return
     */
    List<User> selectUserByEmail(String email);

    void insertUser(User user);

    void updateUser(Map map);

}
