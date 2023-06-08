package com.example.authorizedfilestorage.DTO;

public class FileDTO {

    private long folderID;
    private String folderName;
    private String fileName;
    private String fileType;
    private String fileData;
    private long fileSize;

    public FileDTO() {
    }

    public FileDTO(long folderID, String folderName, String fileName, String fileType, String fileData, long fileSize) {
        this.folderID = folderID;
        this.folderName = folderName;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileData = fileData;
        this.fileSize = fileSize;
    }

    public long getFolderID() {
        return folderID;
    }

    public void setFolderID(long folderID) {
        this.folderID = folderID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

}
