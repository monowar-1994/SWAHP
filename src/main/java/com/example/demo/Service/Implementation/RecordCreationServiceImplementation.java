package com.example.demo.Service.Implementation;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;
import com.example.demo.Repository.ArchiveRecordRepository;
import com.example.demo.Repository.BibliographicRecordRepository;
import com.example.demo.Repository.NewsMediaRecordRepository;
import com.example.demo.Repository.WebsiteRecordRepository;
import com.example.demo.Service.RecordCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordCreationServiceImplementation implements RecordCreationService {

    @Autowired
    private ArchiveRecordRepository archiveRecordRepository;

    @Autowired
    private WebsiteRecordRepository websiteRecordRepository;

    @Autowired
    private NewsMediaRecordRepository newsMediaRecordRepository;

    @Autowired
    BibliographicRecordRepository bibliographicRecordRepository;

    @Override
    public boolean createArchivalRecord(List<ArchivalRecord> recordList) {
        try{
            archiveRecordRepository.saveAll(recordList);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean createArchivalRecord(ArchivalRecord record) {
        try{
            ArchivalRecord updatedRecord = archiveRecordRepository.save(record);
            record = updatedRecord;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createWebsiteRecord(WebsiteRecord record) {
        try{
            WebsiteRecord updatedRecord = websiteRecordRepository.save(record);
            record = updatedRecord;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createNewsMediaRecord(NewsMediaRecord record) {
        try{
            NewsMediaRecord updatedRecord = newsMediaRecordRepository.save(record);
            record = updatedRecord;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean createBibliographicRecord(BibliographicRecord record) {
        try{
            BibliographicRecord updatedRecord = bibliographicRecordRepository.save(record);
            record = updatedRecord;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
