package com.example.authorizedfilestorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorizedfilestorage.model.Folder;
import com.example.authorizedfilestorage.model.User;
import com.example.authorizedfilestorage.repository.UserRepository;

@Service
public class UserStorageService {
    
    @Autowired
    private UserRepository userRepository;


    public void store() {
        User u = new User();
        u.setUsername("aaaaaa");
        u.setPassword("bbbbbb");
        
        Folder f = new Folder();
        f.setOwner(u);
        f.setParentFolder(null);
        f.setListOfFileProperties(null);
        f.setChildrenFolders(null);

        u.setRootFolder(f);

        userRepository.save(u);
    }
}
