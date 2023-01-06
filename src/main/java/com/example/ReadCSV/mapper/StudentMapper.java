package com.example.ReadCSV.mapper;


import com.example.ReadCSV.model.Student;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;


@Component
public class StudentMapper implements FieldSetMapper<Student> {
    @Override
    public Student mapFieldSet(FieldSet fieldSet) {
        return new Student(
                fieldSet.readString("Name"),
                fieldSet.readString("Gender"),
                fieldSet.readString("Dept"));
    }
    }

