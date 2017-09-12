package cn.xkenmon.blog.DAO.impl;

import cn.xkenmon.blog.DAO.Factory.MySqlSessionFactory;
import cn.xkenmon.blog.DAO.UserDAO;
import cn.xkenmon.blog.vo.User;
import org.apache.ibatis.session.SqlSession;

public class UserDAOImpl implements UserDAO {
    private SqlSession session;

    UserDAOImpl() {
        this.session = MySqlSessionFactory.getSession();
    }

    @Override
    public User findUserById(int id) {
        return session.selectOne("UserDAO.findUserById", id);
    }

    @Override
    public User findUserByName(String name) {
        return session.selectOne("UserDAO.findUserByName",name);
    }

    @Override
    public User findUserByEmail(String email) {
        return session.selectOne("UserDAO.findUserByEmail",email);
    }

    @Override
    public void changeLogStatById(int id, int stat) {
        session.update("UserDAO.changeLogStatById", new int[]{id, stat});
        session.commit();
    }

    @Override
    public void createUser(User user) {
        session.insert("UserDAO.createUser",user);
        session.commit();
    }

    @Override
    public void close() {
        session.close();
    }
}
