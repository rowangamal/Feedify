package com.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class ReportPostDTO {
    private long reportID;
    private long postID;
    private long userID; //reporter
    private String username;
    private String reason;
    private Timestamp reportTime;

    public ReportPostDTO() {}
}
