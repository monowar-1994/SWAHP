package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;
import com.example.demo.Service.RecordFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/view")
public class RecordViewController {

    @Value("${pageentrycount}")
    int pageEntryCount;

    @Autowired
    private RecordFetchingService recordFetchingService;

    @GetMapping("/getrecords")
    public String getRecords(Model model){
//        Iterable<DataRecord> records = recordFetchingService.fetchAllRecord();
//        model.addAttribute("records", records);
        return "redirect:/view/getrecords/page/1?sortField=itemNumber&sortDirection=asc";
    }

    @GetMapping("/getrecord/archive")
    public ModelAndView getArchiveRecord(@RequestParam(name="recordID") String recordID){
        ModelAndView modelAndView = new ModelAndView("showArchiveRecord");
        Long queryID;
        try{
            queryID = Long.parseLong(recordID.trim());
            //System.out.println(queryID+" This is the queryID");
            Optional<ArchivalRecord> matchedRecord = recordFetchingService.fetchArchiveRecord(queryID);
            if(matchedRecord.isEmpty()){
                modelAndView.addObject("Status","NotFound");
                //System.out.println("Did not find the record");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("type","archive");
                modelAndView.addObject("resultRecord", matchedRecord.get());
                //System.out.println("Got the record");
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @GetMapping("/getrecord/newsmedia")
    public ModelAndView getNewsMediaRecord(@RequestParam(name="recordID") String recordID){
        ModelAndView modelAndView = new ModelAndView("showNewsMediaRecord");
        Long queryID;
        try{
            queryID = Long.parseLong(recordID.trim());
            //System.out.println(queryID+" This is the queryID");
            Optional<NewsMediaRecord> matchedRecord = recordFetchingService.fetchNewsMediaRecord(queryID);
            if(matchedRecord.isEmpty()){
                modelAndView.addObject("Status","NotFound");
                //System.out.println("Did not find the NewsMedia record");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("type","newsmedia");
                modelAndView.addObject("resultRecord", matchedRecord.get());
                //System.out.println("Got the NewsMedia record");
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @GetMapping("/getrecord/website")
    public ModelAndView getWebsiteRecord(@RequestParam(name="recordID") String recordID){
        ModelAndView modelAndView = new ModelAndView("showWebsiteRecord");
        Long queryID;
        try{
            queryID = Long.parseLong(recordID.trim());
            //System.out.println(queryID+" This is the queryID");
            Optional<WebsiteRecord> matchedRecord = recordFetchingService.fetchWebsiteRecord(queryID);
            if(matchedRecord.isEmpty()){
                modelAndView.addObject("Status","NotFound");
                //System.out.println("Did not find the NewsMedia record");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("type","website");
                modelAndView.addObject("resultRecord", matchedRecord.get());
                //System.out.println("Got the NewsMedia record");
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @GetMapping("/getrecord/bibliography")
    public ModelAndView getBibliographicRecord(@RequestParam(name="recordID") String recordID){
        ModelAndView modelAndView = new ModelAndView("showBibliographyRecord");
        Long queryID;
        try{
            queryID = Long.parseLong(recordID.trim());
            //System.out.println(queryID+" This is the queryID");
            Optional<BibliographicRecord> matchedRecord = recordFetchingService.fetchBibliographicRecord(queryID);
            if(matchedRecord.isEmpty()){
                modelAndView.addObject("Status","NotFound");
                //System.out.println("Did not find the NewsMedia record");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("type","bibliography");
                modelAndView.addObject("resultRecord", matchedRecord.get());
                //System.out.println("Got the NewsMedia record");
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }


    @GetMapping("/getrecords/archive/page/{pageNo}")
    public ModelAndView getPaginatedArchivalRecords(@PathVariable (value = "pageNo") int pageNumber,
                                                    @RequestParam("sortField") Optional<String> sortField,
                                                    @RequestParam("sortDirection") Optional<String>  direction){
        ModelAndView modelAndView = new ModelAndView("showAllArchiveRecords");
        int pageSize = pageEntryCount;
        Page<ArchivalRecord> page = recordFetchingService.findPaginatedRecordsInArchive(pageNumber,pageSize,
                sortField.isEmpty()?null: sortField.get(),
                direction.isEmpty()?null:direction.get());
        List<ArchivalRecord> currentPageRecords = page.getContent();

        modelAndView.addObject("currentPage", pageNumber);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("reverseDirection", direction.equals("asc")?"desc":"asc");
        modelAndView.addObject("records", currentPageRecords);

        return modelAndView;
    }

    @GetMapping("/getrecords/newsmedia/page/{pageNo}")
    public ModelAndView getPaginatedNewsMediaRecords(@PathVariable (value = "pageNo") int pageNumber,
                                                    @RequestParam("sortField") Optional<String> sortField,
                                                    @RequestParam("sortDirection") Optional<String>  direction){
        ModelAndView modelAndView = new ModelAndView("showAllNewsMediaRecords");
        int pageSize = pageEntryCount;
        Page<NewsMediaRecord> page = recordFetchingService.findPaginatedRecordsInNewsMedia(pageNumber,pageSize,
                sortField.isEmpty()?null: sortField.get(),
                direction.isEmpty()?null:direction.get());
        List<NewsMediaRecord> currentPageRecords = page.getContent();
        modelAndView.addObject("currentPage", pageNumber);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("reverseDirection", direction.equals("asc")?"desc":"asc");
        modelAndView.addObject("records", currentPageRecords);
        return modelAndView;
    }

    @GetMapping("/getrecords/website/page/{pageNo}")
    public ModelAndView getPaginatedWebsiteRecords(@PathVariable (value = "pageNo") int pageNumber,
                                                   @RequestParam("sortField") Optional<String> sortField,
                                                   @RequestParam("sortDirection") Optional<String>  direction){
        ModelAndView modelAndView = new ModelAndView("showAllWebsiteRecords");
        int pageSize = pageEntryCount;
        Page<WebsiteRecord> page = recordFetchingService.findPaginatedRecordsInWebsite(pageNumber,pageSize,
                sortField.isEmpty()?null: sortField.get(),
                direction.isEmpty()?null:direction.get());
        List<WebsiteRecord> currentPageRecords = page.getContent();
        modelAndView.addObject("currentPage", pageNumber);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("reverseDirection", direction.equals("asc")?"desc":"asc");
        modelAndView.addObject("records", currentPageRecords);
        return modelAndView;
    }

    @GetMapping("/getrecords/bibliography/page/{pageNo}")
    public ModelAndView getPaginatedBibliographicRecords(@PathVariable (value = "pageNo") int pageNumber,
                                                   @RequestParam("sortField") Optional<String> sortField,
                                                   @RequestParam("sortDirection") Optional<String>  direction){
        ModelAndView modelAndView = new ModelAndView("showAllBibliographicRecords");
        int pageSize = pageEntryCount;
        Page<BibliographicRecord> page = recordFetchingService.findPaginatedRecordsInBibliography(pageNumber,pageSize,
                sortField.isEmpty()?null: sortField.get(),
                direction.isEmpty()?null:direction.get());
        List<BibliographicRecord> currentPageRecords = page.getContent();
        modelAndView.addObject("currentPage", pageNumber);
        modelAndView.addObject("totalPages", page.getTotalPages());
        modelAndView.addObject("totalItems", page.getTotalElements());
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("direction", direction);
        modelAndView.addObject("reverseDirection", direction.equals("asc")?"desc":"asc");
        modelAndView.addObject("records", currentPageRecords);
        return modelAndView;
    }



}
