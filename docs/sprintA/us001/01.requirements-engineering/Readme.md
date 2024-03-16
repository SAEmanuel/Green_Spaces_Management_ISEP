# US001 - Registering Skills for Collaborators


## 1. Requirements Engineering

### 1.1. User Story Description

As a Human Resources Manager (HRM), I want to register skills that may
be appointed to a collaborator
### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> The system aims to facilitate the management of skills attributed to collaborators within the organization. The Human Resources Manager (HRM) is empowered to register various skills that may be assigned to employees. These skills encompass a wide range of tasks and responsibilities relevant to the management of green spaces, including but not limited to designing, estimating, gardening, electrical work, and masonry.

> The system provides an interface dedicated to managing these skills efficiently. The HRM can utilize this interface to add new skills to the system, specifying unique identifiers, names, and descriptions for each skill. Additionally, the HRM can view a comprehensive list of existing skills along with their details, edit the details of existing skills, and delete obsolete skills as necessary.


**From the client clarifications:**

> **Question:** Which are the criterias to create a skill?
>
> **Answer:** The name of the skill (pruner, heavy vehicle driver).

> **Question:** What information a skill should have?
>
> **Answer:** ...

>  **Question:** When we trie to create and a skill that already exist, what the system should do?
>
> **Answer:** By definition it is not possible to have duplicate values in a set. Duplicate verification is not a business regulation at a technological level.





### 1.3. Acceptance Criteria

* **AC01:** There cannot be two skills with the same name.
* **AC02:** The system should ensure that only authorized HR Managers have permission to register or edit skills.
* **AC03:** When editing a skill, there must be confirmation warnings before the execution of actions.
* **AC04:** There cannot be two skills with different names, but they booth have the same functionalities.
* **AC05:** All required fields must be filled in.
* **AC06:** The name of a skill cannot have any numbers and needs to have relevant information.
* **AC07:** If the system encounters any errors during the skill registration process, it should provide informative error messages to the HR Manager, guiding them on how to resolve the issue.





### 1.4. Found out Dependencies

* There is a dependency on "US02 - Register a Job" before registering a job, it's essential to have a clear understanding of the skills required for that job.
* There is a dependency on "US04 - Assign Skills to a Collaborator" Assigning skills to a collaborator (US04) requires the existence of predefined skills, which are registered as part of US01.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * username and password of the HRM
    * skill name 
    * collaborator name
    * a technical description of the skill
    * specific identifier of the skill
	
* Selected data:
    * skills from the list of skills
    * collaborator from the list of collaborators

**Output Data:**

* List of existing skills and description
* Specific skill and description
* Specific collaborator by filtering skills
* Error messages
* Success of secundary opeations
* Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us001-system-sequence-diagram-alternative-one.svg)


### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.