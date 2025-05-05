package com.skillshare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "learning_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LearningPlan {   

    @Id
    private String id;

    private String username;
    private String topic;   
    private String resources;  
    private String deadline;   
    private String status;     
}
