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
    public ResponseEntity<String> markAttendance(@RequestBody Attendance attendance) {
        attendanceService.markAttendance(attendance.getRollNo(), attendance.getStatus().name());
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
