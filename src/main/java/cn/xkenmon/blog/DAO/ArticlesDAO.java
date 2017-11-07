package cn.xkenmon.blog.DAO;

import cn.xkenmon.blog.vo.Article;

import java.util.List;

public interface ArticlesDAO {

    /**
     * @param article article that need create;
     * @return is create success
     */
    boolean createArticle(Article article);

    //find method
    /**
     * @param id find article by this id(int）
     * @return Have this ID article
     */
    Article findById(int id);

    /**
     * @param title find article by this title
     * @return Have this title article
     */
    List<Article> findByTitle(String title);

    /**
     * @param type find articles by this type
     * @return a List include this type articles
     */
    List<Article> findByType(String type);

    /**
     * 通过列查找简洁的文章描述
     * @param col 列名
     * @param str 匹配字符串
     * @param sort 排序方式
     * @param start 数据起始位置
     * @param count 数据条数
     * @return 返回文章的简单描述（id,标题，时间，作者，简介，封面）
     */
    List<Article> findSimpleByOrderBy(String col,String str,String sort,int start,int count);

    List<Article> findSimpleByTypeOrderByCreateTime(String type,int start,int end);
    List<Article> findSimpleByTypeOrderByClick(String type,int start,int end);
    List<Article> findSimpleByAuthorOrderByCreateTime(String author,int start,int end);
    List<Article> findSimpleByAuthorOrderByClick(String author,int start,int end);

    List<Article> findByAuthor(String author);

    List<Article> findByAuthorOrderByClick(String author,int start,int count);

    List<Article> findByAuthorOrderByDate(String author, int start, int count);

    List<Article> findByTypeOrderByClick(String type, int start, int count);

    List<Article> findByTypeOrderByDate(String type, int start, int count);

    //delete method
    /**
     * @param id article ID (int)
     */
    void deleteById(int id);

    //update method

    void updateById(int id, Article article);

    void updateReadCountById(int id, int count);

    //query method
    List<Article> orderByCreateDate(int start, int count);

    List<Article> orderByUpdateTime(int start, int count);

    List<Article> orderByReadCount(int start, int count);

    List<Article> findSimpleOrderByDate(int start,int count);

    List<Article> findSimpleOrderByReadCount(int start,int count);

    List<Article> findSimpleOrderByUpdateTime(int start,int count);

    //获取文章总数
    int queryCount();

    int queryCountByColumn(String column, String type);

    //获取文章类型列表
    List<String> getTypeList();

    void close();
}
