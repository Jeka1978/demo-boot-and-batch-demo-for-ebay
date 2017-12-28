package com.example.demobootandbatch;

import com.example.demobootandbatch.model.Song;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Evgeny Borisov
 */
public class SongItemProcessor implements ItemProcessor<Song,Song> {
    @Override
    public Song process(Song song) throws Exception {
        return song.withContent(song.getContent().replace("wtf","why"));
    }
}
