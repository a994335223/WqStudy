package com.example.reflexlibrary.bean;

public class Student {
    //com.example.reflexlibrary.bean.Student
    public String name="三国";
    private int age=0;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }
    private Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
