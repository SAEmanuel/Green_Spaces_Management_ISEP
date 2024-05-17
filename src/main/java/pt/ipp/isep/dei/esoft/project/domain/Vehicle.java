package pt.ipp.isep.dei.esoft.project.domain;


import pt.ipp.isep.dei.esoft.project.domain.validations.Validations;

public class Vehicle {

    // Attributes
    private String plateId;
    private String brand;
    private String model;
    private Type type;

    private float tareWeight;
    private float grossWeight;
    private float currentKm;
    private float checkUpFrequency;
    private float lastCheckUp;

    private Data registerDate;
    private Data acquisitionDate;

    // Constants
    private static final int LOWEST_LIMIT = 1992;
    private static final int MIDDLE_LIMIT = 2005;
    private static final int HIGHEST_LIMIT = 2020;
    private static final int MAX_CHARS_PLATE = 8;

    /**
     * Represents the type of vehicles.
     */
    public enum Type{
        CAR{
            @Override
            public String toString() {
                return "Car";
            }
        },
        TRUCK{
            @Override
            public String toString() {
                return "Truck";
            }
        },
        MOTORCYCLE{
            @Override
            public String toString() {
                return "Motorcycle";
            }
        },
        BUS{
            @Override
            public String toString() {
                return "Bus";
            }
        },
        TRACTOR{
            @Override
            public String toString() {
                return "Tractor";
            }
        },
        BOAT{
            @Override
            public String toString() {
                return "Boat";
            }
        }
    }

    public Type[] showType(){
        return Type.values();
    }


    /**
     * Constructs a Vehicle object with the given parameters.
     *
     * @param plateId          The plate ID of the vehicle.
     * @param brand            The brand of the vehicle.
     * @param model            The model of the vehicle.
     * @param type             The type of the vehicle.
     * @param tareWeight       The tare weight of the vehicle.
     * @param grossWeight      The gross weight of the vehicle.
     * @param currentKm        The current kilometers of the vehicle.
     * @param checkUpFrequency The frequency of check-up for the vehicle.
     * @param lastCheckUp      The last check-up kilometers of the vehicle.
     * @param registerDate     The registration date of the vehicle.
     * @param acquisitionDate  The acquisition date of the vehicle.
     * @throws IllegalArgumentException if any parameter validation fails.
     */
    public Vehicle(String plateId, String brand, String model, int type, float tareWeight, float grossWeight,
                   float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        validateVehicle(plateId, brand, model, type, tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);

        this.plateId = plateId;
        this.brand = brand;
        this.model = model;
        this.type = Type.values()[type];
        this.tareWeight = tareWeight;
        this.grossWeight = grossWeight;
        this.currentKm = currentKm;
        this.checkUpFrequency = checkUpFrequency;
        this.registerDate = registerDate;
        this.acquisitionDate = acquisitionDate;
        this.lastCheckUp = lastCheckUp;

    }


    /**
     * Returns the current kilometers of the vehicle.
     *
     * @return The current kilometers of the vehicle.
     */
    public float getCurrentKm() {
        return currentKm;
    }

    /**
     * Returns the check-up frequency of the vehicle.
     *
     * @return The check-up frequency of the vehicle.
     */
    public float getCheckUpFrequency() {
        return checkUpFrequency;
    }

    /**
     * Returns the last check-up kilometers of the vehicle.
     *
     * @return The last check-up kilometers of the vehicle.
     */
    public float getLastCheckUp() {
        return lastCheckUp;
    }

    /**
     * Returns the plate ID of the vehicle.
     *
     * @return The plate ID of the vehicle.
     */
    public String getPlateId() {
        return plateId;
    }

    public void setLastCheckUp(float lastCheckUp) {
        this.lastCheckUp = lastCheckUp;
    }

    //---------------------------------------VALIDATIONS----------------------------------------------------------



    /**
     * Validates the parameters passed to the Vehicle constructor.
     *
     * @param plateId          The plate ID of the vehicle.
     * @param brand            The brand of the vehicle.
     * @param model            The model of the vehicle.
     * @param type             The type of the vehicle.
     * @param tareWeight       The tare weight of the vehicle.
     * @param grossWeight      The gross weight of the vehicle.
     * @param currentKm        The current kilometers of the vehicle.
     * @param checkUpFrequency The frequency of check-up for the vehicle.
     * @param lastCheckUp      The last check-up kilometers of the vehicle.
     * @param registerDate     The registration date of the vehicle.
     * @param acquisitionDate  The acquisition date of the vehicle.
     * @throws IllegalArgumentException if any parameter validation fails.
     */
    private void validateVehicle(String plateId, String brand, String model, int type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        validatePlateId(plateId, registerDate);
        validateBrand(brand);
        validateModel(model);
        validateKm(currentKm, lastCheckUp);
        validateDates(registerDate, acquisitionDate);
        validatesWeights(tareWeight,grossWeight);
        validatesNegatives(tareWeight,grossWeight,lastCheckUp,currentKm,checkUpFrequency);


    }

