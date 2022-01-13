package com.example.demo.Repository;

import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BibliographicRecordRepository extends PagingAndSortingRepository<BibliographicRecord, Long> {

    @Query("From BibliographicRecord d where lower(d.itemNumber)  = lower(:keyword)")
    Optional<BibliographicRecord> fetchRecordByItemNumber(@Param("keyword") String keyWord);

    @Query("From BibliographicRecord d where lower(d.title) LIKE lower(concat('%',:keyword,'%')) OR lower(d.summary) LIKE lower(concat('%',:keyword,'%'))")
    List<BibliographicRecord> fetchRecordByKeywordInTitleAndAbstract(@Param("keyword") String titleKeyword); // Note that abstract is summary in the ORM model for abstract

    @Query("From BibliographicRecord d where lower(d.author)  LIKE lower(concat('%',:keyword,'%'))")
    List<BibliographicRecord> fetchRecordByAuthor(@Param("keyword") String keyWord);

    @Query("From BibliographicRecord d where lower(d.publicationTitle)  LIKE lower(concat('%',:keyword,'%'))")
    List<BibliographicRecord> fetchRecordByPublicationTitle(@Param("keyword") String keyWord);

    @Query("From BibliographicRecord d where lower(d.place) LIKE lower(concat('%',:keyword,'%')) OR lower(d.publisher) LIKE lower(concat('%',:keyword,'%'))")
    List<BibliographicRecord> fetchRecordByKeywordInPlaceAndPublisher(@Param("keyword") String titleKeyword);

    @Query("From BibliographicRecord d where lower(d.additionalInformation)  LIKE lower(concat('%',:keyword,'%'))")
    List<BibliographicRecord> fetchRecordByAdditionalInformation(@Param("keyword") String keyWord);
}
