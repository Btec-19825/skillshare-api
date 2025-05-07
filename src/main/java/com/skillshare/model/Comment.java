package com.skillshare.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {  
    private String id;
    private String username;
    private String message;
    private String timestamp;
}
