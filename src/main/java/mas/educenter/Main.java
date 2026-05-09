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

        // =======================================================
        // --- MP2: Associations ---
        // =======================================================
        System.out.println("\n=== EduCenter - MP2 Demonstration ===\n");

        // 1. Binary bidirectional association 1-to-* (Instructor <-> Course)
        i1.addCourse(c1); // binary bidirectional 1-to-*
        i1.addCourse(c2); // binary bidirectional 1-to-*
        i2.addCourse(c3); // binary bidirectional 1-to-*
        System.out.println("Instructor " + i1.getName() + " teaches: " + i1.getCourses());
        System.out.println("Course '" + c1.getTitle() + "' instructor: " + c1.getInstructor().getName());

        // Demonstrates the fuse - calling the reverse does not duplicate
        c1.setInstructor(i1); // already set, contains() prevents duplication

        // 2. Association with attribute (Student <-> Enrollment <-> Course)
        var e1 = new Enrollment(s1, c1, LocalDate.of(2026, 2, 1), "active"); // association with attribute
        var e2 = new Enrollment(s2, c1, LocalDate.of(2026, 2, 1), "active"); // association with attribute
        var e3 = new Enrollment(s2, c2, LocalDate.of(2026, 2, 10), "active"); // association with attribute
        e1.setGrade(4.5);
        e3.setStatus("completed");
        System.out.println("Enrollments of " + s2.getName() + ": " + s2.getEnrollments());
        System.out.println("Enrollments of course '" + c1.getTitle() + "': " + c1.getEnrollments());
        System.out.println("Courses of " + s2.getName() + ": "
                + s2.getCourses().stream().map(Course::getTitle).toList());

        // 3. Composition (Course ◆→ Lesson) - Lesson cannot exist without a Course
        try {
            Lesson.createLesson(c1, "Variables and Types", 90, 1); // composition
            Lesson.createLesson(c1, "Control Flow", 90, 2); // composition
            Lesson.createLesson(c1, "Methods and Classes", 45, 3); // composition
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        System.out.println("Lessons of course '" + c1.getTitle() + "':");
        for (Lesson l : c1.getLessons()) {
            System.out.println("  " + l);
        }

        // 4. Qualified association (Category -> Course by title) - fast lookup via Map
        cat1.addCourse(c1); // qualified association - indexed by title
        cat1.addCourse(c3); // qualified association - indexed by title
        cat2.addCourse(c2); // qualified association - indexed by title
        Course found = cat1.findCourseByTitle("Java Programming"); // qualified lookup
        System.out.println(
                "Qualified lookup in '" + cat1.getName() + "' by title='Java Programming': " + found.getTitle());

        // 5. Recursive bidirectional association (Category parent <-> children)
        var rootCat = new Category("All Courses", "Root category");
        rootCat.addSubcategory(cat1); // recursive bidirectional - parent/children
        rootCat.addSubcategory(cat2); // recursive bidirectional - parent/children
        System.out.println("Subcategories of '" + rootCat.getName() + "': "
                + rootCat.getSubcategories().stream().map(Category::getName).toList());
        System.out.println("Parent of '" + cat1.getName() + "': "
                + cat1.getParentCategory().map(Category::getName).orElse("none"));

        System.out.println("\n=== End of MP2 Demonstration ===\n");

        // =======================================================
        // --- MP3: Inheritance ---
        // =======================================================
        System.out.println("=== EduCenter - MP3 Demonstration ===\n");

        // 1. Disjoint inheritance (Course -> OnlineCourse / InPersonCourse)
        OnlineCourse onCourse = new OnlineCourse("Python Basics", 50,
                List.of(60, 60, 60), "Online intro", "Zoom", "https://zoom.us/abc"); // disjoint inheritance
        InPersonCourse offCourse = new InPersonCourse("Algorithms", 25,
                List.of(90, 90), "Classroom course", "Room 101", "Main Campus", 25); // disjoint inheritance
        System.out.println("Disjoint subclass instance: " + onCourse);
        System.out.println("Disjoint subclass instance: " + offCourse);

        // 2. Abstract class & abstract method (Person.describeRole)
        // Cannot create a Person directly - it is abstract
        // Person abstractTest = new Person("X"); // would not compile - abstract class
        System.out.println("Abstract method via Student: " + s1.describeRole()); // abstract method
        System.out.println("Abstract method via Instructor: " + i1.describeRole()); // abstract method

        // 3. Polymorphic method calls (same reference type, different runtime behavior)
        Person[] people = { s1, s2, i1, i2 };
        for (Person p : people) {
            System.out.println("Polymorphic call: " + p.describeRole()); // polymorphic method call
        }

        // 4. Multiple inheritance via interface (extends Student, implements
        // IInstructor)
        WorkingStudent ws = new WorkingStudent("Ali Ozturk", "S099", 3.5,
                List.of("EN", "TR"), null, 60.0); // multiple inheritance via interface
        ws.addTaughtCourse(c3); // method from IInstructor
        ws.setGpa(3.7); // method inherited from Student
        System.out.println("WorkingStudent GPA (from Student):     " + ws.getGpa());
        System.out.println("WorkingStudent rate (from IInstructor): " + ws.getHourlyRate());

        // 5. Overlapping inheritance (one object plays two roles - Student AND
        // IInstructor)
        boolean isStudent = (ws instanceof Student);
        boolean isInstr = (ws instanceof IInstructor);
        System.out.println("Overlapping - is Student? " + isStudent + ", is IInstructor? " + isInstr); // overlapping
                                                                                                       // inheritance

        // 6. Multi-aspect inheritance (role aspect via class hierarchy + gender aspect
        // via flattening)
        s1.setGender(Person.Gender.MALE); // multi-aspect - second classification
        s2.setGender(Person.Gender.MALE); // multi-aspect - second classification
        i1.setGender(Person.Gender.MALE);
        i2.setGender(Person.Gender.FEMALE);
        System.out.println("Multi-aspect: " + i2.getName() + " is " + i2.getGender()
                + " and " + i2.describeRole());

        // 7. Dynamic inheritance (object changes its class via conversion constructor)
        Person dynamicPerson = new Student("Kerem Yilmaz", "S003"); // starts as Student
        System.out.println("Before role change: " + dynamicPerson.describeRole());

        dynamicPerson = new Employee(dynamicPerson, "TA", 2500.0); // dynamic inheritance - now Employee
        System.out.println("After role change:  " + dynamicPerson.describeRole());

        dynamicPerson = new Instructor(dynamicPerson, "Junior Lecturer", 80.0); // dynamic inheritance - now Instructor
        System.out.println("After another change: " + dynamicPerson.describeRole());

        System.out.println("\n=== End of MP3 Demonstration ===\n");

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

        System.out.println("=== End of Demonstration ===");
    }
}
