# US020 - Register a Green Space

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...                | Answer               | Justification (with patterns)                                                                                                   |
|:---------------|:-----------------------------------------------------------|:---------------------|:--------------------------------------------------------------------------------------------------------------------------------|
| Step 1         | ... interacting with the actor?                            | RegisterGreenSpaceUI | Pure Fabrication: A UI component typically handles user interactions and acts as an interface between the user and the system.  |
|                | ... coordinating the US?                                   | AgendaController     | Controller: The controller orchestrates the use case by coordinating the interactions between the UI and the domain model.      |
|                | ... knowing the user using the system?                     | UserSession          | IE: cf. A&A component documentation.                                                                                            |
| Step 2         | ... displaying the form for the actor to input data?       | RegisterGreenSpaceUI | Pure Fabrication                                                                                                                |
| Step 3         | ... temporarily keeping the input option?                  | RegisterGreenSpaceUI | Pure Fabrication                                                                                                                |
| Step 4         | ... displaying the list for the actor to select an option? | RegisterGreenSpaceUI | Pure Fabrication                                                                                                                |
| Step 5         | ... temporarily keeping the input option?                  | RegisterGreenSpaceUI | Pure Fabrication                                                                                                                |
| Step 6         | ... display all the information before submitting?         | RegisterGreenSpaceUI | Pure Fabrication.                                                                                                               |
| Step 7         | ... creating the green Space object?                       | GreenSpaceRepository | Repository: The repository handles the retrieval and manipulation of data, iterating over the existent Tasks in the repository. |              
| 	              | ... validating all data (global validation)?               | GreenSpaceRepository | IE: knows all its information.                                                                                                  |
| 	              | ... saving the register green space?                       | GreenSpaceRepository | IE: owns all its Task.                                                                                                          |
| Step 8         | ... informing operation success?                           | RegisterGreenSpaceUI | IE: is responsible for user interactions.                                                                                       | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* GreenSpaceRepository
* GreenSpace

Other software classes (i.e. Pure Fabrication) identified:

* GreenSpaceController
* RegisterGreenSpaceUI

## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us020-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this
user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us020-sequence-diagram-split.svg)

**Get Green Space Repository**

![Sequence Diagram - Partial - Get GreenSpace Repository](svg/us020-sequence-diagram-partial-get_green_space_repository.svg)

**Get Sizes List Values**

![Sequence Diagram - Partial - Get Sizes List](svg/us020-sequence-diagram-partial-get_sizes_list.svg)

**Get Responsible**

![Sequence Diagram - Partial - Get User Email](svg/us020-sequence-diagram-partial-register-Get-User-Email.svg)

**Register Green Space**

![Sequence Diagram - Partial - Register Green Space](svg/us020-sequence-diagram-partial-register-greenSpace.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us020-class-diagram.svg)