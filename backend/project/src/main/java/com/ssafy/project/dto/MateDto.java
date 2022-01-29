package com.ssafy.project.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class MateDto {
    private int mateNo;
    private String mateTitle;
    private String mateCampsite;
    private int lowestAge;
    private int highestAge;
    private String mateContent;
    private String mateCampstyle;
    private String mateCamptype;
    private Date mateCampstart;
    private Date mateCampend;
    private int userNo;
    private int contentId;
    private String mateStatus;

    private Date mateCreateTime;
    private Date mateUpdateTime;

    private String mateImageUrl;

    
}