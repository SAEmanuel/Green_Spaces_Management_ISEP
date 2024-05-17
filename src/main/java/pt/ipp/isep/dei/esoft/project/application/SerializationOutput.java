package pt.ipp.isep.dei.esoft.project.application;

import pt.ipp.isep.dei.esoft.project.domain.Skill;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.repository.SkillRepository;

import java.io.*;
import java.util.List;

public class SerializationOutput {

    private Repositories repositories;
    private SkillRepository skillRepository;


    public SerializationOutput() {
        this.repositories = Repositories.getInstance();
        this.skillRepository = getSkillRepository();
    }

    private SkillRepository getSkillRepository() {
        if (skillRepository == null) {
            this.skillRepository = repositories.getSkillRepository();
        }
        return skillRepository;
    }


    //--------------------------- Serialization of Class Infos **OUTPUTS** -----------------------------
    public void serializeSkill() {
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
}
