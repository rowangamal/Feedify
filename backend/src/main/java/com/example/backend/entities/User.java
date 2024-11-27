package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
@Entity(name = TableColNames.USER_TABLE)
public class User extends BaseEntity{
    @Column(name = TableColNames.USER_USERNAME, unique = true)
    private String username;

    @Column(name = TableColNames.USER_PASSWORD)
    private String password;

    @Column(name = TableColNames.USER_EMAIL, unique = true)
    private String email;

    @Column(name = TableColNames.USER_FNAME)
    private String fName;

    @Column(name = TableColNames.USER_LNAME)
    private String lName;

    @Column(name = TableColNames.USER_GENDER)
    private boolean gender;

    @Column(name = TableColNames.USER_BIRTHDATE)
    private Date birthDate;

    @Column(name = TableColNames.USER_PICTUREURL)
    private String pictureURL;

}
