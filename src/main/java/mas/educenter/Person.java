package mas.educenter;

import java.time.LocalDate;

// Abstract class representing a person
public abstract class Person extends ObjectPlus {

    private String name;           // simple attribute
    private Address address;       // complex attribute
    private LocalDate birthDate;   // simple attribute

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

    @Override
    public String toString() {
        return "Person{name='" + name + "', address=" + address + ", birthDate=" + birthDate + "}";
    }
}
