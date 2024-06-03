package pt.ipp.isep.dei.esoft.project.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class AgendaEntryTest {

    private final Data data1 = new Data(2023, 1, 1);
    private final Data data2 = new Data(2021, 1, 1);
    private final Vehicle vehicle1 = new Vehicle("AA-00-AA", "Toyota", "Camry", 0, 1000, 2000, 10000, 2000, 9005, data2, data1);
    private final GreenSpace infanteDomHenrique = new GreenSpace("Infante Dom Henrique", 0, 100, "Porto", "gsm@this.app");
    private final ToDoEntry task1 = new ToDoEntry(infanteDomHenrique, "Cortar relva", "Nenhuma", 0, 2);

    @Test
    public void testAgendaEntryCreation() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);

        assertEquals("Planned", agendaEntry.getAgendaEntry().getStatus());
        assertEquals(data, agendaEntry.getStartingDate());
        assertEquals(data.calculateData(task1.getExpectedDuration()), agendaEntry.getExpected_end_Date());
        assertNull(agendaEntry.getReal_end_Date());
        assertNull(agendaEntry.getTeam());
        assertTrue(agendaEntry.getVehicles().isEmpty());
        assertEquals("gsm@this.app", agendaEntry.getResponsible());
    }

    @Test
    public void testSetExpectedEndDate() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);
        Data newEndDate = new Data(2023, 1, 5);

        agendaEntry.setExpected_end_Date(newEndDate);

        assertEquals(newEndDate, agendaEntry.getExpected_end_Date());
    }

    @Test
    public void testSetStartingDate() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);
        Data newStartDate = new Data(2025, 1, 5);

        agendaEntry.setStarting_Date(newStartDate);

        System.out.println(agendaEntry.getExpected_end_Date());

        assertEquals(newStartDate, agendaEntry.getStartingDate());
        assertEquals(new Data(2025,01,7), agendaEntry.getExpected_end_Date());
    }


    @Test
    public void testSetRealEndDate() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);
        Data realEndDate = new Data(2023, 1, 15);

        agendaEntry.setReal_end_Date(realEndDate);

        assertEquals(realEndDate, agendaEntry.getReal_end_Date());
    }

    @Test
    public void testCancelTask() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);

        agendaEntry.cancelTask();

        assertEquals("Canceled", agendaEntry.getAgendaEntry().getStatus());
    }

    @Test
    public void testAddVehicle() {
        Data data =new Data(2025, 1, 1);
        AgendaEntry agendaEntry = new AgendaEntry(task1, data);

        assertTrue(agendaEntry.addVehicle(vehicle1));
        assertEquals(1, agendaEntry.getVehicles().size());
        assertTrue(agendaEntry.getVehicles().contains(vehicle1));

        // Adding the same vehicle again should not change the size of the vehicle list
        assertFalse(agendaEntry.addVehicle(vehicle1));
        assertEquals(1, agendaEntry.getVehicles().size());
    }
}


