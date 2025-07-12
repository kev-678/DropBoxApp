
package com.typeface.dropbox.serv.repository;


import com.typeface.dropbox.serv.model.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
}
