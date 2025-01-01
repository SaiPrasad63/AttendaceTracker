package com.attendance.Faculty.repository;

import com.attendance.Faculty.model.StudentResponse;
import com.attendance.Faculty.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    @Autowired
    private DataSource dataSource;

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT roll_no, name, class, division FROM students";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Student student = new Student();
                student.setRollNo(resultSet.getInt("roll_no")); // Fetch roll number
                student.setName(resultSet.getString("name"));  // Fetch name
                student.setClassName(resultSet.getString("class")); // Fetch class
                student.setDivision(resultSet.getString("division")); // Fetch division
                students.add(student); // Add student to the list
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching all students", e);
        }
        return students;
    }

    public Student getStudentByRollNo(int rollNo) {
        String query = "SELECT roll_no, name, class, division FROM students WHERE roll_no = ?";
        Student student = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, rollNo); // Set roll number parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                student = new Student();
                student.setRollNo(resultSet.getInt("roll_no")); // Fetch roll number
                student.setName(resultSet.getString("name"));  // Fetch name
                student.setClassName(resultSet.getString("class")); // Fetch class
                student.setDivision(resultSet.getString("division")); // Fetch division
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching student with roll number=" + rollNo, e);
        }

        return student; // Return the fetched student or null if not found
    }
//    public String deleteStudentById(int id) {
//        Student student = null;
//        String query = "SELECT * FROM students WHERE id = ?";
//        String query1 = "DELETE FROM students WHERE id = ?";
//
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement fetchStatement = connection.prepareStatement(query);
//             PreparedStatement deleteStatement = connection.prepareStatement(query1)) {
//
//            fetchStatement.setInt(1, id);
//            ResultSet resultSet = fetchStatement.executeQuery();
//
//            if (resultSet.next()) {
//                student = new Student();
//                student.setId(resultSet.getInt("id"));
//                student.setName(resultSet.getString("name"));
//                student.setRollNo(resultSet.getInt("roll_no"));
//                student.setClassName(resultSet.getString("class"));
//                student.setDivision(resultSet.getString("division"));
//            }
//            if (student != null) {
//                deleteStatement.setInt(1, id);
//                deleteStatement.executeUpdate();
//
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException();
//        }
//        return "Student is deleted having id= " +id;
//    }

    public StudentResponse deleteStudentById(int rollNo) {
        Student student = null;
        String fetchQuery = "SELECT roll_no, name, class, division FROM students WHERE roll_no = ?";
        String deleteQuery = "DELETE FROM students WHERE roll_no = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement fetchStatement = connection.prepareStatement(fetchQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {

            // Step 1: Fetch student details based on roll number
            fetchStatement.setInt(1, rollNo);
            ResultSet resultSet = fetchStatement.executeQuery();

            if (resultSet.next()) {
                student = new Student();
                student.setRollNo(resultSet.getInt("roll_no"));
                student.setName(resultSet.getString("name"));
                student.setClassName(resultSet.getString("class"));
                student.setDivision(resultSet.getString("division"));
            }

            // Step 2: If student exists, delete the record
            if (student != null) {
                deleteStatement.setInt(1, rollNo);
                int rowsAffected = deleteStatement.executeUpdate();

                if (rowsAffected > 0) {
                    return new StudentResponse(
                            student.getName(),
                            student.getClassName(),
                            student.getRollNo(),
                            student.getDivision(),
                            "deleted"
                    );
                } else {
                    return new StudentResponse(
                            student.getName(),
                            student.getClassName(),
                            student.getRollNo(),
                            student.getDivision(),
                            "failed to delete"
                    );
                }
            } else {
                return new StudentResponse(
                        "N/A", "N/A", 0, "N/A", "student not found"
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new StudentResponse(
                    "N/A", "N/A", 0, "N/A", "SQL error: " + e.getMessage()
            );
        }
    }

    public boolean isRollNoExists(int rollNo) {
        String query = "SELECT COUNT(*) AS count FROM students WHERE roll_no = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, rollNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking roll number existence", e);
        }
        return false;
    }

    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (roll_no, name, class, division) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, student.getRollNo());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getClassName());
            preparedStatement.setString(4, student.getDivision());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding student", e);
        }
    }
}
