package mas.educenter;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== EduCenter - MP1 Demonstration ===\n");

        // --- MP1: Classes and Attributes ---

        // 1. Extent + auto-add in constructor
        var s1 = new Student("Recep Aktas", "S001");
        var s2 = new Student("Enes Catal", "S002", 3.8, List.of("EN", "TR", "PL"), "Prof. Werner");
        ObjectPlus.showExtent(Student.class); // extent

        // 2. Complex attribute
        s1.setAddress(new Address("ul. Dolna 2", "Warsaw", "00-001")); // complex attribute
        System.out.println("Complex attribute (address): " + s1.getAddress());

        // 3. Optional attribute
        s2.setAdvisor(Optional.of("Prof. Tom")); // optional attribute - present
        System.out.println("Optional attribute (present): " + s2.getAdvisor());
        System.out.println("Optional attribute (empty):   " + s1.getAdvisor()); // optional attribute - empty

        // 4. Multi-valued attribute
        s2.setLanguages(List.of("EN", "TR", "PL")); // multi-valued attribute
        System.out.println("Multi-valued attribute (languages): " + s2.getLanguages());

        // 5. Class attribute
        System.out.println("Class attribute (totalStudents): " + Student.totalStudents); // class attribute

        // Create courses to demonstrate more constructs
        var c1 = new Course("Java Programming", 30, List.of(90, 90, 45, 60), "Intro to Java");
        var c2 = new Course("Database Systems", 25, List.of(90, 90, 90), "SQL and NoSQL");
        var c3 = new Course("Web Development", 20);

        System.out.println("Class attribute (totalCourses):  " + Course.totalCourses); // class attribute

        // 6. Derived attribute
        System.out.println("Derived attribute (totalDuration): " + c1.getTotalDuration() + " min"); // derived attribute
                                                                                                    // - computed from
                                                                                                    // lessonDurations
        System.out.println("Derived attribute (fullName):      " + s1.getFullName()); // derived attribute

        // 7. Class method
        List<Student> enSpeakers = Student.findByLanguage("EN"); // class method
        System.out.println("Class method findByLanguage('EN'): " + enSpeakers);
        System.out.println("Class method getTotalCourses():    " + Course.getTotalCourses()); // class method

        // 8. Override
        System.out.println("Override toString(): " + s1.toString()); // override
        System.out.println("Override toString(): " + c1.toString()); // override

        // 9. Overload
        System.out.println("Overload getGpa():      " + s2.getGpa()); // overload - no param
        System.out.println("Overload getGpa(0.5):   " + s2.getGpa(0.5)); // overload - with bonus param

        // Create instructors
        var i1 = new Instructor("Dr. Mariusz Trzaska",
                new Address("ul. Koszykowa 1", "Warsaw", "00-100"),
                LocalDate.of(1975, 5, 12),
                "Professor", List.of("Java", "OOP", "Design Patterns"), 150.0);
        var i2 = new Instructor("Dr. Emily Clark",
                new Address("Baker St 10", "London", "W1U 3BW"),
                LocalDate.of(1980, 8, 23),
                "Associate Professor", List.of("Databases", "SQL"), 120.0);

        // Class method & class attribute for Instructor
        System.out.println("Highest paid instructor: " + Instructor.findHighestPaid()); // class method
        System.out.println("Average hourly rate:     " + Instructor.averageHourlyRate); // class attribute

        // Category with optional self-reference
        var cat1 = new Category("Programming", "All programming courses");
        var cat2 = new Category("Advanced Programming", "Advanced topics", cat1);
        System.out.println("Category (no parent):   " + cat1);
        System.out.println("Category (with parent): " + cat2);

        // Show all extents
        System.out.println("\n=== All Extents ===");
        ObjectPlus.showExtent(Student.class);
        ObjectPlus.showExtent(Instructor.class);
        ObjectPlus.showExtent(Course.class);
        ObjectPlus.showExtent(Category.class);

        // Class method - find courses by capacity
        List<Course> largeCourses = Course.findByCapacity(25); // class method
        System.out.println("Courses with capacity >= 25: " + largeCourses);

        // 10. Extent persistency
        try {
            // Write extents to file
            ObjectPlus.writeExtents(new ObjectOutputStream(new FileOutputStream("extents.ser"))); // extent persistency
                                                                                                  // - write
            System.out.println("\nExtents saved to extents.ser");

            // Read extents from file
            ObjectPlus.readExtents(new ObjectInputStream(new FileInputStream("extents.ser"))); // extent persistency -
                                                                                               // read
            System.out.println("Extents loaded from extents.ser");

            // Verify loaded extents
            System.out.println("\n=== Extents after deserialization ===");
            ObjectPlus.showExtent(Student.class);
            ObjectPlus.showExtent(Course.class);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Extent persistency error: " + e.getMessage());
        }

        System.out.println("=== End of MP1 Demonstration ===");
    }
}
