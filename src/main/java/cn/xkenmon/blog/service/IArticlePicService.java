package cn.xkenmon.blog.service;

import java.util.List;

public interface IArticlePicService {
    List<String> findPicByArticleId(int id);

    List<String> findNullIdPic();

    int insertPic(String pic, int articleId);
}