    /**
     * Validates if any of the given floats are negative.
     *
     * @param tareWeight       The tare weight of the vehicle.
     * @param grossWeight      The gross weight of the vehicle.
     * @param lastCheckUp      The last check-up kilometers of the vehicle.
     * @param currentKm        The current kilometers of the vehicle.
     * @param checkUpFrequency The frequency of check-up for the vehicle.
     * @throws IllegalArgumentException if any of the given floats are negative.
     */
    private void validatesNegatives(float tareWeight, float grossWeight, float lastCheckUp, float currentKm, float checkUpFrequency) {
        if (Validations.isNegative(tareWeight)) {
            throw new IllegalArgumentException("Tare Weight cannot be negative!");
        }
        if (Validations.isNegative(grossWeight)) {
            throw new IllegalArgumentException("Gross Weight cannot be negative!");
        }
        if (Validations.isNegative(lastCheckUp)) {
            throw new IllegalArgumentException("Last Check Up cannot be negative!");
        }
        if (Validations.isNegative(currentKm)) {
            throw new IllegalArgumentException("Current Km cannot be negative!");
        }
        if (Validations.isNegative(checkUpFrequency)) {
            throw new IllegalArgumentException("Check Up Frequency cannot be negative!");
        }
    }


    /**
     * Validates if the tare weight is not greater than the gross weight.
     *
     * @param tareWeight  The tare weight of the vehicle.
     * @param grossWeight The gross weight of the vehicle.
     * @throws IllegalArgumentException if the tare weight is greater than the gross weight.
     */
    private void validatesWeights(float tareWeight, float grossWeight) {
        if (tareWeight > grossWeight) {
            throw new IllegalArgumentException("'Tare Weight' -> [" + tareWeight + "] cannot be bigger than 'Gross Weight' -> [" + grossWeight + "].");
        }
    }


    /**
     * Validates if the registration date is not later than the acquisition date.
     *
     * @param registerDate    The registration date of the vehicle.
     * @param acquisitionDate The acquisition date of the vehicle.
     * @throws IllegalArgumentException if the registration date is later than the acquisition date.
     */
    private void validateDates(Data registerDate, Data acquisitionDate) {
        if (registerDate.isGreater(acquisitionDate)) {
            throw new IllegalArgumentException("'Acquisition Date' -> [" + acquisitionDate + "] must be latter than 'Register Date' -> [" + registerDate + "].");
        }
    }


    /**
     * Validates if the current kilometers are not less than the last check-up kilometers.
     *
     * @param currentKm  The current kilometers of the vehicle.
     * @param lastCheckUp The last check-up kilometers of the vehicle.
     * @throws IllegalArgumentException if the current kilometers are less than the last check-up kilometers.
     */
    private void validateKm(float currentKm, float lastCheckUp) {
        if (currentKm < lastCheckUp) {
            throw new IllegalArgumentException("'Last Checkup' -> [" + lastCheckUp + "] cannot be bigger than 'Current Km' -> [" + currentKm + "].");
        }

    }

    /**
     * Validates the plate ID format and structure.
     *
     * @param plateId      The plate ID of the vehicle.
     * @param registerDate The registration date of the vehicle.
     * @throws IllegalArgumentException if the plate ID format or structure is invalid.
     */
    private void validatePlateId(String plateId, Data registerDate) {
        if (!Validations.validateString(plateId)) {
            throw new IllegalArgumentException("PLATE ID cannot be null or empty -> [" + plateId + "].");
        }
        validatePlate(plateId, registerDate);
    }


    /**
     * Validates the brand of the vehicle.
     *
     * @param brand The brand of the vehicle.
     * @throws IllegalArgumentException if the brand is null, empty, contains special characters, or numbers.
     */
    private void validateBrand(String brand) {
        if (!Validations.validateString(brand)) {
            throw new IllegalArgumentException("BRAND cannot be null or empty -> [" + brand + "].");
        }
        if (!Validations.hasOnlyLettersAndSpaces(brand.trim())) {
            throw new IllegalArgumentException("BRAND cannot contain special characters or numbers -> [" + brand + "].");
        }


    }


