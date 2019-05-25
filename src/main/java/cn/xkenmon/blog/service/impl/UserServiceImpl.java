package cn.xkenmon.blog.service.impl;

import cn.xkenmon.blog.dao.UserMapper;
import cn.xkenmon.blog.service.IUserService;
import cn.xkenmon.blog.vo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    final private
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void createUser(User user) {
        userMapper.createUser(user);
    }

    @Override
    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    @Override
    public int changeLogStatById(int id, int stat) {
        return userMapper.changeLogStatById(id, stat);
    }

    @Override
    public int changeAvatarById(int id, String avt) {
        return userMapper.changeAvatarById(id, avt);
    }
}
