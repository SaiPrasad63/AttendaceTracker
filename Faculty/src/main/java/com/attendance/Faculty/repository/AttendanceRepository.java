package com.attendance.Faculty.repository;

import com.attendance.Faculty.model.Attendance;
import com.attendance.Faculty.model.Status;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/attendance_db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public void markAttendance(int rollNo, Status status) {
        String query = "INSERT INTO attendance (roll_no, status, date) VALUES (?, ?, NOW())";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, rollNo);
            preparedStatement.setString(2, status.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Attendance> getAllAttendanceRecords() {
        List<Attendance> records = new ArrayList<>();
        String query = "SELECT * FROM attendance";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Attendance attendance = new Attendance();
                attendance.setId(resultSet.getInt("id"));
                attendance.setRollNo(resultSet.getInt("roll_no"));
                attendance.setStatus(Status.valueOf(resultSet.getString("status")));
                attendance.setDate(resultSet.getString("date"));
                records.add(attendance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public List<Attendance> getMonthlyAttendanceReport(int studentId, int month, int year) {
        List<Attendance> records = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE roll_no = ? AND MONTH(date) = ? AND YEAR(date) = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, year);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setId(resultSet.getInt("id"));
                    attendance.setRollNo(resultSet.getInt("roll_no"));
                    attendance.setStatus(Status.valueOf(resultSet.getString("status")));
                    attendance.setDate(resultSet.getString("date"));
                    records.add(attendance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
