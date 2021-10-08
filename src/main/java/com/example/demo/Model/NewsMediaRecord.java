package com.example.demo.Model;

import javax.persistence.*;

@Entity
public class NewsMediaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String itemNumber;
    public String title;
    public String language;
    @Column(length = 1000)
    public String summary; // Call it abstract in UI design.
    // 'abstract' is a keyword in java and not allowed in variable name
    public String dateOriginalCreated;
    public String dateCaptured;
    public String genreOfItem;
    public String author;
    @Column(length = 1000)
    public String publicationTitle;
    public String place;
    public String section;
    public String pages;
    public String rights;
    @Column(length = 2048)
    public String url;

    public String artifactFileName;
    public String artifactFileLocation;
    public String accessFileLocation;
    public String preservationHash;
    public String creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDateOriginalCreated() {
        return dateOriginalCreated;
    }

    public void setDateOriginalCreated(String dateOriginalCreated) {
        this.dateOriginalCreated = dateOriginalCreated;
    }

    public String getDateCaptured() {
        return dateCaptured;
    }

    public void setDateCaptured(String dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    public String getGenreOfItem() {
        return genreOfItem;
    }

    public void setGenreOfItem(String genreOfItem) {
        this.genreOfItem = genreOfItem;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtifactFileName() {
        return artifactFileName;
    }

    public void setArtifactFileName(String artifactFileName) {
        this.artifactFileName = artifactFileName;
    }

    public String getArtifactFileLocation() {
        return artifactFileLocation;
    }

    public void setArtifactFileLocation(String artifactFileLocation) {
        this.artifactFileLocation = artifactFileLocation;
    }

    public String getAccessFileLocation() {
        return accessFileLocation;
    }

    public void setAccessFileLocation(String accessFileLocation) {
        this.accessFileLocation = accessFileLocation;
    }

    public String getPreservationHash() {
        return preservationHash;
    }

    public void setPreservationHash(String preservationHash) {
        this.preservationHash = preservationHash;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}