package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class RegisterGreenSpaceController {
    private GreenSpaceRepository greenSpaceRepository;

    public RegisterGreenSpaceController(){
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            Repositories repositories = Repositories.getInstance();
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }


    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area) {
        Optional<GreenSpace> newGreenSpace;

        newGreenSpace = greenSpaceRepository.registerGreenSpace(name,size,area);

        return newGreenSpace;
    }


}
