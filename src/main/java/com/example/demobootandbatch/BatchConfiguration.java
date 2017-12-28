package com.example.demobootandbatch;

import com.example.demobootandbatch.model.Song;
import com.example.demobootandbatch.model.SongRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgeny Borisov
 */
@Configuration
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SongRepo songRepo;

    @Bean
    public FlatFileItemReader<Song> songReader(){
        FlatFileItemReader<Song> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("sample-data.csv"));
        reader.setLineMapper(new DefaultLineMapper<Song>() {{
            setLineTokenizer(new DelimitedLineTokenizer(";") {{
                setNames(new String[] { "name", "content" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Song>() {{
                setTargetType(Song.class);
            }});
        }});
        return reader;
    }

    @Bean
    public SongItemProcessor songProcessor() {
        return new SongItemProcessor();
    }

    @Bean
    public SongItemMongoProcessor songItemMongoProcessor() {
        return new SongItemMongoProcessor();
    }

    @Bean
    public ItemWriter<Integer> countWriter(){
        return list -> list.forEach(count-> System.out.println("song context length: "+count));
    }

    @Bean
    public ItemWriter<Song> songWriter() {
        return new SongWriter();
    }


    @Bean
    public RepositoryItemReader<Song> songFromMongoReader(){
        RepositoryItemReader<Song> songRepositoryItemReader = new RepositoryItemReader<>();
        songRepositoryItemReader.setPageSize(10);
        Map<String, Sort.Direction> sorts= new HashMap<>();
        sorts.put("name",Sort.Direction.ASC);
        songRepositoryItemReader.setSort(sorts);
        songRepositoryItemReader.setMethodName("findAll");
        songRepositoryItemReader.setRepository(songRepo);
        return songRepositoryItemReader;
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Song,Song>chunk(2)
                .reader(songReader())
                .processor(songProcessor())
                .writer(songWriter())
                .build();
    }
    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").<Song,Integer>chunk(2)
                .reader(songFromMongoReader())
                .processor(songItemMongoProcessor())
                .writer(countWriter())
                .build();
    }

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .start(step1()).next(step2())
                .build();
    }

}
