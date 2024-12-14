package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportUserDTO {
    private long reportID;
    private long reporterID;
    private long reportedID;
    private String emailReporter ;
    private String emailReported ;
    private String reason ;
    private ReportUserDTO(){}

    public ReportUserDTO(long reportID, long reporterID, long reportedID, String emailReporter, String emailReported, String reason) {
        this.reportID = reportID;
        this.reporterID = reporterID;
        this.reportedID = reportedID;
        this.emailReporter = emailReporter;
        this.emailReported = emailReported;
        this.reason = reason;
    }
}
