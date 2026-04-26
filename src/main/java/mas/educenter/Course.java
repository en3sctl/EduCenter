package mas.educenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Course extends ObjectPlus {

    private String title;                  // simple attribute
    private int maxCapacity;               // simple attribute
    private List<Integer> lessonDurations; // multi-valued attribute
    private String description;            // optional attribute

    // lessons belonging to this course (composition)
    private List<Lesson> lessons = new ArrayList<>();

    // enrollments of students to this course
    private List<Enrollment> enrollments = new ArrayList<>();

    // the instructor teaching this course
    private Instructor instructor;

    // category this course belongs to
    private Category category;

    public static int totalCourses = 0;    // class attribute

    // Full constructor
    public Course(String title, int maxCapacity, List<Integer> lessonDurations, String description) {
        super();
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.lessonDurations = new ArrayList<>(lessonDurations);
        this.description = description;
        totalCourses++;
    }

    // Minimal constructor
    public Course(String title, int maxCapacity) {
        super();
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.lessonDurations = new ArrayList<>();
        this.description = null;
        totalCourses++;
    }

    public String getTitle() { return title; }
    public int getMaxCapacity() { return maxCapacity; }

    public List<Integer> getLessonDurations() { return lessonDurations; }

    public void setLessonDurations(List<Integer> lessonDurations) {
        this.lessonDurations = new ArrayList<>(lessonDurations);
    }

    // Optional attribute
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(Optional<String> description) {
        this.description = description.orElse(null);
    }

    // Derived attribute - sum of lesson durations, not stored
    public int getTotalDuration() {
        int total = 0;
        for (int d : lessonDurations) {
            total += d;
        }
        return total;
    }

    // package-private - called only by Lesson.createLesson
    void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    // removing the course also removes all its lessons
    public void removeCourse() {
        for (Lesson l : lessons) {
            Lesson.disconnect(l);
        }
        lessons.clear();
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

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setInstructor(Instructor instructor) {
        if (this.instructor == instructor) {
            return;
        }
        if (this.instructor != null) {
            Instructor old = this.instructor;
            this.instructor = null;
            old.removeCourse(this);
        }
        this.instructor = instructor;
        if (instructor != null) {
            instructor.addCourse(this);
        }
    }

    public Instructor getInstructor() { return instructor; }

    void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() { return category; }

    // Class method
    public static int getTotalCourses() {
        return totalCourses;
    }

    // Class method - finds courses with at least given capacity
    public static List<Course> findByCapacity(int min) {
        List<Course> result = new ArrayList<>();
        try {
            for (Course c : ObjectPlus.getExtent(Course.class)) {
                if (c.maxCapacity >= min) {
                    result.add(c);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Course extent not found.");
        }
        return result;
    }

    @Override
    public String toString() {
        return "Course{title='" + title + "', maxCapacity=" + maxCapacity
                + ", lessonDurations=" + lessonDurations
                + ", description=" + getDescription().orElse("none")
                + ", totalDuration=" + getTotalDuration() + "min}";
    }
}
