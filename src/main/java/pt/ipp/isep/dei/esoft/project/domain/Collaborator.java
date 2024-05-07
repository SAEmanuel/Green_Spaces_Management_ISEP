package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.repository.JobRepository;

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
    private String docType;
    private Job job;
    private List<Skill> skills;

    private static final int TAX_PAYER_NUMBER_MIN = 100000000;
    private static final int TAX_PAYER_NUMBER_MAX = 999999999;
    private static final int phoneNumberMin = 910000000;
    private static final int phoneNumberMiddleLeft = 939999999;
    private static final int phoneNumberMiddleRight = 960000000;
    private static final int phoneNumberMax = 969999999;


    public Collaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, Job job) {
        validateData(name, birthDate, admissionDate, address, phoneNumber, emailAddress, taxPayerNumber, docType, job);
        this.name = name.trim();
        this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.address = address.trim();
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress.trim();
        this.taxPayerNumber = taxPayerNumber;
        this.docType = docType.trim();
        this.job = job;

        this.skills = new ArrayList<Skill>();
    }

    private void validateData(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, Job job) {
        isValidName(name);
        isValidBirthDate(birthDate);
        isValidPhoneNumber(phoneNumber);
        isValidEmailAddress(emailAddress);
        isValidTaxPayerNumber(taxPayerNumber);


    }


    private void isValidTaxPayerNumber(int taxPayerNumber) {
        if (taxPayerNumber < TAX_PAYER_NUMBER_MIN || taxPayerNumber > TAX_PAYER_NUMBER_MAX) {
            throw new IllegalArgumentException("Tax payer number is invalid");
        }
    }

    private void isValidEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        emailAddress = emailAddress.trim();
        if (!emailAddress.contains("@")) {
            throw new IllegalArgumentException("Email address is not a valid email address.");
        }

    }

    private void isValidPhoneNumber(int phoneNumber) {
        if (phoneNumber < phoneNumberMin || phoneNumber > phoneNumberMiddleLeft && phoneNumber < phoneNumberMiddleRight || phoneNumber > phoneNumberMax) {
            throw new IllegalArgumentException("Phone number is invalid");
        }

    }

    private void isValidBirthDate(Data birthDate) {
        if (!birthDate.over18()) {
            throw new IllegalArgumentException("Collaborator is less than 18 years old");
        }
    }

    private void isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        name = name.trim();
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' '){
                throw new IllegalArgumentException("Name contains invalid characters.");
            }
        }

    }



    public Collaborator clone() {
        return new Collaborator(this.name,this.birthDate,this.admissionDate,this.address,this.phoneNumber,this.emailAddress,this.taxPayerNumber,this.docType,this.job);
    }

    public List<Skill> cloneList() {
        return this.skills;
    }

    public boolean equals(Object otherCollaborator) {
        if (this == otherCollaborator)
            return true;
        if (otherCollaborator == null || getClass() != otherCollaborator.getClass())
            return false;
        Collaborator collaborator = (Collaborator) otherCollaborator;
        return (name.equalsIgnoreCase(collaborator.name) && birthDate.equals(collaborator.birthDate)
                && admissionDate.equals(collaborator.admissionDate) && address.equalsIgnoreCase(collaborator.address)
                && phoneNumber == collaborator.phoneNumber && emailAddress.equalsIgnoreCase(collaborator.emailAddress)
                && taxPayerNumber == collaborator.taxPayerNumber && docType.equalsIgnoreCase(collaborator.docType) && job.equals(collaborator.job));
    }

    public boolean addSkill(Skill skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
            return true;
        }
        return false;
    }

    public int getTaxPayerNumber() {
        return taxPayerNumber;
    }

    public String getName() {
        return name;
    }

    public Collaborator(List<Skill> skills) {
        this.skills = cloneList();
    }

}
