package com.example.demobootandbatch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Evgeny Borisov
 */

@Data
@Wither
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Song {
    @Id
    private String id;
    private String name;
    private String content;
}
