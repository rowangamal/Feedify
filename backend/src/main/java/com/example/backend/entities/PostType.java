package com.example.backend.entities;

import com.example.backend.enums.TableColNames;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TableColNames.TOPIC_TABLE)
public class PostType extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy = "postTypes")
    @JsonIgnore
    private List<Post> posts;

    public PostType(String name) {
        this.name = name;
    }
    public PostType(int id) {
        super(id);
    }
}
