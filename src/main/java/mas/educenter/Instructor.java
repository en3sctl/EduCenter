package mas.educenter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructors")
public class Instructor extends Person {

    private String title;              // simple attribute

    @ElementCollection                 // multi-valued attribute
    @CollectionTable(name = "instructor_expertise", joinColumns = @JoinColumn(name = "instructor_id"))
    @Column(name = "expertise")
    private List<String> expertise;

    private double hourlyRate;         // simple attribute

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses = new ArrayList<>();

    @Transient
    public static double averageHourlyRate = 0.0; // class attribute

    @Transient
    private static int instructorCount = 0;
    @Transient
    private static double totalRate = 0.0;

    protected Instructor() {
        super();
    }

    public Instructor(String name, Address address, LocalDate birthDate,
                      String title, List<String> expertise, double hourlyRate) {
        super(name, address, birthDate);
        this.title = title;
        this.expertise = new ArrayList<>(expertise);
        this.hourlyRate = hourlyRate;
        updateAverage(hourlyRate);
    }

    public Instructor(String name, String title) {
        super(name);
        this.title = title;
        this.expertise = new ArrayList<>();
        this.hourlyRate = 0.0;
        updateAverage(0.0);
    }

    // Conversion constructor - dynamic inheritance support
    public Instructor(Person prev, String title, double hourlyRate) {
        super(prev.getName(), prev.getAddress(), prev.getBirthDate());
        this.title = title;
        this.expertise = new ArrayList<>();
        this.hourlyRate = hourlyRate;
        updateAverage(hourlyRate);
    }

    private static void updateAverage(double rate) {
        instructorCount++;
        totalRate += rate;
        averageHourlyRate = totalRate / instructorCount;
    }

    public String getTitle() { return title; }

    public List<String> getExpertise() { return expertise; }

    public void setExpertise(List<String> expertise) {
        this.expertise = new ArrayList<>(expertise);
    }

    public double getHourlyRate() { return hourlyRate; }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            course.setInstructor(this);
        }
    }

    public void removeCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.setInstructor(null);
        }
    }

    public List<Course> getCourses() { return courses; }

    // Class method - finds highest paid instructor from extent
    public static Instructor findHighestPaid() {
        Instructor highest = null;
        try {
            for (Instructor i : ObjectPlus.getExtent(Instructor.class)) {
                if (highest == null || i.hourlyRate > highest.hourlyRate) {
                    highest = i;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Instructor extent not found.");
        }
        return highest;
    }

    // Polymorphic implementation of the abstract role method
    @Override
    public String describeRole() {
        return "Instructor (" + title + ") teaching " + courses.size() + " course(s)";
    }

    @Override
    public String toString() {
        return "Instructor{name='" + getName() + "', title='" + title + "', expertise=" + expertise
                + ", hourlyRate=" + hourlyRate + "}";
    }
}
