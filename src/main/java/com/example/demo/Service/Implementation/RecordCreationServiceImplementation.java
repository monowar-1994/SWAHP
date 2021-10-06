package com.example.demo.Service.Implementation;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Repository.RecordRepository;
import com.example.demo.Service.RecordCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordCreationServiceImplementation implements RecordCreationService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public boolean createRecord(List<ArchivalRecord> recordList) {
        try{
            recordRepository.saveAll(recordList);
            return true;
        }catch(Exception e){
            return false;
        }

    }

    @Override
    public boolean createRecord(ArchivalRecord record) {
        try{
            ArchivalRecord updatedRecord = recordRepository.save(record);
            record = updatedRecord;
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
