package com.example.authorizedfilestorage.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "folders")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@Id")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private long id;

    @OneToOne
    private User owner;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FileProperties> listOfFileProperties;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parentFolder;

    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL)
    private List<Folder> childrenFolders;

    private boolean isInRecycleBin;

    private String name;

    public Folder() {
    }

    public Folder(User owner, List<FileProperties> listOfFileProperties, List<Folder> childrenFolders,
            Folder parentFolder, String name) {
        this.owner = owner;
        this.listOfFileProperties = listOfFileProperties;
        this.childrenFolders = childrenFolders;
        this.parentFolder = parentFolder;
        isInRecycleBin = false;
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Folder> getChildrenFolders() {
        return childrenFolders;
    }

    public void setChildrenFolders(List<Folder> childrenFolders) {
        this.childrenFolders = childrenFolders;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<FileProperties> getListOfFileProperties() {
        return listOfFileProperties;
    }

    public void setListOfFileProperties(List<FileProperties> listOfFileProperties) {
        this.listOfFileProperties = listOfFileProperties;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isInRecycleBin() {
        return isInRecycleBin;
    }

    public void setInRecycleBin(boolean isInRecycleBin) {
        this.isInRecycleBin = isInRecycleBin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void linkFileProperties(FileProperties fileProperties) {
        if (fileProperties != null) {
            listOfFileProperties.add(fileProperties);
            fileProperties.setFolder(this);
        }
    }

    public void unlinkFileProperties(FileProperties fileProperties) {
        if (listOfFileProperties.contains(fileProperties)) {
            listOfFileProperties.remove(fileProperties);
            fileProperties.setFolder(null);
        }
    }

    public void linkUser(User user) {
        if (user != null) {
            setOwner(user);
            user.setRootFolder(this);
        }
    }

    public void unlinkUser(User user) {
        setOwner(null);
        user.setRootFolder(null);
    }

    public void unlinkFolderFromParent() {
        this.setParentFolder(null);
    }

}
