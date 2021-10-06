package com.example.demo.Service.Implementation;

import com.example.demo.Model.FileStoreOperationResult;
import com.example.demo.Service.FileService;
import com.example.demo.Utility.AccessFileCreationUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImplementation implements FileService {
    /**
     * This function just saves the artifact file in its permanent space. The location is listed in the database.
     * @param multipartFile The artifact file that needs to be stored.
     * @param uploadDirectory The directory where the file will be preserved
     * @return A boolean value indicating whether the file upload succeeded or not
     */
    @Override
    public boolean uploadFileToLocalStorage(MultipartFile multipartFile, String uploadDirectory) {
        try{
            byte[] data = multipartFile.getBytes();
            Path path = Paths.get(uploadDirectory+ multipartFile.getOriginalFilename());
            Files.write(path, data);
            return true;
        }catch(IOException ie){
            ie.printStackTrace();
            return false;
        }
    }



    /**
     * This function is supposed to be user id based file retrieval. But I found a shortcut.
     * For the next iteration please use the user id based retrieval method.
     * i.e. Get the DataRecord from userDB -> get the artifact file location-> retrieve and download
     * @param absoluteFilePath UNIX file path of the resource.
     * @return The file as a URL resource.
     * @throws Exception Shows File not found exception if error occurs
     */
    @Override
    public Resource downloadFileFromLocalStorage(String absoluteFilePath) throws Exception{
        try{
            Path path = Paths.get(absoluteFilePath).normalize();
            Resource fileResource = new UrlResource(path.toUri());
            if(fileResource.exists()){
                return fileResource;
            }
            else{
                throw new FileNotFoundException("Could not find the file specified");
            }
        }catch(MalformedURLException mue){
            throw new FileNotFoundException(mue.getMessage());
        }
    }

    @Override
    public boolean deleteFromLocalStorage(String absoluteFilePath) {
        try{
            return Files.deleteIfExists(Paths.get(absoluteFilePath));
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public FileStoreOperationResult createAccessCopyInLocalStorage(File preservationCopy,
                                                                   String accessCopySaveLocation,
                                                                   String fileNameWithoutExtension,
                                                                   String fileNameExtension) {
        FileStoreOperationResult fileStoreOperationResult = new FileStoreOperationResult();
        File accessFile = null;
        try{
            if(isPdfConvertibleExtension(fileNameExtension)){
                accessFile = convertToPdf(preservationCopy, fileNameWithoutExtension, fileNameExtension, accessCopySaveLocation);
                if(accessFile == null){
                    fileStoreOperationResult.setResult(false);
                    return fileStoreOperationResult;
                }
                else{
                    fileStoreOperationResult.setFileStorageLocation(accessFile.getAbsolutePath());
                }
            }
            else{
                accessFile = new File(accessCopySaveLocation+preservationCopy.getName());
                FileUtils.copyFile(preservationCopy, accessFile);
                fileStoreOperationResult.setFileStorageLocation(accessFile.getAbsolutePath());
            }
        }catch(Exception e){
            e.printStackTrace();
            fileStoreOperationResult.setResult(false);
            return fileStoreOperationResult;
        }

        fileStoreOperationResult.setResult(true);
        return fileStoreOperationResult;
    }


//    @Override
//    public FileStoreOperationResult createAccessCopyInLocalStorage(MultipartFile originalFile, String accessCopySaveLocation) {
//        FileStoreOperationResult fileStoreOperationResult = new FileStoreOperationResult();
//        try{
//            // This blocks temporarily copies the artifact file into another file for transformation.
//            byte[] data = originalFile.getBytes();
//            Path tempArtifactPath = Paths.get( accessCopySaveLocation + originalFile.getOriginalFilename());
//            Files.write(tempArtifactPath, data);
//        }catch(IOException ie){
//            ie.printStackTrace();
//            fileStoreOperationResult.result = false;
//            return fileStoreOperationResult;
//        }
//        File accessFile = null;
//        String extension = "";
//        try{
//            // Get the copied temporary file
//            // that will either go through a conversion
//            // Or will be just left as is.
//            File tempArtifactFile = new File(accessCopySaveLocation + originalFile.getOriginalFilename());
//            // We are getting the extension to differentiate between which files to convert and not
//            extension = FilenameUtils.getExtension(originalFile.getOriginalFilename());
//            // To rename the file with .pdf extension if pdf is converted
//            String baseName = FilenameUtils.removeExtension(originalFile.getOriginalFilename()) +".pdf" ;
//            // Calling the function to make the conversion now the parameters are settled
//            accessFile = createAppropriateAccessCopy(tempArtifactFile, extension, accessCopySaveLocation, baseName);
//            if(isPdfConvertibleExtension(extension)){
//                // If it is possible to convert to PDF then the temporary artifact file is deleted.
//                deleteFromLocalStorage(tempArtifactFile.getAbsolutePath());
//                fileStoreOperationResult.fileStorageLocation = accessCopySaveLocation+baseName;
//            }
//            else{
//                fileStoreOperationResult.fileStorageLocation = tempArtifactFile.getAbsolutePath();
//            }
//        }catch(Exception ex){
//            ex.printStackTrace();
//            fileStoreOperationResult.result = false;
//            return fileStoreOperationResult;
//        }
//        if(accessFile == null && isPdfConvertibleExtension(extension)){
//            fileStoreOperationResult.result = false;
//            return fileStoreOperationResult;
//        }
//        fileStoreOperationResult.result = true;
//        return fileStoreOperationResult;
//    }

    /**
     * Utility Function to keep track of whether a file is getting converted into pdf or just being copied
     * @param ext The extension of the filename. Which means: 'txt' if the filename is 'filename.txt'
     * @return Boolean value. Please look at the function body to understand how it works
     */
    boolean isPdfConvertibleExtension(String ext){
        if(ext.equalsIgnoreCase("tiff") || ext.equalsIgnoreCase("tif")){
            return true;
        }
        else if(ext.equalsIgnoreCase("jpeg")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("png")){
            return true;
        }
        else{
            return false;
        }

    }


    public File convertToPdf(File preservationFile, String fileNameWithoutExtension, String fileNameExtension, String saveLocation) throws Exception{
        if(fileNameExtension.equalsIgnoreCase("jpeg")
                ||fileNameExtension.equalsIgnoreCase("jpg")
                ||fileNameExtension.equalsIgnoreCase("png")){
            File pdfFile = AccessFileCreationUtils.convertJpegToPdf(preservationFile, saveLocation+fileNameWithoutExtension+".pdf");
            return pdfFile;
        }
        if(fileNameExtension.equalsIgnoreCase("tiff") || fileNameExtension.equalsIgnoreCase("tif")){
            File pdfFile = AccessFileCreationUtils.convertTIFFToPDF(preservationFile, saveLocation+fileNameWithoutExtension+".pdf");
            return pdfFile;
        }
//        else{
//            Path source = Paths.get(tempArtifactFile.getAbsolutePath());
//            Path destination = Paths.get(accessFileLocation+tempArtifactFile.getName());
//
//            System.out.println("Reached Here and Printing Now: "+source.toString()+" "+destination.toString());
//
//            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
//        }

        return null;
    }


}
