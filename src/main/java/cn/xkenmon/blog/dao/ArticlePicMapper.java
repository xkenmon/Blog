package cn.xkenmon.blog.dao;

import java.util.List;

public interface ArticlePicMapper {
    List<String> findPicByArticleId(int id);

    List<String> findNullIdPic();

    int insertPic(String pic, int id);
}
