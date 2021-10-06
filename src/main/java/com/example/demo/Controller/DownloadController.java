package com.example.demo.Controller;

import com.example.demo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private FileService fileService;

    @GetMapping("/byLoc")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileLocation") String fileLocation,
                                                 HttpServletRequest request){
        Resource fileResource = null;
        try{
            fileResource = fileService.downloadFileFromLocalStorage(fileLocation);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String contentType = null;
        if(fileResource != null){
            try{
                contentType = request.getServletContext().getMimeType(fileResource.getFile().getAbsolutePath());
            }catch(Exception e){
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            }
        }
        // Default content type
        if(contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileResource.getFilename()+"\"")
                .body(fileResource);

    }

}
