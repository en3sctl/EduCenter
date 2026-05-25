package mas.educenter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "students")
public class Student extends Person {

    @Column(nullable = false, unique = true)
    private String studentNo;          // simple attribute

    private double gpa;                // simple attribute

    @ElementCollection                 // multi-valued attribute - separate join table
    @CollectionTable(name = "student_languages", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "language")
    private List<String> languages;

    @Column(nullable = true)
    private String advisor;            // optional attribute

    @OneToMany(mappedBy = "student")
    private List<Enrollment> enrollments = new ArrayList<>();

    @Transient                         // class attribute - not persisted
    public static int totalStudents = 0;

    protected Student() {
        super();
    }

    // Full constructor (overload)
    public Student(String name, String studentNo, double gpa, List<String> languages, String advisor) {
        super(name);
        this.studentNo = studentNo;
        this.gpa = gpa;
        this.languages = new ArrayList<>(languages);
        this.advisor = advisor;
        totalStudents++;
    }

    // Minimal constructor (overload)
    public Student(String name, String studentNo) {
        super(name);
        this.studentNo = studentNo;
        this.gpa = 0.0;
        this.languages = new ArrayList<>();
        this.advisor = null;
        totalStudents++;
    }

    // Conversion constructor - used for dynamic inheritance
    public Student(Person prev, String studentNo, double gpa) {
        super(prev.getName(), prev.getAddress(), prev.getBirthDate());
        this.studentNo = studentNo;
        this.gpa = gpa;
        this.languages = new ArrayList<>();
        this.advisor = null;
        totalStudents++;
    }

    public String getStudentNo() { return studentNo; }

    // Overloaded method - no param
    public double getGpa() { return gpa; }

    // Overloaded method - with bonus param
    public double getGpa(double bonus) { return gpa + bonus; }

    public void setGpa(double gpa) { this.gpa = gpa; }

    public List<String> getLanguages() { return languages; }

    public void setLanguages(List<String> languages) {
        this.languages = new ArrayList<>(languages);
    }

    public void addLanguage(String language) {
        this.languages.add(language);
    }

    // Optional attribute - returns Optional
    public Optional<String> getAdvisor() {
        return Optional.ofNullable(advisor);
    }

    public void setAdvisor(Optional<String> advisor) {
        this.advisor = advisor.orElse(null);
    }

    // Derived attribute - computed, not stored
    @Transient
    public String getFullName() {
        return getName() + " [student]";
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        if (!enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    public void removeEnrollment(Enrollment enrollment) {
        if (enrollments.contains(enrollment)) {
            enrollments.remove(enrollment);
        }
    }

    public List<Course> getCourses() {
        List<Course> result = new ArrayList<>();
        for (Enrollment e : enrollments) {
            result.add(e.getCourse());
        }
        return result;
    }

    // Class method - searches in-memory extent
    public static List<Student> findByLanguage(String lang) {
        List<Student> result = new ArrayList<>();
        try {
            for (Student s : ObjectPlus.getExtent(Student.class)) {
                if (s.languages.contains(lang)) {
                    result.add(s);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Student extent not found.");
        }
        return result;
    }

    // Polymorphic implementation of the abstract role method
    @Override
    public String describeRole() {
        return "Student with GPA " + gpa + " (" + studentNo + ")";
    }

    @Override
    public String toString() {
        return "Student{name='" + getName() + "', studentNo='" + studentNo + "', gpa=" + gpa
                + ", languages=" + languages + ", advisor=" + getAdvisor().orElse("none")
                + ", address=" + getAddress() + "}";
    }
}
