package mas.educenter;

import java.io.Serializable;

// Complex attribute - does not extend ObjectPlus
public class Address implements Serializable {

    private String street;
    private String city;
    private String zipCode;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getZipCode() { return zipCode; }

    @Override
    public String toString() {
        return street + ", " + city + " " + zipCode;
    }
}
