package mas.educenter;

import java.util.ArrayList;
import java.util.List;

// Multiple inheritance via interface: extends Student AND implements IInstructor
// Demonstrates overlapping inheritance - a person who is both student and instructor
public class WorkingStudent extends Student implements IInstructor {

    private double hourlyRate;
    private List<Course> taughtCourses = new ArrayList<>();

    public WorkingStudent(String name, String studentNo, double gpa,
                          List<String> languages, String advisor, double hourlyRate) {
        super(name, studentNo, gpa, languages, advisor);
        this.hourlyRate = hourlyRate;
    }

    public WorkingStudent(String name, String studentNo, double hourlyRate) {
        super(name, studentNo);
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public List<Course> getTaughtCourses() {
        return taughtCourses;
    }

    @Override
    public void addTaughtCourse(Course course) {
        if (!taughtCourses.contains(course)) {
            taughtCourses.add(course);
        }
    }

    // Override polymorphic method - both student and instructor sides
    @Override
    public String describeRole() {
        return "WorkingStudent: studies and teaches " + taughtCourses.size() + " course(s)";
    }

    @Override
    public String toString() {
        return "WorkingStudent{name='" + getName() + "', studentNo='" + getStudentNo()
                + "', hourlyRate=" + hourlyRate + ", teaching=" + taughtCourses.size() + "}";
    }
}