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
    private String username;
    private String reason;
    private Timestamp reportTime;

    public ReportPostDTO(long reportID, long postID, long userID, String username, String reason, Timestamp reportTime) {
        this.reportID = reportID;
        this.postID = postID;
        this.userID = userID;
        this.username = username;
        this.reason = reason;
        this.reportTime = reportTime;
    }
    public ReportPostDTO() {
    }
}
