package com.attendance.Faculty.service;

import com.attendance.Faculty.model.Attendance;
import com.attendance.Faculty.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public boolean markAttendance(int studentId, String status) {
        return attendanceRepository.markAttendance(studentId,status);
    }

    public List<Attendance> getAllAttendanceRecords() {
        return attendanceRepository.getAllAttendanceRecords();
    }

    public List<Attendance> getMonthlyAttendanceReport(int studentId, int month, int year) {
        return attendanceRepository.getMonthlyAttendanceReport(studentId, month, year);
    }
}
