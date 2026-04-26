package mas.educenter;

import java.time.LocalDate;

// Association class - holds data about a Student's enrollment to a Course
public class Enrollment extends ObjectPlus {

    private Student student;
    private Course course;
    private LocalDate enrollDate;
    private double grade;
    private String status;  // "active", "completed", "dropped"

    public Enrollment(Student student, Course course, LocalDate enrollDate, String status) {
        super();
        this.student = student;
        this.course = course;
        this.enrollDate = enrollDate;
        this.status = status;
        this.grade = 0.0;

        // Maintain both sides of the association
        student.addEnrollment(this);
        course.addEnrollment(this);
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollDate() { return enrollDate; }
    public double getGrade() { return grade; }
    public String getStatus() { return status; }

    public void setGrade(double grade) { this.grade = grade; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Enrollment{student=" + student.getName()
                + ", course=" + course.getTitle()
                + ", date=" + enrollDate
                + ", grade=" + grade
                + ", status='" + status + "'}";
    }
}
