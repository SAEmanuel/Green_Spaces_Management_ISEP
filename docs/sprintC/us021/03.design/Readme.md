# US021 - Adding a To-Do List Entry for Green Space Management

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...                | Answer                                      | Justification (with patterns)                                                                                                   |
|:---------------|:-----------------------------------------------------------|:--------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                            | ToDoListUI                                  | Pure Fabrication: A UI component typically handles user interactions and acts as an interface between the user and the system.  |
|                | ... coordinating the US?                                   | ToDoListController                          | Controller: The controller orchestrates the use case by coordinating the interactions between the UI and the domain model.      |
|                | ... knowing the user using the system?                     | UserSession                                 | IE: cf. A&A component documentation.                                                                                            |
|                | ... retrieving the list of Green Spaces?                   | ToDoListController and GreenSpaceRepository | Repository: The repository pattern encapsulates the logic for retrieving data                                                   |
| Step 2         | ... displaying the list for the actor to select an option? | GreenSpaceRepository                        | Pure Fabrication                                                                                                                |
| Step 3         | ... temporarily keeping the input option?                  | ToDoListUI                                  | Pure Fabrication                                                                                                                |
| Step 4         | ... displaying the form for the actor to input data?       | ToDoListUI                                  | Pure Fabrication                                                                                                                |
| Step 5         | ... temporarily keeping the input option?                  | ToDoListUI                                  | Pure Fabrication                                                                                                                |
| Step 6         | ... display all the information before submitting?         | ToDoListUI                                  | Pure Fabrication.                                                                                                               |
| Step 7         | ... submiting data?                                        | toDoListRepository                          | Repository: The repository handles the retrieval and manipulation of data, iterating over the existent Tasks in the repository. |              
| 	              | ... validating all data (local validation)?                | ToDoEntry                                   | IE: kowns its data.                                                                                                             | 
| 	              | ... validating all data (global validation)?               | toDoListRepository                          | IE: knows all its information.                                                                                                  |
| 	              | ... saving the register toDo Entry?                        | toDoListRepository                          | IE: owns all data.                                                                                                              |
| Step 8         | ... informing operation success?                           | ToDoListUI                                  | IE: is responsible for user interactions.                                                                                       | 


### Systematization

According to the taken rationale, the conceptual classes promoted to software classes are:

* GreenSpaceRepository (Repository)
* UserSession (Entity)
* ToDoEntry

Other software classes (i.e. Pure Fabrication) identified:

* ToDoListController
* ToDoListUI
* toDoListRepository



## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us021-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this
user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us021-sequence-diagram-split.svg)

**Get Responsible User's Email Partial SD**

![Sequence Diagram - Partial - Get Responsible User's Email](svg/us021-sequence-diagram-partial-get-responsible-email-UI_Sequence_Diagram__SSD____Get_Responsible_User_s_Email.svg)

**Get Green Spaces by Responsible User Partial SD**

![Sequence Diagram - Partial - Get Green Spaces by Responsible User](svg/us021-sequence-diagram-partial-get-green-spaces-by-responsible-UI_Sequence_Diagram__SSD____Get_Green_Spaces_by_Responsible_User.svg)

**Register To-Do Entry Partial SD**

![Sequence Diagram - Partial - Register To-Do Entry](svg/us021-sequence-diagram-partial-register-to-do-entry-UI_Sequence_Diagram__SSD____Register_To_Do_Entry.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](svg/us021-class-diagram.svg)