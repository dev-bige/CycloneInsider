package com.ethanevans.service;

import com.ethanevans.dao.studentdao;
import com.ethanevans.entity.student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class studentService {

    @Autowired
    private studentdao dao;

    public Collection<student> getAllStudents() {
        return this.dao.getAllStudents();
    }

    public student getStudentById(int id) {
        return this.dao.getStudentById(id);
    }

    public void removeStudentById(int id) {
        this.dao.removeStudentById(id);
    }

    public void updateStudent(student stu) {
        this.dao.updateStudent(stu);
    }
}
