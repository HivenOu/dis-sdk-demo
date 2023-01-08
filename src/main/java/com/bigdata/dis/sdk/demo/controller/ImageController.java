package com.bigdata.dis.sdk.demo.controller;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ief-images")
public class ImageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    private static String path;
    //从环境变量获取path
    static {
        String getenv = System.getenv("FILE_PATH");
        LOGGER.info("get env FILE_PATH is {}",getenv);
        if (!Strings.isBlank(getenv)){
            path=getenv;
        }else{
            LOGGER.warn("FILE_PATH env is not set,use default");
            //fixme 默认在我的Windows运行，所以设置为这个路径
            path="D:\\files\\images\\";
        }
    }
    //获取所有接收到的图片的名称
    @GetMapping
    public List<String> getImageNames(){
        LOGGER.info(path);
        File dir = new File(path);
        if (dir.exists()){
            //获取下面的所有文件的文件名称
            String[] list = dir.list();
            if (list!=null&&list.length!=0){
                return Arrays.asList(list);
            }
        }
        return Collections.singletonList("");
    }

    //获取所有接收到的图片的名称
    @GetMapping("/photo")
    public ResponseEntity<byte[]> getImage(@RequestParam(value = "name")String photoName){
        //根据图片获取图片流并交给Resp 返回给前端
        LOGGER.info("get param:{}",photoName);
        String filePath=path + photoName;
        byte [] body = null;
        try {
            File file = new File(filePath);
            if (file.exists()){
                FileInputStream fileInputStream = new FileInputStream(file);
                body = new byte[fileInputStream.available()];
                fileInputStream.read(body);
            }
        } catch (IOException e1) {
            LOGGER.debug("文件读入出错，文件路径为："+filePath);
            e1.printStackTrace();
        }

        //添加响应头
        HttpHeaders headers = new HttpHeaders();
        try {
            photoName = URLDecoder.decode(photoName, "UTF-8");
            LOGGER.debug("获取的文件名为："+new String(photoName.getBytes("ISO8859-1"), StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //这里fileName有可能出现下载文件乱码-需要自己处理 （attachment;导致强制下载）
        headers.add("Content-Disposition", "filename="+photoName);
        //直接在浏览器展示图片
        headers.setContentType(MediaType.IMAGE_JPEG);
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<>(body, headers, statusCode);
    }

    @PostMapping
    public String uploadImage(MultipartFile file){
         //获取文件名
        String originalFilename = file.getOriginalFilename();
        LOGGER.info("get file and name is : {}",originalFilename);
        File dir = new File(path);
        if (!dir.exists()){
            LOGGER.warn("{} dir is not exists,will mkdir",path);
            dir.mkdir();
        }
        File targetFile = new File(path+originalFilename);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Success";
    }

}
