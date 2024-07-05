import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystem {
    private static final String DATABASE_URL = "jdbc:sqlite:students.db";

    public StudentManagementSystem() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                    "rollNumber INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "grade TEXT NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addStudent(Student student) {
        String sql = "INSERT INTO students(name, rollNumber, grade) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getRollNumber());
            pstmt.setString(3, student.getGrade());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeStudent(int rollNumber) {
        String sql = "DELETE FROM students WHERE rollNumber = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rollNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Student searchStudent(int rollNumber) {
        String sql = "SELECT * FROM students WHERE rollNumber = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rollNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String grade = rs.getString("grade");
                return new Student(name, rollNumber, grade);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                int rollNumber = rs.getInt("rollNumber");
                String grade = rs.getString("grade");
                students.add(new Student(name, rollNumber, grade));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return students;
    }

    public void displayAllStudents() {
        List<Student> students = getAllStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
