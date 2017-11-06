package cn.xkenmon.blog.util;

import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class Config {
    private final static Logger log = LoggerFactory.getLogger(Config.class);
    private static JSONObject cfgJson;

    private static void initConfig() {
        if (cfgJson != null) {
            return;
        }
        String userDir = System.getProperty("user.dir");
        if (userDir != null && !userDir.equals("")) {
            File cfgFile = new File(userDir, "config.json");
            if (cfgFile.exists()) {
                log.info("loading config file : " + cfgFile.getAbsolutePath());
                String cfgStr;
                try {
                    cfgStr = FileUtils.readFileToString(cfgFile, "utf8");
                    cfgJson = JSONObject.fromObject(cfgStr);
                    new Thread(FlushThread.getThread(cfgFile)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error(cfgFile.getAbsolutePath() + e);
                }
            } else {
                log.info("can't find config file.");
            }
        } else {
            log.error("Can not get user.dir");
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

    private static class FlushThread implements Runnable {

        private static volatile FlushThread instance;
        File cfgFile;

        private FlushThread(File cfgFile) {
            this.cfgFile = cfgFile;
        }

        private static FlushThread getThread(File cfgFile) {
            if (instance == null) {
                synchronized (FlushThread.class) {
                    instance = new FlushThread(cfgFile);
                }
            }
            return instance;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    cfgJson = JSONObject.fromObject(FileUtils.readFileToString(cfgFile, "utf8"));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.warn("Flush config file failed : " + e);
                }
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
