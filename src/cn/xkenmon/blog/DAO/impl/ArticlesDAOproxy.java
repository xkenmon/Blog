package cn.xkenmon.blog.DAO.impl;

import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.vo.Article;

import java.util.List;

public class ArticlesDAOproxy implements ArticlesDAO {
    private ArticlesDAOImpl daoImpl = new ArticlesDAOImpl();

    @Override
    public boolean createArticle(Article article) {
        return daoImpl.createArticle(article);
    }

    @Override
    public Article findById(int id) {
        return daoImpl.findById(id);
    }

    @Override
    public List<Article> findByTitle(String title) {
        return daoImpl.findByTitle(title);
    }

    @Override
    public List<Article> findByType(String type) {
        return daoImpl.findByType(type);
    }

    @Override
    public List<Article> findSimpleByOrderBy(String col, String str, String sort, int start, int count) {
        return daoImpl.findSimpleByOrderBy(col,str,sort,start,count);
    }

    @Override
    public List<Article> findSimpleByTypeOrderByCreateTime(String type, int start, int end) {
        return daoImpl.findSimpleByTypeOrderByCreateTime(type,start,end);
    }

    @Override
    public List<Article> findSimpleByTypeOrderByClick(String type, int start, int end) {
        return daoImpl.findSimpleByTypeOrderByClick(type,start,end);
    }

    @Override
    public List<Article> findSimpleByAuthorOrderByCreateTime(String author, int start, int end) {
        return daoImpl.findSimpleByAuthorOrderByCreateTime(author,start,end);
    }

    @Override
    public List<Article> findSimpleByAuthorOrderByClick(String author, int start, int end) {
        return daoImpl.findSimpleByAuthorOrderByClick(author,start,end);
    }

    @Override
    public List<Article> findByAuthor(String author) {
        return daoImpl.findByAuthor(author);
    }

    @Override
    public List<Article> findByAuthorOrderByClick(String author, int start, int count) {
        return daoImpl.findByAuthorOrderByClick(author, start, count);
    }

    @Override
    public List<Article> findByAuthorOrderByDate(String author, int start, int count) {
        return daoImpl.findByAuthorOrderByDate(author, start, count);
    }

    @Override
    public List<Article> findByTypeOrderByClick(String type, int start, int count) {
        return daoImpl.findByTypeOrderByClick(type, start, count);
    }

    @Override
    public List<Article> findByTypeOrderByDate(String type, int start, int count) {
        return daoImpl.findByTypeOrderByDate(type, start, count);
    }

    @Override
    public void deleteById(int id) {
        daoImpl.deleteById(id);
    }

    @Override
    public void updateById(int id, Article article) {
        daoImpl.updateById(id, article);
    }


    @Override
    public void updateReadCountById(int id, int count) {
        daoImpl.updateReadCountById(id, count);
    }

    @Override
    public List<Article> orderByCreateDate(int start, int count) {
        return daoImpl.orderByCreateDate(start, count);
    }

    @Override
    public List<Article> orderByUpdateTime(int start, int count) {
        return daoImpl.orderByUpdateTime(start, count);
    }

    @Override
    public List<Article> orderByReadCount(int start, int count) {
        return
                daoImpl.orderByReadCount(start, count);
    }

    @Override
    public List<Article> findSimpleOrderByDate(int start, int count) {
        return daoImpl.findSimpleOrderByDate(start,count);
    }

    @Override
    public List<Article> findSimpleOrderByReadCount(int start, int count) {
        return daoImpl.findSimpleOrderByReadCount(start,count);
    }

    @Override
    public List<Article> findSimpleOrderByUpdateTime(int start, int count) {
        return daoImpl.findSimpleOrderByUpdateTime(start,count);
    }

    @Override
    public int queryCount() {
        return daoImpl.queryCount();
    }

    @Override
    public int queryCountByColumn(String column, String type) {
        return daoImpl.queryCountByColumn(column, type);
    }

    @Override
    public List<String> getTypeList() {
        return daoImpl.getTypeList();
    }

    @Override
    public void close() {
        daoImpl.close();
    }
}
