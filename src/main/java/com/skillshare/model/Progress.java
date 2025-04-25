package com.skillshare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    @Id
    private String id;

    private String username;
    private String type;      // e.g., Completed Tutorial, Practiced
    private String message;   // Optional custom note
    private String timestamp; // Optional: ISO date/time string
}
