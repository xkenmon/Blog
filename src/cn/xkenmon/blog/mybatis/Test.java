package cn.xkenmon.blog.mybatis;

import cn.xkenmon.blog.DAO.impl.ArticlesDAOproxy;
import cn.xkenmon.blog.vo.Article;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    private static ArticlesDAOproxy proxy = new ArticlesDAOproxy();

    public static void main(String[] args) {
        Article article = new Article();
        article.setTitle("mybatiseTest");
        article.setDate(new Date());
        article.setSummary("Test");
        article.setContent("content");
        article.setReadCount(0);

        System.out.println(proxy.queryCountByColumn("type","java"));
        for (String a:proxy.getTypeList()){
            System.out.println(a);
        }
//All of test is pass
        proxy.close();

    }

    //ok
    static void create(Article article) {
        proxy.createArticle(article);
    }

    //ok
    static void delete(int id) {
        proxy.deleteById(id);
    }

    //ok
    static Article findById(int id) {
        return proxy.findById(id);
    }

    //ok
    static List<Article> findByTitle(String title) {
        return proxy.findByTitle(title);
    }

    //ok
    static List<Article> findByType(String title) {
        return proxy.findByType(title);
    }

    //ok
    static List<Article> findByTypeOrderByClick(String type) {
        return proxy.findByTypeOrderByClick(type, 0, 3);
    }

    //ok
    static List<Article> findByTypeOrderByDate(String type) {
        return proxy.findByTypeOrderByDate(type, 0, 43);
    }

    //ok
    static void updateById(int id, Article article) {
        proxy.updateById(id, article);
    }

    //ok
    static void updateReadCountById(int id, int count) {
        proxy.updateReadCountById(id, count);
    }
}
