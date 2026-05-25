package mas.educenter;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;

// Disjoint inheritance: Person -> Student / Instructor / Employee
@Entity
@Table(name = "employees")
public class Employee extends Person {

    private String position;
    private double salary;
    private LocalDate hireDate;

    protected Employee() {
        super();
    }

    public Employee(String name, Address address, LocalDate birthDate,
                    String position, double salary, LocalDate hireDate) {
        super(name, address, birthDate);
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    public Employee(String name, String position, double salary) {
        super(name);
        this.position = position;
        this.salary = salary;
        this.hireDate = LocalDate.now();
    }

    // Conversion constructor - used for dynamic inheritance
    public Employee(Person prev, String position, double salary) {
        super(prev.getName(), prev.getAddress(), prev.getBirthDate());
        this.position = position;
        this.salary = salary;
        this.hireDate = LocalDate.now();
    }

    public String getPosition() { return position; }
    public double getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }

    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String describeRole() {
        return "Employee working as " + position + " (salary: " + salary + ")";
    }

    @Override
    public String toString() {
        return "Employee{name='" + getName() + "', position='" + position
                + "', salary=" + salary + ", hireDate=" + hireDate + "}";
    }
}
