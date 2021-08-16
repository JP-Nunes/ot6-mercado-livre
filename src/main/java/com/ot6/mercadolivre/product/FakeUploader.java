package com.ot6.mercadolivre.product;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FakeUploader implements Uploader {

    @Override
    public Set<String> send(List<MultipartFile> files) {
        return files.stream()
                .map(image -> "http://bucket.io/" + image.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
