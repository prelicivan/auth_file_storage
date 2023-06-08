package com.example.authorizedfilestorage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.authorizedfilestorage.model.Folder;

import jakarta.transaction.Transactional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    
    Optional<Folder> findByOwnerId(long userID);

    //Optional<Folder> findByOwnerIdAndIsInRecycleBinFalse(long userID);

    Optional<Folder> findByOwnerIdAndIsInRecycleBinTrue(long userID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE folders SET is_in_recycle_bin = TRUE WHERE folder_id = :folderId", nativeQuery = true)
    void addToRecycleBin(@Param("folderId")long folderId);

    
    @Transactional
    @Modifying
    @Query(value = "UPDATE folders SET is_in_recycle_bin = FALSE WHERE folder_id = :folderId", nativeQuery = true)
    void removeFromRecycleBin(long folderId);
}
