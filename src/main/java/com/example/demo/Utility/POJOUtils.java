package com.example.demo.Utility;

import com.example.demo.Model.ArchivalRecord;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class POJOUtils {
    public static ArchivalRecord populateDataRecordFromMap(Map<String, String> paramMap){

        if(paramMap == null){
            return null;
        }

        ArchivalRecord entry = new ArchivalRecord();

        Field field = null;
        Class type = null;
        for(Map.Entry<String, String> e: paramMap.entrySet()){
            String fieldName = e.getKey();
            String fieldValue = e.getValue();
            if(fieldValue.length() != 0){
                try{


                    field = ArchivalRecord.class.getDeclaredField(fieldName);
                    type = field.getType();

                    if(type.equals(Integer.class)){
                        field.setAccessible(true);
                        field.set(entry, Integer.parseInt(fieldValue));
                        field.setAccessible(false);
                    }
                    else if(type.equals((Date.class))){
                        field.setAccessible(true);
                        field.set(entry, stringToDateFormatter(fieldValue));
                        field.setAccessible(false);
                    }
                    else{
                        field.setAccessible(true);
                        field.set(entry, fieldValue);
                        field.setAccessible(false);
                    }

                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        return entry;
    }

    public static Date stringToDateFormatter (String inputDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = df.parse(inputDate);
        return dt;
    }

}
