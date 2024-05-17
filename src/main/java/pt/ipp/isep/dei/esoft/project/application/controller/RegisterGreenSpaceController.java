package pt.ipp.isep.dei.esoft.project.application.controller;

import pt.ipp.isep.dei.esoft.project.domain.GreenSpace;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.util.Optional;

public class RegisterGreenSpaceController {
    private GreenSpaceRepository greenSpaceRepository;
    private Repositories repositories = Repositories.getInstance();

    public RegisterGreenSpaceController(){
        this.greenSpaceRepository = getGreenSpaceRepository();
    }

    private GreenSpaceRepository getGreenSpaceRepository() {
        if (greenSpaceRepository == null) {
            greenSpaceRepository = repositories.getGreenSpaceRepository();
        }
        return greenSpaceRepository;
    }


    public Optional<GreenSpace> registerGreenSpace(String name, int size, float area,String address) {
        Optional<GreenSpace> newGreenSpace;

        String resposible = repositories.getAuthenticationRepository().getCurrentUserSession().getUserId().getEmail();

        newGreenSpace = greenSpaceRepository.registerGreenSpace(name,size,area,address,resposible);

        return newGreenSpace;
    }


}
