package com.example.demo.Service.Implementation;

import com.example.demo.Repository.ArchiveRecordRepository;
import com.example.demo.Repository.BibliographicRecordRepository;
import com.example.demo.Repository.NewsMediaRecordRepository;
import com.example.demo.Repository.WebsiteRecordRepository;
import com.example.demo.Service.RecordDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordDeletionServiceImplementation implements RecordDeletionService {

    @Autowired
    private ArchiveRecordRepository archiveRecordRepository;

    @Autowired
    private WebsiteRecordRepository websiteRecordRepository;

    @Autowired
    private NewsMediaRecordRepository newsMediaRecordRepository;

    @Autowired
    private BibliographicRecordRepository bibliographicRecordRepository;

    @Override
    public boolean deleteArchiveRecordFromRepository(long id) {
        try{
            archiveRecordRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteWebsiteRecordFromRepository(long id) {
        try{
            websiteRecordRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteNewsMediaRecordFromRepository(long id) {
        try{
            newsMediaRecordRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBibliographicRecordFromRepository(long id) {
        try{
            bibliographicRecordRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


}
