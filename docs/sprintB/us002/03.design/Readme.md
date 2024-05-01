# US002 - Job Registration

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for... | Answer                | Justification (with patterns)                                                                                                             |
|:---------------|:--------------------------------------------|:----------------------|:------------------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?             | RegisterJobUI         | Pure Fabrication: A UI component typically handles user interactions and acts as an interface between the user and the system.            |
|                | ... coordinating the US?                    | RegisterJobController | Controller: The controller orchestrates the use case by coordinating the interactions between the UI and the domain model.                |
| Step 2         | ... coordinating with the repositories?     | RegisterJobController | Controller: The controller interacts with repositories to retrieve necessary data and perform business logic.                             |
|                | ... retrieving the job repository?          | RegisterJobController | Controller: The controller interacts with the repositories to retrieve the necessary data for the use case.                               |
| Step 3         | ... getting the job needing check-up?       | JobRepository         | Repository: The repository pattern encapsulates the logic for retrieving data, in this case, jobs needing check-up.                       |
| Step 4         | ... iterating over each job?                | JobRepository         | Repository: The repository handles the retrieval and manipulation of data, iterating over jobs needing check-up.                          |
| Step 5         | ... validating data?                        | JobRepository         | Repository: The repository pattern encapsulates the logic for data access and validation, ensuring data integrity.                        |
| Step 6         | ... retrieving the valid jobs?              | JobRepository         | Repository: The repository pattern encapsulates the logic for data access, retrieving jobs needing check-up.                              | 
|                | ... saving the jobs?                        | Job                   | Repository: The repository pattern encapsulates the logic for data access and manipulation, saving jobs needing check-up.                 | 
| Step 7         | ... sending the list of jobs to the actor?  | RegisterJobUI         | Pure Fabrication: The UI component is responsible for presenting information to the user and is a separate concern from the domain logic. |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Job

Other software classes (i.e. Pure Fabrication) identified:

* RegisterJobUI
* RegisterJobController
* JobRepository

## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us002-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this
user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us002-sequence-diagram-split.svg)

**Register Job Partial SD**

![Sequence Diagram - Partial - Register Job](svg/us002-sequence-diagram-partial-register-job.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us002-class-diagram.svg)