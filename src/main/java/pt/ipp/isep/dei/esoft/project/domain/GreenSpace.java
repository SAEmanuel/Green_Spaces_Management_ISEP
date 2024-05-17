package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.validations.Validations;

public class GreenSpace {

    private String name;
    private String address;
    private Size size;
    private float area;

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


    public GreenSpace(String name, int size, float area, String address) {

        validateInfo(name,area,address);

        this.name = name;
        this.area = area;
        this.address = address;
        this.size = Size.values()[size];
    }

    private void validateInfo(String name, float area,String address) {
        validateName(name);
        validateAddress(address);
//        validateArea(area);

    }

    private void validateAddress(String address) {
        if (!Validations.validateString(address)) {
            throw new IllegalArgumentException("NAME cannot be null or empty -> [" + name + "].");
        }
    }

    private void validateName(String name) {
        if (!Validations.validateString(name)) {
            throw new IllegalArgumentException("NAME cannot be null or empty -> [" + name + "].");
        }
        if (Validations.haveSpecialCharacters(name)) {
            throw new IllegalArgumentException("NAME cannot contain special characters -> [" + name + "].");
        }
    }


    private void validateArea(float area) {
    }

    public GreenSpace clone() {
        return new GreenSpace(name,size.ordinal(),area,address);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("• Name: %s | Size: %s | Area: %f | Address: %s",name,size,area,address);
    }
}
