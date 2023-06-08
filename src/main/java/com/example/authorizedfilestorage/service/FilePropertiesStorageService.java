package com.example.authorizedfilestorage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorizedfilestorage.model.FileProperties;
import com.example.authorizedfilestorage.model.Folder;
import com.example.authorizedfilestorage.repository.FilePropertiesRepository;

@Service
public class FilePropertiesStorageService {

    @Autowired
    private FilePropertiesRepository filePropertiesRepository;

    public void deleteFile(String filePropertiesId) throws Exception {
        Optional<FileProperties> fProperties = filePropertiesRepository.findById(filePropertiesId);
        if (fProperties.isEmpty()) {
            throw new Exception("AAAAAAa");
        } else if (fProperties.get().isInRecycleBin()) {

            Folder folder = fProperties.get().getFolder();
            folder.unlinkFileProperties(fProperties.get());

            filePropertiesRepository.deleteById(filePropertiesId);
        }
    }

    public void addToRecycleBin(String filePropertiesId) {
        Optional<FileProperties> fOptional = filePropertiesRepository.findById(filePropertiesId);
        if (fOptional.isEmpty()) {
            // throw exp
        }
        filePropertiesRepository.addToRecycleBin(filePropertiesId);
    }

    public void removeFromRecycleBin(String filePropertiesId) {
        filePropertiesRepository.removeFromRecycleBin(filePropertiesId);
    }
}
