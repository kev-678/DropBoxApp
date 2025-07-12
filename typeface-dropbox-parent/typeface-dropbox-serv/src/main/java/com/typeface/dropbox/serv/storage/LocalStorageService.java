
package com.typeface.dropbox.serv.storage;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import com.typeface.dropbox.serv.common.AllowedContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.typeface.dropbox.serv.model.FileMetadata;
import com.typeface.dropbox.serv.repository.FileMetadataRepository;
import com.typeface.dropbox.serv.service.StorageService;

@Service
public class LocalStorageService implements StorageService {

    private final Path root;
    private final FileMetadataRepository repo;

    public LocalStorageService(
            @Value("${storage.local.path:uploads}") String path,
            FileMetadataRepository repo) throws IOException {

        this.root = Paths.get(path).toAbsolutePath().normalize();
        Files.createDirectories(root);
        this.repo = repo;
    }

    @Override
    public UUID save(MultipartFile file) {
        try {
            String contentType = file.getContentType();

            if (contentType == null || !AllowedContentType.isAllowed(contentType)) {
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }

            UUID id = UUID.randomUUID();
            String storedName = id.toString();
            Path target = root.resolve(storedName);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            FileMetadata meta = new FileMetadata(
                    id,
                    target.toString(),
                    StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())),
                    storedName,
                    file.getSize(),
                    contentType,
                    Instant.now()
            );

            repo.save(meta);
            return id;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }


    @Override
    public InputStream loadAsStream(UUID id) {
        return repo.findById(id)
                .map(m -> {
                    try { return Files.newInputStream(Paths.get(m.getStoragePath())); }
                    catch (IOException e) { throw new RuntimeException("File not found", e); }
                })
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}