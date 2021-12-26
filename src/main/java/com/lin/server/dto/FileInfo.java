package com.lin.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class FileInfo {
    private  Long size;
    private  String type;
    private  String name;
    private  String url;
    private  String uuid;
    private  Date createTime;

    public FileInfo(MultipartFile file, String url, String uuid) {
        this.name = StringUtils.cleanPath(file.getOriginalFilename());
        this.type = file.getContentType();
        this.size = file.getSize();
        this.url = url;
        this.uuid = uuid;
        this.createTime = new Date();
    }
}
