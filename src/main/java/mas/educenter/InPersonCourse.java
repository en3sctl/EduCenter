package mas.educenter;

import java.util.List;

// Disjoint subclass of Course - taught physically in a classroom
public class InPersonCourse extends Course {

    private String room;
    private String campus;
    private int maxSeats;

    public InPersonCourse(String title, int maxCapacity, List<Integer> lessonDurations,
                          String description, String room, String campus, int maxSeats) {
        super(title, maxCapacity, lessonDurations, description);
        this.room = room;
        this.campus = campus;
        this.maxSeats = maxSeats;
    }

    public InPersonCourse(String title, int maxCapacity, String room, String campus) {
        super(title, maxCapacity);
        this.room = room;
        this.campus = campus;
        this.maxSeats = maxCapacity;
    }

    public String getRoom() { return room; }
    public String getCampus() { return campus; }
    public int getMaxSeats() { return maxSeats; }

    @Override
    public String toString() {
        return "InPersonCourse{title='" + getTitle() + "', room='" + room
                + "', campus='" + campus + "', maxSeats=" + maxSeats + "}";
    }
}