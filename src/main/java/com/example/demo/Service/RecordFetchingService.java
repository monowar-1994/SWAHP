package com.example.demo.Service;

import com.example.demo.Model.ArchivalRecord;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RecordFetchingService {

    Iterable<ArchivalRecord> fetchAllRecord();

    Optional<ArchivalRecord> fetchRecord(Long id);

    ArchivalRecord saveEditedRecord(ArchivalRecord editedRecord);

    List<ArchivalRecord> searchByKeywordInTitleAndTags(String titleKeyword);

    List<ArchivalRecord> searchByKeywordInSummaryAndNotes(String keyWord);

    List<ArchivalRecord> searchByKeywordInTypeAndGenre(String keyWord);

    List<ArchivalRecord> searchByKeywordInContributionAndCopyright(String keyWord);

    Page<ArchivalRecord> findPaginatedDataRecords(int pageNo, int pageSize, String sortField, String sortDirection);

    Optional<ArchivalRecord> fetchRecordByItemNumber(String itemNumber);
}
