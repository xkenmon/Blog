package cn.xkenmon.blog.controller;

import cn.xkenmon.blog.service.IArticlePicService;
import cn.xkenmon.blog.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/userImg")
public class PicController {
    private static Logger logger = LoggerFactory.getLogger(PicController.class);

    final private IArticlePicService picService;

    public PicController(IArticlePicService picService) {
        this.picService = picService;
    }

    @RequestMapping("/ArticlePic")
    public String download(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileName") String fileName) {
//        response.sendRedirect("http://119.28.7.42:8080/userImg/ArticlePic?fileName="+fileName);
        String path = "ArticlePic";

        Pattern pattern = Pattern.compile("(\\w{8}(-\\w{4}){3}-\\w{12}?).\\w*");
        Matcher matcher = pattern.matcher(fileName);
        //检查文件名中非法字符，只允许是字母、数字和下划线
        if (matcher.matches()) {
            try {
                OutputStream out = response.getOutputStream();
                File pic = new File(Config.get("picPath") + File.separator + path + File.separator + fileName);
                if (!pic.exists()) {
                    logger.debug(pic.getAbsolutePath() + "请求的图片不存在");
                    return "redirect:/res/images/img_4.jpg";
                }
                FileInputStream in = new FileInputStream(pic);
                byte[] buf = new byte[1024 * 512];
                int i;
                while ((i = in.read(buf)) != -1) {
                    out.write(buf, 0, i);
                }
                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                logger.info(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getLocalizedMessage());
            }
        }
        return null;
    }
}
