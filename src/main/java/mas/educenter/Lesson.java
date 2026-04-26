package mas.educenter;

import java.util.HashSet;
import java.util.Set;

// Part of Course composition - cannot exist without a Course
public class Lesson extends ObjectPlus {

    private String title;
    private int duration;       // in minutes
    private int order;          // lesson order in the course
    private Course course;      // the whole

    // Keeps track of all lessons connected to a course (part can't be shared)
    private static Set<Lesson> allLessons = new HashSet<>();

    // Private constructor - prevents creating a lesson without a course
    private Lesson(Course course, String title, int duration, int order) {
        super();
        this.course = course;
        this.title = title;
        this.duration = duration;
        this.order = order;
    }

    // Factory method - the only way to create a Lesson (composition)
    public static Lesson createLesson(Course course, String title, int duration, int order) throws Exception {
        if (course == null) {
            throw new Exception("Cannot create a lesson without a course!");
        }
        Lesson lesson = new Lesson(course, title, duration, order);
        course.addLesson(lesson);
        allLessons.add(lesson);
        return lesson;
    }

    // Called by Course when the whole is destroyed
    static boolean isConnected(Lesson lesson) {
        return allLessons.contains(lesson);
    }

    static void disconnect(Lesson lesson) {
        allLessons.remove(lesson);
    }

    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public int getOrder() { return order; }
    public Course getCourse() { return course; }

    @Override
    public String toString() {
        return "Lesson{order=" + order + ", title='" + title + "', duration=" + duration + "min}";
    }
}
