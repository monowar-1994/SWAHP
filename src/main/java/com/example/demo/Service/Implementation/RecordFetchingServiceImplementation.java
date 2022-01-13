package com.example.demo.Service.Implementation;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;
import com.example.demo.Repository.ArchiveRecordRepository;
import com.example.demo.Repository.BibliographicRecordRepository;
import com.example.demo.Repository.NewsMediaRecordRepository;
import com.example.demo.Repository.WebsiteRecordRepository;
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
    @Autowired
    private NewsMediaRecordRepository newsMediaRecordRepository;
    @Autowired
    private WebsiteRecordRepository websiteRecordRepository;
    @Autowired
    private BibliographicRecordRepository bibliographicRecordRepository;

    @Override
    public Iterable<ArchivalRecord> fetchAllRecord() {
        return archiveRecordRepository.findAll();
    }

    @Override
    public Optional<ArchivalRecord> fetchArchiveRecord(Long id) {
        return archiveRecordRepository.findById(id);
    }

    @Override
    public Optional<WebsiteRecord> fetchWebsiteRecord(Long id) {
        return websiteRecordRepository.findById(id);
    }

    @Override
    public Optional<NewsMediaRecord> fetchNewsMediaRecord(Long id) {
        return newsMediaRecordRepository.findById(id);
    }

    @Override
    public Optional<BibliographicRecord> fetchBibliographicRecord(Long id) {
        return bibliographicRecordRepository.findById(id);
    }

    @Override
    public ArchivalRecord saveEditedArchiveRecord(ArchivalRecord editedRecord) {
        return archiveRecordRepository.save(editedRecord);
    }

    @Override
    public NewsMediaRecord saveEditedNewsMediaRecord(NewsMediaRecord editedRecord) {
        return newsMediaRecordRepository.save(editedRecord);
    }

    @Override
    public BibliographicRecord saveEditedBibliographicRecord(BibliographicRecord editedRecord) {
        return bibliographicRecordRepository.save(editedRecord);
    }

    @Override
    public WebsiteRecord saveEditedWebsiteRecord(WebsiteRecord editedRecord) {
        return websiteRecordRepository.save(editedRecord);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInTitleAndTagsInArchive(String titleKeyword) {
        if(titleKeyword.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordInTitleAndTags(titleKeyword);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInSummaryAndNotesInArchive(String keyWord) {
        if(keyWord.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordSummaryAndNotes(keyWord);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInTypeAndGenreInArchive(String keyWord) {
        if(keyWord.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordTypeAndGenre(keyWord);
    }

    @Override
    public List<ArchivalRecord> searchByKeywordInContributionAndCopyrightInArchive(String keyWord) {
        if(keyWord.length()==0){
            return new ArrayList<ArchivalRecord>();
        }
        return archiveRecordRepository.fetchRecordByKeywordInContributorAndCopyright(keyWord);
    }

    @Override
    public Page<ArchivalRecord> findPaginatedRecordsInArchive(int pageNo, int pageSize, String sortField, String sortDirection) {
//        Sort sort = null;
//        Pageable pageable = null;
//        if(sortField != null && sortDirection != null){
//            sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                    Sort.by(sortField).descending();
//        }
//        if(sort != null){
//            pageable = PageRequest.of(pageNo-1,pageSize, sort);
//        }
//        else{
//            pageable = PageRequest.of(pageNo-1, pageSize);
//        }
        return archiveRecordRepository.findAll(getPageSettings(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<NewsMediaRecord> findPaginatedRecordsInNewsMedia(int pageNo, int pageSize, String sortField, String sortDirection) {
//        Sort sort = null;
//        Pageable pageable = null;
//        if(sortField != null && sortDirection != null){
//            sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                    Sort.by(sortField).descending();
//        }
//        if(sort != null){
//            pageable = PageRequest.of(pageNo-1,pageSize, sort);
//        }
//        else{
//            pageable = PageRequest.of(pageNo-1, pageSize);
//        }
        return newsMediaRecordRepository.findAll(getPageSettings(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<WebsiteRecord> findPaginatedRecordsInWebsite(int pageNo, int pageSize, String sortField, String sortDirection) {
//        Sort sort = null;
//        Pageable pageable = null;
//        if(sortField != null && sortDirection != null){
//            sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                    Sort.by(sortField).descending();
//        }
//        if(sort != null){
//            pageable = PageRequest.of(pageNo-1,pageSize, sort);
//        }
//        else{
//            pageable = PageRequest.of(pageNo-1, pageSize);
//        }
        return websiteRecordRepository.findAll(getPageSettings(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Page<BibliographicRecord> findPaginatedRecordsInBibliography(int pageNo, int pageSize, String sortField, String sortDirection) {
//        Sort sort = null;
//        Pageable pageable = null;
//        if(sortField != null && sortDirection != null){
//            sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
//                    Sort.by(sortField).descending();
//        }
//        if(sort != null){
//            pageable = PageRequest.of(pageNo-1,pageSize, sort);
//        }
//        else{
//            pageable = PageRequest.of(pageNo-1, pageSize);
//        }
        return bibliographicRecordRepository.findAll(getPageSettings(pageNo, pageSize, sortField, sortDirection));
    }

    @Override
    public Optional<ArchivalRecord> fetchRecordByItemNumberInArchive(String itemNumber) {
        return archiveRecordRepository.fetchRecordByItemNumber(itemNumber);
    }

    @Override
    public Optional<WebsiteRecord> fetchRecordByItemNumberInWebsite(String itemNumber) {
        return websiteRecordRepository.fetchRecordByItemNumber(itemNumber);
    }

    @Override
    public Optional<NewsMediaRecord> fetchRecordByItemNumberInNewsMedia(String itemNumber) {
        return newsMediaRecordRepository.fetchRecordByItemNumber(itemNumber);
    }

    @Override
    public Optional<BibliographicRecord> fetchRecordByItemNumberInBibliography(String itemNumber) {
        return bibliographicRecordRepository.fetchRecordByItemNumber(itemNumber);
    }

    @Override
    public List<WebsiteRecord> fetchWebsiteRecordByKeywordInAbstractAndTitle(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return websiteRecordRepository.fetchRecordByKeywordInTitleAndAbstract(keyword);
    }

    @Override
    public List<WebsiteRecord> fetchWebsiteRecordByKeywordInCreatorAndCollection(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return websiteRecordRepository.fetchRecordByCreatorAndCollection(keyword);
    }

    @Override
    public List<WebsiteRecord> fetchWebsiteRecordByKeywordInTags(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return websiteRecordRepository.fetchRecordByTags(keyword);
    }

    @Override
    public List<NewsMediaRecord> fetchNewsMediaRecordByKeywordInTitleAndAbstract(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return newsMediaRecordRepository.fetchRecordByKeywordInTitleAndAbstract(keyword);
    }

    @Override
    public List<NewsMediaRecord> fetchNewsMediaRecordByKeywordInAuthor(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return newsMediaRecordRepository.fetchRecordByAuthor(keyword);
    }

    @Override
    public List<NewsMediaRecord> fetchNewsMediaRecordByKeywordInPublicationTitle(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return newsMediaRecordRepository.fetchRecordByPublicationTitle(keyword);
    }

    @Override
    public List<NewsMediaRecord> fetchNewsMediaRecordByKeywordInPlaceAndSection(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return newsMediaRecordRepository.fetchRecordByKeywordInPlaceAndSection(keyword);
    }

    @Override
    public List<BibliographicRecord> fetchBibliographyRecordByKeywordInTitleAndAbstract(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return bibliographicRecordRepository.fetchRecordByKeywordInTitleAndAbstract(keyword);
    }

    @Override
    public List<BibliographicRecord> fetchBibliographyRecordByKeywordInAuthor(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return bibliographicRecordRepository.fetchRecordByAuthor(keyword);
    }

    @Override
    public List<BibliographicRecord> fetchBibliographicRecordByKeywordInPublicationTitle(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return bibliographicRecordRepository.fetchRecordByPublicationTitle(keyword);
    }

    @Override
    public List<BibliographicRecord> fetchBibliographicRecordByKeywordInPlaceAndPublication(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return bibliographicRecordRepository.fetchRecordByKeywordInPlaceAndPublisher(keyword);
    }

    @Override
    public List<BibliographicRecord> fetchBibliographicRecordByKeywordInAdditionalInformation(String keyword) {
        if(keyword== null || keyword.length()==0){
            return null;
        }
        return bibliographicRecordRepository.fetchRecordByAdditionalInformation(keyword);
    }

    public Pageable getPageSettings(int pageNo, int pageSize, String sortField, String sortDirection){
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
        return pageable;
    }

}
