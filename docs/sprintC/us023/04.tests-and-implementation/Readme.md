# US0023 -  Assign a Team to an entry in the Agenda


## 4. Tests
###
#### ------------------------------- Validations to Assign Team --------------------------------------

**Test 1:** Assign Team Successfully

     @Test
    public void assignTeam_ValidTeam_AssignedSuccessfully() {
        // Arrange
        Team team = new Team(1);
        GreenSpace greenSpace = new GreenSpace("Test Green Space", 0, 100, "Test City", "test@greenspace.com");
        ToDoEntry todoEntry = new ToDoEntry(greenSpace, "Test Task", "None", 0, 10);
        Data startDate = new Data(2024, 6, 4);
        Optional<AgendaEntry> registeredEntry = agendaRepository.registerAgendaEntry(todoEntry, startDate);
        AgendaEntry entry = registeredEntry.orElse(null);
        assertNotNull(entry);

        // Act
        boolean assigned = agendaRepository.assignTeam(team, 0, entry.getResponsible());

        // Assert
        assertTrue(assigned);
    }


## 5. Construction (Implementation)

### Class AssignTeamToTaskAgendaUI

```java
    private void submitData() {
    try {
        boolean result = getController().assignTeam(teamID,agendaEntryID, emailService, controller.getResponsible());
        if (result) {
            System.out.println(ANSI_BRIGHT_GREEN + "\nTeam successfully assigned!" + ANSI_RESET);
        } else {
            System.out.printf(ANSI_BRIGHT_RED + "\nTeam not assigned - This task already have a team assigned!" + ANSI_RESET);
        }
    }
    catch (IllegalArgumentException e){
        System.out.println(ANSI_BRIGHT_GREEN + "\nTeam successfully assigned!" + ANSI_RESET);
    }
}
```

### Class AgendaController

```java
    public boolean assignTeam(int teamID, int agendaEntryID, String emailService, String responsible) {
    List<Team> teams = teamRepository.getListTeam();
    SendEmail sendEmail = new SendEmail();

    if (agendaRepository.assignTeam(teams.get(teamID), agendaEntryID, responsible)) {
        try {
            if(!sendEmail.sendEmail(emailService, teams.get(teamID).getCollaboratorsEmail(), "Assigned to a Task", "You and your team members have been assigned to a task in an agenda")) {
                System.out.println(ANSI_BRIGHT_RED + "Email not sent, invalid configuration file data" + ANSI_RESET);
                throw new IllegalArgumentException("Email not sent, invalid configuration file data");
            }
        } catch (IOException e) {
            System.out.println("Email not sent, file not found or invalid configuration file data");
            throw new IllegalArgumentException("Email not sent, file not found or invalid configuration file data");
        }
        return true;
    }
    return false;
}
```

### Class AgendaRepository

```java
    public boolean assignTeam(Team team, int agendaEntryID, String responsible) {
        AgendaEntry task = getAgendaEntryByID(agendaEntryID, responsible);
        AgendaEntry taskBackUp = getAgendaBackUpByID(agendaEntryID, responsible);
    
        if (validateInfo(team, task)) {
            task.setTeam(team);
            taskBackUp.setTeam(team);
            return true;
        }
        return false;
    }
```

### Class SendEmail

```java
    public static boolean sendEmail(String serviceName, List<String> toEmails, String subject, String body) throws IOException {
        loadEmailConfig();
    
        try {
            serviceName = serviceName != null ? "email_service." + serviceName : null;
            String convertToPrefix = config.getProperty(serviceName + ".smtp_server_name");
            String servicePrefix = "email_service." + convertToPrefix;
    
            String smtpServer = config.getProperty(servicePrefix + ".smtp_server");
            int port = Integer.parseInt(config.getProperty(servicePrefix + ".port"));
            boolean useTls = Boolean.parseBoolean(config.getProperty(servicePrefix + ".use_tls"));
            boolean useSsl = Boolean.parseBoolean(config.getProperty(servicePrefix + ".use_ssl"));
    
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            if (useTls) {
                props.put("mail.smtp.starttls.enable", "true");
            }
            if (useSsl) {
                props.put("mail.smtp.socketFactory.port", port);
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }
    
            try {
                BufferedWriter writer = null;
                try {
                    // Create BufferedWriter object
                    writer = new BufferedWriter(new FileWriter("Email/email_log.txt"));
                    // Write data to the file
    //                    writer.write(smtpServer);
    //                    writer.newLine();
    //                    writer.write(String.valueOf(port));
    //                    writer.newLine();
    //                    writer.write(String.valueOf(useTls));
    //                    writer.newLine();
    //                    writer.write(String.valueOf(useSsl));
    //                    writer.newLine();
    //                    writer.newLine();
                    writer.write("-----------------------------------------------------------------------------------------\n");
                    writer.write("From: " + serviceName);
                    writer.newLine();
                    writer.write("-----------------------------------------------------------------------------------------\n");
                    StringBuilder emailStringBuilder = new StringBuilder();
                    for (int i = 0; i < toEmails.size(); i++) {
                        if (i > 0) {
                            emailStringBuilder.append(", ");
                        }
                        emailStringBuilder.append(toEmails.get(i));
                    }
                    writer.write("To: " + emailStringBuilder.toString());
                    writer.newLine();
                    writer.write("-----------------------------------------------------------------------------------------\n");
                    writer.write("Subject: " + subject);
                    writer.newLine();
                    writer.write("-----------------------------------------------------------------------------------------\n\n");
    
                    writer.write(body);
                    writer.newLine();
                    writer.newLine();
                    writer.write("Best regards\n\n\n");
                    writer.newLine();
                    writer.write("MusgoSublime | MakeItSimple\n");
                    writer.write("Rua Dr. António Bernardino de Almeida, 431\n");
                    writer.write("4249-015 Porto - PORTUGAL\n");
                    writer.write("tel. +351 228 340 500 | fax +351 228 321 159\n");
    
    
                }finally {
                    writer.flush();
                    writer.close();
                }
                return true;
            }
            catch (IOException e) {
                System.out.println(ANSI_BRIGHT_RED + "Sending Email processes didn't complete (Writting to file)" + ANSI_RESET);
            };
            return false;
        }catch (Exception e){
            return false;
        }
    }
```