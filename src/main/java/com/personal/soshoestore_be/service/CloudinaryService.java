package com.personal.soshoestore_be.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {
    
    private final Cloudinary cloudinary;

//    public CloudinaryService(Cloudinary cloudinary) {
//        this.cloudinary = cloudinary;
//    }

    private String uploadToCloudinary(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", Base64.getEncoder().encodeToString(file.getBytes()).replace("/", "[").substring(0, 175),
                "use_filename_as_display_name", false,
                "overwrite", true)).get("secure_url").toString();
    }

    public List<String> uploadMultiFile(List<MultipartFile> files) throws IOException {
        List<String> uploadResult = new ArrayList<>();
       if(files != null){
           files.forEach(f -> {
               try {
                   uploadResult.add(uploadToCloudinary(f));
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           });
       }
       return uploadResult;
    }

    public String uploadSingleFile(MultipartFile file) throws IOException {
        String imageUploadUrl = "";
        if(file != null){
            imageUploadUrl = uploadToCloudinary(file);
        }
        return imageUploadUrl;
    }
}
