package com.example.demobootandbatch;

import com.example.demobootandbatch.model.Song;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author Evgeny Borisov
 */
public class SongItemMongoProcessor implements ItemProcessor<Song,Integer> {
    @Override
    public Integer process(Song song) throws Exception {
        return song.getContent().length();
    }
}
