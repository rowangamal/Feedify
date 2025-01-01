package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TableColNames.TOPIC_TABLE)
public class PostType extends BaseEntity {
    @Column(name = TableColNames.TOPIC_NAME, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "postTypes")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> posts;

    @ManyToMany(mappedBy = "postTypes")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> users;

    public PostType(String name) {
        this.name = name;
    }
    public PostType(Long id, String name) {
        super(id);
        this.name = name;
    }

    public PostType(Long id) {
        super(id);
    }
}
