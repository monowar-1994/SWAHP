package com.example.demo.Repository;


import com.example.demo.Model.WebsiteRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebsiteRecordRepository extends PagingAndSortingRepository<WebsiteRecord, Long> {

    @Query("From WebsiteRecord d where lower(d.itemNumber)  = lower(:keyword)")
    Optional<WebsiteRecord> fetchRecordByItemNumber(@Param("keyword") String keyWord);

    @Query("From WebsiteRecord d where lower(d.title) LIKE lower(concat('%',:keyword,'%')) OR lower(d.summary) LIKE lower(concat('%',:keyword,'%'))")
    List<WebsiteRecord> fetchRecordByKeywordInTitleAndAbstract(@Param("keyword") String titleKeyword); // Note that abstract is summary in the ORM model for website

    @Query("From WebsiteRecord d where lower(d.creator) LIKE lower(concat('%',:keyword,'%')) OR lower(d.collection) LIKE lower(concat('%',:keyword,'%'))")
    List<WebsiteRecord> fetchRecordByCreatorAndCollection(@Param("keyword") String keyWord);

    @Query("From WebsiteRecord d where lower(d.tags)  LIKE lower(concat('%',:keyword,'%'))")
    List<WebsiteRecord> fetchRecordByTags(@Param("keyword") String keyWord);
}
