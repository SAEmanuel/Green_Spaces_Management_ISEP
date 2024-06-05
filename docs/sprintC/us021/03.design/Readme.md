# US021 - Adding a To-Do List Entry for Green Space Management

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...           | Answer                | Justification (with patterns)                                                                                                             |
|:---------------|:------------------------------------------------------|:----------------------|:------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                       | ToDoListUI            | Pure Fabrication: A UI component typically handles user interactions and acts as an interface between the user and the system.            |
|                | ... coordinating the use case?                        | ToDoListController    | Controller: The controller orchestrates the use case by coordinating the interactions between the UI and the domain model.                |
| Step 2         | ... getting the current session instance?             | ApplicationSession    | Singleton: The session instance is retrieved using the Singleton pattern to ensure a single instance throughout the application.           |
|                | ... getting the current user session?                 | ApplicationSession    | Controller: The controller retrieves the current session details to determine the responsible user's email.                               |
| Step 3         | ... getting the responsible user's email?             | UserSession           | Controller: The controller retrieves the email from the user session to identify the responsible user.                                     |
| Step 4         | ... getting the green spaces by the responsible user? | GreenSpaceRepository  | Repository: The repository pattern encapsulates the logic for retrieving green spaces managed by the responsible user.                     |
| Step 5         | ... converting green spaces to DTOs?                  | toDoEntryMapper       | Pure Fabrication: The mapper component handles the conversion of domain objects to data transfer objects (DTOs) for UI presentation.      |
| Step 6         | ... showing the list of green spaces to the actor?    | ToDoListUI            | Pure Fabrication: The UI component is responsible for presenting the list of green spaces to the user.                                    |
| Step 7         | ... searching for the green space by ID?              | GreenSpaceRepository  | Repository: The repository pattern encapsulates the logic for searching and retrieving green space details by ID.                         |
| Step 8         | ... registering and validating the to-do entry?                 | toDoListRepository    | Repository: The repository pattern encapsulates the logic for adding a new to-do entry to the system and validating it.                   |

### Systematization

According to the taken rationale, the conceptual classes promoted to software classes are:

* GreenSpaceRepository (Repository)
* ApplicationSession (Singleton)
* UserSession (Entity)

Other software classes (i.e. Pure Fabrication) identified:

* ToDoListController
* ToDoListUI
* toDoEntryMapper
* toDoListRepository
* Repositories (Singleton)



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