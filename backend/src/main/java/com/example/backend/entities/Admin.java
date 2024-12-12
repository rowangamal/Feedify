package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = TableColNames.ADMIN_TABLE)
public class Admin extends BaseEntity{
    @OneToOne
    @JoinColumn(name = TableColNames.USER_ID)
    private User user;
}
