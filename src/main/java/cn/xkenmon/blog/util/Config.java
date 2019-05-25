package cn.xkenmon.blog.util;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Config {
    private final static Logger log = LoggerFactory.getLogger(Config.class);
    private static JSONObject cfgJson;

    private static void initConfig() {
        if (cfgJson != null) {
            return;
        }
        File cfgFile = null;
        try {
            cfgFile = ResourceUtils.getFile("classpath:config.json");
            log.info("***loading config file : " + cfgFile.getAbsolutePath());
            String cfgStr;
            try {
                cfgStr = FileUtils.readFileToString(cfgFile, "utf8");
                cfgJson = JSONObject.fromObject(cfgStr);
            } catch (IOException e) {
                e.printStackTrace();
                log.error(cfgFile.getAbsolutePath() + e);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            cfgJson = new JSONObject();
            log.warn("***can't find config file: " + cfgFile.getAbsolutePath());
        }
    }

    public static String get(String key) {
        if (cfgJson == null)
            initConfig();
        Object value;
        if (cfgJson.containsKey(key)) {
            value = cfgJson.get(key);
            if (!(value instanceof String)) {
                value = "";
            }
        } else {
            value = "";
        }
        return (String) value;
    }
}
