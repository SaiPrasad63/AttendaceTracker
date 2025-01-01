package com.attendance.Faculty.controller;

import com.attendance.Faculty.model.Attendance;
import com.attendance.Faculty.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(@RequestBody int studentId, String status) {
        System.out.println("Received studentId: " + studentId);
        System.out.println("Received status: " + status);
        attendanceService.markAttendance(studentId,status);
        return ResponseEntity.ok("Attendance marked successfully.");
    }

    @GetMapping
    public List<Attendance> getAllAttendanceRecords() {
        return attendanceService.getAllAttendanceRecords();
    }

    @GetMapping("/report")
    public List<Attendance> getMonthlyAttendanceReport(@RequestParam int studentId,
                                                       @RequestParam int month,
                                                       @RequestParam int year) {
        return attendanceService.getMonthlyAttendanceReport(studentId, month, year);
    }
}