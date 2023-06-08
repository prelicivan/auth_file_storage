package com.example.authorizedfilestorage.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorizedfilestorage.model.Folder;
import com.example.authorizedfilestorage.repository.FolderRepository;

@Service
public class FolderStorageService {

    @Autowired
    private FolderRepository folderRepository;

    public void store(long folderId, String folderName) {
        Optional<Folder> parentFolder = folderRepository.findById(folderId);
        if (parentFolder.isEmpty()) {
            // throw exception
        }

        Folder folder = new Folder();
        folder.setChildrenFolders(null);
        folder.setParentFolder(parentFolder.get());
        folder.setListOfFileProperties(null);
        folder.setOwner(null);
        folder.setName(folderName);

        folderRepository.save(folder);
    }

    public Optional<Folder> getRoot(long userID) {
        Optional<Folder> folder = folderRepository.findByOwnerId(userID);
        return folder;
    }

    public Optional<Folder> getRecycleBin(long userID) {
        Optional<Folder> folder = folderRepository.findByOwnerIdAndIsInRecycleBinTrue(userID);
        return folder;
    }

    public void delete(long folderId) throws Exception {
        Optional<Folder> folder = folderRepository.findById(folderId);
        if (folder.isEmpty()) {
            // throw exp
        } else if (folder.get().getParentFolder() == null) {
            throw new Exception("Root folder can not be deleted!");
        } else if (folder.get().isInRecycleBin()) {
            folder.get().unlinkFolderFromParent();
            folderRepository.deleteById(folderId);
        } else {
            throw new Exception("Folder is not in recycle bin!");
        }

    }

    public void addToRecycleBin(long folderId) throws Exception {
        Optional<Folder> folder = folderRepository.findById(folderId);
        if (folder.isEmpty()) {
            // throw exp
        } else if (folder.get().getParentFolder() == null) {
            throw new Exception("Root folder can not be moved to recycle bin!");
        } else {
            folderRepository.addToRecycleBin(folderId);
        }
    }

    public void removeFromRecycleBin(long folderId) throws Exception {
        Optional<Folder> folder = folderRepository.findById(folderId);
        if (folder.isEmpty()) {
            // throw exp
        }
        folderRepository.removeFromRecycleBin(folderId);
    }
}
