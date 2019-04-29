package com.leyou.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class UploadService {

    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");


    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if(!suffixes.contains(contentType)){
                return "文件类型有误";
            }
            //校验文件内容
            BufferedImage read = ImageIO.read(file.getInputStream());
            if(read == null){
                return "文件类型有误";
            }
            //目标路径
            File path = new File("F:/leyou/upload",file.getOriginalFilename());
            //保存文件到本地
            file.transferTo(path);
            //保存文件的路径
            //return null;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件上传失败");
        }
        return "http:com.leyou.image/"+file.getOriginalFilename();
    }
}
