package com.attendance.Faculty.repository;

import com.attendance.Faculty.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceRepository {

    @Autowired
    private DataSource dataSource;

    public boolean markAttendance(int studentId, String status) {
        String query = "INSERT INTO attendance (student_id, status, date) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, status);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Attendance> getAllAttendanceRecords() {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT * FROM attendance";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Attendance attendance = new Attendance();
                attendance.setId(resultSet.getInt("id"));
                attendance.setStudentId(resultSet.getInt("student_id"));
                attendance.setStatus(resultSet.getString("status"));
                attendance.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                attendanceList.add(attendance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }

    public List<Attendance> getMonthlyAttendanceReport(int studentId, int month, int year) {
        List<Attendance> report = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE student_id = ? AND MONTH(date) = ? AND YEAR(date) = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, year);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Attendance attendance = new Attendance();
                attendance.setId(resultSet.getInt("id"));
                attendance.setStudentId(resultSet.getInt("student_id"));
                attendance.setStatus(resultSet.getString("status"));
                attendance.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                report.add(attendance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
}