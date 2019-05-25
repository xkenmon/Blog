package cn.xkenmon.blog.service;

import cn.xkenmon.blog.vo.User;

public interface IUserService {
    void createUser(User user);

    User findUserById(int id);

    User findUserByName(String name);

    User findUserByEmail(String email);

//    public String findAvatarById()

    int changeLogStatById(int id, int stat);

    int changeAvatarById(int id, String avt);
}
