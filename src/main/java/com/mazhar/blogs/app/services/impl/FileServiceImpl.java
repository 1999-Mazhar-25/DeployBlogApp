package com.mazhar.blogs.app.services.impl;

import com.mazhar.blogs.app.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {

       String name = file.getOriginalFilename();
       String random =  UUID.randomUUID().toString();
      String fname = random.concat(name.substring(name.lastIndexOf(".")));
      String fullPath = path + File.separator + fname;
      File f = new File(path);
      if (!f.exists())
      {
          f.mkdir();
      }
      Files.copy(file.getInputStream(), Paths.get(fullPath));
      return fname;
    }

    @Override
    public InputStream getResources(String path, String fileName)
            throws IOException {
        String fullPath = path + File.separator + fileName;
        InputStream is =new FileInputStream(fullPath);
        is.close();
        return is;
    }
}
