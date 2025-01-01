package com.attendance.Faculty.service;

import com.attendance.Faculty.model.StudentResponse;
import com.attendance.Faculty.model.Student;
import com.attendance.Faculty.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public Student getStudentById(int rollNo) {
        return studentRepository.getStudentByRollNo(rollNo);
    }

    public StudentResponse deleteStudentById(int id) {
        return studentRepository.deleteStudentById(id);
    }

    public StudentResponse addStudent(Student student) {
        StudentResponse response = new StudentResponse();

        if (studentRepository.isRollNoExists(student.getRollNo())) {
            response.setName(student.getName());
            response.setClassName(student.getClassName());
            response.setDivision(student.getDivision());
            response.setStatus("Failed - Roll number already exists");
            return response;
        }

        boolean isAdded = studentRepository.addStudent(student);
        if (isAdded) {
            response.setName(student.getName());
            response.setClassName(student.getClassName());
            response.setDivision(student.getDivision());
            response.setStatus("Success");
        } else {
            response.setStatus("Error - Unable to add student");
        }

        return response;
    }
}