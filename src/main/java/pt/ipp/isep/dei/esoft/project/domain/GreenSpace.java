package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.Extras.validations.Validations;

import java.io.Serializable;

/**
 * The GreenSpace class represents a green space in the project domain.
 */
public class GreenSpace implements Serializable {

    private final String name;       // The name of the green space
    private final String address;    // The address of the green space
    private final String responsible; // The responsible person for the green space
    private final Size size;         // The size of the green space
    private final float area;        // The area of the green space


    public Size getSize() {
        return size;
    }


    /**
     * Enum {@code Size} defines the possible sizes for a green space.
     */
    public enum Size{
        GARDEN{
            @Override
            public String toString() {
                return "Garden";
            }
        },
        MEDIUM{
            @Override
            public String toString() {
                return "Medium";
            }
        },
        LARGE{
            @Override
            public String toString() {
                return "Large";
            }
        }
    }

    /**
     * Constructs a new {@code GreenSpace} object with the specified details.
     *
     * @param name the name of the green space
     * @param size the size of the green space (0 for GARDEN, 1 for MEDIUM, 2 for LARGE)
     * @param area the area of the green space
     * @param address the address of the green space
     * @param responsible the responsible person for the green space
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public GreenSpace(String name, int size, float area, String address,String responsible) {

        validateInfo(name,area,address);

        this.name = name.trim();
        this.area = area;
        this.address = address.trim();
        this.size = Size.values()[size];
        this.responsible = responsible;
    }

    /**
     * Validates the provided name, area, and address.
     *
     * @param name the name to validate
     * @param area the area to validate
     * @param address the address to validate
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    private void validateInfo(String name, float area,String address) {
        validateName(name);
        validateAddress(address);
        validateArea(area);

    }


    /**
     * Validates the address.
     *
     * @param address the address to validate
     * @throws IllegalArgumentException if the address is null or empty
     */
    private void validateAddress(String address) {
        if (!Validations.validateString(address)) {
            throw new IllegalArgumentException("NAME cannot be null or empty -> [" + name + "].");
        }
    }


    /**
     * Validates the name.
     *
     * @param name the name to validate
     * @throws IllegalArgumentException if the name is null, empty, or contains special characters
     */
    private void validateName(String name) {
        if (!Validations.validateString(name)) {
            throw new IllegalArgumentException("NAME cannot be null or empty -> [" + name + "].");
        }
        if (Validations.haveSpecialCharacters(name)) {
            throw new IllegalArgumentException("NAME cannot contain special characters -> [" + name + "].");
        }
    }


    /**
     * Validates the area.
     *
     * @param area the area to validate
     * @throws IllegalArgumentException if the area is zero or negative
     */
    private void validateArea(float area) {
        if (area <= 0) {
            throw new IllegalArgumentException("AREA cannot be zero or negative.");
        }
    }


    /**
     * Creates and returns a copy of this {@code GreenSpace} object.
     *
     * @return a clone of this {@code GreenSpace} object
     */
    public GreenSpace clone() {
        return new GreenSpace(name,size.ordinal(),area,address,responsible);
    }


    /**
     * Returns the name of the green space.
     *
     * @return the name of the green space
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the responsible person for the green space.
     *
     * @return the responsible person for the green space
     */
    public String getResponsible() {
        return responsible;
    }

    /**
     *
     * @return the area of the green space
     */
    public float getArea() {
        return area;
    }

    /**
     * Returns the address for the green space.
     *
     * @return the address for the green space
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return String.format("• Name: %s | Size: %s | Area: %f | Address: %s | Responsible: %s",name,size,area,address,responsible);
    }
}
