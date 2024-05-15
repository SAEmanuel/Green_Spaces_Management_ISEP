package pt.ipp.isep.dei.esoft.project.domain;

import org.w3c.dom.DocumentType;

import java.util.ArrayList;
import java.util.List;

public class Collaborator {
    private String name;
    private Data birthDate;
    private Data admissionDate;
    private String address;
    private int phoneNumber;
    private String emailAddress;
    private int taxPayerNumber;
    private int docType;
    private Job job;
    private List<Skill> skills;
    private static Enum<DocType> documentType;

    private static final int TAX_PAYER_NUMBER_MIN = 100000000;
    private static final int TAX_PAYER_NUMBER_MAX = 999999999;
    private static final int phoneNumberMin = 910000000;
    private static final int phoneNumberMiddleLeft = 939999999;
    private static final int phoneNumberMiddleRight = 960000000;
    private static final int phoneNumberMax = 969999999;

    /**
     * Constructor that initializes the Collaborator
     *
     * @param name           of the collaborator
     * @param birthDate      of the collaborator
     * @param admissionDate  of the collaborator
     * @param address        of the collaborator
     * @param phoneNumber    of the collaborator
     * @param emailAddress   of the collaborator
     * @param taxPayerNumber of the collaborator
     * @param docType        of the collaborator
     * @param job            of the collaborator
     */
    public Collaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, int docType, Job job) {
        validateData(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);
        this.name = name.trim();
        this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.address = address.trim();
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress.trim();
        this.taxPayerNumber = taxPayerNumber;
        this.docType = docType;
        this.job = job;
        this.skills = new ArrayList<Skill>();
    }

    /**
     * Validates all the data of the collaborator
     *
     * @param name           of the collaborator
     * @param birthDate      of the collaborator
     * @param admissionDate  of the collaborator
     * @param address        of the collaborator
     * @param phoneNumber    of the collaborator
     * @param emailAddress   of the collaborator
     * @param taxPayerNumber of the collaborator
     * @param docType        of the collaborator
     * @param job            of the collaborator
     */
    private void validateData(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, int docType, Job job) {
        isValidName(name);
        isValidBirthDate(birthDate, admissionDate);
        isValidPhoneNumber(phoneNumber);
        isValidEmailAddress(emailAddress);
        isValidTaxPayerNumber(taxPayerNumber);
        isValidAddress(address);
        isValidDocType(docType);
    }


    /**
     * Validates taxpayer number
     *
     * @param taxPayerNumber of the collaborator
     */
    private void isValidTaxPayerNumber(int taxPayerNumber) {
        if (taxPayerNumber < TAX_PAYER_NUMBER_MIN || taxPayerNumber > TAX_PAYER_NUMBER_MAX) {
            throw new IllegalArgumentException("Tax payer number is invalid");
        }
    }

    /**
     * Validates email address
     *
     * @param emailAddress of the collaborator
     */
    private void isValidEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        emailAddress = emailAddress.trim();
        if (!verifyEmail(emailAddress)) {
            throw new IllegalArgumentException("Invalid email address!");
        }
    }

    /**
     * Verifies if the email address has "@" and "."
     *
     * @param emailAddress of the collaborator
     * @return true if email has "@", "." and has no digits after ".", false otherwise
     */
    private boolean verifyEmail(String emailAddress) {
        try {

            String[] splitEmailAddress = emailAddress.split("@");
            if (splitEmailAddress.length != 2 && !splitEmailAddress[1].contains(".") &&
                    splitEmailAddress[0].contains(" ") && splitEmailAddress[1].contains(" ")) {
                return false;
            }

            String[] secondSplit = splitEmailAddress[1].split("\\.");

            if (secondSplit.length < 2 || secondSplit.length > 3 || hasNoDigits(secondSplit)) {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid email address!");
        }
        return true;
    }

    /**
     * Verifies if the String has no digits
     *
     * @param secondSplit String to verify
     * @return true if String has no digits, false otherwise
     */
    private boolean hasNoDigits(String[] secondSplit) {
        if (secondSplit.length == 2) {
            for (int i = 1; i < secondSplit[1].length(); i++) {
                if (!Character.isDigit(secondSplit[1].charAt(i))) {
                    return false;
                }
            }
        } else if (secondSplit.length == 3) {
            for (int i = 1; i < secondSplit[1].length(); i++) {
                if (!Character.isDigit(secondSplit[1].charAt(i)) && !Character.isDigit(secondSplit[2].charAt(i))) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Validates address
     *
     * @param address of the collaborator
     */
    private void isValidAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
    }

    private void isValidDocType(int docType) {

        for (int i = 0; i < DocType.values().length; i++) {
            if (docType != i) {
                throw new IllegalArgumentException("Invalid document type!");
            }

        }

    }


    /**
     * Validates phone number
     *
     * @param phoneNumber of the collaborator
     */
    private void isValidPhoneNumber(int phoneNumber) {
        if (phoneNumber < phoneNumberMin || phoneNumber > phoneNumberMiddleLeft && phoneNumber < phoneNumberMiddleRight || phoneNumber > phoneNumberMax) {
            throw new IllegalArgumentException("Phone number is invalid");
        }
    }

    /**
     * Validates birthdate
     *
     * @param birthDate     of the collaborator
     * @param admissionDate of the collaborator
     */
    private void isValidBirthDate(Data birthDate, Data admissionDate) {
        if (!birthDate.over18(admissionDate)) {
            throw new IllegalArgumentException("Collaborator is less than 18 years old");
        }
    }

    /**
     * Validates name
     *
     * @param name of the collaborator
     */
    private void isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        name = name.trim();
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
                throw new IllegalArgumentException("Name contains invalid characters.");
            }
        }

    }

    /**
     * Creates a clone of the Collaborator
     *
     * @return the cloned Collaborator
     */
    public Collaborator clone() {
        return new Collaborator(this.name, this.birthDate, this.admissionDate, this.address, this.phoneNumber, this.emailAddress, this.taxPayerNumber, this.docType, this.job);
    }

    /**
     * Gets the skills list
     *
     * @return skillList
     */
    public List<Skill> cloneList() {
        return this.skills;
    }

    /**
     * Compares this collaborator to another object for equality.
     *
     * @param otherCollaborator the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object otherCollaborator) {
        if (this == otherCollaborator)
            return true;
        if (otherCollaborator == null || getClass() != otherCollaborator.getClass())
            return false;
        Collaborator collaborator = (Collaborator) otherCollaborator;
        return (name.equalsIgnoreCase(collaborator.name) && birthDate.equals(collaborator.birthDate)
                && admissionDate.equals(collaborator.admissionDate) && address.equalsIgnoreCase(collaborator.address)
                && phoneNumber == collaborator.phoneNumber && emailAddress.equalsIgnoreCase(collaborator.emailAddress)
                && taxPayerNumber == collaborator.taxPayerNumber && docType == collaborator.docType && job.equals(collaborator.job));
    }

    /**
     * Adds a skill to the collaborator
     *
     * @param skill to be added
     * @return true if skill was added successfully, false otherwise
     */
    public boolean addSkill(Skill skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
            return true;
        }
        return false;
    }

    /**
     * Gets taxpayer number
     *
     * @return taxpayer number
     */
    public int getTaxPayerNumber() {
        return taxPayerNumber;
    }

    /**
     * Gets name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Constructor that initializes the Collaborator
     *
     * @param skills of the collaborator
     */
    public Collaborator(List<Skill> skills) {
        this.skills = cloneList();
    }

    /**
     * Gets the birthdate of the collaborator.
     *
     * @return the birthdate of the collaborator
     */
    public Data getBirthDate() {
        return birthDate;
    }

    /**
     * Gets the admission date of the collaborator.
     *
     * @return the admission date of the collaborator
     */
    public Data getAdmissionDate() {
        return admissionDate;
    }

    /**
     * Gets the address of the collaborator.
     *
     * @return the address of the collaborator
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the phone number of the collaborator.
     *
     * @return the phone number of the collaborator
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the email address of the collaborator.
     *
     * @return the email address of the collaborator
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Gets the document type of the collaborator.
     *
     * @return the document type of the collaborator
     */
    public int getDocType() {
        return docType;
    }

    /**
     * Gets the job of the collaborator.
     *
     * @return the job of the collaborator
     */
    public Job getJob() {
        return job;
    }

    public static Enum getDocumentType() {
        return documentType;
    }

    private enum DocType {
        PASSPORT {
            @Override
            public String toString() {
                return "Passport";
            }
        },
        CITIZEN_CARD {
            @Override
            public String toString() {
                return "Citizen Card";
            }
        },
        DRIVER_LICENSE {
            @Override
            public String toString() {
                return "Driver License";
            }
        }
    }

}
