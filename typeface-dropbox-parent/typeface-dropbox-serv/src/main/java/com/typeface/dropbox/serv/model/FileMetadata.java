
package com.typeface.dropbox.serv.model;


import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_metadata")
public class FileMetadata {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String storedName;

    private long size;

    private String contentType;

    private Instant uploadTime;

    private String storagePath;

    public Instant getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getStoredName() {
        return storedName;
    }

    public void setStoredName(String storedName) {
        this.storedName = storedName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public FileMetadata(UUID id, String storagePath, String originalName, String storedName, long size, String contentType, Instant uploadTime) {
        this.id = id;
        this.storagePath = storagePath;
        this.originalName = originalName;
        this.storedName = storedName;
        this.size = size;
        this.contentType = contentType;
        this.uploadTime = uploadTime;
    }

    public FileMetadata(){};
}
