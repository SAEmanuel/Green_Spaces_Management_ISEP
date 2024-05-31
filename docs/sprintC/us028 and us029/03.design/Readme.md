# US028 and US029 - Consulting Tasks and Task Completion

## 3. Design - User Story Realization

### 3.1. Rationale
//MUDAR TABELA TODA

| Interaction ID | Question: Which class is responsible for...                | Answer          | Justification (with patterns)                                                                                                             |
|:---------------|:-----------------------------------------------------------|:----------------|:------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                            | RetrieveTasksUI | Pure Fabrication: A UI component typically handles user interactions and acts as an interface between the user and the system.            |
|                | ... coordinating the US?                                   | TeamController  | Controller: The controller orchestrates the use case by coordinating the interactions between the UI and the domain model.                |
| Step 2         | ... coordinating with the repositories?                    | TeamController  | Controller: The controller interacts with repositories to retrieve necessary data and perform business logic.                             |
|                | ... retrieving the vehicle repository?                     | TeamController  | Controller: The controller interacts with the repositories to retrieve the necessary data for the use case.                               |
| Step 3         | ... getting the tasks assigned to collaborator?            | TeamRepository  | Repository: The repository pattern encapsulates the logic for retrieving data, in this case, tasks assigned.                              |
| Step 4         | ... iterating over each team?                              | TeamRepository  | Repository: The repository handles the retrieval and manipulation of data, iterating over teams.                                          |
|                | ... determining in which team the desired collaborator is? | TeamRepository  | Repository: The repository pattern encapsulates the logic for data access and business logic, determining team members.                   |
| Step 5         | ... retrieving the tasks assigned to that collaborator?    | TeamRepository  | Repository: The repository pattern encapsulates the logic for data access, retrieving the list of tasks assigned to the person.           |
| Step 6         | ... validating data?                                       | TeamRepository  | Repository: The repository pattern encapsulates the logic for data access and validation, ensuring data integrity.                        | 
|                | ... saving the tasks assigned to the collaborator?         | TeamRepository  | Repository: The repository pattern encapsulates the logic for data access and manipulation, saving vehicles needing check-up.             | 
| Step 7         | ... sending the list of tasks to the actor?                | RetrieveTasksUI | Pure Fabrication: The UI component is responsible for presenting information to the user and is a separate concern from the domain logic. |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* None

Other software classes (i.e. Pure Fabrication) identified:

* TeamController
* RetrieveTasksUI
* TeamRepository

## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.
#### Version 1
![Sequence Diagram v1 - Full](svg/us028_29-sequence-diagram-full-v1.svg)

#### Version 2
![Sequence Diagram v2 - Full](svg/us028_29-sequence-diagram-full-v2.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us028_29-class-diagram.svg)