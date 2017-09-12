package cn.xkenmon.blog.DAO.impl;

import cn.xkenmon.blog.DAO.UserDAO;
import cn.xkenmon.blog.vo.User;

public class UserDAOProxy implements UserDAO {
    private UserDAOImpl dao = new UserDAOImpl();

    @Override
    public User findUserById(int id) {
        return dao.findUserById(id);
    }

    @Override
    public User findUserByName(String name) {
        return dao.findUserByName(name);
    }

    @Override
    public User findUserByEmail(String email) {
        return dao.findUserByEmail(email);
    }

    @Override
    public void changeLogStatById(int id, int stat) {
        dao.changeLogStatById(id, stat);
    }

    @Override
    public void createUser(User user) {
        dao.createUser(user);
    }


    @Override
    public void close() {
        dao.close();
    }
}
