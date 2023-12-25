package com.myblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String body;
    private String email;


// ---------------------------------------Relationship-----------------------------------------------------------------------------------

    // fetch=FetchType.LAZY------- Load the required table(entity class) into the class memory
    // -----data access is faster----prefer to use with large amt of data
    // fetch=FetchType.EAGER------- Load all the available table(entity class) into the class memory
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

}
