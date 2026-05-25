package mas.educenter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Inheritance in the relational model - JOINED strategy: one table per class
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "courses")
public class Course extends ObjectPlus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;                  // simple attribute

    private int maxCapacity;               // simple attribute

    @ElementCollection                     // multi-valued attribute
    @CollectionTable(name = "course_lesson_durations", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "duration")
    private List<Integer> lessonDurations;

    @Column(nullable = true)
    private String description;            // optional attribute

    // Composition (Course ◆→ Lesson) - lessons are deleted together with the course
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments = new ArrayList<>();

    // 1-* association with Instructor - foreign key lives in this table
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    // Qualified association with Category - in the database it's just a 1-*
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    public static int totalCourses = 0;    // class attribute

    public Course() {
        super();
    }

    public Course(String title, int maxCapacity, List<Integer> lessonDurations, String description) {
        super();
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.lessonDurations = new ArrayList<>(lessonDurations);
        this.description = description;
        totalCourses++;
    }

    public Course(String title, int maxCapacity) {
        super();
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.lessonDurations = new ArrayList<>();
        this.description = null;
        totalCourses++;
    }

    public Long getId() { return id; }

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
    @Transient
    public int getTotalDuration() {
        int total = 0;
        for (int d : lessonDurations) {
            total += d;
        }
        return total;
    }

    void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

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

    public static int getTotalCourses() {
        return totalCourses;
    }

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
