package pt.ipp.isep.dei.esoft.project.domain;

/**
 * The CheckUp class represents a vehicle check-up in the project domain.
 */
public class CheckUp {
    // Attributes
    private Data checkUpDate;
    private float checkUpKms;
    private Vehicle vehicle;

    /**
     * Constructor for creating a CheckUp object.
     *
     * @param vehicle    The vehicle associated with the check-up.
     * @param checkUpKms The kilometers at the time of the check-up.
     * @param checkUpDate The date of the check-up.
     */
    public CheckUp(Vehicle vehicle, Float checkUpKms, Data checkUpDate) {
        validateCheckUp(vehicle, checkUpKms, checkUpDate);

        this.vehicle = vehicle;
        this.checkUpKms = checkUpKms;
        this.checkUpDate = checkUpDate;
    }

    /**
     * Clones the CheckUp object.
     *
     * @return A new CheckUp object with the same attributes.
     */
    public CheckUp clone() {
        return new CheckUp(this.vehicle, this.checkUpKms, this.checkUpDate);
    }

    /**
     * Validates the check-up data.
     *
     * @param vehicle     The vehicle associated with the check-up.
     * @param checkUpKms  The kilometers at the time of the check-up.
     * @param checkUpDate The date of the check-up.
     */
    private void validateCheckUp(Vehicle vehicle, float checkUpKms, Data checkUpDate) {
        validateDate(checkUpDate);
        validatesNegatives(checkUpKms);
        validateCheckUpKms(vehicle, checkUpKms);
    }

    /**
     * Validates the check-up date.
     *
     * @param checkUpDate The date of the check-up.
     */
    private void validateDate(Data checkUpDate) {
        Data actualDate = Data.currentDate();

        if (checkUpDate.isGreater(actualDate)) {
            throw new IllegalArgumentException("'Check-Up Date' -> [" + checkUpDate +
                    "] cannot be greater than 'Actual Date' -> [" + actualDate + "].");
        }
    }

    /**
     * Validates the check-up kilometers.
     *
     * @param vehicle    The vehicle associated with the check-up.
     * @param checkUpKms The kilometers at the time of the check-up.
     */
    public static void validateCheckUpKms(Vehicle vehicle, float checkUpKms) {
        float vehicleLastCheckUp = vehicle.getLastCheckUp();

        if (checkUpKms < vehicleLastCheckUp) {
            throw new IllegalArgumentException("'Last Checkup' -> [" + vehicle +
                    "] cannot be bigger than 'Current Check-Up Kms' -> [" + checkUpKms + "].");
        }
    }

    /**
     * Validates if the kilometers value is negative.
     *
     * @param checkUpKms The kilometers at the time of the check-up.
     */
    private void validatesNegatives(float checkUpKms) {
        if (isNegative(checkUpKms)) {
            throw new IllegalArgumentException("Check-Up kilometers cannot be negative!");
        }
    }

    /**
     * Checks if a value is negative.
     *
     * @param checkUpKms The kilometers at the time of the check-up.
     * @return True if the value is negative, false otherwise.
     */
    private boolean isNegative(float checkUpKms) {
        return checkUpKms < 0;
    }

    /**
     * Checks if two CheckUp objects are equal.
     *
     * @param otherObject The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean equals(Object otherObject) {
        if (this == otherObject)
            return true;

        if (otherObject == null || getClass() != otherObject.getClass())
            return false;

        CheckUp otherCheckUp = (CheckUp) otherObject;
        return vehicle.equals(otherCheckUp.vehicle)
                && checkUpKms == otherCheckUp.checkUpKms
                && checkUpDate.equals(otherCheckUp.checkUpDate);
    }
}
