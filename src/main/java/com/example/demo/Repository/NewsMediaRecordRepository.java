package com.example.demo.Repository;


import com.example.demo.Model.NewsMediaRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMediaRecordRepository extends PagingAndSortingRepository<NewsMediaRecord, Long> {

}