    /**
     * Validates the model of the vehicle.
     *
     * @param model The model of the vehicle.
     * @throws IllegalArgumentException if the model is null, empty, or contains special characters.
     */
    private void validateModel(String model) {
        if (!Validations.validateString(model)) {
            throw new IllegalArgumentException("MODEL cannot be null or empty -> [" + model + "].");
        }
        if (Validations.haveSpecialCharacters(model)) {
            throw new IllegalArgumentException("MODEL cannot contain special characters -> [" + model + "].");
        }
    }




    //--------------------------------INTERNAL METHODS FOR VALIDATIONS--------------------------------


    /**
     * Validates the format of the plate ID based on the registration date.
     *
     * @param plateId The plate ID of the vehicle.
     * @param registerDate The registration date of the vehicle.
     * @throws IllegalArgumentException if the plate ID format is invalid.
     */
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
                throw new IllegalArgumentException("Plate format does not correspond to a valid plate id between: [" + LOWEST_LIMIT + "-" + HIGHEST_LIMIT + "].");

            }
        } catch (ArrayIndexOutOfBoundsException e) {

            throw new IllegalArgumentException("Plate ID -> [" + plateId + "] is malformed, make sure is in the format: __-__-__");
        }

    }


    /**
     * Validates the plate format for vehicles registered after the highest limit.
     *
     * @param first  The first part of the plate ID.
     * @param second The second part of the plate ID.
     * @param third  The third part of the plate ID.
     * @throws IllegalArgumentException if the plate format is invalid for vehicles registered after the highest limit.
     */
    private void validatePlateAfterHighest(String first, String second, String third) {
        if (first.matches("[A-Z]{2}") && second.matches("\\d{2}") && third.matches("[A-Z]{2}")) {
            // Plate matches "After 2020: AA-00-AA"
        } else {
            throw new IllegalArgumentException("Invalid plate format for vehicle after year: [" + HIGHEST_LIMIT + "] \"AA-00-AA\"");
        }
    }


    /**
     * Validates the plate format for vehicles registered between the middle and highest limit.
     *
     * @param first  The first part of the plate ID.
     * @param second The second part of the plate ID.
     * @param third  The third part of the plate ID.
     * @throws IllegalArgumentException if the plate format is invalid for vehicles registered between the middle and highest limit.
     */
    private void validatePlateAfterMiddle(String first, String second, String third) {
        if (first.matches("\\d{2}") && second.matches("[A-Z]{2}") && third.matches("\\d{2}")) {
            // Plate matches "Between 2005-2020: 00-AA-00"
        } else {
            throw new IllegalArgumentException("Invalid plate format for vehicle between years: [" + MIDDLE_LIMIT + "-" + HIGHEST_LIMIT + "] \"00-AA-00\"");
        }
    }


    /**
     * Validates the plate format for vehicles registered between the lowest and middle limit.
     *
     * @param first  The first part of the plate ID.
     * @param second The second part of the plate ID.
     * @param third  The third part of the plate ID.
     * @throws IllegalArgumentException if the plate format is invalid for vehicles registered between the lowest and middle limit.
     */
    private void validatePlateAfterLowest(String first, String second, String third) {
        if (first.matches("\\d{2}") && second.matches("\\d{2}") && third.matches("[A-Z]{2}")) {
            // Plate matches "Between 1992-2005: 00-00-XX"
        } else {
            throw new IllegalArgumentException("Invalid plate format for vehicle between years: [" + LOWEST_LIMIT + "-" + MIDDLE_LIMIT + "] \"00-00-XX\"");
        }
    }


    /**
     * Verifies the size of the plate ID.
     *
     * @param plateId The plate ID of the vehicle.
     * @throws IllegalArgumentException if the plate ID size exceeds the maximum allowed length.
     */
    private void verifySizeOfPlate(String plateId) {
        if (plateId.length() > MAX_CHARS_PLATE) {
            throw new IllegalArgumentException("PlateId is too long... MAX LENGTH -> [" + MAX_CHARS_PLATE + "]");
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
     * Clones the current instance of Vehicle.
     *
     * @return A clone of the current instance.
     */
    public Vehicle clone() {
        return new Vehicle(plateId, brand, model, type.ordinal(), tareWeight, grossWeight, currentKm, checkUpFrequency, lastCheckUp, registerDate, acquisitionDate);
    }


    /**
     * Returns a string representation of the Vehicle object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return String.format("• Plate: %s | Brand: %s | Model: %s | Type: %s | Current Km: %.2f%n" +
                "    Register Date: %s%n    Acquisition Date: %s", plateId, brand, model,type, currentKm, registerDate, acquisitionDate);
    }

}
