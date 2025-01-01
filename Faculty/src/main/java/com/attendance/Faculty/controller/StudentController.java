package com.attendance.Faculty.controller;

import com.attendance.Faculty.model.StudentResponse;
import com.attendance.Faculty.model.Student;
import com.attendance.Faculty.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{rollNo}")
    public Student getStudentById(@PathVariable int rollNo) {
        return studentService.getStudentById(rollNo);
    }

    @DeleteMapping("/delete/{rollNo}")
    public StudentResponse deleteStudent(@PathVariable int rollNo) {
        return studentService.deleteStudentById(rollNo);
    }

    @PostMapping("/add")
    public ResponseEntity<StudentResponse> addStudent(@RequestBody Student student) {
        StudentResponse response = studentService.addStudent(student);
        if ("Success".equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else if (response.getStatus().contains("Failed")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
