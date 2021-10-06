package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.FileStoreOperationResult;
import com.example.demo.Model.DataRecordFormEntity;
import com.example.demo.Model.WebsiteRecord;
import com.example.demo.Service.FileService;
import com.example.demo.Service.RecordCreationService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/submitrecord")
public class RecordUploadController {

    @Value("${fileservice.filestoragelocation}")
    private String uploadLocation;
    @Value("${fileservice.accessstoragelocation}")
    private String accessFileStorageLocation;
    @Value("${fileservice.archive}")
    private String archive;
    @Value("${fileservice.website}")
    private String website;

    @Autowired
    private RecordCreationService recordCreationService;
    @Autowired
    private FileService fileService;

    @GetMapping("/recordType")
    public ModelAndView getSubmissionType(){
        ModelAndView modelAndView = new ModelAndView("uploadRecordType");
        return modelAndView;
    }

    @GetMapping("/informationform/archive")
    public String getArchiveSubmissionForm(Model model){
        model.addAttribute("dataRecordFormEntity", new DataRecordFormEntity());
        return "archiveRecordEntryForm";
    }

    @GetMapping("/informationform/website")
    public String getWebsiteSubmissionForm(Model model){
        model.addAttribute("dataRecordFormEntity", new DataRecordFormEntity());
        return "websiteEntryForm";
    }

    @PostMapping("/submitinformation/archive")
    public ModelAndView getArchiveRecordInformationFromUser(@ModelAttribute("dataRecordFormEntity")
                                                                 DataRecordFormEntity submittedFormData){

        ModelAndView modelAndView = new ModelAndView("archiveRecordUploadResult");
        MultipartFile multipartFile = submittedFormData.artifactFile;
        ArchivalRecord archivalRecord = submittedFormData.archivalRecord;
        FileStoreOperationResult preservationResult = new FileStoreOperationResult();
        FileStoreOperationResult accessResult = new FileStoreOperationResult();

        preservationResult.result = false;
        accessResult.result = false;

        if(!multipartFile.isEmpty()){
            preservationResult = storePreservationCopy(multipartFile, "archive");
            accessResult = storeAccessCopy(preservationResult.getFileStorageLocation());

            if(preservationResult.result && accessResult.result){
                archivalRecord.setTiffFileName(FilenameUtils.getName(preservationResult.getFileStorageLocation()));
                archivalRecord.setTiffFileLocation(preservationResult.getFileStorageLocation());
                archivalRecord.setPdfFileLocation(accessResult.getFileStorageLocation());
                archivalRecord.setArtifact_MD5_Hash(getMD5Hash(preservationResult.getFileStorageLocation()));
            }
            else{
                modelAndView.addObject("status","FAILED");
                if(preservationResult.result){
                    fileService.deleteFromLocalStorage(preservationResult.getFileStorageLocation());
                    modelAndView.addObject("status_code","01");
                }
                if(accessResult.result){
                    fileService.deleteFromLocalStorage(accessResult.getFileStorageLocation());
                    modelAndView.addObject("status_code","02");
                }
                return modelAndView;
            }
        }

        archivalRecord.setRecordCreationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        recordCreationService.createArchivalRecord(archivalRecord);
        modelAndView.addObject("status","SUCCESS");
        modelAndView.addObject("type","archive");
        modelAndView.addObject("result", archivalRecord);
        return modelAndView;
    }

    @PostMapping("/submitinformation/website")
    public ModelAndView getWebsiteRecordInformationFromUser(@ModelAttribute("dataRecordFormEntity")
                                                             DataRecordFormEntity submittedFormData){

        ModelAndView modelAndView = new ModelAndView("websiteRecordUploadResult");
        MultipartFile multipartFile = submittedFormData.artifactFile;
        WebsiteRecord websiteRecord = submittedFormData.websiteRecord;
        FileStoreOperationResult preservationResult = new FileStoreOperationResult();
        FileStoreOperationResult accessResult = new FileStoreOperationResult();

        preservationResult.result = false;
        accessResult.result = false;

        if(!multipartFile.isEmpty()){
            preservationResult = storePreservationCopy(multipartFile, "website");
            accessResult = storeAccessCopy(preservationResult.getFileStorageLocation());

            if(preservationResult.result && accessResult.result){
                websiteRecord.setArtifactFileName(FilenameUtils.getName(preservationResult.getFileStorageLocation()));
                websiteRecord.setArtifactFileLocation(preservationResult.getFileStorageLocation());
                websiteRecord.setAccessFileLocation(accessResult.getFileStorageLocation());
                websiteRecord.setPreservationHash(getMD5Hash(preservationResult.getFileStorageLocation()));
            }
            else{
                modelAndView.addObject("status","FAILED");
                if(preservationResult.result){
                    fileService.deleteFromLocalStorage(preservationResult.getFileStorageLocation());
                    modelAndView.addObject("status_code","01");
                }
                if(accessResult.result){
                    fileService.deleteFromLocalStorage(accessResult.getFileStorageLocation());
                    modelAndView.addObject("status_code","02");
                }
                return modelAndView;
            }
        }


        websiteRecord.setCreationTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        recordCreationService.createWebsiteRecord(websiteRecord);
        modelAndView.addObject("status","SUCCESS");
        modelAndView.addObject("type","archive");
        modelAndView.addObject("result", websiteRecord.getId());
        return modelAndView;

    }

    public FileStoreOperationResult storePreservationCopy(MultipartFile multipartFile, String recordType){
        FileStoreOperationResult fileStoreOperationResult = new FileStoreOperationResult();

        String location = uploadLocation;

        if(recordType.equalsIgnoreCase("archive")){
            location += archive;
        }
        else if(recordType.equalsIgnoreCase("website")){
            location += website;
        }
        else if(recordType.equalsIgnoreCase("newsmedia")){

        }
        else if(recordType.equalsIgnoreCase("bibliographic")){

        }
        try{
            Path path = Paths.get(location + multipartFile.getOriginalFilename());
            multipartFile.transferTo(path);
            fileStoreOperationResult.setResult(true);
            fileStoreOperationResult.setFileStorageLocation(path.toString());
            return fileStoreOperationResult;
        }catch (Exception e){
            e.printStackTrace();
            fileStoreOperationResult.setResult(false);
            return fileStoreOperationResult;
        }
    }

    public FileStoreOperationResult storeAccessCopy(String preservationCopyPath){

        File preservationCopy = new File(preservationCopyPath);
        String filename = preservationCopy.getName();
        String fileExtension = FilenameUtils.getExtension(filename);
        String fileNameWithoutExtension = FilenameUtils.removeExtension(filename);

        return fileService.createAccessCopyInLocalStorage(preservationCopy, accessFileStorageLocation, fileNameWithoutExtension, fileExtension);

    }

    public String getMD5Hash(String filePath){
        try{
            InputStream is = Files.newInputStream(Paths.get(filePath));
            String preservationMd5Hash = DigestUtils.md5Hex(is);
            return preservationMd5Hash;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
