package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.repository.JobRepository;

public class Collaborator {
    private static final int TAX_PAYER_NUMBER_MIN = 100000000;
    private static final int TAX_PAYER_NUMBER_MAX = 999999999;
    private static final int phoneNumberMin = 910000000;
    private static final int phoneNumberMiddleLeft = 939999999;
    private static final int phoneNumberMiddleRight = 960000000;
    private static final int phoneNumberMax = 969999999;
    private String name;
    private Data birthDate;
    private Data admissionDate;
    private String address;
    private int phoneNumber;
    private String emailAddress;
    private int taxPayerNumber;
    private String docType;
    private Job job;

    public Collaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, int jobID) {
        if (isValidName(name)) this.name = name;
        if (isValidBirthDate(birthDate)) this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.address = address;
        if (isValidPhoneNumber(phoneNumber)) this.phoneNumber = phoneNumber;
        if (isValidEmailAddress(emailAddress)) this.emailAddress = emailAddress;
        if (isValidTaxPayerNumber(taxPayerNumber)) this.taxPayerNumber = taxPayerNumber;
        this.docType = docType;
        this.job = getJob(jobID);
    }

    public Collaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, Job job) {
        if (isValidName(name)) this.name = name;
        if (isValidBirthDate(birthDate)) this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.address = address;
        if (isValidPhoneNumber(phoneNumber)) this.phoneNumber = phoneNumber;
        if (isValidEmailAddress(emailAddress)) this.emailAddress = emailAddress;
        if (isValidTaxPayerNumber(taxPayerNumber)) this.taxPayerNumber = taxPayerNumber;
        this.docType = docType;
        this.job = job;
    }

    private Job getJob(int jobID) {
        JobRepository jobList = new JobRepository();
        return jobList.getJob(jobID);
    }

    private boolean isValidTaxPayerNumber(int taxPayerNumber) {
        return (taxPayerNumber >= TAX_PAYER_NUMBER_MIN && taxPayerNumber <= TAX_PAYER_NUMBER_MAX);
    }

    private boolean isValidEmailAddress(String emailAddress) {
        boolean found = false;
        if (emailAddress == null || emailAddress.isEmpty()) {
            throw new IllegalArgumentException("Job name cannot be null or empty.");
        } else {
            emailAddress = emailAddress.trim();
            if (emailAddress.contains("@")) {
                found = true;
            }
        }
        return found;
    }

    private boolean isValidPhoneNumber(int phoneNumber) {
        return (phoneNumber >= phoneNumberMin && phoneNumber <= phoneNumberMiddleLeft) || (phoneNumber >= phoneNumberMiddleRight && phoneNumber <= phoneNumberMax);
    }

    private boolean isValidBirthDate(Data birthDate) {
        return birthDate.over18();
    }

    private boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Job name cannot be null or empty.");
        } else {
            name = name.trim();
            for (int i = 0; i < name.length(); i++) {
                if (!Character.isLetter(name.charAt(i))) {
                    throw new IllegalArgumentException("Job name contains invalid characters.");
                }
            }
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public Data getBirthDate() {
        return birthDate;
    }

    public Data getAdmissionDate() {
        return admissionDate;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getTaxPayerNumber() {
        return taxPayerNumber;
    }

    public String getDocType() {
        return docType;
    }

    public Job getJob() {
        return job;
    }

    public Collaborator clone() {
        return new Collaborator(getName(),getBirthDate(),getAdmissionDate(),getAddress(),getPhoneNumber(),getEmailAddress(),getTaxPayerNumber(),getDocType(),getJob());
    }

    public boolean equals(Object otherCollaborator) {
        if (this == otherCollaborator)
            return true;
        if (otherCollaborator == null || getClass() != otherCollaborator.getClass())
            return false;
        Collaborator collaborator = (Collaborator) otherCollaborator;
        return  (getName().equals(collaborator.getName()) &&  getBirthDate().equals(collaborator.getBirthDate()) &&
                getAdmissionDate().equals(collaborator.getAdmissionDate()) &&
                getAddress().equals(collaborator.getAddress()) && getPhoneNumber() == collaborator.getPhoneNumber() &&
                getEmailAddress().equals(collaborator.getAddress()) && getTaxPayerNumber() == collaborator.getTaxPayerNumber() &&
                getDocType().equals(collaborator.getDocType()) && getJob().equals(collaborator.job));
    }
}
