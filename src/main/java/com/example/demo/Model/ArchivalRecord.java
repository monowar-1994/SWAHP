package com.example.demo.Model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
public class ArchivalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String itemNumber;
    private String Title;
    private String Language;
    @Column(length = 500)
    private String Summary;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date Original_Created;
    private String Original_Date_Description;
    private String Type;
    private String Genre;
    private String Place;
    private String Creator;
    private String Contributors;
    private String Access_Or_Preservation;
    private String Copyright;
    private String Collection;
    private String Subcollection;
    @Column(length=10000)
    private String Notes;
    private String Keywords;
    private String Format;
    //private String CompressionType;
    private Double Size_MB;
    private Integer Pixel_width;
    private Integer Pixel_height;
    private Integer Resolution;
    private Integer Bit_depth;
    private String Color_GreyScale;
    private Integer PageCount;
    private String Date_scanned;
    @Column(length = 500)
    //private String Scanner_Notes;
    //private String Abstract;
    private String Access_Copy_Number;


    // Fields added by me
    private String TiffFileName;
    private String tiffFileLocation;
    private String pdfFileLocation;
    private String Artifact_MD5_Hash;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date RecordCreationTime;
    //private String Generic_Column_3;

    @Override
    public String toString() {
        return "DataRecord{" +
                "id=" + id +
                ", Title='" + Title + '\'' +
                ", Language='" + Language + '\'' +
                ", Summary='" + Summary + '\'' +
                ", Original_Created=" + Original_Created +
                ", Original_Date_Description='" + Original_Date_Description + '\'' +
                ", Type='" + Type + '\'' +
                ", Category='" + Genre + '\'' +
                ", Place='" + Place + '\'' +
                ", Creator='" + Creator + '\'' +
                ", Contributors='" + Contributors + '\'' +
                ", Access_Or_Preservation='" + Access_Or_Preservation + '\'' +
                ", Copyright='" + Copyright + '\'' +
                ", Collection='" + Collection + '\'' +
                ", Subcollection='" + Subcollection + '\'' +
                ", Notes='" + Notes + '\'' +
                ", Keywords='" + Keywords + '\'' +
                ", Format='" + Format + '\'' +
                //", CompressionType='" + CompressionType + '\'' +
                ", Size_MB=" + Size_MB +
                ", Pixel_width=" + Pixel_width +
                ", Pixel_height=" + Pixel_height +
                ", Resolution=" + Resolution +
                ", Bit_depth=" + Bit_depth +
                ", Color_GreyScale='" + Color_GreyScale + '\'' +
                ", PageCount=" + PageCount +
                ", Date_scanned='" + Date_scanned + '\'' +
                //", Scanner_Notes='" + Scanner_Notes + '\'' +
                //", Abstract='" + Abstract + '\'' +
                ", Access_Copy_Number='" + Access_Copy_Number + '\'' +
                ", TiffFileName='" + TiffFileName + '\'' +
                ", tiffFileLocation='" + tiffFileLocation + '\'' +
                ", pdfFileLocation='" + pdfFileLocation + '\'' +
                ", Generic_Column_1='" + Artifact_MD5_Hash + '\'' +
                ", Generic_Column_2='" + RecordCreationTime + '\'' +
                //", Generic_Column_3='" + Generic_Column_3 + '\'' +
                '}';
    }

    public String getTiffFileName() {
        return TiffFileName;
    }

    public void setTiffFileName(String tiffFileName) {
        this.TiffFileName = tiffFileName;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public Date getOriginal_Created() {
        return Original_Created ;
    }

    public void setOriginal_Created(Date original_Created) {
        Original_Created = original_Created;
    }

    public String getOriginal_Date_Description() {
        return Original_Date_Description;
    }

    public void setOriginal_Date_Description(String original_Date_Description) {
        Original_Date_Description = original_Date_Description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }


    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getContributors() {
        return Contributors;
    }

    public void setContributors(String contributors) {
        Contributors = contributors;
    }

    public String getAccess_Or_Preservation() {
        return Access_Or_Preservation;
    }

    public void setAccess_Or_Preservation(String access_Or_Preservation) {
        Access_Or_Preservation = access_Or_Preservation;
    }

    public String getCopyright() {
        return Copyright;
    }

    public void setCopyright(String copyright) {
        Copyright = copyright;
    }

    public String getCollection() {
        return Collection;
    }

    public void setCollection(String collection) {
        Collection = collection;
    }

    public String getSubcollection() {
        return Subcollection;
    }

    public void setSubcollection(String subCollection) {
        Subcollection = subCollection;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

//    public String getCompressionType() {
//        return CompressionType;
//    }
//
//    public void setCompressionType(String compressionType) {
//        CompressionType = compressionType;
//    }

    public Double getSize_MB() {
        return Size_MB;
    }

    public void setSize_MB(Double size_MB) {
        Size_MB = size_MB;
    }

    public Integer getPixel_width() {
        return Pixel_width;
    }

    public void setPixel_width(Integer pixel_width) {
        Pixel_width = pixel_width;
    }

    public Integer getPixel_height() {
        return Pixel_height;
    }

    public void setPixel_height(Integer pixel_height) {
        Pixel_height = pixel_height;
    }

    public Integer getResolution() {
        return Resolution;
    }

    public void setResolution(Integer resolution) {
        Resolution = resolution;
    }

    public Integer getBit_depth() {
        return Bit_depth;
    }

    public void setBit_depth(Integer bit_depth) {
        Bit_depth = bit_depth;
    }

    public String getColor_GreyScale() {
        return Color_GreyScale;
    }

    public void setColor_GreyScale(String color_GreyScale) {
        Color_GreyScale = color_GreyScale;
    }

    public Integer getPageCount() {
        return PageCount;
    }

    public void setPageCount(Integer pageCount) {
        PageCount = pageCount;
    }

    public String getDate_scanned() {
        return Date_scanned;
    }

    public void setDate_scanned(String date_scanned) {
        Date_scanned = date_scanned;
    }

//    public String getScanner_Notes() {
//        return Scanner_Notes;
//    }
//
//    public void setScanner_Notes(String scanner_Notes) {
//        Scanner_Notes = scanner_Notes;
//    }

//    public String getAbstract() {
//        return Abstract;
//    }
//
//    public void setAbstract(String anAbstract) {
//        Abstract = anAbstract;
//    }

    public String getAccess_Copy_Number() {
        return Access_Copy_Number;
    }

    public void setAccess_Copy_Number(String access_Copy_Number) {
        Access_Copy_Number = access_Copy_Number;
    }

    public String getTiffFileLocation() {
        return tiffFileLocation;
    }

    public void setTiffFileLocation(String tiffFileLocation) {
        this.tiffFileLocation = tiffFileLocation;
    }

    public String getPdfFileLocation() {
        return pdfFileLocation;
    }

    public void setPdfFileLocation(String pdfFileLocation) {
        this.pdfFileLocation = pdfFileLocation;
    }

    public String getArtifact_MD5_Hash() {
        return Artifact_MD5_Hash;
    }

    public void setArtifact_MD5_Hash(String artifact_MD5_Hash) {
        Artifact_MD5_Hash = artifact_MD5_Hash;
    }

    public Date getRecordCreationTime() {
        return RecordCreationTime;
    }

    public void setRecordCreationTime(Date recordCreationTime) {
        RecordCreationTime = recordCreationTime;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

//    public String getGeneric_Column_3() {
//        return Generic_Column_3;
//    }
//
//    public void setGeneric_Column_3(String generic_Column_3) {
//        Generic_Column_3 = generic_Column_3;
//    }


}

