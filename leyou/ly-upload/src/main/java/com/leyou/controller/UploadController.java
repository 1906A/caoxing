package com.leyou.controller;

import org.apache.commons.lang.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.omg.CORBA.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("upload")
public class UploadController {

    public static  final List<String> FILE_TYPE= Arrays.asList("jpg","png");

    @Value("${user.httpImageYuMing}")
    private String httpImage;

    @RequestMapping("image")
    public String uploadImage(@RequestParam("file")MultipartFile file){

        String fileName=file.getOriginalFilename();
        String  type= fileName.substring(fileName.lastIndexOf(".")+1);

        String type2 = StringUtils.substringAfterLast(fileName, ".");

        System.out.println("type"+type);
        System.out.println("type2"+type2);

        if(!FILE_TYPE.contains(type2)){
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage==null){
                return null;
            }
            /*String filePath=System.currentTimeMillis()+fileName;
            file.transferTo(new File("E:/photo/"+filePath));
            return httpImage+filePath;*/

            ClientGlobal.init("fastdfs.conf");

            TrackerClient trackerClient = new TrackerClient();

            TrackerServer trackerServer = trackerClient.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, null);

            String[] upload_file = storageClient.upload_appender_file(file.getBytes(), type2, null);

            return httpImage+upload_file[0]+"/"+upload_file[1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
