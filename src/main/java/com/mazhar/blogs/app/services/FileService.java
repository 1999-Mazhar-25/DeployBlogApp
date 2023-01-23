package com.mazhar.blogs.app.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    public String uploadFile(String path, MultipartFile file) throws IOException;
    public InputStream getResources(String path, String fileName) throws IOException;
}
