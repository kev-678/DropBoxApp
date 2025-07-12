
package com.typeface.dropbox.serv.controller;


import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.typeface.dropbox.serv.model.FileMetadata;
import com.typeface.dropbox.serv.repository.FileMetadataRepository;
import com.typeface.dropbox.serv.service.StorageService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final StorageService storageService;
    private final FileMetadataRepository repo;

    public FileController(StorageService storageService, FileMetadataRepository repo) {
        this.storageService = storageService;
        this.repo = repo;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileMetadata upload(@RequestParam("file") MultipartFile file) {
        UUID id = storageService.save(file);
        return repo.findById(id).orElseThrow();
    }

    @GetMapping
    public List<FileMetadata> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public FileMetadata metadata(@PathVariable UUID id) {
        return repo.findById(id).orElseThrow();
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> download(@PathVariable UUID id) throws Exception {
        FileMetadata meta = repo.findById(id).orElseThrow(() -> new RuntimeException("File not found"));

        byte[] data;
        try (InputStream in = storageService.loadAsStream(id)) {
            data = in.readAllBytes();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + meta.getOriginalName() + "\"")
                .contentType(MediaType.parseMediaType(meta.getContentType()))
                .body(data);
    }

}
