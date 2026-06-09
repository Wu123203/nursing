package com.elderly.controller.base;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.elderly.common.vo.JSONReturn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    @Value("${server.ip}")
    private String ip;


    /**
     * 上传接口
     *
     * @param file
     * @param c 类型：1头像，1商品
     * @return
     * @throws IOException
     */
    @PostMapping("/upload/{c}")
    public JSONReturn upload(MultipartFile file, @PathVariable(value = "c")Integer c) throws IOException {
        //文件原始名称
        String originalFilename = file.getOriginalFilename(); // 获取源文件的名称
        //文件后缀
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        // 定义文件的唯一标识（前缀）
        String uuid = IdUtil.fastSimpleUUID();
        String dir = "avatar";
        if(c == 1){
            dir = "avatar/";
        }else if(c == 2){
            dir = "resthome/";
        }else if(c == 3){
            dir = "news/";
        }
        String rootFilePath =
                System.getProperty("user.dir") + "/files/" + dir + uuid + suffix; // 获取上传的路径
        File rootFile = new File(rootFilePath);
        if (!rootFile.getParentFile().exists()) {
            rootFile.getParentFile().mkdirs();
        }
        FileUtil.writeBytes(file.getBytes(), rootFilePath); // 把文件写入到上传的路径
        String filePath = "/files/" + dir + uuid + suffix;
        Map<String,String> result = new HashMap<>();
        result.put("fileName",originalFilename);
        if(suffix.equalsIgnoreCase(".jpg") || suffix.equalsIgnoreCase(".jpeg") || suffix.equalsIgnoreCase(".png") || c==4){
            result.put("filePath","http://" + ip + ":" + port + "/files/" + dir + uuid + suffix);
        }else{
            result.put("filePath",filePath);
        }
        return JSONReturn.success("上传成功！",result); // 返回结果 url
    }



}
