package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Service.RecordFetchingService;
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
        ModelAndView modelAndView = new ModelAndView("showSingleArchiveRecord");
        String itemId = itemID.trim();
        Long queryId;
        if(itemId.length()==0){
            modelAndView.addObject("Status","NotFound");
        }
        else{
            try{
                queryId = Long.parseLong(itemId);
                Optional<ArchivalRecord> matchedRecord = recordFetchingService.fetchRecord(queryId);
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
        ModelAndView modelAndView = new ModelAndView("showSingleArchiveRecord");
        String queryNumber = itemNumber.trim();
        if(queryNumber.length() == 0){
            modelAndView.addObject("Status","NotFound");
        }
        else{
            try{
                Optional<ArchivalRecord> matchedRecord =  recordFetchingService.fetchRecordByItemNumber(queryNumber);
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
        ModelAndView modelAndView = new ModelAndView("showAllArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInTitleAndTags(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }


    @PostMapping("/bySummaryAndNotes")
    public ModelAndView getSearchedItemByKeywordInSummaryAndNotes(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showAllArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInSummaryAndNotes(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }

    @PostMapping("/byTypeAndGenre")
    public ModelAndView getSearchedItemByKeywordInTypeAndGenre(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showAllArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInTypeAndGenre(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }

    @PostMapping("/byContributorsAndCopyright")
    public ModelAndView getSearchedItemByKeywordInContributorsAndCopyright(@RequestParam("search_keyword") String keyWord){
        ModelAndView modelAndView = new ModelAndView("showAllArchiveRecords");
        List<ArchivalRecord> matchedRecords = recordFetchingService.searchByKeywordInContributionAndCopyright(keyWord.trim());
        modelAndView.addObject("records",matchedRecords);
        return modelAndView;
    }
}
