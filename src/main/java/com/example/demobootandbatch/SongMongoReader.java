package com.example.demobootandbatch;

import com.example.demobootandbatch.model.Song;
import com.example.demobootandbatch.model.SongRepo;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Evgeny Borisov
 */

public class SongMongoReader implements ItemReader<Song>{
    @Autowired
    private SongRepo songRepo;

    @Override
    public Song read() throws Exception{
        return null;
    }
}
