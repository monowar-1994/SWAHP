package com.example.demo.Service;

import com.example.demo.Model.FileStoreOperationResult;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {
    public boolean uploadFileToLocalStorage(MultipartFile multipartFile, String uploadDirectory);

    public Resource downloadFileFromLocalStorage(String absoluteFilePath) throws Exception;

    public boolean deleteFromLocalStorage(String absoluteFilePath);

    public FileStoreOperationResult createAccessCopyInLocalStorage(File preservationCopy,
                                                                   String accessCopySaveLocation,
                                                                   String fileNameWithoutExtension,
                                                                   String fileNameExtension);
}
