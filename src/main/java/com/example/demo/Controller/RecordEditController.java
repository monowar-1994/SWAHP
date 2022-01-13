package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.BibliographicRecord;
import com.example.demo.Model.NewsMediaRecord;
import com.example.demo.Model.WebsiteRecord;
import com.example.demo.Service.FileService;
import com.example.demo.Service.RecordDeletionService;
import com.example.demo.Service.RecordFetchingService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/{recordType}/byId")
    public ModelAndView getEditForm(@RequestParam("recordID") String recordID, @PathVariable("recordType") String type){
        String recordType = type.trim();
        if(recordType.length()!=0 && recordType.equalsIgnoreCase("archive")){
            return deliverArchivalRecordEditForm(recordID);
        }
        else if(recordType.length()!=0 && recordType.equalsIgnoreCase("newsmedia")){
            return deliverNewsMediaRecordEditForm(recordID);
        }
        else if(recordType.length()!=0 && recordType.equalsIgnoreCase("website")){
            return deliverWebsiteRecordEditForm(recordID);
        }
        else if(recordType.length()!=0 && recordType.equalsIgnoreCase("bibliography")){
            return deliverBibliographicRecordEditForm(recordID);
        }
        return null;
    }

    public ModelAndView deliverArchivalRecordEditForm(String recordId){
        ModelAndView formModelAndView = new ModelAndView("editArchiveRecordForm");
        Long id;
        try{
            id = Long.parseLong(recordId.trim());
        }catch (Exception e){
            formModelAndView.addObject("Status","FAIL");
            formModelAndView.addObject("Status_code","Parsing error happened on ID.");
            return formModelAndView;
        }

        Optional<ArchivalRecord> record;

        try{
            record = recordFetchingService.fetchArchiveRecord(id);

            if(record.isEmpty()){
                formModelAndView.addObject("Status","FAIL");
                formModelAndView.addObject("Status_code","Could not find any archival record associated with the id.");
            }
            else{
                formModelAndView.addObject("Status","SUCCESS");
                formModelAndView.addObject("record",record.get());
            }
            return formModelAndView;
        }catch (Exception e){
            formModelAndView.addObject("Status","FAIL");
            formModelAndView.addObject("Status_code","Unknown error occurred. Contact sys admin.");
            return formModelAndView;
        }
    }

    public ModelAndView deliverWebsiteRecordEditForm(String recordId){
        ModelAndView modelAndView = new ModelAndView("editWebsiteRecordForm");
        Long id;
        try{
            id = Long.parseLong(recordId.trim());
        }catch (Exception e){
            modelAndView.addObject("Status","FAIL");
            modelAndView.addObject("Status_code","Parsing error happened on ID.");
            return modelAndView;
        }

        Optional<WebsiteRecord> record;

        try{
            record = recordFetchingService.fetchWebsiteRecord(id);

            if(record.isEmpty()){
                modelAndView.addObject("Status","FAIL");
                modelAndView.addObject("Status_code","Could not find any website record associated with the id.");
            }
            else{
                modelAndView.addObject("Status","SUCCESS");
                modelAndView.addObject("websiteRecord",record.get());
            }
            return modelAndView;
        }catch (Exception e){
            modelAndView.addObject("Status","FAIL");
            modelAndView.addObject("Status_code","Unknown error occurred. Contact sys admin.");
            return modelAndView;
        }
    }

    public ModelAndView deliverNewsMediaRecordEditForm(String recordId){
        ModelAndView modelAndView = new ModelAndView("editNewsMediaRecordForm");
        Long id;
        try{
            id = Long.parseLong(recordId.trim());
        }catch (Exception e){
            modelAndView.addObject("Status","FAIL");
            modelAndView.addObject("Status_code","Parsing error happened on ID.");
            return modelAndView;
        }

        Optional<NewsMediaRecord> record;

        try{
            record = recordFetchingService.fetchNewsMediaRecord(id);

            if(record.isEmpty()){
                modelAndView.addObject("Status","FAIL");
                modelAndView.addObject("Status_code","Could not find any newsmedia record associated with the id.");
            }
            else{
                modelAndView.addObject("Status","SUCCESS");
                modelAndView.addObject("record",record.get());
            }
            return modelAndView;
        }catch (Exception e){
            modelAndView.addObject("Status","FAIL");
            modelAndView.addObject("Status_code","Unknown error occurred. Contact sys admin.");
            return modelAndView;
        }
    }

    public ModelAndView deliverBibliographicRecordEditForm(String recordId){
        ModelAndView modelAndView = new ModelAndView("editBibliographicRecordForm");
        Long id;
        try{
            id = Long.parseLong(recordId.trim());
        }catch (Exception e){
            modelAndView.addObject("Status","FAIL");
            modelAndView.addObject("Status_code","Parsing error happened on ID.");
            return modelAndView;
        }

        Optional<BibliographicRecord> record;

        try{
            record = recordFetchingService.fetchBibliographicRecord(id);

            if(record.isEmpty()){
                modelAndView.addObject("Status","FAIL");
                modelAndView.addObject("Status_code","Could not find any bibliographic record associated with the id.");
            }
            else{
                modelAndView.addObject("Status","SUCCESS");
                modelAndView.addObject("record",record.get());
            }
            return modelAndView;
        }catch (Exception e){
            modelAndView.addObject("Status","FAIL");
            modelAndView.addObject("Status_code","Unknown error occurred. Contact sys admin.");
            return modelAndView;
        }
    }

    @PostMapping("/archive/request")
    public ModelAndView editArchivalRecordAndConfirm(@ModelAttribute("record") ArchivalRecord editedRecord){
        editedRecord.setRecordModificationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ArchivalRecord postEditRecord = recordFetchingService.saveEditedArchiveRecord(editedRecord);
        ModelAndView editRecordRequestResultView = new ModelAndView("editArchiveRecordResult");
        if(postEditRecord == null){
            editRecordRequestResultView.addObject("Status","FAIL");
            editRecordRequestResultView.addObject("Status_code:","Unknown error occurred while trying to save the archival record.");
        }
        else{
            editRecordRequestResultView.addObject("Status","SUCCESS");
            editRecordRequestResultView.addObject("type","archive");
            editRecordRequestResultView.addObject("resultRecord", postEditRecord);
        }
        return editRecordRequestResultView;
    }

    @PostMapping("/newsmedia/request")
    public ModelAndView editNewsMediaRecordAndConfirm(@ModelAttribute("record") NewsMediaRecord editedRecord){
        editedRecord.setLastModifiedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        NewsMediaRecord postEditRecord = recordFetchingService.saveEditedNewsMediaRecord(editedRecord);
        ModelAndView editRecordRequestResultView = new ModelAndView("editNewsMediaRecordResult");
        if(postEditRecord == null){
            editRecordRequestResultView.addObject("Status","FAIL");
            editRecordRequestResultView.addObject("Status_code:","Unknown error occurred while trying to save the newsmedia record.");
        }
        else{
            editRecordRequestResultView.addObject("Status","SUCCESS");
            editRecordRequestResultView.addObject("type","newsmedia");
            editRecordRequestResultView.addObject("resultRecord", postEditRecord);
        }
        return editRecordRequestResultView;
    }

    @PostMapping("/website/request")
    public ModelAndView editWebsiteRecordAndConfirm(@ModelAttribute("record") WebsiteRecord editedRecord){
        editedRecord.setLastModifiedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        WebsiteRecord postEditRecord = recordFetchingService.saveEditedWebsiteRecord(editedRecord);
        ModelAndView editRecordRequestResultView = new ModelAndView("editWebsiteRecordResult");
        if(postEditRecord == null){
            editRecordRequestResultView.addObject("Status","FAIL");
            editRecordRequestResultView.addObject("Status_code:","Unknown error occurred while trying to save the website record.");
        }
        else{
            editRecordRequestResultView.addObject("Status","SUCCESS");
            editRecordRequestResultView.addObject("type","website");
            editRecordRequestResultView.addObject("resultRecord", postEditRecord);
        }
        return editRecordRequestResultView;
    }

    @PostMapping("/bibliography/request")
    public ModelAndView editBibliographicRecordAndConfirm(@ModelAttribute("record") BibliographicRecord editedRecord){
        editedRecord.setLastModifiedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        BibliographicRecord postEditRecord = recordFetchingService.saveEditedBibliographicRecord(editedRecord);
        ModelAndView editRecordRequestResultView = new ModelAndView("editBibliographicRecordResult");
        if(postEditRecord == null){
            editRecordRequestResultView.addObject("Status","FAIL");
            editRecordRequestResultView.addObject("Status_code:","Unknown error occurred while trying to save the bibliographic record.");
        }
        else{
            editRecordRequestResultView.addObject("Status","SUCCESS");
            editRecordRequestResultView.addObject("type","bibliography");
            editRecordRequestResultView.addObject("resultRecord", postEditRecord);
        }
        return editRecordRequestResultView;
    }

    @PostMapping("/delete")
    public ModelAndView deleteRecordFromRepository(@RequestParam("id") String deleteId,
                                                   @RequestParam("recordType") String recordType){

        String modelName = "recorddeleteresult";
        Long id = Long.parseLong(deleteId);
        if(recordType.equalsIgnoreCase("archive")){
            Optional<ArchivalRecord> recordToDelete =  recordFetchingService.fetchArchiveRecord(id);
            return executeArchiveRecordDelete(recordToDelete, id, modelName);
        }
        else if(recordType.equalsIgnoreCase("website")){
            Optional<WebsiteRecord> recordToDelete = recordFetchingService.fetchWebsiteRecord(id);
            return executeWebsiteRecordDelete(recordToDelete, id, modelName);
        }
        else if(recordType.equalsIgnoreCase("newsmedia")){
            Optional<NewsMediaRecord> recordToDelete = recordFetchingService.fetchNewsMediaRecord(id);
            return executeNewsMediaRecordDelete(recordToDelete, id, modelName);
        }
        else if(recordType.equalsIgnoreCase("bibliography")){
            Optional<BibliographicRecord> recordToDelete = recordFetchingService.fetchBibliographicRecord(id);
            return executeBibliographicRecordDelete(recordToDelete, id, modelName);
        }

        return null;


//        Optional<ArchivalRecord> recordToDelete =  recordFetchingService.fetchArchiveRecord(id);

//        boolean hasArtifactFile = true;
//        String artifactFilePath = recordToDelete.get().getTiffFileLocation();
//        if( artifactFilePath == null || artifactFilePath.length() == 0){
//            hasArtifactFile = false;
//        }
//
//        boolean hasAccessFile = true;
//        String accessFilePath = recordToDelete.get().getPdfFileLocation();
//        if(accessFilePath == null || accessFilePath.length() == 0){
//            hasAccessFile = false;
//        }
//
//        boolean artifactDeleteResult = false;
//        boolean accessDeleteResult = false;
//
//        if(hasArtifactFile) { artifactDeleteResult = fileService.deleteFromLocalStorage(artifactFilePath); }
//        if(hasAccessFile){ accessDeleteResult = fileService.deleteFromLocalStorage(accessFilePath); }
//
//        boolean recordDeleteResult = false;
//        if( (hasArtifactFile && artifactDeleteResult) && (hasAccessFile && accessDeleteResult)){
//            recordDeleteResult = recordDeletionService.deleteRecordFromRepository(id);
//        }
//        else if(!hasArtifactFile && !hasAccessFile){
//            recordDeleteResult = recordDeletionService.deleteRecordFromRepository(id);
//        }
//
//        return getDeleteResultModelAndView(modelName, hasArtifactFile, artifactDeleteResult, hasAccessFile, accessDeleteResult, recordDeleteResult);
    }

    public ModelAndView executeArchiveRecordDelete(Optional<ArchivalRecord> recordToDelete, Long id, String modelName){
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
        if((hasArtifactFile && artifactDeleteResult) && (hasAccessFile && accessDeleteResult)){
            recordDeleteResult = recordDeletionService.deleteArchiveRecordFromRepository(id);
        }
        else if(!hasArtifactFile && !hasAccessFile){
            recordDeleteResult = recordDeletionService.deleteArchiveRecordFromRepository(id);
        }

        return getDeleteResultModelAndView(modelName, hasArtifactFile, artifactDeleteResult,
                hasAccessFile, accessDeleteResult, recordDeleteResult,"archive");
    }

    public ModelAndView executeWebsiteRecordDelete(Optional<WebsiteRecord> recordToDelete, Long id, String modelName){
        boolean hasArtifactFile = true;
        String artifactFilePath = recordToDelete.get().getArtifactFileLocation();
        if(artifactFilePath == null || artifactFilePath.length() == 0){
            hasArtifactFile = false;
        }

        boolean hasAccessFile = true;
        String accessFilePath = recordToDelete.get().getAccessFileLocation();
        if(accessFilePath == null || accessFilePath.length() == 0){
            hasAccessFile = false;
        }

        boolean artifactDeleteResult = false;
        boolean accessDeleteResult = false;

        if(hasArtifactFile) { artifactDeleteResult = fileService.deleteFromLocalStorage(artifactFilePath); }
        if(hasAccessFile){ accessDeleteResult = fileService.deleteFromLocalStorage(accessFilePath); }

        boolean recordDeleteResult = false;
        if( (hasArtifactFile && artifactDeleteResult) && (hasAccessFile && accessDeleteResult)){
            recordDeleteResult = recordDeletionService.deleteWebsiteRecordFromRepository(id);
        }
        else if(!hasArtifactFile && !hasAccessFile){
            recordDeleteResult = recordDeletionService.deleteWebsiteRecordFromRepository(id);
        }

        return getDeleteResultModelAndView(modelName, hasArtifactFile, artifactDeleteResult, hasAccessFile,
                accessDeleteResult, recordDeleteResult,"website");
    }

    public ModelAndView executeNewsMediaRecordDelete(Optional<NewsMediaRecord> recordToDelete, Long id, String modelName){
        boolean hasArtifactFile = true;
        String artifactFilePath = recordToDelete.get().getArtifactFileLocation();
        if(artifactFilePath == null || artifactFilePath.length() == 0){
            hasArtifactFile = false;
        }

        boolean hasAccessFile = true;
        String accessFilePath = recordToDelete.get().getAccessFileLocation();
        if(accessFilePath == null || accessFilePath.length() == 0){
            hasAccessFile = false;
        }

        boolean artifactDeleteResult = false;
        boolean accessDeleteResult = false;

        if(hasArtifactFile) { artifactDeleteResult = fileService.deleteFromLocalStorage(artifactFilePath); }
        if(hasAccessFile){ accessDeleteResult = fileService.deleteFromLocalStorage(accessFilePath); }

        boolean recordDeleteResult = false;
        if( (hasArtifactFile && artifactDeleteResult) && (hasAccessFile && accessDeleteResult)){
            recordDeleteResult = recordDeletionService.deleteNewsMediaRecordFromRepository(id);
        }
        else if(!hasArtifactFile && !hasAccessFile){
            recordDeleteResult = recordDeletionService.deleteNewsMediaRecordFromRepository(id);
        }

        return getDeleteResultModelAndView(modelName, hasArtifactFile, artifactDeleteResult, hasAccessFile,
                accessDeleteResult, recordDeleteResult,"newsmedia");
    }

    public ModelAndView executeBibliographicRecordDelete(Optional<BibliographicRecord> recordToDelete, Long id, String modelName){
        boolean hasArtifactFile = true;
        String artifactFilePath = recordToDelete.get().getArtifactFileLocation();
        if(artifactFilePath == null || artifactFilePath.length() == 0){
            hasArtifactFile = false;
        }

        boolean hasAccessFile = true;
        String accessFilePath = recordToDelete.get().getAccessFileLocation();
        if(accessFilePath == null || accessFilePath.length() == 0){
            hasAccessFile = false;
        }

        boolean artifactDeleteResult = false;
        boolean accessDeleteResult = false;

        if(hasArtifactFile) { artifactDeleteResult = fileService.deleteFromLocalStorage(artifactFilePath); }
        if(hasAccessFile){ accessDeleteResult = fileService.deleteFromLocalStorage(accessFilePath); }

        boolean recordDeleteResult = false;
        if( (hasArtifactFile && artifactDeleteResult) && (hasAccessFile && accessDeleteResult)){
            recordDeleteResult = recordDeletionService.deleteBibliographicRecordFromRepository(id);
        }
        else if(!hasArtifactFile && !hasAccessFile){
            recordDeleteResult = recordDeletionService.deleteBibliographicRecordFromRepository(id);
        }

        return getDeleteResultModelAndView(modelName, hasArtifactFile, artifactDeleteResult, hasAccessFile,
                accessDeleteResult, recordDeleteResult,"bibliography");
    }

    public ModelAndView getDeleteResultModelAndView(String modelName, boolean hasArtifact, boolean artifactDeleted,
                                                    boolean hasAccess, boolean accessDeleted, boolean recordDeleted,
                                                    String type){
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
            modelAndView.addObject("type",type);
            modelAndView.addObject("status","SUCCESS");
        }
        else if(!hasArtifact && !hasAccess && recordDeleted){
            modelAndView.addObject("type",type);
            modelAndView.addObject("status","SUCCESS");
        }
        return modelAndView;
    }
}
