package com.example.authorizedfilestorage.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "file_properties")
public class FileProperties {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "file_properties_id")
    private String id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private File file;

    private String name;
    private String type;
    private long size;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    private boolean isInRecycleBin;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public FileProperties() {
        isInRecycleBin = false;
    }

    public FileProperties(File file, String name, String type, Folder folder, long size) {
        this.file = file;
        this.name = name;
        this.type = type;
        isInRecycleBin = false;
        this.folder = folder;
        this.size = size;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isInRecycleBin() {
        return isInRecycleBin;
    }

    public void setInRecycleBin(boolean isInRecycleBin) {
        this.isInRecycleBin = isInRecycleBin;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public void linkFile(File file) {
        if (file != null) {
            setFile(file);
            file.setFileProperties(this);
        }
    }

    public void unlinkFile(File file) {
        if (this.file == file) {
            this.file = null;
            file.setFileProperties(null);
        }
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
