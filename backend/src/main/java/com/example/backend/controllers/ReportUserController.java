package com.example.backend.controllers;


import com.example.backend.dtos.ReportUserDTO;
import com.example.backend.services.ReportUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("report/user")
public class ReportUserController {
    @Autowired
    private ReportUserService reportUserService;

    @GetMapping("/all")
    public ResponseEntity<List<ReportUserDTO>> getAllPostReports() {
        try {
            return ResponseEntity.ok(reportUserService.getAllUserReports());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/newReport")
    public ResponseEntity<Object> reportUser(@RequestBody ReportUserDTO reportUserDTO){
        try {
            reportUserService.reportUser(reportUserDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            if (e instanceof NullPointerException)
                return ResponseEntity.badRequest().body("Your report can't be null");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
