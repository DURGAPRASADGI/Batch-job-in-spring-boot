package com.example.Batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.Batch.model.Coffee;
import com.example.Batch.service.CoffeeItemProcessor;
import com.example.Batch.service.JobCompletionNotificationListener;

@Configuration
public class BatchConfiguration {
    
    @Value("${file.input}")
    private String fileInput;
    
    
	@Bean
     FlatFileItemReader<Coffee> reader() {
        return new FlatFileItemReaderBuilder<Coffee>().name("coffeeItemReader")
          .resource(new ClassPathResource(fileInput))
          .delimited()
          .names("coffeeId","brand","origin","characteristics" )
          .targetType(Coffee.class)
          .build();
    }
    @Bean
     ItemWriter<Coffee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Coffee>()
          .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Coffee>())
          .sql("INSERT INTO coffee (coffee_Id,brand, origin, characteristics) VALUES (:coffeeId, :brand, :origin, :characteristics)")
          .dataSource(dataSource)
          .beanMapped()
          .build();
    }
    
    
    @Bean
     Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
          .incrementer(new RunIdIncrementer())
          .listener(listener)
          .flow(step1)
          .end()
          .build();
    }

    @Bean
     Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemWriter<Coffee> writer) {
        return new StepBuilder("step1", jobRepository)
          .<Coffee, Coffee> chunk(10, transactionManager)
          .reader(reader())
          .processor(processor())
          .writer(writer)
          .build();
    }

    @Bean
     ItemProcessor<Coffee, Coffee> processor() {
        return new CoffeeItemProcessor();
    }
}
