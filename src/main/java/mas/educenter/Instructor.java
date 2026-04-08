package mas.educenter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private String title;              // simple attribute
    private List<String> expertise;    // multi-valued attribute
    private double hourlyRate;         // simple attribute

    public static double averageHourlyRate = 0.0; // class attribute
    private static int instructorCount = 0;
    private static double totalRate = 0.0;

    // Full constructor
    public Instructor(String name, Address address, LocalDate birthDate,
                      String title, List<String> expertise, double hourlyRate) {
        super(name, address, birthDate);
        this.title = title;
        this.expertise = new ArrayList<>(expertise);
        this.hourlyRate = hourlyRate;
        updateAverage(hourlyRate);
    }

    // Minimal constructor
    public Instructor(String name, String title) {
        super(name);
        this.title = title;
        this.expertise = new ArrayList<>();
        this.hourlyRate = 0.0;
        updateAverage(0.0);
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

    @Override
    public String toString() {
        return "Instructor{name='" + getName() + "', title='" + title + "', expertise=" + expertise
                + ", hourlyRate=" + hourlyRate + "}";
    }
}
