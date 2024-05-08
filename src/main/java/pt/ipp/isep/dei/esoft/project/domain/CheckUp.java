package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Scanner;

public class CheckUp {
    private Data checkUpDate;
    private float checkUpKms;
    private Vehicle vehicle;


    public CheckUp(Vehicle vehicle, float checkUpKms, Data checkUpDate) {

        validateCheckUp(vehicle, checkUpKms, checkUpDate);

        this.vehicle = vehicle;
        this.checkUpKms = checkUpKms;
        this.checkUpDate = checkUpDate;
    }

    public CheckUp clone() {
        return new CheckUp(this.vehicle, this.checkUpKms, this.checkUpDate);
    }


    private void validateCheckUp(Vehicle vehicle, float checkUpKms, Data checkUpDate) {

        validateDate(checkUpDate);
        validatesNegatives(checkUpKms);
        validateCheckUpKms(vehicle, checkUpKms);
    }


    private void validateDate(Data checkUpDate) {
        Data actualDate = Data.currentDate();

        if (checkUpDate.isGreater(actualDate)) {
            throw new IllegalArgumentException("'Check-Up Date' -> [" + checkUpDate + "] cannot be greater than 'Actual Date' -> [" + actualDate + "].");
        }


    }


    public static void validateCheckUpKms(Vehicle vehicle, float checkUpKms) {
        float vehicleLastCheckUp = vehicle.getLastCheckUp();

        if(checkUpKms < vehicleLastCheckUp) {
            throw new IllegalArgumentException("'Last Checkup' -> [" + vehicle + "] cannot be bigger than 'Current Check-Up Kms' -> [" + checkUpKms + "].");
        }


    }

    private void validatesNegatives(float checkUpKms) {
        if (isNegative(checkUpKms)) {
            throw new IllegalArgumentException("Check-Up kilometers cannot be negative!");
        }

    }

    private boolean isNegative(float checkUpKms) {
        return checkUpKms < 0;
    }

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