# US022 - Add a new entry in the Agenda

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...                | Answer                                  | Justification (with patterns)                                                                                                   |
|:---------------|:-----------------------------------------------------------|:----------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                            | AgendaUI                                | Pure Fabrication: A UI component typically handles user interactions and acts as an interface between the user and the system.  |
|                | ... coordinating the US?                                   | AgendaController                        | Controller: The controller orchestrates the use case by coordinating the interactions between the UI and the domain model.      |
|                | ... knowing the user using the system?                     | UserSession                             | IE: cf. A&A component documentation.                                                                                            |
|                | ... retrieving the list of ToDO Entry?                     | AgendaController and ToDoListRepository | Repository: The repository pattern encapsulates the logic for retrieving data, in this case, vehicles needing check-up.         |
| Step 2         | ... displaying the list for the actor to select an option? | AgendaUI                                | Pure Fabrication                                                                                                                |
| Step 3         | ... temporarily keeping the input option?                  | AgendaUI                                | Pure Fabrication                                                                                                                |
| Step 4         | ... displaying the form for the actor to input data?       | AgendaUI                                | Pure Fabrication                                                                                                                |
| Step 5         | ... temporarily keeping the input option?                  | AgendaUI                                | Pure Fabrication                                                                                                                |
| Step 6         | ... display all the information before submitting?         | AgendaUI                                | Pure Fabrication.                                                                                                               |
| Step 7         | ... postponing the date?                                   | AgendaRepository                        | Repository: The repository handles the retrieval and manipulation of data, iterating over the existent Tasks in the repository. |              
| 	              | ... validating all data (local validation)?                | AgendaEntry                             | IE: kowns its data.                                                                                                             | 
| 	              | ... validating all data (global validation)?               | AgendaRepository                        | IE: knows all its information.                                                                                                  |
| 	              | ... saving the register agendaEntry?                       | AgendaRepository                        | IE: owns all its Task.                                                                                                          |
| Step 8         | ... informing operation success?                           | AgendaUI                                | IE: is responsible for user interactions.                                                                                       | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* AgendaEntry
* AgendaRepository
* ToDoListRepository

Other software classes (i.e. Pure Fabrication) identified:

* AgendaController
* AgendaUI

## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us022-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this
user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us022-sequence-diagram-split.svg)

**Get Repositories**

![Sequence Diagram - Partial - Get Repositories](svg/us022-sequence-diagram-partial-get-repositories.svg)

**Get Responsible email**

![Sequence Diagram - Partial - Get Responsible email](svg/us022-sequence-diagram-partial-get-responsible.svg)

**Get ToDo list for responsible**

![Sequence Diagram - Partial - Get ToDo list for responsible](svg/us022-sequence-diagram-partial-get-ToDo-list-for-responsible.svg)

**List to DTO**

![Sequence Diagram - Partial - List to DTO](svg/us022-sequence-diagram-partial-toDto.svg)

**Register Agenda Entry**

![Sequence Diagram - Partial - Register Agenda Entry](svg/us022-sequence-diagram-partial-register-Agenda-Entry.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us022-class-diagram.svg)