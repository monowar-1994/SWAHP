package com.example.demo.Repository;

import com.example.demo.Model.BibliographicRecord;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliographicRecordRepository extends PagingAndSortingRepository<BibliographicRecord, Long> {

}
