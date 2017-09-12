package cn.xkenmon.blog.DAO.impl;

import cn.xkenmon.blog.DAO.ArticlesDAO;
import cn.xkenmon.blog.DAO.Factory.MySqlSessionFactory;
import cn.xkenmon.blog.vo.Article;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ArticlesDAOImpl implements ArticlesDAO {

    private SqlSession session;

    ArticlesDAOImpl() {
        this.session = MySqlSessionFactory.getSession();
    }

    //create method
    @Override
    public boolean createArticle(Article article) {
        session.insert("ArticleDAO.createArticle", article);
        session.commit();
        return true;
    }

    ///find method
    @Override
    public Article findById(int id) {
        return (Article) session.selectOne("ArticleDAO.findById", id);
    }

    @Override
    public List<Article> findByTitle(String title) {
        return session.selectList("ArticleDAO.findByTitle", title);
    }

    @Override
    public List<Article> findByType(String type) {
        return session.selectList("ArticleDAO.findByType", type);
    }

    @Override
    public List<Article> findSimpleByOrderBy(String col, String str1, String sort, int start, int count) {
        return session.selectList("ArticleDAO.findSimpleByOrderBy",
                new Object[]{col, str1, sort},
                new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleByTypeOrderByCreateTime(String type, int start, int end) {
        return findSimpleByOrderBy("type", type, "date", start, end);
    }

    @Override
    public List<Article> findSimpleByTypeOrderByClick(String type, int start, int end) {
        return findSimpleByOrderBy("type", type, "read_count", start, end);
    }

    @Override
    public List<Article> findSimpleByAuthorOrderByCreateTime(String author, int start, int end) {
        return findSimpleByOrderBy("author", author, "date", start, end);
    }

    @Override
    public List<Article> findSimpleByAuthorOrderByClick(String author, int start, int end) {
        return findSimpleByOrderBy("author", author, "read_count", start, end);
    }


    @Override
    public List<Article> findByAuthor(String author) {
        return session.selectList("ArticleDAO.findByAuthor", author);
    }

    @Override
    public List<Article> findByAuthorOrderByClick(String author, int start, int count) {
        return session.selectList("ArticleDAO.findByAuthorOrderBy", new String[]{author, "read_count"}, new RowBounds(start, count));
    }

    @Override
    public List<Article> findByAuthorOrderByDate(String author, int start, int count) {
        return session.selectList("ArticleDAO.findByAuthorOrderBy", new String[]{author, "date"}, new RowBounds(start, count));
    }

    @Override
    public List<Article> findByTypeOrderByClick(String type, int start, int count) {
        return session.selectList("ArticleDAO.findByTypeOrderBy", new String[]{type, "read_count"}, new RowBounds(start, count));
    }

    @Override
    public List<Article> findByTypeOrderByDate(String type, int start, int count) {
        return session.selectList("ArticleDAO.findByTypeOrderBy", new String[]{type, "date"}, new RowBounds(start, count));
    }

    //delete method
    @Override
    public void deleteById(int id) {
        session.delete("ArticleDAO.deleteById", id);
        session.commit();
    }

    //update method
    @Override
    public void updateById(int id, Article article) {
        article.setId(id);
        if (article.getReadCount() == -1)
            article.setReadCount(findById(id).getReadCount());
        session.update("ArticleDAO.updateById", article);
        session.commit();
    }

    @Override
    public void updateReadCountById(int id, int count) {
        Article article = new Article();
        article.setId(id);
        article.setReadCount(count);
        session.update(("ArticleDAO.updateById"), article);
        session.commit();
    }

    //order query
    @Override
    public List<Article> orderByCreateDate(int start, int count) {
        return session.selectList("ArticleDAO.orderBy", "date", new RowBounds(start, count));
    }

    @Override
    public List<Article> orderByUpdateTime(int start, int count) {
        return session.selectList("ArticleDAO.orderBy", "update_time", new RowBounds(start, count));
    }

    @Override
    public List<Article> orderByReadCount(int start, int count) {
        return session.selectList("ArticleDAO.orderBy", "read_count", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleOrderByDate(int start, int count) {
        return session.selectList("ArticleDAO.findSimpleOrderBy", "date", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleOrderByReadCount(int start, int count) {
        return session.selectList("ArticleDAO.findSimpleOrderBy", "read_count", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleOrderByUpdateTime(int start, int count) {
        return session.selectList("ArticleDAO.findSimpleOrderBy", "update_time", new RowBounds(start, count));
    }

    @Override
    public int queryCount() {
        return session.selectOne("ArticleDAO.queryCount");
    }

    @Override
    public int queryCountByColumn(String column, String type) {
        return session.selectOne("ArticleDAO.queryCountByColumn", new String[]{column, type});
    }


    //get type list
    @Override
    public List<String> getTypeList() {
        return session.selectList("ArticleDAO.getTypeList");
    }

    //close db
    @Override
    public void close() {
        session.close();
    }
}
