package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    User findByUserName(String username);

    void save(User user);

    User findByCode(String code);

    void updateStatus(User user);
}
