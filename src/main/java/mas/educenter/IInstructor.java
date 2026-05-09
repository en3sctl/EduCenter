package mas.educenter;

import java.util.List;

// Interface used for multiple inheritance in Java
// Allows a class to act as an instructor without extending Instructor
public interface IInstructor {
    double getHourlyRate();
    List<Course> getTaughtCourses();
    void addTaughtCourse(Course course);
}