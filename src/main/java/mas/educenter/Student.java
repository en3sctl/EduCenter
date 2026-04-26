package mas.educenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Student extends Person {

    private String studentNo;          // simple attribute
    private double gpa;                // simple attribute
    private List<String> languages;    // multi-valued attribute
    private String advisor;            // optional attribute

    private List<Enrollment> enrollments = new ArrayList<>();

    public static int totalStudents = 0; // class attribute

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

    // Class method - searches extent
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

    @Override
    public String toString() {
        return "Student{name='" + getName() + "', studentNo='" + studentNo + "', gpa=" + gpa
                + ", languages=" + languages + ", advisor=" + getAdvisor().orElse("none")
                + ", address=" + getAddress() + "}";
    }
}
