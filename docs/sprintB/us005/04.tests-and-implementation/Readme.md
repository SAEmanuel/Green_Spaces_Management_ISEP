# US005 - Generate a team proposal automatically

## 4. Tests 

**Test 1:** check team generation with 1 collaborator 

	 @Test
        public void testGenerateTeam1Collaborator() {
            // Setup
            int minCollaborators = 1;
            int maxCollaborators = 2;

            skills.addSkill(skill1);
            // Execute
            Optional<Team> result = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            // Verify
            assertTrue(result.isPresent());
        }

**Test 2:** check team generation with 2 collaborator

        @Test
        public void testGenerateTeamMultipleCollabortor() {
            // Setup
            int minCollaborators = 2;
            int maxCollaborators = 3;

            skills.addSkill(skill2);
            // Execute
            Optional<Team> result = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            // Verify
            assertTrue(result.isPresent());
        }

**Test 3:** check team generation without collaborator

        @Test
        public void testGenerateTeamWithoutCollabortors() {
            // Setup
            int minCollaborators = 2;
            int maxCollaborators = 3;

            skills.addSkill(skill1);
            skills.addSkill(skill2);
            // Execute
            Optional<Team> result = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            // Verify
            assertTrue(result.isPresent());
        }

**Test 4:** check accepting team
        @Test
        public void testRemoveTeam() {
            // Setup

            int minCollaborators = 1;
            int maxCollaborators = 2;
            skills.cleanSkillList();
            skills.addSkill(skill1);

            Optional<Team> teamOpt = teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);

            assertTrue(teamOpt.isPresent());
            Team team = teamOpt.get();

            // Execute
            teamRepository.removeTeam(team.getTeamId());

            // Verify
            assertTrue(teamRepository.getTeamList().isEmpty());
        }
	


_It is also recommended to organize this content by subsections._ 


## 5. Construction (Implementation)

### Class CreateTaskController 

```java
public Optional<Team> generateTeam(int minCollaborators, int maxCollaborators) {
    Optional<Team> optionalValue = Optional.empty();

    SkillList skills = new SkillList();
    skills.setSkills(skillList.getSkillList());

    if(collaboratorRepository == null || skills == null)
        return optionalValue;

    if(!skills.getSkillList().isEmpty() && !collaboratorRepository.getCollaboratorList().isEmpty() && minCollaborators != 0 && maxCollaborators != 0){
        return teamRepository.generateTeam(skills, collaboratorRepository.getCollaboratorList(), minCollaborators, maxCollaborators);
    }
    else{
        return optionalValue;
    }
}
```