package com.senla.courses.shops.api.services;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    void uploadData(MultipartFile file);
}
