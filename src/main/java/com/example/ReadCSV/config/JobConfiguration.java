package com.example.ReadCSV.config;

import javax.sql.DataSource;

import com.example.ReadCSV.model.Student;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean(name = "generateReportCard")
    public Job generateReportCard() {
        return jobBuilderFactory
                .get("generateReportCard")
               .incrementer(new RunIdIncrementer())
                .start(processStudentReport())
                .build();
    }

//    @Bean
//    protected Step writeLines() {
//        return stepBuilderFactory
//                .get("writeLines")
//                .tasklet((Tasklet) readCSVFile())
//                .build();
//    }
    @Bean
    public Step processStudentReport() {
        return  stepBuilderFactory.get("processStudentReport")
                 .<Student,Student>chunk(1)
                .reader(readCSVFile())
                .writer(studentJdbcBatchItemWriter())
                .build();
//                .tasklet((Tasklet) readCSVFil()).build();
    }
//    @Bean
//    public Step processStudentReport1() {
//        return stepBuilderFactory.get("processStudentReport1")
//                .tasklet((Tasklet) studentJdbcBatchItemWriter())
//                .build();
//    }


    @Bean
    public FlatFileItemReader readCSVFile() {
        FlatFileItemReader csvFileReader = new FlatFileItemReader<>();
        csvFileReader.setResource(new FileSystemResource("C://Users//Nandhini//Downloads//Student.csv"));
        csvFileReader.setLineMapper(getStudentLineMapper());
        return csvFileReader;
    }

    @Bean
    public LineMapper getStudentLineMapper() {
        DefaultLineMapper studentLineMapper = new DefaultLineMapper();
        studentLineMapper.setLineTokenizer(new DelimitedLineTokenizer() {
                    {setNames(new String[] {"Name","Gender","Dept"});}});
        studentLineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper() {
                    {
                        setTargetType(Student.class);
                    }
                });
        return studentLineMapper;
    }


    @Bean
    public JdbcBatchItemWriter<Student> studentJdbcBatchItemWriter() {
        JdbcBatchItemWriter<Student> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO Student VALUES (:Name, :Gender, :Dept)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
        itemWriter.afterPropertiesSet();

        return itemWriter;
    }


}

