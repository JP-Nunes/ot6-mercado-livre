package com.ot6.mercadolivre.product;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface Uploader {

    Set<String> send(List<MultipartFile> files);
}
