package pt.ipp.isep.dei.esoft.project.application;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.domain.Vehicle;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.io.*;
import java.util.List;

public class Serialization {

    private Repositories repositories;
    private SkillRepository skillRepository;
    private VehicleRepository vehicleRepository;


    public Serialization() {
        this.repositories = Repositories.getInstance();
        this.skillRepository = getSkillRepository();
        this.vehicleRepository = getVehicleRepository();
    }

    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            this.skillRepository = repositories.getSkillRepository();
        }
        return skillRepository;
    }

    private VehicleRepository getVehicleRepository() {
        if (vehicleRepository == null) {
            this.vehicleRepository = repositories.getVehicleRepository();
        }
        return vehicleRepository;
    }


    //--------------------------- Serialization of Class Infos **OUTPUTS** -----------------------------
    public void serializeSkillOutput() {
        String filePath = "AppInformaion/skills.ser";
        List<Skill> skillList = skillRepository.getSkillList();

        try {
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);

            output.writeObject(skillList);

            output.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    public void serializeVehicleOutput() {
        String filePath = "AppInformaion/vehicle.ser";
        List<Vehicle> vehicleList = vehicleRepository.getVehicleListSerialization();

        try {
            File file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);

            output.writeObject(vehicleList);

            output.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------------------



    //--------------------------- Serialization of Class Infos **INPUTS** -----------------------------

    public void serializeSkillInput() {
        String filePath = "AppInformaion/skills.ser";
        List<Skill> skillList;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            skillList = (List<Skill>) ois.readObject();

            this.skillRepository.serializationInput(skillList);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void serializeVehicleInput() {
        String filePath = "AppInformaion/vehicle.ser";
        List<Vehicle> vehicleList;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            vehicleList = (List<Vehicle>) ois.readObject();

            this.vehicleRepository.serializationInput(vehicleList);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    //--------------------------------------------------------------------------------------------------



}
