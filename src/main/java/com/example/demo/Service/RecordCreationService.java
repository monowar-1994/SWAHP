package com.example.demo.Service;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;

import java.util.List;

public interface RecordCreationService {

    public boolean createArchivalRecord(List<ArchivalRecord> recordList);

    public boolean createArchivalRecord(ArchivalRecord record);

    public boolean createWebsiteRecord(WebsiteRecord record);

    public boolean createNewsMediaRecord(NewsMediaRecord record);

    public boolean createBibliographicRecord(BibliographicRecord record);
}
