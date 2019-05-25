package cn.xkenmon.blog.service.impl;

import cn.xkenmon.blog.dao.ArticleMapper;
import cn.xkenmon.blog.service.IArticleService;
import cn.xkenmon.blog.vo.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {

    private final
    ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean createArticle(Article article) {
        return articleMapper.createArticle(article);
    }

    @Override
    public Article findById(int id) {
        return articleMapper.findById(id);
    }

    @Override
    public List<Article> findByTitle(String title) {
        return articleMapper.findByTitle(title);
    }

    @Override
    public List<Article> findSimpleByType(String type, int start, int count) {
        return articleMapper.findSimpleBy("type", type, new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleByTypeOrderByCreateTime(String type, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("type", type, "date", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleByTypeOrderByClick(String type, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("type", type, "read_count", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleByAuthorOrderByCreateTime(String author, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("author", author, "date", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleByAuthorOrderByClick(String author, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("author", author, "read_count", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findSimpleByAuthor(String author, int start, int count) {
        return articleMapper.findSimpleBy("author", author, new RowBounds(start, count));
    }

    @Override
    public List<Article> findByAuthorOrderByClick(String author, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("author", author, "read_count", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findByAuthorOrderByDate(String author, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("author", author, "date", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findByTypeOrderByClick(String type, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("type", type, "read_count", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> findByTypeOrderByDate(String type, int start, int count) {
        return articleMapper.findSimpleByOrderBySortBy("type", type, "date", "desc", new RowBounds(start, count));
    }

    @Override
    public int deleteById(int id) {
        return articleMapper.deleteById(id);
    }

    @Override
    public int updateById(int id, Article article) {
        return articleMapper.updateById(id, article);
    }

    @Override
    public int updateReadCountById(int id, int count) {
        return articleMapper.updateReadCountById(id, count);
    }

    @Override
    public List<Article> orderByCreateDate(int start, int count) {
        return articleMapper.findSimpleOrderBySortBy("date", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> orderByUpdateTime(int start, int count) {
        return articleMapper.findSimpleOrderBySortBy("update_time", "desc", new RowBounds(start, count));
    }

    @Override
    public List<Article> orderByReadCount(int start, int count) {
        return articleMapper.findSimpleOrderBySortBy("read_count", "desc", new RowBounds(start, count));
    }

    @Override
    public int queryCount() {
        return articleMapper.queryCount();
    }

    @Override
    public int queryCountByColumn(String column, String type) {
        return articleMapper.queryCountByColumn(column, type);
    }

    @Override
    public List<String> getTypeList() {
        return articleMapper.getTypeList();
    }
}
