package com.example.demo.Service;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RecordFetchingService {

    Iterable<ArchivalRecord> fetchAllRecord();

    Optional<ArchivalRecord> fetchArchiveRecord(Long id);

    Optional<WebsiteRecord> fetchWebsiteRecord(Long id);

    Optional<NewsMediaRecord> fetchNewsMediaRecord(Long id);

    Optional<BibliographicRecord> fetchBibliographicRecord(Long id);

    ArchivalRecord saveEditedArchiveRecord(ArchivalRecord editedRecord);

    List<ArchivalRecord> searchByKeywordInTitleAndTagsInArchive(String titleKeyword);

    List<ArchivalRecord> searchByKeywordInSummaryAndNotesInArchive(String keyWord);

    List<ArchivalRecord> searchByKeywordInTypeAndGenreInArchive(String keyWord);

    List<ArchivalRecord> searchByKeywordInContributionAndCopyrightInArchive(String keyWord);

    Page<ArchivalRecord> findPaginatedRecordsInArchive(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<NewsMediaRecord> findPaginatedRecordsInNewsMedia(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<WebsiteRecord> findPaginatedRecordsInWebsite(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<BibliographicRecord> findPaginatedRecordsInBibliography(int pageNo, int pageSize, String sortField, String sortDirection);

    Optional<ArchivalRecord> fetchRecordByItemNumberInArchive(String itemNumber);

    Optional<WebsiteRecord> fetchRecordByItemNumberInWebsite(String itemNumber);

    Optional<NewsMediaRecord> fetchRecordByItemNumberInNewsMedia(String itemNumber);

    Optional<BibliographicRecord> fetchRecordByItemNumberInBibliography(String itemNumber);
}
