package com.example.demo.Utility;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;

import java.io.File;
import java.io.FileOutputStream;

public class AccessFileCreationUtils {

    public static File convertTIFFToPDF (File tiffFile, String saveLocation) throws Exception
    {
        File pdfFile = new File(saveLocation);

        RandomAccessFileOrArray myTiffFile = new RandomAccessFileOrArray(tiffFile.getCanonicalPath());
        // Find number of images in Tiff file
        int numberOfPages = TiffImage.getNumberOfPages(myTiffFile);
        Document TifftoPDF = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(TifftoPDF, new FileOutputStream(pdfFile));
        pdfWriter.setStrictImageSequence(true);
        TifftoPDF.open();
        Image tempImage;
        // Run a for loop to extract images from Tiff file
        // into a Image object and add to PDF recursively
        for (int i = 1; i <= numberOfPages; i++) {

            tempImage = TiffImage.getTiffImage(myTiffFile, i);
            tempImage.setAbsolutePosition(0,0);
            tempImage.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            //Rectangle pageSize = new Rectangle(tempImage.getWidth(), tempImage.getHeight());
            //TifftoPDF.setPageSize(pageSize);
            TifftoPDF.newPage();
            TifftoPDF.add(tempImage);
        }
        TifftoPDF.close();

        return pdfFile;
    }

    public static File convertJpegToPdf(File jpegFile, String saveLocation) throws Exception{
        File pdfFile = new File(saveLocation); // Creates the access pdf file
        Document jpegToPdfDocument = new Document(); // Creates the itext document
        PdfWriter pdfWriter = PdfWriter.getInstance(jpegToPdfDocument, new FileOutputStream(pdfFile)); // Creates an output stream to write to document

        Image image = Image.getInstance(jpegFile.getAbsolutePath()); // Getting the image data from teh jpeg
        image.scaleToFit(400,600);
        jpegToPdfDocument.open(); // Opens the document
        jpegToPdfDocument.add(image); // Write the data of image in the pdf
        jpegToPdfDocument.close(); // Close this

        return pdfFile;
    }
}
