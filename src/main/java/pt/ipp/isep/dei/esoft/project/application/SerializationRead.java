package pt.ipp.isep.dei.esoft.project.application;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.io.*;
import java.util.List;

public class SerializationRead {

    private Repositories repositories;
    private SkillRepository skillRepository;

    public SerializationRead() {
        this.repositories = Repositories.getInstance();
        this.skillRepository = getSkillRepository();
    }

    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            this.skillRepository = repositories.getSkillRepository();
        }
        return skillRepository;
    }

    //--------------------------- Serialization of Class Infos **INPUTS** -----------------------------
    public void serializeSkill() {
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


    //--------------------------------------------------------------------------------------------------



}
