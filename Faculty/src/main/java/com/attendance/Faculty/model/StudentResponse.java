package com.attendance.Faculty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private String name;
    private String className;
    private int year;
    private String division;
    private String status;


}
