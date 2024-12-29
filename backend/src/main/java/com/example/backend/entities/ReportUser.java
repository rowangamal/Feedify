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
@Table(name = TableColNames.USER_REPORT_TABLE)
public class ReportUser extends BaseEntity {
    @Column(name = TableColNames.USER_REPORT_REASON, nullable = false)
    private String reason;

    @Column(name = TableColNames.USER_REPORT_DATE, nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = TableColNames.USER_REPORT_REPORTED_ID)
    private User reported;

    @ManyToOne
    @JoinColumn(name = TableColNames.USER_REPORT_REPORTER_ID)
    private User reporter;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
