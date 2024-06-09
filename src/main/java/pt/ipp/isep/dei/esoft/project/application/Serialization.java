package pt.ipp.isep.dei.esoft.project.application;


import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;
import pt.ipp.isep.dei.esoft.project.repository.VehicleRepository;

import java.io.*;

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

    public void serializeRepositoriesOutput() {
        String filePath = "AppInformation/repositories.ser";

        try {

            File file = new File(filePath);

            // Create the file and directory if necessary
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }


            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);

            output.writeObject(Repositories.getInstance());

        } catch (IOException e) {
            throw new IllegalArgumentException("Error serializing repositories: " + e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------------------


    //--------------------------- Serialization of Class Infos **INPUTS** -----------------------------

    public void serializeRepositoriesInput() {
        String filePath = "AppInformation/repositories.ser";

        try {
            File file = new File(filePath);

            // Create the file and directory if necessary
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            // Check if the file is empty
            if (file.length() == 0) {
                initializeEmptyRepositories(filePath);
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Deserialize the Repositories object from the file
            Repositories deserializedRepositories = (Repositories) ois.readObject();
            Repositories.setInstance(deserializedRepositories);

            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Error deserializing repositories: " + e.getMessage(), e);
        }
    }

    private void initializeEmptyRepositories(String filePath) {
        try {
            Repositories initialRepositories = Repositories.getInstance();
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(initialRepositories);

            oos.close();
            fos.close();

            // Set the initial empty repositories instance
            Repositories.setInstance(initialRepositories);

        } catch (IOException e) {
            throw new IllegalArgumentException("Error initializing empty repositories: " + e.getMessage(), e);
        }
    }




}


//--------------------------------------------------------------------------------------------------

