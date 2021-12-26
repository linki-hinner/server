package com.lin.server.controller;

import com.lin.server.dto.FileInfo;
import com.lin.server.dto.FileResource;
import com.lin.server.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RestController
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return fileStorageService.storeFile(file);
    }


    /**
     * 下载文件
     * @param uuid
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("uuid") String uuid) throws UnsupportedEncodingException {
        FileResource fileResource = fileStorageService.loadFileAsResource(uuid);
        ResponseEntity<Resource> result;
        if(fileResource == null){
            result = ResponseEntity.status(410).build();
        }else {
            result = ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileResource.getType()))
                    .header("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileResource.getName(), "utf-8")))
                    .body(fileResource.getResource());
        }
        return result;
    }

    @GetMapping("/fileInfo")
    public FileInfo getFileInfo(@RequestParam("uuid") String uuid) {
        return fileStorageService.getInfoByUUID(uuid);
    }

}
