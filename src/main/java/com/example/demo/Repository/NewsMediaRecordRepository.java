package com.example.demo.Repository;


import com.example.demo.Model.NewsMediaRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsMediaRecordRepository extends PagingAndSortingRepository<NewsMediaRecord, Long> {

    @Query("From NewsMediaRecord d where lower(d.itemNumber)  = lower(:keyword)")
    Optional<NewsMediaRecord> fetchRecordByItemNumber(@Param("keyword") String keyWord);

    @Query("From NewsMediaRecord d where lower(d.title) LIKE lower(concat('%',:keyword,'%')) OR lower(d.summary) LIKE lower(concat('%',:keyword,'%'))")
    List<NewsMediaRecord> fetchRecordByKeywordInTitleAndAbstract(@Param("keyword") String titleKeyword); // Note that abstract is summary in the ORM model for newsmedia

    @Query("From NewsMediaRecord d where lower(d.author)  LIKE lower(concat('%',:keyword,'%'))")
    List<NewsMediaRecord> fetchRecordByAuthor(@Param("keyword") String keyWord);

    @Query("From NewsMediaRecord d where lower(d.publicationTitle)  LIKE lower(concat('%',:keyword,'%'))")
    List<NewsMediaRecord> fetchRecordByPublicationTitle(@Param("keyword") String keyWord);

    @Query("From NewsMediaRecord d where lower(d.place) LIKE lower(concat('%',:keyword,'%')) OR lower(d.section) LIKE lower(concat('%',:keyword,'%'))")
    List<NewsMediaRecord> fetchRecordByKeywordInPlaceAndSection(@Param("keyword") String titleKeyword);

}
