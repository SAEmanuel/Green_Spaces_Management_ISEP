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
> **Answer:** 

> **Question:** What information a skill should have?
>
> **Answer:** 

>  **Question:** When we trie to create and a skill that already exist, what the system should do?
>
> **Answer:**  

> **Question:**  As an HRM when i trie to login in the system, my username needs to have some especial format? and the password to?
>
> **Answer:** 

> **Question:**  As an HRM when i trie to login in the system, my username needs to have some especial format? and the password to?
>
> **Answer:**

### 1.3. Acceptance Criteria

* **AC01:** As an HRM, I can access a dedicated interface or module within the system to manage the skills of collaborators.
  * If the HRM does not have the necessary permissions, the system should prompt a warning message.
  * If HRM puts an invalid username or password, the system should prompt an error message.
* **AC02:** I can view a list of existing skills along with their details, such as the skill name and description.
* **AC03:** I can add new skills to the system, specifying a unique identifier, name, and description for each skill.
  * If the system successfully add the skill to the collaborator, a prompt messages to inform. 
* **AC04:** I can edit the details of an existing skill, including name and description.
  * If the system successfully edit the skill to the collaborator, a prompt messages to inform.
* **AC05:** I can delete skills (from the list of skills) that are no longer required and also remove one or more skills from a collaborator.
  * If the system successfully add the skill to the collaborator, a prompt messages to inform.
* **AC06:** I can search for a specific skill or a collaborator based on their assigned skills.
  * If the skill/collaborator name introduce by the HRM does not exist, the system must prompt an error message.
* **AC07:** Changes made to skills (skills assignments, rename skills, delete skills...) are logged in the system, including details (who,when,what).
* **AC08:** The system ensures data integrity by preventing duplicate skill entries.
* **AC09:** The interface for managing skills is user-friendly and intuitive.
* **AC10:** The system provides appropriate error handling and feedback messages to guide the HRM in case of any issues or incorrect inputs.
* **AC11:** The information regarding skills and their assignments is securely stored in the system, following best practices for data protection and privacy.



   


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

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)


### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.