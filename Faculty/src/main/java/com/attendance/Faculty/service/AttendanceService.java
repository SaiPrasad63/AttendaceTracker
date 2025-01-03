package com.attendance.Faculty.service;

import com.attendance.Faculty.model.Attendance;
import com.attendance.Faculty.model.Status;
import com.attendance.Faculty.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void markAttendance(int rollNo, String status) {
        Status attendanceStatus = Status.valueOf(status.toUpperCase());
        attendanceRepository.markAttendance(rollNo, attendanceStatus);
    }

    public List<Attendance> getAllAttendanceRecords() {
        return attendanceRepository.getAllAttendanceRecords();
    }

    public List<Attendance> getMonthlyAttendanceReport(int studentId, int month, int year) {
        return attendanceRepository.getMonthlyAttendanceReport(studentId, month, year);
    }
}
