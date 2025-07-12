
package com.typeface.dropbox.serv.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface StorageService {
    UUID save(MultipartFile file);
    InputStream loadAsStream(UUID id);
}
