package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Service.RecordFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RecordFetchingService recordFetchingService;

    @GetMapping("/getrecords")
    public String getRecords(Model model){
//        Iterable<DataRecord> records = recordFetchingService.fetchAllRecord();
//        model.addAttribute("records", records);
        return "redirect:/view/getrecords/page/1?sortField=itemNumber&sortDirection=asc";
    }

    @GetMapping("/getrecord")
    public ModelAndView getRecord(@RequestParam(name="recordID") String recordID){
        ModelAndView modelAndView = new ModelAndView("showindividualrecord");

        Long queryID;

        try{
            queryID = Long.parseLong(recordID.trim());
            System.out.println(queryID+" This is the queryID");
            Optional<ArchivalRecord> matchedRecord = recordFetchingService.fetchRecord(queryID);
            if(matchedRecord.isEmpty()){
                modelAndView.addObject("Status","NotFound");
                System.out.println("Did not find the record");
            }
            else{
                modelAndView.addObject("Status","Found");
                modelAndView.addObject("resultRecord", matchedRecord.get());
                System.out.println("Got the record");
            }
        }catch(Exception ex){
            modelAndView.addObject("Status","NotFound");
        }
        return modelAndView;
    }

    @GetMapping("/getrecords/page/{pageNo}")
    public ModelAndView getPaginatedRecords(@PathVariable (value = "pageNo") int pageNumber,
                                            @RequestParam("sortField") Optional<String> sortField,
                                            @RequestParam("sortDirection") Optional<String>  direction){
        ModelAndView modelAndView = new ModelAndView("showrecords");
        int pageSize = 15;
        Page<ArchivalRecord> page = recordFetchingService.findPaginatedDataRecords(pageNumber,pageSize,
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

}
