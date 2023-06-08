package com.example.authorizedfilestorage.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.authorizedfilestorage.DTO.FileDTO;
import com.example.authorizedfilestorage.message.ResponseMessage;
import com.example.authorizedfilestorage.model.File;
import com.example.authorizedfilestorage.service.FilePropertiesStorageService;
import com.example.authorizedfilestorage.service.FileStorageService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("http://localhost:3000")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FilePropertiesStorageService filePropertiesStorageService;

    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseEntity<ResponseMessage> uploadFile(@RequestBody FileDTO fDTO) {
        String message = "";
        try {
            fileStorageService.store(fDTO);

            message = "Uploaded file successfully: " + fDTO.getFileName();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload file: " + fDTO.getFileName();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity<ResponseMessage> deleteFile(@RequestParam("filePropertiesId") String filePropertiesId)
            throws Exception {
        String message = "";
        try {
            filePropertiesStorageService.deleteFile(filePropertiesId);

            message = "Deleted file successfully: " + filePropertiesId;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete file with ID: " + filePropertiesId;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Transactional
    @GetMapping("/getFile")
    public ResponseEntity<byte[]> getFile(@RequestParam("filePropertiesId") String filePropertiesId) {
        try {
            Optional<File> file = fileStorageService.getFile(filePropertiesId);

            // return ResponseEntity.of(fileStorageService.getFile(filePropertiesId));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.get().getFileProperties().getName() + "\"")
                    .body(file.get().getData());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @Modifying
    @PatchMapping("/addFileToRecycleBin")
    public ResponseEntity<ResponseMessage> addFileToRecycleBin(
            @RequestParam("filePropertiesId") String filePropertiesId) {
        String message = "";
        try {
            filePropertiesStorageService.addToRecycleBin(filePropertiesId);

            message = "Added file to reycle bin: " + filePropertiesId;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not add file to recycle bin: " + filePropertiesId;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
            ResponseMessage(message));
        }
    }

    @Modifying
    @PatchMapping("/removeFileFromRecycleBin")
    public ResponseEntity<ResponseMessage> removeFileFromRecycleBin(
            @RequestParam("filePropertiesId") String filePropertiesId) throws Exception {
        String message = "";
        try {
            filePropertiesStorageService.removeFromRecycleBin(filePropertiesId);

            message = "Removed file from reycle bin: " + filePropertiesId;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not remove file from recycle bin: " + filePropertiesId;
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
            ResponseMessage(message));
        }
    }
}
