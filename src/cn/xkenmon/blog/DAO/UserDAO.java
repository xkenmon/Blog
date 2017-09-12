package cn.xkenmon.blog.DAO;

import cn.xkenmon.blog.vo.User;

public interface UserDAO {
    public User findUserById(int id);

    public User findUserByName(String name);

    public User findUserByEmail(String email);

//    public String findAvatarById()

    public void changeLogStatById(int id,int stat);

    public void createUser(User user);

    public void close();
}
