package com.example.demo.Model;

import org.springframework.web.multipart.MultipartFile;

public class DataRecordFormEntity {

    public String type;

    public ArchivalRecord archivalRecord;
    public WebsiteRecord websiteRecord;
    public NewsMediaRecord newsMediaRecord;
    public BibliographicRecord bibliographicRecord;
    public MultipartFile artifactFile;


    public WebsiteRecord getWebsiteRecord() {
        return websiteRecord;
    }

    public void setWebsiteRecord(WebsiteRecord websiteRecord) {
        this.websiteRecord = websiteRecord;
    }

    public ArchivalRecord getArchivalRecord() {
        return archivalRecord;
    }

    public void setArchivalRecord(ArchivalRecord archivalRecord) {
        this.archivalRecord = archivalRecord;
    }

    public MultipartFile getArtifactFile() {
        return artifactFile;
    }

    public void setArtifactFile(MultipartFile artifactFile) {
        this.artifactFile = artifactFile;
    }

    public NewsMediaRecord getNewsMediaRecord() {
        return newsMediaRecord;
    }

    public void setNewsMediaRecord(NewsMediaRecord newsMediaRecord) {
        this.newsMediaRecord = newsMediaRecord;
    }

    public BibliographicRecord getBibliographicRecord() {
        return bibliographicRecord;
    }

    public void setBibliographicRecord(BibliographicRecord bibliographicRecord) {
        this.bibliographicRecord = bibliographicRecord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public DataRecordFormEntity(){
        archivalRecord = new ArchivalRecord();
        websiteRecord = new WebsiteRecord();
        newsMediaRecord = new NewsMediaRecord();
        bibliographicRecord = new BibliographicRecord();
    }
}
