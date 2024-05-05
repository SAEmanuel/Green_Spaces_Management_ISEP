package pt.ipp.isep.dei.esoft.project.domain;


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
    private static final int MAX_CHARS_PLATE = 6;


    public Vehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight,
                   float currentKm, float checkUpFrequency,float lastCheckUp, Data registerDate, Data acquisitionDate) {

        plateId = plateId.trim();
        brand = brand.trim();
        model = model.trim();
        type = type.trim();

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

//---------------------------------------VALIDATIONS----------------------------------------------------------

    private void validateVehicle(String plateId, String brand, String model, String type, float tareWeight, float grossWeight, float currentKm, float checkUpFrequency, float lastCheckUp, Data registerDate, Data acquisitionDate) {

        //String validations and plate id format
        validatePlateId(plateId, registerDate);
        validateBrand(brand);
        validateModel(model);
        validateType(type);


    }

    private void validatePlateId(String plateId, Data registerDate) {
        if (!validateString(plateId)) {
            throw new IllegalArgumentException("Reference (PLATE ID) cannot be null or empty.");
        }
        validatePlate(plateId, registerDate);
    }


    private void validateBrand(String brand) {
        if (!validateString(brand)) {
            throw new IllegalArgumentException("Reference (BRAND) cannot be null or empty.");
        }
    }

    private void validateModel(String model) {
        if (!validateString(model)) {
            throw new IllegalArgumentException("Reference (MODEL) cannot be null or empty.");
        }
    }

    private void validateType(String type) {
        if (!validateString(type)) {
            throw new IllegalArgumentException("Reference (TYPE) cannot be null or empty.");
        }

    }


    //--------------------------------INTERNAL METHODS FOR VALIDATIONS--------------------------------
    private boolean validateString(String string) {
        return (string != null && !string.isEmpty());
    }

    private void validatePlate(String plateId, Data registerDate) {

        verifySizeOfPlate(plateId);

        String[] plateByParts = plateId.split("-");
        String first = plateByParts[0];
        String second = plateByParts[1];
        String third = plateByParts[2];

        if (registerDate.getAno() > HIGHEST_LIMIT) {

            validatePlateAfterHighest(first, second, third);

        } else if (registerDate.getAno() > MIDDLE_LIMIT) {

            validatePlateAfterMiddle(first, second, third);

        } else if (registerDate.getAno() > LOWEST_LIMIT) {

            validatePlateAfterLowest(first, second, third);

        } else {
            throw new IllegalArgumentException("Plate format does not correspond to a valid plate id between: [" + LOWEST_LIMIT + "-" + HIGHEST_LIMIT + "].");
        }

    }

    private void validatePlateAfterHighest(String first, String second, String third) {
        if (first.matches("[A-Z]{2}") && second.matches("\\d{2}") && third.matches("[A-Z]{2}")) {
            // Plate matches "After 2020: AA-00-AA"
        } else {
            throw new IllegalArgumentException("Invalid plate format for vehicle after year: [" + HIGHEST_LIMIT + "] \"AA-00-AA\"");
        }
    }

    private void validatePlateAfterMiddle(String first, String second, String third) {
        if (first.matches("\\d{2}") && second.matches("[A-Z]{2}") && third.matches("\\d{2}")) {
            // Plate matches "Between 2005-2020: 00-AA-00"
        } else {
            throw new IllegalArgumentException("Invalid plate format for vehicle between years: [" + MIDDLE_LIMIT + "-" + HIGHEST_LIMIT + "] \"00-AA-00\"");
        }
    }


    private void validatePlateAfterLowest(String first, String second, String third) {
        if (first.matches("\\d{2}") && second.matches("\\d{2}") && third.matches("[A-Z]{2}")) {
            // Plate matches "Between 1992-2005: 00-00-XX"
        } else {
            throw new IllegalArgumentException("Invalid plate format for vehicle between years: [" + LOWEST_LIMIT + "-" + MIDDLE_LIMIT + "] \"00-00-XX\"");
        }
    }

    private void verifySizeOfPlate(String plateId) {
        if (plateId.length() > MAX_CHARS_PLATE) {
            throw new IllegalArgumentException("PlateId is too long");
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
}
