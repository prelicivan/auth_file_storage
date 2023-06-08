package com.example.authorizedfilestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.authorizedfilestorage.message.ResponseMessage;
import com.example.authorizedfilestorage.model.Folder;
import com.example.authorizedfilestorage.service.FolderStorageService;

@RestController
@CrossOrigin("http://localhost:3000")
public class FolderController {

    @Autowired
    private FolderStorageService folderStorageService;

    @PostMapping("/createFolder")
    public ResponseEntity<ResponseMessage> createNewFolder(@RequestParam("parentFolder") long folderID,
            @RequestParam("folderName") String folderName) {
        String message = "";
        try {
            folderStorageService.store(folderID, folderName);

            message = "Created new folder successfully!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not create folder!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/getRootFolder")
    public ResponseEntity<Folder> getRootFolder(@RequestParam("userID") long userID) {
        try {
            return ResponseEntity.of(folderStorageService.getRoot(userID));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @GetMapping("/getRecycleBin")
    public ResponseEntity<Folder> getRecycleBin(@RequestParam("userID") long userID) {
        try {
            return ResponseEntity.of(folderStorageService.getRecycleBin(userID));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @DeleteMapping("/deleteFolder")
    public ResponseEntity<ResponseMessage> deleteFile(@RequestParam("folderId") long folderId) {
        String message = "";
        try {
            folderStorageService.delete(folderId);

            message = "Deleted folder successfully: " + folderId;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete folder with ID: " + folderId;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Modifying
    @PatchMapping("/addFolderToRecycleBin")
    public ResponseEntity<ResponseMessage> addFolderToRecycleBin(@RequestParam("folderId") long folderId) {
        String message = "";
        try {
            folderStorageService.addToRecycleBin(folderId);

            message = "Added folder to reycle bin: " + folderId;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add folder to recycle bin: " + folderId;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Modifying
    @PatchMapping("/removeFolderFromRecycleBin")
    public ResponseEntity<ResponseMessage> removeFolderFromRecycleBin(@RequestParam("folderId") long folderId) {
        String message = "";
        try {
            folderStorageService.removeFromRecycleBin(folderId);

            message = "Removed folder to reycle bin: " + folderId;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not remove folder from recycle bin: " + folderId;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
