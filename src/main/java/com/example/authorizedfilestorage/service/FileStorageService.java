package com.example.authorizedfilestorage.service;

import java.io.IOException;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorizedfilestorage.DTO.FileDTO;
import com.example.authorizedfilestorage.model.File;
import com.example.authorizedfilestorage.model.FileProperties;
import com.example.authorizedfilestorage.model.Folder;
import com.example.authorizedfilestorage.repository.FileRepository;
import com.example.authorizedfilestorage.repository.FolderRepository;



@Service
public class FileStorageService {
    
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderRepository folderRepository;
    
    public void store(FileDTO fDTO) throws IOException {
        Optional<Folder> optionalFolder = folderRepository.findById(fDTO.getFolderID());
        if (optionalFolder.isEmpty()) {
            //throw exception
        }

        File file = new File();
        byte[] decodedBytes = Base64.getDecoder().decode(fDTO.getFileData());
        file.setData(decodedBytes);

        FileProperties fileProperties = new FileProperties();
        fileProperties.setFile(file);
        fileProperties.setName(fDTO.getFileName());
        fileProperties.setType(fDTO.getFileType());
        fileProperties.setSize(fDTO.getFileSize());
        fileProperties.linkFile(file);

        Folder folder = optionalFolder.get();
        folder.linkFileProperties(fileProperties);

        folderRepository.save(folder);
    }

    public Optional<File> getFile(String filePropertiesId) {
        Optional<File> optionalFile = fileRepository.findByFilePropertiesId(filePropertiesId);
        if (optionalFile.isEmpty()) {
            //throw exp
        }
        return optionalFile;
    }

}
