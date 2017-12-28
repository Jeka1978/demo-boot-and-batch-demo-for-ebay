package com.example.demobootandbatch;

import com.example.demobootandbatch.model.Song;
import com.example.demobootandbatch.model.SongRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Evgeny Borisov
 */
public class SongWriter implements ItemWriter<Song> {
    @Autowired
    private SongRepo songRepo;

    @PostConstruct
    public void deleteAll() {
        songRepo.deleteAll();
    }

    @Override
    public void write(List<? extends Song> list) throws Exception {
        list.forEach(System.out::println);
        songRepo.save(list);

        System.out.println("*********************");
    }
}
