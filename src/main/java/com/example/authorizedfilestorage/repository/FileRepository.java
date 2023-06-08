package com.example.authorizedfilestorage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authorizedfilestorage.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    
    Optional<File> findByFilePropertiesId(String filePropertiesId);
}
