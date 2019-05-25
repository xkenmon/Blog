package cn.xkenmon.blog.service.impl;

import cn.xkenmon.blog.dao.ArticlePicMapper;
import cn.xkenmon.blog.service.IArticlePicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlePicServiceImpl implements IArticlePicService {

    final private
    ArticlePicMapper articlePicMapper;

    public ArticlePicServiceImpl(ArticlePicMapper articlePicMapper) {
        this.articlePicMapper = articlePicMapper;
    }

    @Override
    public List<String> findPicByArticleId(int id) {
        return articlePicMapper.findPicByArticleId(id);
    }

    @Override
    public List<String> findNullIdPic() {
        return articlePicMapper.findNullIdPic();
    }

    @Override
    public int insertPic(String pic, int articleId) {
        return articlePicMapper.insertPic(pic, articleId);
    }
}
