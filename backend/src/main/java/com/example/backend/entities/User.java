package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TableColNames.USER_TABLE)
public class User extends BaseEntity{
    @Column(name = TableColNames.USER_USERNAME, unique = true , nullable = false)
    private String username;

    @Column(name = TableColNames.USER_PASSWORD  )
    private String password;

    @Column(name = TableColNames.USER_EMAIL, unique = true , nullable = false )
    private String email;

    @Column(name = TableColNames.USER_FNAME)
    private String fName;

    @Column(name = TableColNames.USER_LNAME)
    private String lName;

    @Column(name = TableColNames.USER_GENDER , nullable = false )
    private boolean gender;

    @Column(name = TableColNames.USER_BIRTHDATE)
    private Date birthDate;

    @Column(name = TableColNames.USER_PICTUREURL )
    private String pictureURL;
  
    @Column(name = TableColNames.USER_RESET_PASSWORD_OTP)
    private String resetPasswordOtp;

}