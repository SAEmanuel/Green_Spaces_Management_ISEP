package pt.ipp.isep.dei.esoft.project.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Employee class represents an employee in the project domain.
 */
public class Employee implements Serializable {
    private final String email;
    private String name;
    private String position;
    private String phone;

    /**
     * Constructor for creating an Employee object with email.
     *
     * @param email The email of the employee.
     */
    public Employee(String email) {
        this.email = email;
    }

    /**
     * Checks if two Employee objects are equal.
     *
     * @param o The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee employee = (Employee) o;
        return email.equals(employee.email);
    }

    /**
     * Generates a hash code value for the Employee object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    /**
     * Checks if the employee has the given email.
     *
     * @param email The email to check.
     * @return True if the employee has the email, false otherwise.
     */
    public boolean hasEmail(String email) {
        return this.email.equals(email);
    }


    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Employee clone() {
        return new Employee(this.email);
    }
}