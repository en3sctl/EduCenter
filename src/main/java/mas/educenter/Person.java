package mas.educenter;

import jakarta.persistence.*;
import java.time.LocalDate;

// Abstract class representing a person
// Inheritance in the relational model - JOINED strategy: separate table for each subclass
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "persons")
public abstract class Person extends ObjectPlus {

    // Multi-aspect: second classification dimension (besides role: Student/Instructor/Employee)
    public enum Gender { MALE, FEMALE, OTHER }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;            // simple attribute

    @Embedded                       // complex attribute - mapped as columns inside persons table
    private Address address;

    private LocalDate birthDate;    // simple attribute

    @Enumerated(EnumType.STRING)    // multi-aspect classification stored as string
    private Gender gender;

    // No-arg constructor required by JPA
    protected Person() {
        super();
    }

    public Person(String name, Address address, LocalDate birthDate) {
        super();
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
    }

    public Person(String name) {
        super();
        this.name = name;
    }

    public Long getId() { return id; }

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
