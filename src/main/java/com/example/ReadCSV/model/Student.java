package com.example.ReadCSV.model;



import javax.persistence.*;


@Entity
@Table(name = "student")
public class Student {

    public Student() {
    }

    @Id
    @Column
    private String Name;

    @Column
    private String Gender;

    @Column
    private String Dept;

    public Student(String name, String gender, String dept) {
    }


    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }


}