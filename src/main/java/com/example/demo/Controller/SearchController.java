package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;
import com.example.demo.Service.RecordFetchingService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private RecordFetchingService recordFetchingService;

    @GetMapping("/queryForm")
    public ModelAndView getSearchForm(){
        ModelAndView returnModelAndView = new ModelAndView("searchArchive");
        return returnModelAndView;
    }

    @PostMapping("/byID")
    public ModelAndView getSearchedItembyId(@RequestParam("item_id") String itemID){
        ModelAndView modelAndView = new ModelAndView("showArchiveRecord");
        String itemId = itemID.trim();
        Long queryId;
        if(itemId.length()==0){
            modelAndView.addObject("Status","NotFound");
        }
        else{
            try{
                queryId = Long.parseLong(itemId);
                Optional<ArchivalRecord> matchedRecord = recordFetchingService.fetchArchiveRecord(queryId);
                if(matchedRecord.isEmpty()){
                    modelAndView.addObject("Status","NotFound");
                    System.out.println("Did not find the record");
                }
                else{
                    modelAndView.addObject("Status","Found");
                    modelAndView.addObject("resultRecord", matchedRecord.get());
                    System.out.println("Got the record");
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
                ex.printStackTrace();
            }
        }
        return modelAndView;
    }

    @PostMapping("/byItemNumber")
    public ModelAndView getSearchedItembyItemNumber(@RequestParam("item_number") String itemNumber){
        ModelAndView modelAndView = new ModelAndView("showArchiveRecord");
        String queryNumber = itemNumber.trim();
        if(queryNumber.length() == 0){
            modelAndView.addObject("Status","NotFound");
        }
        else{
            try{
                Optional<ArchivalRecord> matchedRecord =  recordFetchingService.fetchRecordByItemNumberInArchive(queryNumber);
                if(!matchedRecord.isEmpty()){
                    modelAndView.addObject("Status","Found");
                    modelAndView.addObject("resultRecord", matchedRecord.get());
                    System.out.println("Got the record");
                }
                else{
                    modelAndView.addObject("Status","NotFound");
                    System.out.println("Did not find the record");
                }
            }catch(Exception ex){
                modelAndView.addObject("Status","NotFound");
                ex.printStackTrace();
            }
        }

        return modelAndView;
    }

    @PostMapping("/byTitleKeyword")
    public ModelAndView getSearchedItemByKeywordInTitleAndTags(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInTitleAndTagsInArchive(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }

    @PostMapping("/bySummaryAndNotes")
    public ModelAndView getSearchedItemByKeywordInSummaryAndNotes(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInSummaryAndNotesInArchive(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }

    @PostMapping("/byTypeAndGenre")
    public ModelAndView getSearchedItemByKeywordInTypeAndGenre(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInTypeAndGenreInArchive(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }

    @PostMapping("/byContributorsAndCopyright")
    public ModelAndView getSearchedItemByKeywordInContributorsAndCopyright(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInContributionAndCopyrightInArchive(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }

    @PostMapping("/website/byItemNumber")
    public ModelAndView getSearchedWebsiteByItemNumber(@RequestParam("item_number") String itemNumber){
        ModelAndView modelAndView = new ModelAndView("showWebsiteRecord");
        String queryItemNumber = itemNumber.trim();
        if(queryItemNumber.length()==0){
            modelAndView.addObject("Status","NotFound");
            System.out.println("No length");
        }
        else{
            try{
                Optional<WebsiteRecord> matchedRecord = recordFetchingService.fetchRecordByItemNumberInWebsite(queryItemNumber);
                if(!matchedRecord.isEmpty()){
                    modelAndView.addObject("Status","Found");
                    modelAndView.addObject("resultRecord", matchedRecord.get());
                }
                else{
                    modelAndView.addObject("Status","NotFound");
                    System.out.println("Matched Record is empty");
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
                System.out.println("Caught an exception");
                ex.printStackTrace();
            }
        }
        return modelAndView;
    }

    @PostMapping("/website/byAbstractAndTitle")
    public ModelAndView getSearchedWebsiteByAbstractAndTitle(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedWebsiteRecords");
        String queryKeyWord = keyWord.trim();
        if(queryKeyWord.length() == 0){
            modelAndView.addObject("Status", "NotFound");
        }
        else{
            try{
                List<WebsiteRecord> matchedRecords = recordFetchingService.fetchWebsiteRecordByKeywordInAbstractAndTitle(queryKeyWord);
                if(matchedRecords == null){
                    modelAndView.addObject("Status", "NotFound");
                }
                else{
                    modelAndView.addObject("records", matchedRecords);
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
            }
        }
        return modelAndView;
    }

    @PostMapping("/website/byCreatorAndCollection")
    public ModelAndView getSearchedWebsiteByCreatorAndCollection(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedWebsiteRecords");
        String queryKeyWord = keyWord.trim();
        if(queryKeyWord.length() == 0){
            modelAndView.addObject("Status", "NotFound");
        }
        else{
            try{
                List<WebsiteRecord> matchedRecords = recordFetchingService.fetchWebsiteRecordByKeywordInCreatorAndCollection(queryKeyWord);
                if(matchedRecords == null){
                    modelAndView.addObject("Status", "NotFound");
                }
                else{
                    modelAndView.addObject("Status", "Found");
                    modelAndView.addObject("records", matchedRecords);
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
            }
        }
        return modelAndView;
    }

    @PostMapping("/website/byTags")
    public ModelAndView getSearchedWebsiteByTags(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showMatchedWebsiteRecords");
        String queryKeyWord = keyWord.trim();
        if(queryKeyWord.length() == 0){
            modelAndView.addObject("Status", "NotFound");
        }
        else{
            try{
                List<WebsiteRecord> matchedRecords = recordFetchingService.fetchWebsiteRecordByKeywordInTags(queryKeyWord);
                if(matchedRecords == null){
                    modelAndView.addObject("Status", "NotFound");
                }
                else{
                    modelAndView.addObject("Status", "Found");
                    modelAndView.addObject("records", matchedRecords);
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
            }
        }
        return modelAndView;
    }

    @PostMapping("/newsmedia/byItemNumber")
    public ModelAndView getSearchedNewsMediaByItemNumber(@RequestParam("item_number") String itemNumber){
        ModelAndView modelAndView = new ModelAndView("showNewsMediaRecord");
        String queryNumber = itemNumber.trim();
        if(queryNumber.length() == 0){
            modelAndView.addObject("Status","NotFound");
            System.out.println("Query Number Length is 0");
        }
        else{
            try{
                Optional<NewsMediaRecord> matchedRecord = recordFetchingService.fetchRecordByItemNumberInNewsMedia(queryNumber);
                if(matchedRecord.isEmpty()){
                    modelAndView.addObject("Status","NotFound");
                    System.out.println("Matched Record is Empty");
                }
                else{
                    modelAndView.addObject("Status","Found");
                    modelAndView.addObject("resultRecord", matchedRecord.get());
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
                ex.printStackTrace();
            }
        }
        return modelAndView;
    }

    @PostMapping("/newsmedia/byTitleAndAbstract")
    public ModelAndView getSearchedNewsMediaByTitleAndAbstract(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedNewsMediaRecords");
        try{
            List<NewsMediaRecord> matchedRecords = recordFetchingService.fetchNewsMediaRecordByKeywordInTitleAndAbstract(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/newsmedia/byAuthor")
    public ModelAndView getSearchedNewsMediaByAuthor(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedNewsMediaRecords");
        try{
            List<NewsMediaRecord> matchedRecords = recordFetchingService.fetchNewsMediaRecordByKeywordInAuthor(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/newsmedia/byPublicationTitle")
    public ModelAndView getSearchedNewsMediaByPublicationTitle(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedNewsMediaRecords");
        try{
            List<NewsMediaRecord> matchedRecords = recordFetchingService.fetchNewsMediaRecordByKeywordInPublicationTitle(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/newsmedia/byPlaceAndSection")
    public ModelAndView getSearchedNewsMediaByPlaceAndSection(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedNewsMediaRecords");
        try{
            List<NewsMediaRecord> matchedRecords = recordFetchingService.fetchNewsMediaRecordByKeywordInPlaceAndSection(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/bibliography/byItemNumber")
    public ModelAndView getSearchedBibliographyByItemNumber(@RequestParam("item_number") String itemNumber){
        ModelAndView modelAndView = new ModelAndView("showBibliographyRecord");
        String queryNumber = itemNumber.trim();
        if(queryNumber.length() == 0){
            modelAndView.addObject("Status","NotFound");
        }
        else{
            try{
                Optional<BibliographicRecord> matchedRecord = recordFetchingService.fetchRecordByItemNumberInBibliography(queryNumber);
                if(matchedRecord.isEmpty()){
                    modelAndView.addObject("Status","NotFound");
                }
                else{
                    modelAndView.addObject("Status","Found");
                    modelAndView.addObject("resultRecord", matchedRecord.get());
                }
            }catch (Exception ex){
                modelAndView.addObject("Status","NotFound");
            }
        }
        return modelAndView;
    }

    @PostMapping("/bibliography/byTitleAndAbstract")
    public ModelAndView getSearchedBibliographyByTitleAndAbstract(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedBibliographicRecords");
        try{
            List<BibliographicRecord> matchedRecords = recordFetchingService.fetchBibliographyRecordByKeywordInTitleAndAbstract(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/bibliography/byAuthor")
    public ModelAndView getSearchedBibliographyByAuthor(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedBibliographicRecords");
        try{
            List<BibliographicRecord> matchedRecords = recordFetchingService.fetchBibliographyRecordByKeywordInAuthor(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/bibliography/byPublicationTitle")
    public ModelAndView getSearchedBibliographyByPublicationTitle(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedBibliographicRecords");
        try{
            List<BibliographicRecord> matchedRecords = recordFetchingService.fetchBibliographicRecordByKeywordInPublicationTitle(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/bibliography/byPlaceAndPublication")
    public ModelAndView getSearchedBibliographyByPlaceAndPublication(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedBibliographicRecords");
        try{
            List<BibliographicRecord> matchedRecords = recordFetchingService.fetchBibliographicRecordByKeywordInPlaceAndPublication(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @PostMapping("/bibliography/byAdditionalInformation")
    public ModelAndView getSearchedBibliographyByAdditionalInformation(@RequestParam("search_keyword") String keyword){
        ModelAndView modelAndView = new ModelAndView("showMatchedBibliographicRecords");
        try{
            List<BibliographicRecord> matchedRecords = recordFetchingService.fetchBibliographicRecordByKeywordInAdditionalInformation(keyword.trim());
            if(matchedRecords == null){
                modelAndView.addObject("Status","NotFound");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("records",matchedRecords);
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }


}
