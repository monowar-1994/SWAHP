package com.example.demo.Service.Implementation;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Repository.ArchiveRecordRepository;
import com.example.demo.Service.RecordFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecordFetchingServiceImplementation implements RecordFetchingService {

    @Autowired
    private ArchiveRecordRepository archiveRecordRepository;

    @Override
    public Iterable<ArchivalRecord> fetchAllRecord() {
        return archiveRecordRepository.findAll();
    }

    @Override
    public Optional<ArchivalRecord> fetchRecord(Long id) {
        return archiveRecordRepository.findById(id);
    }

    @Override
    public ArchivalRecord saveEditedRecord(ArchivalRecord editedRecord) {
        return archiveRecordRepository.save(editedRecord);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInTitleAndTags(String titleKeyword) {
        if(titleKeyword.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordInTitleAndTags(titleKeyword);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInSummaryAndNotes(String keyWord) {
        if(keyWord.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordSummaryAndNotes(keyWord);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInTypeAndGenre(String keyWord) {
        if(keyWord.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordTypeAndGenre(keyWord);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInContributionAndCopyright(String keyWord) {
        if(keyWord.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordInContributorAndCopyright(keyWord);
    }

    @Override
    public Page<ArchivalRecord> findPaginatedDataRecords(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = null;
        Pageable pageable = null;
        if(sortField != null && sortDirection != null){
            sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                    Sort.by(sortField).descending();
        }
        if(sort != null){
            pageable = PageRequest.of(pageNo-1,pageSize, sort);
        }
        else{
            pageable = PageRequest.of(pageNo-1, pageSize);
        }

        return archiveRecordRepository.findAll(pageable);
    }

    @Override
    public Optional<ArchivalRecord> fetchRecordByItemNumber(String itemNumber) {
        return archiveRecordRepository.fetchRecordByItemNumber(itemNumber);
    }

}
