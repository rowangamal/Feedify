package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
@Table(name = TableColNames.POST_REPORT_TABLE)
public class ReportPost extends BaseEntity {
    @Column(name = TableColNames.POST_REPORT_REASON, nullable = false)
    private String reason;

    @Column(name = TableColNames.POST_REPORT_DATE, nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = TableColNames.POST_REPORT_REPORTER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = TableColNames.POST_REPORT_POST_ID)
    private Post post;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
