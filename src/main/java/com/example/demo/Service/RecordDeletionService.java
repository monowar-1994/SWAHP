package com.example.demo.Service;


import org.springframework.stereotype.Service;


public interface RecordDeletionService {
    public boolean deleteRecordFromRepository(long id);
}
