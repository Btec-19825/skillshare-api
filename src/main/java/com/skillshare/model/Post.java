package com.skillshare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;
import com.skillshare.model.Comment;

import java.util.Set;
import java.util.HashSet;

@Document(collection = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    private String id;

    private String username;
    private String title;
    private String description;

    private List<String> mediaUrls; 
    private List<Comment> comments = new ArrayList<>();

    private Set<String> likedBy = new HashSet<>();
    private Set<String> unlikedBy = new HashSet<>();

    private int likes = 0; //like
    private int unlikes = 0;

}
