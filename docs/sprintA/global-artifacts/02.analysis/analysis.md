# OO Analysis

## Rationale to identify domain conceptual classes

### _Conceptual Class Category List_ 

**Business Transactions**

* Vehicle Maintenance
* Team Assignment


---

**Transaction Line Items**

* 

---

**Product/Service related to a Transaction or Transaction Line Item**

* 

---

**Transaction Records**

* 

---  

**Roles of People or Organizations**

* GSM manages multiple GreenSpace.

* GSU uses multiple GreenSpace.

* VFM registers multiple Vehicle.
* VFM selects multiple VehicleForCheckUp.
* VFM registers multiple CheckUp.

* HRM registers multiple Skill.
* HRM registers multiple Job.
* HRM registers multiple Collaborator.
* HRM generates multiple Team.

* Collaborator has multiple Job 

---

**Places**

* GreenSpace contains Garden.
* GreenSpace contains Medium-sized park.
* GreenSpace contains Large-sized park.

---

**Noteworthy Events**

* MusgoSublime manages green spaces, which are used by GSU and managed by GSM. This system has collaborators who have a job and skills assigned to them which are registered by HRM.
  HRM also generates teams composed by collaborators. These teams have an agenda to follow defined by tasks. They also use vehicles (registered by VFM) who can transport equipment and machines.
  These vehicles need a periodical check-up (defined by VFM), the system informs the VFM of what vehicles need check-up, he may select the ones he wants and registers the overhauling.

---

**Physical Objects**

* Vehicle transports Equipment
* Vehicle transports Machine
* Vehicle is registered by VFM
---

**Descriptions of Things**

* Agenda defines multiple Task
* Skill is assigned to multiple Collaborator
* Task requires multiple Skill
* Task requires multiple Job

---

**Catalogs**

* 

---

**Containers**

* Team
* VehicleForCheckUp

---

**Elements of Containers**

* Team contains Collaborator
* VehicleForCheckUp contains (some)Vehicle

---

**Organizations**

* MusgoSublime :  is an organization dedicated to the planning, construction and maintenance of green spaces for collective use in their multiple dimensions.
* MusgoSublime manages multiple GreenSpace
---

**Other External/Collaborating Systems**

* No info

---

**Records of finance, work, contracts, legal matters**

* No info

---

**Financial Instruments**

* No info

---

**Documents mentioned/used to perform some work/**

* Integrative Project Assignment (EN) - Versions 1 to 1.2 (to be updated)

---


## Rationale to identify associations between conceptual classes

An association is a relationship between instances of objects that indicates a relevant connection and that is worth of remembering, or it is derivable from the List of Common Associations:

- **_A_** is physically or logically part of **_B_**
- **_A_** is physically or logically contained in/on **_B_**
- **_A_** is a description for **_B_**
- **_A_** known/logged/recorded/reported/captured in **_B_**
- **_A_** uses or manages or owns **_B_**
- **_A_** is related with a transaction (item) of **_B_**
- etc.
    

| Concept (A)        |   Association    |       Concept (B) |
|--------------------|:----------------:|------------------:|
| MusgoSublime       |     manages      |        GreenSpace |
| MusgoSublime       |    possesses     |      Collaborator |
| GreenSpace  	      |  is managed by   |               GSM |
| GreenSpace  	      |    is used by    |               GSU |
| GreenSpace  	      | has carried out  |              Task |
| Task               |  is defined by   |            Agenda |
| Task               |     requires     |             Skill |
| Task               |     requires     |               Job |
| Skill              |   assigned to    |      Collaborator |
| Collaborator       |    possesses     |              Team |
| Collaborator       |       has        |               Job |
| HRM                |     register     |      Collaborator |
| HRM                |     register     |               Job |
| HRM                |     register     |             Skill |
| HRM                |    generates     |              Team |
| Team               |       has        |            Agenda |
| Team               |       uses       |           Vehicle |
| Vehicle            |    transport     |         Equipment |
| Vehicle            |    transport     |           Machine |
| Vehicle            | is registered by |               VFM |
| VFM                |     register     |           CheckUp |
| VFM                |     register     |           Vehicle |
| VFM                |     selects      | VehicleForCheckUp |
| VehicleForCheckUp  |     informs      |               VFM |
| VehicleForCheckUp  |       does       |           CheckUp |
| VehicleForCheckUp  | is contained in  |           Vehicle |




## Domain Model

![Domain Model](svg/project-domain-model.svg)