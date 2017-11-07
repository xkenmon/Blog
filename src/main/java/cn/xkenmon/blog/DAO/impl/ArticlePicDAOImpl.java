package cn.xkenmon.blog.DAO.impl;

import cn.xkenmon.blog.DAO.Factory.MySqlSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ArticlePicDAOImpl {
    private SqlSession session;
    public ArticlePicDAOImpl(){
        session = MySqlSessionFactory.getSession();
    }

    public List<String> findPicByArticleId(int id){
        return session.selectList("ArticlePicDAO.findPicByArticleId",id);
    }

    public List<String > findNullIdPic(){
        return session.selectList("ArticlePicDAO.findNullIdPic");
    }

    public void insertPic(String pic,int id){
        session.insert("ArticlePicDAO.insertPic",new Object[]{pic,id});
        session.commit();
    }

    public void close(){
        session.close();
    }
}
