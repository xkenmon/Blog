package cn.xkenmon.blog.util;

import cn.xkenmon.blog.Throwable.FileFormatMistakenException;
import cn.xkenmon.blog.Throwable.FileSizeTooLargeException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.net.Socket;

public class ImgUploadUtil {
    private static Logger logger = LoggerFactory.getLogger(ImgUploadUtil.class);

    public static String upload(CommonsMultipartFile upload, String uploadPath, int size_kb) throws FileSizeTooLargeException, FileFormatMistakenException, IOException {
        String uploadContentType = upload.getContentType();
        String expandedName;
        switch (uploadContentType) {
            case "image/pjpeg":
            case "image/jpeg":
                // IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
                expandedName = ".jpg";
                break;
            case "image/png":
            case "image/x-png":
                // IE6上传的png图片的headimageContentType是"image/x-png"
                expandedName = ".png";
                break;
            case "image/gif":
                expandedName = ".gif";
                break;
            case "image/bmp":
                expandedName = ".bmp";
                break;
            default:
                logger.info("文件格式错误！");
                throw new FileFormatMistakenException(uploadContentType);
        }

        if (upload.getSize() > size_kb * 1024) {
            logger.info("文件过大");
            throw new FileSizeTooLargeException(size_kb * 1024, upload.getSize());
        }

        DiskFileItem diskFileItem = (DiskFileItem) upload.getFileItem();
        File inFile = diskFileItem.getStoreLocation();

        String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
        fileName += expandedName;
//        uploadToRemote(inFile,fileName);
        File toFile = new File(uploadPath, fileName);
        try (OutputStream os = new FileOutputStream(toFile); InputStream is = upload.getInputStream()) {

            File file = new File(uploadPath);
            if (!file.exists()) { // 如果路径不存在，创建
                if (!file.mkdirs()) {
                    throw new IOException();
                }
            }

            logger.info("文件路径：" + toFile.getAbsolutePath());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            logger.info("上传完毕");
        }
        return fileName;
    }

    private static void uploadToRemote(File file,String fileName) {
        String ip = "119.28.7.42";
        int port = 55555;
        try (Socket socket = new Socket(ip, 55555);) {
            OutputStream socketOut = socket.getOutputStream();
            byte[] fileName_byte = fileName.getBytes("ASCII");
            byte fileName_len = (byte) fileName_byte.length;
            FileInputStream fileIn = new FileInputStream(file);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();

            byteOut.write(fileName_len);
            byteOut.write(fileName_byte);

            byte[] buf = new byte[1024 * 1024];
            int len = fileIn.read(buf);
            while (len != -1) {
                byteOut.write(buf,0,len);
                len = fileIn.read(buf);
            }

            byte[] data = byteOut.toByteArray();

            socketOut.write(data);
            socketOut.close();
            fileIn.close();
            byteOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}