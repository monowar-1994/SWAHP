package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Service.FileService;
import com.example.demo.Service.RecordDeletionService;
import com.example.demo.Service.RecordFetchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/edit")
public class RecordEditController {

    @Autowired
    private RecordFetchingService recordFetchingService;

    @Autowired
    private RecordDeletionService recordDeletionService;

    @Autowired
    private FileService fileService;

    @GetMapping("")
    public ModelAndView editRequest(){
        ModelAndView editRequestPage = new ModelAndView("editRecord");
        String record_id = "";
        editRequestPage.addObject("record_id",record_id);
        return editRequestPage;
    }

    @GetMapping("/byId")
    public ModelAndView getEditForm(@RequestParam("recordID") String recordID){

        Long id;
        ModelAndView formModelAndView = new ModelAndView("editRecordRequestForm");

        try{
            id = Long.parseLong(recordID.trim());
        }catch (Exception e){
            formModelAndView.addObject("Status","FAIL");
            formModelAndView.addObject("Status_code","E00");
            return formModelAndView;
        }

        Optional<ArchivalRecord> record;

        try{
            record = recordFetchingService.fetchRecord(id);

            if(record.isEmpty()){
                formModelAndView.addObject("Status","FAIL");
                formModelAndView.addObject("Status_code","E01");
            }
            else{
                formModelAndView.addObject("Status","SUCCESS");
                formModelAndView.addObject("record",record.get());
            }
            return formModelAndView;
        }catch (Exception e){
            formModelAndView.addObject("Status","FAIL");
            formModelAndView.addObject("Status_code","E02");
            return formModelAndView;
        }
    }

    @PostMapping("/request")
    public ModelAndView editRecordAndConfirm(@ModelAttribute("record") ArchivalRecord editedRecord){
        ArchivalRecord postEditRecord = recordFetchingService.saveEditedRecord(editedRecord);
        ModelAndView editRecordRequestResultView = new ModelAndView("editRecordRequestResult");
        if(postEditRecord == null){
            editRecordRequestResultView.addObject("Status","FAIL");
            editRecordRequestResultView.addObject("Status_code:","E03");
        }
        else{
            editRecordRequestResultView.addObject("Status","SUCCESS");
            editRecordRequestResultView.addObject("resultRecord", postEditRecord);
        }
        return editRecordRequestResultView;
    }


    @PostMapping("/delete")
    public ModelAndView deleteRecordFromRepository(@RequestParam("id") String deleteId){

        String modelName = "recorddeleteresult";

        Long id = Long.parseLong(deleteId);
        Optional<ArchivalRecord> recordToDelete =  recordFetchingService.fetchRecord(id);

        boolean hasArtifactFile = true;
        String artifactFilePath = recordToDelete.get().getTiffFileLocation();
        if( artifactFilePath == null || artifactFilePath.length() == 0){
            hasArtifactFile = false;
        }

        boolean hasAccessFile = true;
        String accessFilePath = recordToDelete.get().getPdfFileLocation();
        if(accessFilePath == null || accessFilePath.length() == 0){
            hasAccessFile = false;
        }

        boolean artifactDeleteResult = false;
        boolean accessDeleteResult = false;

        if(hasArtifactFile) { artifactDeleteResult = fileService.deleteFromLocalStorage(artifactFilePath); }
        if(hasAccessFile){ accessDeleteResult = fileService.deleteFromLocalStorage(accessFilePath); }

        boolean recordDeleteResult = false;
        if( (hasArtifactFile && artifactDeleteResult) && (hasAccessFile && accessDeleteResult)){
            recordDeleteResult = recordDeletionService.deleteRecordFromRepository(id);
        }
        else if(!hasArtifactFile && !hasAccessFile){
            recordDeleteResult = recordDeletionService.deleteRecordFromRepository(id);
        }

        return getDeleteResultModelAndView(modelName, hasArtifactFile, artifactDeleteResult, hasAccessFile, accessDeleteResult, recordDeleteResult);
    }

    public ModelAndView getDeleteResultModelAndView(String modelName, boolean hasArtifact, boolean artifactDeleted,
                                                    boolean hasAccess, boolean accessDeleted, boolean recordDeleted){
        ModelAndView modelAndView = new ModelAndView(modelName);
        if(hasArtifact &&  !artifactDeleted){
            modelAndView.addObject("status","FAILED");
            modelAndView.addObject("status_code", "DEL_FAIL_01");
        }
        else if(hasAccess && !accessDeleted){
            modelAndView.addObject("status","FAILED");
            modelAndView.addObject("status_code", "DEL_FAIL_02");
        }
        else if(!recordDeleted){
            modelAndView.addObject("status","FAILED");
            modelAndView.addObject("status_code", "DEL_FAIL_03");
        }
        else if(hasArtifact && artifactDeleted && hasAccess && accessDeleted && recordDeleted){
            modelAndView.addObject("status","SUCCESS");
        }
        else if(!hasArtifact && !hasAccess && recordDeleted){
            modelAndView.addObject("status","SUCCESS");
        }
        return modelAndView;
    }
}
