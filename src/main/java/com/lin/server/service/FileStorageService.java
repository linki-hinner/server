package com.lin.server.service;

import com.lin.server.dao.FileDAO;
import com.lin.server.dto.FileInfo;
import com.lin.server.dto.FileResource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {
    private final FileDAO fileDAO;
    private final SimpleDateFormat DEFAULT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 存储文件
     * @param file
     * @return uuid
     * @throws IOException
     */
    public String storeFile(MultipartFile file) throws IOException {
        String dir = File.separator + DEFAULT.format(new Date());
        new File(dir).mkdir();
        String uuid = UUID.randomUUID().toString();
        String filePath = dir + File.separator + uuid;

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        fileDAO.addFileInfo(new FileInfo(file, filePath, uuid));
        return uuid;
    }

    /**
     * 加载文件
     * @param uuid
     * @return 文件资源
     */
    public FileResource loadFileAsResource(String uuid) {
        try {
            FileInfo fileInfo = fileDAO.getFileInfo(uuid);
            Resource resource = new UrlResource(Paths.get(fileInfo.getUrl()).toUri());
            if (!resource.exists()) {
                return null;
            }
            return FileResource.builder()
                    .resource(resource)
                    .name(fileInfo.getName())
                    .type(fileInfo.getType())
                    .build();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public FileInfo getInfoByUUID(String uuid) {
        return fileDAO.getFileInfo(uuid);
    }
}
