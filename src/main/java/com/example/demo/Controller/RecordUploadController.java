package com.example.demo.Controller;

import com.example.demo.Model.ArchivalRecord;
import com.example.demo.Model.FileStoreOperationResult;
import com.example.demo.Model.DataRecordFormEntity;
import com.example.demo.Service.FileService;
import com.example.demo.Service.RecordCreationService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/submitrecord")
public class RecordUploadController {

    @Value("${fileservice.filestoragelocation}")
    private String uploadLocation;

    @Value("${fileservice.accessstoragelocation}")
    private String accessFileStorageLocation;

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
        return "recordEntryForm";
    }

    @GetMapping("/informationform/website")
    public String getWebsiteSubmissionForm(Model model){
        model.addAttribute("dataRecordFormEntity", new DataRecordFormEntity());
        return "websiteEntryForm";
    }

    @PostMapping("/submitinformation")
    public ModelAndView getRecordInformationFromUser(@ModelAttribute("dataRecordFormEntity")
                                                                 DataRecordFormEntity submittedFormData){

        ModelAndView modelAndView = new ModelAndView("singlerecorduploadresult");
        MultipartFile multipartFile = submittedFormData.artifactFile;
        ArchivalRecord archivalRecord = submittedFormData.archivalRecord;
        FileStoreOperationResult preservationResult = new FileStoreOperationResult();
        FileStoreOperationResult accessResult = new FileStoreOperationResult();

        preservationResult.result = false;
        accessResult.result = false;

        if(!multipartFile.isEmpty()){
            preservationResult = storePreservationCopy(multipartFile);
            accessResult = storeAccessCopy(preservationResult.getFileStorageLocation());

            if(preservationResult.result && accessResult.result){
                archivalRecord.setTiffFileName(FilenameUtils.getName(preservationResult.getFileStorageLocation()));
                archivalRecord.setTiffFileLocation(preservationResult.getFileStorageLocation());
                archivalRecord.setPdfFileLocation(accessResult.getFileStorageLocation());
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


        recordCreationService.createRecord(archivalRecord);
        modelAndView.addObject("status","SUCCESS");
        modelAndView.addObject("result", archivalRecord);
        return modelAndView;

//        boolean fileUploadResult = false;
//        boolean recordCreationResult = false;
//        FileStoreOperationResult accessFileStorageResult = new FileStoreOperationResult();
//        String currentArtifactFilePath;
//        String destinationAccessFileFolder;
//
//        MultipartFile multipartFile;
//        DataRecord dataRecord;
//        // I am checking whether the uploaded files can be successfully stored or not.
//        try{
//            multipartFile = submittedFormData.artifactFile;
//            currentArtifactFilePath = uploadLocation+"/"+multipartFile.getOriginalFilename();
//            destinationAccessFileFolder = accessFileStorageLocation +"/";
//            if(!multipartFile.isEmpty()){
//                fileUploadResult = fileService.uploadFileToLocalStorage(multipartFile, destinationAccessFileFolder);
//                accessFileStorageResult = fileService.createAccessCopyInLocalStorage(multipartFile, destinationAccessFileFolder);
//                if(!fileUploadResult){
//                    // Notify that upload failed. and return
//                    modelAndView.addObject("status","FAILED");
//                    modelAndView.addObject("status_code","00");
//                    return modelAndView;
//                }
//                if(!accessFileStorageResult.result){
//                    // Notify that access copy storage failed. and return
//                    modelAndView.addObject("status","FAILED");
//                    modelAndView.addObject("status_code","08");
//                    fileService.deleteFromLocalStorage(currentArtifactFilePath);
//                    return modelAndView;
//                }
//            }
//            else{
//                // Do nothing. There is a possibility that file is empty. It means a record is uploaded without any corresponding files.
//            }
//
//        }catch(Exception e){
//            // Notify that exception happened and they should contact administrator. and return
//            modelAndView.addObject("status","FAILED");
//            modelAndView.addObject("status_code","01");
//            return modelAndView;
//        }
//
//
//        try{
//            dataRecord = submittedFormData.dataRecord;
//            if(dataRecord == null){
//                System.out.println("The form data is resulting in null data record. Please contact administrator over possible issues.");
//                modelAndView.addObject("status","FAILED");
//                modelAndView.addObject("status_code","10");
//
//                if(!multipartFile.isEmpty()){
//                    fileService.deleteFromLocalStorage(currentArtifactFilePath);
//                }
//
//                return modelAndView;
//
//            }
//            else{
//                if(fileUploadResult){
//                    dataRecord.setTiffFileName(multipartFile.getOriginalFilename());
//                    dataRecord.setTiffFileLocation(currentArtifactFilePath);
//                    InputStream is = Files.newInputStream(Paths.get(currentArtifactFilePath));
//                    String preservationMd5Hash = DigestUtils.md5Hex(is);
//                    dataRecord.setArtifact_MD5_Hash(preservationMd5Hash);
//                }
//                if(accessFileStorageResult.result){
////                    if(FilenameUtils.getExtension(multipartFile.getOriginalFilename()).equalsIgnoreCase("pdf")){
////                        dataRecord.setPdfFileLocation(accessFileStorageLocation+"/access_copy"+ FilenameUtils.getBaseName(multipartFile.getOriginalFilename())+".pdf");
////                    }
////                    else{
////                        dataRecord.setPdfFileLocation(accessFileStorageLocation+"/"+ FilenameUtils.getBaseName(multipartFile.getOriginalFilename())+".pdf");
////                    }
//                    dataRecord.setPdfFileLocation(accessFileStorageResult.fileStorageLocation);
//
//                }
//
//                recordCreationResult = recordCreationService.createRecord(dataRecord);
//
//                if(!recordCreationResult){
//                    // Notify that record creation ran into some issues.
//                    modelAndView.addObject("status","FAILED");
//                    modelAndView.addObject("status_code","11");
//
//                    if(fileUploadResult){
//                        fileService.deleteFromLocalStorage(currentArtifactFilePath);
//                    }
//
//                    return modelAndView;
//                }
//            }
//
//        }catch(Exception e){
//            // Notify that there is an issue and probably administrator should be hailed on the horn
//            modelAndView.addObject("status","FAILED");
//            modelAndView.addObject("status_code","12");
//
//            return modelAndView;
//        }
//
//        modelAndView.addObject("status","SUCCESS");
//        modelAndView.addObject("result", dataRecord);


    }

    public FileStoreOperationResult storePreservationCopy(MultipartFile multipartFile){
        FileStoreOperationResult fileStoreOperationResult = new FileStoreOperationResult();
        try{
            Path path = Paths.get(uploadLocation+multipartFile.getOriginalFilename());
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

}
