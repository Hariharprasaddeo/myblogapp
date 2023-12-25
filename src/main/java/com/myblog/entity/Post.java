package com.myblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @Data - This lombok annotation automatically generates getters and
         // setters method internally instead of writing manually
         // @Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})  ---->
         // This is the another way of declaring unique constraints to the column
@Entity
@Data // this will create a getters and setters method internally
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content", nullable = false)
    private String content;

//---------------------------------------Mapping Relationship------------------------------------------------------------------------------------------

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

}
