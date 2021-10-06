package com.example.demo.Service;

import com.example.demo.Model.ArchivalRecord;

import java.util.List;

public interface RecordCreationService {

    public boolean createRecord(List<ArchivalRecord> recordList);

    public boolean createRecord(ArchivalRecord record);
}
