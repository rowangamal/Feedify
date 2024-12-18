package com.example.backend.controllers;

import com.example.backend.dtos.ReportPostDTO;
import com.example.backend.exceptions.PostNoFoundException;
import com.example.backend.exceptions.ReportNotFoundException;
import com.example.backend.services.ReportPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/report/post")
public class AdminReportPostController {
    @Autowired
    private ReportPostService reportPostService;

    @GetMapping("/all")
    public ResponseEntity<List<ReportPostDTO>> getAllPostReports() {
        try {
            return ResponseEntity.ok(reportPostService.getAllPostReports());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/approve/{reportID}")
    public ResponseEntity<Object> deletePost(@PathVariable Long reportID){
        try {
            reportPostService.deletePost(reportID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof ReportNotFoundException || e instanceof PostNoFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            if (e instanceof NullPointerException)
                return ResponseEntity.badRequest().body("Your approved report can't be null");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deny/{reportID}")
    public ResponseEntity<Object> denyReport(@PathVariable long reportID){
        try {
            reportPostService.denyReport(reportID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof ReportNotFoundException || e instanceof PostNoFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            if (e instanceof NullPointerException)
                return ResponseEntity.badRequest().body("Your denied report can't be null");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
