package cn.xkenmon.blog.util;

import cn.xkenmon.blog.DAO.impl.ArticlePicDAOImpl;
import cn.xkenmon.blog.vo.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

public class ArticleDeleteUtil {
    private static Logger log = LoggerFactory.getLogger(ArticleDeleteUtil.class);

    public static void delete(String path, Article article) {
        ArticlePicDAOImpl dao = new ArticlePicDAOImpl();

        ArrayList<String> picList = (ArrayList<String>) dao.findPicByArticleId(article.getId());
        if (picList != null) {
            for (String pic : picList) {
                File delFile = new File(path, pic);
//                if (delFile.delete()) {
                    log.info(delFile.getAbsolutePath() + "已成功删除");
//                } else {
//                    log.warn("无法删除图片：" + delFile.getAbsolutePath());
//                }
                log.info("文章标题:" + article.getTitle());
                log.info("文章id:" + article.getId());
            }
        }
        if (article.getCover() != null && !article.getCover().equals("")) {
            File cover = new File(path,article.getCover());
//            if (cover.delete()){
                log.info("删除封面图片：" + cover.getAbsolutePath());

//            }else {
//                log.warn("删除封面图片失败：" + cover.getAbsolutePath());
//            }
            log.info("文章标题" + article.getTitle());
            log.info("文章id:" + article.getId());
        }
    }
}
