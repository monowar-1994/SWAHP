package com.example.demo.Repository;

import com.example.demo.Model.WebsiteRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordWebsiteRepository extends PagingAndSortingRepository<WebsiteRecord, Long> {

}
