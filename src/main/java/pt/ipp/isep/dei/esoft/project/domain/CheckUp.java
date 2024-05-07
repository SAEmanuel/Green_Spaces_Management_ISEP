package pt.ipp.isep.dei.esoft.project.domain;

import java.util.Scanner;

public class CheckUp {
    private Data checkUpDate;
    private int checkUpKms;
    private Vehicle vehicle;


    public CheckUp(Vehicle vehicle, Data checkUpDate, int checkUpKms) {
        this.vehicle = vehicle;
        this.checkUpDate = checkUpDate;
        this.checkUpKms = checkUpKms;
    }

    public CheckUp clone() {
        return new CheckUp(this.vehicle, this.checkUpDate, this.checkUpKms);
    }

    public Data getCheckUpDate() {
        return checkUpDate;
    }

    public int getCheckUpKms() {
        return checkUpKms;
    }

    public Vehicle getCheckUpVehicle() {
        return vehicle;
    }

    public static Data validateDate() {
        Scanner input = new Scanner(System.in);

        System.out.print("Check Up Date (dd/mm/yyyy): ");
        String date = input.nextLine();
        String[] splitDate = date.split("/");

        // continuar
        return null;
    }


    public static int validateKms(int lastCheckUpKms, int currentKms) {
        Scanner input = new Scanner(System.in);
        System.out.print("Kms at Check Up: ");

        if (input.nextInt() > lastCheckUpKms && input.nextInt() > currentKms) {
            return input.nextInt();
        } else {
            System.out.println("\nKms smaller than at last check up or at the current time.\n");
            return validateKms(lastCheckUpKms, currentKms);
        }
    }


}
