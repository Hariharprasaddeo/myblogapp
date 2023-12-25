package com.myblog.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;
    @NotEmpty(message= "title must not be empty")
    @Size(min = 2, message = "post title should have least 2 characters")
    private String title;
    @NotEmpty(message= "description must not be empty")
    @Size(min = 10, message = "post description should have least 10 characters")
    private String description;
    @NotEmpty(message= "content must not be empty")
    private String content;

}
