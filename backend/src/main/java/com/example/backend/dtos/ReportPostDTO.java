package com.example.backend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class ReportPostDTO {
    private long reportID;
    private long postID;
    private long userID;
    private String email;
    private String reason;
    private Timestamp reportTime;

    public ReportPostDTO(long reportID, long postID, long userID, String email, String reason, Timestamp reportTime) {
        this.reportID = reportID;
        this.postID = postID;
        this.userID = userID;
        this.email = email;
        this.reason = reason;
        this.reportTime = reportTime;
    }
    public ReportPostDTO() {}

}
