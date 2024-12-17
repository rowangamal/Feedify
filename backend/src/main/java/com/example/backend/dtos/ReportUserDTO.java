package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ReportUserDTO {
    private long reportID;
    private long reporterID;
    private long reportedID;
    private String emailReporter ;
    private String emailReported ;
    private String reason ;
    public ReportUserDTO(){}

}
