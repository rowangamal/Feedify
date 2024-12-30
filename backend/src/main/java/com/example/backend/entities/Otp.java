package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import com.example.backend.enums.TableColNames;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TableColNames.OTP_TABLE)
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = TableColNames.USER_ID, nullable = false, unique = true)
    private User user;

    @Column(name = TableColNames.OTP)
    private String resetPasswordOtp;

    @Column(name = TableColNames.OTP_EXPIRATION_DATE)
    private Timestamp resetOtpExpiration;
}
