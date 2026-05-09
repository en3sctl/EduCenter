package mas.educenter;

import java.time.LocalDate;

// Abstract class representing a person
public abstract class Person extends ObjectPlus {

    // Multi-aspect: second classification dimension (besides role: Student/Instructor/Employee)
    public enum Gender { MALE, FEMALE, OTHER }

    private String name;           // simple attribute
    private Address address;       // complex attribute
    private LocalDate birthDate;   // simple attribute
    private Gender gender;         // multi-aspect classification

    // Full constructor
    public Person(String name, Address address, LocalDate birthDate) {
        super();
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
    }

    // Minimal constructor
    public Person(String name) {
        super();
        this.name = name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    // Abstract method - each subclass describes its role differently (polymorphism)
    public abstract String describeRole();

    @Override
    public String toString() {
        return "Person{name='" + name + "', address=" + address + ", birthDate=" + birthDate + "}";
    }
}
