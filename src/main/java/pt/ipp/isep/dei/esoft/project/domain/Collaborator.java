package pt.ipp.isep.dei.esoft.project.domain;

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

    public Collaborator(String name, Data birthDate, Data admissionDate, String address, int phoneNumber, String emailAddress, int taxPayerNumber, String docType, Job job) {
        this.name = name;
        this.birthDate = birthDate;
        this.admissionDate = admissionDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.taxPayerNumber = taxPayerNumber;
        this.docType = docType;
        this.job = job;
    }

    public boolean isValidName(String name) {
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

    public void setName(String name) {
        this.name = name;
    }

    public Data getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Data birthDate) {
        this.birthDate = birthDate;
    }

    public Data getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Data admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getTaxPayerNumber() {
        return taxPayerNumber;
    }

    public void setTaxPayerNumber(int taxPayerNumber) {
        this.taxPayerNumber = taxPayerNumber;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
