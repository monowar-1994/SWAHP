package com.example.demo.Service;


import org.springframework.stereotype.Service;


public interface RecordDeletionService {
    public boolean deleteArchiveRecordFromRepository(long id);

    public boolean deleteWebsiteRecordFromRepository(long id);

    public boolean deleteNewsMediaRecordFromRepository(long id);

    public boolean deleteBibliographicRecordFromRepository(long id);
}
