package com.example.demo.Repository;

import com.example.demo.Model.ArchivalRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchiveRecordRepository extends PagingAndSortingRepository<ArchivalRecord, Long> {

    @Query("From ArchivalRecord d where lower(d.Title) LIKE lower(concat('%',:keyword,'%')) OR lower(d.Keywords) LIKE lower(concat('%',:keyword,'%'))")
    List<ArchivalRecord> fetchRecordByKeywordInTitleAndTags(@Param("keyword") String titleKeyword);

    //List<DataRecord> findByTitleContainingIgnoreCase(String titleKeyword);

    @Query("From ArchivalRecord d where lower(d.Summary) LIKE lower(concat('%',:keyword,'%')) OR lower(d.Notes) LIKE lower(concat('%',:keyword,'%'))")
    List<ArchivalRecord> fetchRecordByKeywordSummaryAndNotes(@Param("keyword") String keyWord);

    @Query("From ArchivalRecord d where lower(d.Type) LIKE lower(concat('%',:keyword,'%')) OR lower(d.Genre) LIKE lower(concat('%',:keyword,'%'))")
    List<ArchivalRecord> fetchRecordByKeywordTypeAndGenre(@Param("keyword") String keyWord);

    @Query("From ArchivalRecord d where lower(d.Creator) LIKE lower(concat('%',:keyword,'%')) OR lower(d.Contributors) LIKE lower(concat('%',:keyword,'%')) OR lower(d.Copyright) LIKE lower(concat('%',:keyword,'%'))")
    List<ArchivalRecord> fetchRecordByKeywordInContributorAndCopyright(@Param("keyword") String keyWord);

    @Query("From ArchivalRecord d where lower(d.itemNumber)  = lower(:keyword)")
    Optional<ArchivalRecord> fetchRecordByItemNumber(@Param("keyword") String keyWord);

}
