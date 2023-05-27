package com.projekt.projekt.Notes;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String category;
    private String content;
    private LocalDateTime adddate;


    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }
    public String dateToString(){
        String tmp=adddate.getYear()+"-";
        String month=Integer.toString(adddate.getMonthValue());
        if(adddate.getMonthValue()<10){
            month = "0"+month;
        }
        tmp = tmp + month+"-"+adddate.getDayOfMonth();
        return tmp;
    }




}
