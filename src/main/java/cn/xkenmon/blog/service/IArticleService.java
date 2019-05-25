package cn.xkenmon.blog.service;

import cn.xkenmon.blog.vo.Article;

import java.util.List;

public interface IArticleService {
    boolean createArticle(Article article);

    Article findById(int id);

    List<Article> findByTitle(String title);

    List<Article> findSimpleByType(String type, int start, int count);

    List<Article> findSimpleByTypeOrderByCreateTime(String type, int start, int count);

    List<Article> findSimpleByTypeOrderByClick(String type, int start, int count);

    // not used
    List<Article> findSimpleByAuthorOrderByCreateTime(String author, int start, int count);

    List<Article> findSimpleByAuthorOrderByClick(String author, int start, int count);

    List<Article> findSimpleByAuthor(String author, int start, int count);

    List<Article> findByAuthorOrderByClick(String author, int start, int count);

    List<Article> findByAuthorOrderByDate(String author, int start, int count);

    List<Article> findByTypeOrderByClick(String type, int start, int count);

    List<Article> findByTypeOrderByDate(String type, int start, int count);

    int deleteById(int id);

    int updateById(int id, Article article);

    int updateReadCountById(int id, int count);

    List<Article> orderByCreateDate(int start, int count);

    List<Article> orderByUpdateTime(int start, int count);

    List<Article> orderByReadCount(int start, int count);

    int queryCount();

    int queryCountByColumn(String column, String type);


    List<String> getTypeList();
}
