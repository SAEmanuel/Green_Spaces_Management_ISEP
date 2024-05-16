package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.validations.Validations;

public class GreenSpace {

    private String name;
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


    public GreenSpace(String name, int size, float area) {

        validateInfo(name,area);

        this.name = name;
        this.size = Size.values()[size];
        this.area = area;
    }

    private void validateInfo(String name, float area) {
        validteName(name);
//        validateArea(area);

    }

    private void validateArea(float area) {
    }

    private void validteName(String name) {
        if (!Validations.validateString(name)) {
            throw new IllegalArgumentException("NAME cannot be null or empty -> [" + name + "].");
        }
        if (Validations.haveSpecialCharacters(name)) {
            throw new IllegalArgumentException("NAME cannot contain special characters -> [" + name + "].");
        }
    }


    public GreenSpace clone() {
        return new GreenSpace(name,size.ordinal(),area);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("• Name: %s | Size: %s | Area: %f",name,size,area);
    }
}
