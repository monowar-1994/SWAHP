package com.example.demo.Service.Implementation;

import com.example.demo.Repository.RecordRepository;
import com.example.demo.Service.RecordDeletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordDeletionServiceImplementation implements RecordDeletionService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public boolean deleteRecordFromRepository(long id) {
        try{
            recordRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }


}
