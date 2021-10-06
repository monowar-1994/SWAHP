package com.example.demo.Service.Implementation;

import com.example.demo.Repository.ArchiveRecordRepository;
import com.example.demo.Service.RecordDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordDeletionServiceImplementation implements RecordDeletionService {

    @Autowired
    private ArchiveRecordRepository archiveRecordRepository;

    @Override
    public boolean deleteRecordFromRepository(long id) {
        try{
            archiveRecordRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }


}
