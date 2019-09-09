package com.ethanevans.dao;

import com.ethanevans.entity.student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class studentdao {

    private static Map<Integer, student> students;

    static {

        students = new HashMap<Integer, student>() {
            {
                put(1, new student(1, "Said", "Computer Science"));
                put(2, new student(2, "Alex U", "Finance"));
                put(3, new student(3, "Anna", "Maths"));
            }
        };
    }

    public Collection<student> getAllStudents() {
        return this.students.values();
    }

    public student getStudentById(int id) {
        return this.students.get(id);
    }

    public void removeStudentById(int id) {
        this.students.remove(id);
    }

    public void updateStudent(student stu) {
        student s = students.get(stu.getId());
        s.setCourse(stu.getCourse());
        s.setName(stu.getName());
        students.put(stu.getId(), stu);
    }
}
