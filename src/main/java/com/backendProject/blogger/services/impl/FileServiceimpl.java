package com.backendProject.blogger.services.impl;

import com.backendProject.blogger.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceimpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name
        String name = file.getOriginalFilename();
        //abc.png

        //random name generate file
        String randomID = UUID.randomUUID().toString();
        assert name != null;
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        //Fullpath
        String filePath = path + File.separator + fileName1;

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;

        return new FileInputStream(fullPath);
    }
}
