package cn.xkenmon.blog.DAO.Factory;

import cn.xkenmon.blog.DAO.impl.ArticlesDAOproxy;

public class DAOFactory {
    public static ArticlesDAOproxy getInstance() {
        return new ArticlesDAOproxy();
    }
}
