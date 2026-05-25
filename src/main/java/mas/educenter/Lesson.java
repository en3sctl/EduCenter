package mas.educenter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

// Part of Course composition - cannot exist without a Course
@Entity
@Table(name = "lessons")
public class Lesson extends ObjectPlus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "duration_min")
    private int duration;       // in minutes

    @Column(name = "lesson_order")
    private int order;          // lesson order in the course

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;      // the whole

    @Transient
    private static Set<Lesson> allLessons = new HashSet<>();

    // Protected no-arg constructor for JPA only
    protected Lesson() {
        super();
    }

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

    static boolean isConnected(Lesson lesson) {
        return allLessons.contains(lesson);
    }

    static void disconnect(Lesson lesson) {
        allLessons.remove(lesson);
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public int getDuration() { return duration; }
    public int getOrder() { return order; }
    public Course getCourse() { return course; }

    @Override
    public String toString() {
        return "Lesson{order=" + order + ", title='" + title + "', duration=" + duration + "min}";
    }
}
