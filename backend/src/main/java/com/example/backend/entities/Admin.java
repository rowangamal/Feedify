package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = TableColNames.ADMIN_TABLE)

public class Admin extends BaseEntity{
    @OneToOne
    @JoinColumn(name = TableColNames.USER_ID)
    private User user;
}
