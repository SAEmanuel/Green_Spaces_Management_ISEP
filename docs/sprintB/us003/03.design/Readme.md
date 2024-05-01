# US003 - Register a collaborator

## 3. Design - User Story Realization 

### 3.1. Rationale

_**Note that SSD - Alternative One is adopted.**_

| Interaction ID                                                                                                                  | Question: Which class is responsible for...   | Answer                       | Justification (with patterns)                                                                                             |
|:--------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------|:-----------------------------|:--------------------------------------------------------------------------------------------------------------------------|
| Step 1 : asks to create a new collaborator		                                                                                    | 	... interacting with the actor?              | CreateCollaboratorUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.             |
| 			  		                                                                                                                         | 	... coordinating the US?                     | CreateCollaboratorController | Controller                                                                                                                |
| Step 2 : shows jobsList list and asks to select one job                                                                         | 	...displaying the jobs list?						           | CreateCollaboratorUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.             |
| Step 3 : requests data (name, birthDate, admissionDate, address, phoneNumber, email, taxpayerNumber, docType, docNumber)	       | 	...displaying the requested data?            | CreateCollaboratorUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.             |
| Step 4 : types requested data (name, birthDate, admissionDate, address, phoneNumber, email, taxpayerNumber, docType, docNumber) | 	...temporarily keeping the input data?       | CreateCollaboratorUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.             |
| Step 5 : shows selected job and requested data		                                                                                | 	...display the typed data before submitting? | CreateCollaboratorUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model.             |
| Step 6 : submits data		                                                                                                         | 	...creating the collaborator object?						   | CollaboratorsRepository      | Creator (Rule 1): in the DM CollaboratorsRepository has Collaborator                                                      |              
|                                                                                                                                 | ...validating all data (global validation)?   | CollaboratorsRepository      | IE : knows all its collaborators                                                                                          |
| 		                                                                                                                              | 	... validating all data (local validation)?  | Collaborator                 | IE: knows its data.                                                                                                       | 
| 			  		                                                                                                                         | 	... saving the created collaborator?         | CollaboratorsRepository      | IE: knows all its collaborators.                                                                                          | 
| Step 7 : confirms the success of the operation			  		                                                                           | 	...informing operation success?              | CreateCollaboratorUI         | is responsible for user interactions.                                                                                     |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* JobsRepository
* Job
* CollaboratorsRepository
* Collaborators

Other software classes (i.e. Pure Fabrication) identified: 

* CreateCollaboratorUI  
* CreateCollaboratorController


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us006-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us006-sequence-diagram-split.svg)

**Get Task Category List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us006-sequence-diagram-partial-get-task-category-list.svg)

**Get Task Category Object**

![Sequence Diagram - Partial - Get Task Category Object](svg/us006-sequence-diagram-partial-get-task-category.svg)

**Get Employee**

![Sequence Diagram - Partial - Get Employee](svg/us006-sequence-diagram-partial-get-employee.svg)

**Create Task**

![Sequence Diagram - Partial - Create Task](svg/us006-sequence-diagram-partial-create-task.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us006-class-diagram.svg)