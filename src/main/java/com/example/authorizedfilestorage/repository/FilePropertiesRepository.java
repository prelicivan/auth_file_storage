package com.example.authorizedfilestorage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.authorizedfilestorage.model.FileProperties;

import jakarta.transaction.Transactional;

@Repository
public interface FilePropertiesRepository extends JpaRepository<FileProperties, String>{
    
    List<FileProperties> findAllByFolderId(long folderId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE file_properties SET is_in_recycle_bin = TRUE WHERE file_properties_id = :filePropertiesId", nativeQuery = true)
    void addToRecycleBin(@Param("filePropertiesId") String filePropertiesId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE file_properties SET is_in_recycle_bin = FALSE WHERE file_properties_id = :filePropertiesId", nativeQuery = true)
    void removeFromRecycleBin(@Param("filePropertiesId") String filePropertiesId);
}
