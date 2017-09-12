package cn.xkenmon.blog.DAO.Factory;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MySqlSessionFactory {

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSession(){
        if (sqlSessionFactory == null)
            sqlSessionFactory = getFactory();
        return sqlSessionFactory.openSession();
    }

private static SqlSessionFactory getFactory() {
        Reader in = null;
        try {
            in = Resources.getResourceAsReader("mybatis-cfg.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(in);
    }
}
