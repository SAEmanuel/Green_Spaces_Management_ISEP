# US028 and US029 - Consulting Tasks and Task Completion

## 3. Design - User Story Realization

### 3.1. Rationale

#### **Version 1**

| Interaction ID | Question: Which class is responsible for...                            | Answer              | Justification (with patterns)                                                                                                                     |
|:---------------|:-----------------------------------------------------------------------|:--------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                                        | RetrieveTasksUI     | **Pure Fabrication**: A UI component handles user interactions and acts as an interface between the user and the system.                          |
| Step 2         | ... asking the actor to select a filter?                               | RetrieveTasksUI     | **Pure Fabrication**: The UI component handles user interactions, prompting the user to select a filter.                                          |
| Step 3         | ... asking for start and end dates?                                    | RetrieveTasksUI     | **Pure Fabrication**: The UI component is responsible for gathering necessary input from the user, such as dates.                                 |
| Step 4         | ... confirming continuation of the operation?                          | RetrieveTasksUI     | **Pure Fabrication**: The UI component handles user interactions and confirmations, ensuring the user wants to proceed with the operation.        |
| Step 5         | ... coordinating the use case?                                         | AgendaController    | **Controller**: The controller orchestrates the use case by coordinating interactions between the UI and the domain model.                        |
| Step 6         | ... coordinating with the repositories?                                | AgendaController    | **Controller**: The controller interacts with repositories to retrieve necessary data and perform business logic.                                 |
|                | ... getting a singleton instance of the repository?                    | RepositorySingleton | **Singleton**: Provides a single instance of the repositories, ensuring a global access point.                                                    |
|                | ... retrieving the agenda repository?                                  | Repositories        | **Pure Fabrication**: The Repositories class encapsulates the logic to manage and provide specific repositories such as the agenda repository.    |
| Step 7         | ... retrieving the list of planned tasks assigned to the collaborator? | AgendaRepository    | **Repository**: The repository pattern encapsulates the logic for data access, retrieving the list of planned tasks assigned to the collaborator. |
|                | ... sending the planned task list to the actor?                        | RetrieveTasksUI     | **Pure Fabrication**: The UI component is responsible for presenting information to the user and is a separate concern from the domain logic.     |
| Step 8         | ... selecting the task to be completed?                                | RetrieveTasksUI     | **Pure Fabrication**: The UI component allows the user to select a task to be marked as completed.                                                |
|                | ... marking the task as completed?                                     | AgendaController    | **Controller**: The controller handles the task completion process, interacting with the agenda repository to update the task status.             |
| Step 9         | ... updating the task status and end date?                             | AgendaRepository    | **Repository**: The repository is responsible for data modification, updating the task status and end date upon completion.                       |
| Step 10        | ... confirming the success of the operation?                           | RetrieveTasksUI     | **Pure Fabrication**: The UI component informs the user about the success of the operation.                                                       |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* None

Other software classes (i.e. Pure Fabrication) identified:

* RetrieveTasksUI
* AgendaController
* AgendaRepository

## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram v1 - Full](svg/us028_29-sequence-diagram-full.svg)


## 3.3. Class Diagram (CD)

![Class Diagram](svg/us028_29-class-diagram.svg)