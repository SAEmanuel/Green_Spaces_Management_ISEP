package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.repository.GreenSpaceRepository;
import pt.ipp.isep.dei.esoft.project.repository.Repositories;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortGreenSpacesTest {
    private final GreenSpace infanteDomHenrique = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
    private final GreenSpace palacioDeCristal = new GreenSpace("Palacio de Cristal", 1, 40, "Porto", "gsm@this.app");
    private final GreenSpace parqueDaCidade = new GreenSpace("Parque da Cidade", 2, 100, "Matosinhos", "gsm@this.app");

    GreenSpaceRepository greenSpaceRepository = Repositories.getInstance().getGreenSpaceRepository();

    @BeforeEach
    void setUp() {
        greenSpaceRepository.add(infanteDomHenrique);
        greenSpaceRepository.add(palacioDeCristal);
        greenSpaceRepository.add(parqueDaCidade);
    }

//    @Test
//    void sortGreenSpacesAscendingByName() throws IOException {
//        String responsible = "gsm@this.app";
//        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces(greenSpaceRepository.getGreenSpacesByResponsible(responsible));
//        assertNotNull(sortedGreenSpaces);
//        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(0));
//        assertEquals(palacioDeCristal, sortedGreenSpaces.get(1));
//        assertEquals(parqueDaCidade, sortedGreenSpaces.get(2));
//    }

//    @Test
//    void sortGreenSpacesDescendingByName() throws IOException {
//        String responsible = "gsm@this.app";
//        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces(greenSpaceRepository.getGreenSpacesByResponsible(responsible));
//        assertNotNull(sortedGreenSpaces);
//        assertEquals(parqueDaCidade, sortedGreenSpaces.get(0));
//        assertEquals(palacioDeCristal, sortedGreenSpaces.get(1));
//        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(2));
//    }

//    @Test
//    void sortGreenSpacesBubbleSort() throws IOException {
//        String responsible = "gsm@this.app";
//        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces(greenSpaceRepository.getGreenSpacesByResponsible(responsible));
//        assertNotNull(sortedGreenSpaces);
//        assertEquals(palacioDeCristal, sortedGreenSpaces.get(0));
//        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(1));
//        assertEquals(parqueDaCidade, sortedGreenSpaces.get(2));
//    }

//    @Test
//    void sortGreenSpacesInsertionSort() throws IOException {
//        String responsible = "gsm@this.app";
//        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces(greenSpaceRepository.getGreenSpacesByResponsible(responsible));
//        assertNotNull(sortedGreenSpaces);
//        assertEquals(palacioDeCristal, sortedGreenSpaces.get(0));
//        assertEquals(infanteDomHenrique, sortedGreenSpaces.get(1));
//        assertEquals(parqueDaCidade, sortedGreenSpaces.get(2));
//    }

    @Test
    void sortGreenSpacesInvalidMethod() throws IOException {
        String responsible = "gsm@this.app";
        List<GreenSpace> sortedGreenSpaces = SortGreenSpaces.sortGreenSpaces(greenSpaceRepository.getGreenSpacesByResponsible(responsible));
        assertNull(sortedGreenSpaces);
    }
}