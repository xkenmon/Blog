package cn.xkenmon.blog.dao;

import cn.xkenmon.blog.vo.Article;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ArticleMapper {

    /**
     * @param article article that need create;
     * @return is create success
     */
    boolean createArticle(@Param("article") Article article);

    //find method

    /**
     * @param id find article by this id(int）
     * @return Have this ID article
     */
    Article findById(@Param("id") int id);

    /**
     * @param title find article by this title
     * @return Have this title article
     */
    List<Article> findByTitle(@Param("title") String title);

    List<Article> findSimpleByOrderBySortBy(@Param("col") String col, @Param("val") String val, @Param("order") String order, @Param("sort") String sort, RowBounds rowBounds);

    List<Article> findSimpleOrderBySortBy(@Param(value = "order") String order, @Param("sort") String sort, RowBounds rowBounds);

    List<Article> findSimpleBy(@Param("col") String col, @Param("val") String val, RowBounds rowBounds);

    /**
     * @param id article ID (int)
     */
    int deleteById(@Param("id") int id);

    //update method

    int updateById(@Param("id") int id, @Param("article") Article article);

    int updateReadCountById(@Param("id") int id, @Param("count") int count);

    //获取文章总数
    int queryCount();

    int queryCountByColumn(@Param("col") String col, @Param("type") String type);

    //获取文章类型列表
    List<String> getTypeList();
}
