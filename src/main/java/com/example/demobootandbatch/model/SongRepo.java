package com.example.demobootandbatch.model;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Evgeny Borisov
 */
public interface SongRepo extends MongoRepository<Song,Long> {
}
