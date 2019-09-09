package com.ethanevans.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ethanevans.service.studentService;
import com.ethanevans.entity.student;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class studentController {

    @Autowired
    private studentService studentServ;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<student> getAllStudents() {
        return studentServ.getAllStudents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public student getStudentById(@PathVariable("id") int id) {
        return studentServ.getStudentById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStudentById(@PathVariable ("id") int id) {
        studentServ.removeStudentById(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateStudent(@RequestBody student stu) {
        studentServ.updateStudent(stu);
    }
}
