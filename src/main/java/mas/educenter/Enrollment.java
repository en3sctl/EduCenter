package mas.educenter;

import jakarta.persistence.*;
import java.time.LocalDate;

// Association class - holds data about a Student's enrollment to a Course
// Mapped to its own table with two foreign keys (student, course) plus its own attributes
@Entity
@Table(name = "enrollments")
public class Enrollment extends ObjectPlus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private LocalDate enrollDate;
    private double grade;
    private String status;  // "active", "completed", "dropped"

    protected Enrollment() {
        super();
    }

    public Enrollment(Student student, Course course, LocalDate enrollDate, String status) {
        super();
        this.student = student;
        this.course = course;
        this.enrollDate = enrollDate;
        this.status = status;
        this.grade = 0.0;

        student.addEnrollment(this);
        course.addEnrollment(this);
    }

    public Long getId() { return id; }
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
