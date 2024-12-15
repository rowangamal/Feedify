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
@RequestMapping("report/post")
public class ReportPostController {
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

    @PostMapping("/newReport")
    public ResponseEntity<Object> reportPost(@RequestBody ReportPostDTO reportPostDTO){
        try {
            reportPostService.reportPost(reportPostDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof NullPointerException)
                return ResponseEntity.badRequest().body("Your report can't be null");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
