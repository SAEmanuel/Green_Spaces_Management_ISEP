package pt.ipp.isep.dei.esoft.project.domain;


import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_BRIGHT_RED;
import static pt.ipp.isep.dei.esoft.project.ui.console.ColorfulOutput.ANSI_RESET;

public class Vehicle {

    private String plateId;
    private String brand;
    private String model;
    private String type;

    private float tareWeight;
    private float grossWeight;
    private float currentKm;
    private float checkUpFrequency;
    private float lastCheckUp;

    private Data registerDate;
    private Data acquisitionDate;


    private static final int LOWEST_LIMIT = 1992;
    private static final int MIDDLE_LIMIT = 2005;
    private static final int HIGHEST_LIMIT = 2020;
    private static final int MAX_CHARS_PLATE = 8;


    public Vehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight,
                   float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        validateVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        this.plateId = plateId;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.currentKm = currentKm;
        this.checkUpFrequency = checkUpFrequency;
        this.registerDate = registerDate;
        this.acquisitionDate = acquisitionDate;
        this.lastCheckUp = lastCheckUp;

    }

    public float getCurrentKm() {
        return currentKm;
    }

    public float getCheckUpFrequency() {
        return checkUpFrequency;
    }

    public float getLastCheckUp() {
        return lastCheckUp;
    }

    public String getPlateId() {
        return plateId;
    }


    //---------------------------------------VALIDATIONS----------------------------------------------------------

    private void validateVehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        //String validations and plate id format
        validatePlateId(plateId, registerDate);
        validateBrand(brand);
        validateModel(model);
        validateType(type);
        validateKm(currentKm, lastCheckUp);
        validateDates(registerDate, acquisitionDate);


    }

    private void validateDates(Data registerDate, Data acquisitionDate) {
        if (acquisitionDate.isGreater(registerDate)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"'Acquisition Date' -> [" + acquisitionDate + "] cannot be latter than 'Register Date' -> [" + registerDate + "]."+ANSI_RESET);
        }
    }

    private void validateKm(float currentKm, float lastCheckUp) {
        if (currentKm < lastCheckUp) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"'Last Checkup' -> [" + lastCheckUp + "] cannot be bigger than 'Current Km' -> [" + currentKm + "]."+ANSI_RESET);
        }

    }


    private void validatePlateId(String plateId, Data registerDate) {
        if (!validateString(plateId)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"PLATE ID cannot be null or empty -> [" + plateId + "]."+ANSI_RESET);
        }
        validatePlate(plateId, registerDate);
    }


    private void validateBrand(String brand) {
        if (!validateString(brand)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"BRAND cannot be null or empty -> [" + brand + "]."+ANSI_RESET);
        }
        if (haveSpecialCharacters(brand)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"BRAND cannot contain special characters -> [" + brand + "]."+ANSI_RESET);
        }

    }


    private void validateModel(String model) {
        if (!validateString(model)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"Reference (MODEL) cannot be null or empty."+ANSI_RESET);
        }
    }

    private void validateType(String type) {
        if (!validateString(type)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"TYPE cannot be null or empty -> [" + type + "]."+ANSI_RESET);
        }
        if (haveSpecialCharacters(type)) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"TYPE cannot contain special characters -> [" + type + "]."+ANSI_RESET);
        }

    }


    //--------------------------------INTERNAL METHODS FOR VALIDATIONS--------------------------------
    private boolean validateString(String string) {
        return (string != null && !string.trim().isEmpty());
    }

    private boolean haveSpecialCharacters(String string) {
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }


    private void validatePlate(String plateId, Data registerDate) {

        verifySizeOfPlate(plateId);
        String first, second, third;
        try {
            String[] plateByParts = plateId.split("-");
            first = plateByParts[0];
            second = plateByParts[1];
            third = plateByParts[2];

            if (registerDate.getYear() > HIGHEST_LIMIT) {

                validatePlateAfterHighest(first, second, third);

            } else if (registerDate.getYear() > MIDDLE_LIMIT) {

                validatePlateAfterMiddle(first, second, third);

            } else if (registerDate.getYear() > LOWEST_LIMIT) {

                validatePlateAfterLowest(first, second, third);

            } else {
                throw new IllegalArgumentException(ANSI_BRIGHT_RED+"Plate format does not correspond to a valid plate id between: [" + LOWEST_LIMIT + "-" + HIGHEST_LIMIT + "]."+ANSI_RESET);

            }
        } catch (ArrayIndexOutOfBoundsException e) {

            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"Plate ID -> [" + plateId + "] is malformed, make sure is in the format: __-__-__"+ANSI_RESET);
        }

    }

    private void validatePlateAfterHighest(String first, String second, String third) {
        if (first.matches("[A-Z]{2}") && second.matches("\\d{2}") && third.matches("[A-Z]{2}")) {
            // Plate matches "After 2020: AA-00-AA"
        } else {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"Invalid plate format for vehicle after year: [" + HIGHEST_LIMIT + "] \"AA-00-AA\""+ANSI_RESET);
        }
    }

    private void validatePlateAfterMiddle(String first, String second, String third) {
        if (first.matches("\\d{2}") && second.matches("[A-Z]{2}") && third.matches("\\d{2}")) {
            // Plate matches "Between 2005-2020: 00-AA-00"
        } else {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"Invalid plate format for vehicle between years: [" + MIDDLE_LIMIT + "-" + HIGHEST_LIMIT + "] \"00-AA-00\""+ANSI_RESET);
        }
    }


    private void validatePlateAfterLowest(String first, String second, String third) {
        if (first.matches("\\d{2}") && second.matches("\\d{2}") && third.matches("[A-Z]{2}")) {
            // Plate matches "Between 1992-2005: 00-00-XX"
        } else {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"Invalid plate format for vehicle between years: [" + LOWEST_LIMIT + "-" + MIDDLE_LIMIT + "] \"00-00-XX\""+ANSI_RESET);
        }
    }

    private void verifySizeOfPlate(String plateId) {
        if (plateId.length() > MAX_CHARS_PLATE) {
            throw new IllegalArgumentException(ANSI_BRIGHT_RED+"PlateId is too long... MAX LENGTH -> [" + MAX_CHARS_PLATE + "]"+ANSI_RESET);
        }
    }

    //------------------------------------------------------------------------------------------------

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj The reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if the other object is the same as this one by reference.
        if (this == obj) {
            return true;
        }
        // Check if the other object is an instance of Skill
        if (!(obj instanceof Vehicle)) {
            return false;
        }
        // Cast the other object to Vehicle
        Vehicle vehicle = (Vehicle) obj;
        return ((plateId.equals(vehicle.plateId)) && (brand.equals(vehicle.brand)) && (model.equals(vehicle.model)) && (type.equals(vehicle.type))
                && (tareWeight == vehicle.tareWeight) && (grossWeight == vehicle.grossWeight) && (currentKm == vehicle.currentKm) && (checkUpFrequency == vehicle.checkUpFrequency)
                && (registerDate.equals(vehicle.registerDate)) && (acquisitionDate.equals(vehicle.acquisitionDate)));
    }

    /**
     * Clone method.
     *
     * @return A clone of the current instance.
     */
    public Vehicle clone() {
        return new Vehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);
    }

    @Override
    public String toString() {
        return String.format("• Plate: %s | Brand: %s | Model: %s | Current Km: %.2f%n" +
                "    Register Date: %s%n    Acquisition Date: %s", plateId, brand, model, currentKm, registerDate, acquisitionDate);
    }

}
