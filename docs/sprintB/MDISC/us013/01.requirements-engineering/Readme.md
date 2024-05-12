# US013 - Optimizing irrigation


## 1. Requirements Engineering

### 1.1. User Story Description

As a Green Spaces Manager I intend to now the routes to be opened and the pipes needed to be laid with a minimum accumulated cost, ensuring that all points are adequately supplied.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

>   Only primitive operations must be used during the making of the algorithm

>	Text output format with ordered pair is acceptable to represent the routes


**From forum:**

> **Question:** Do the vertices mentioned in the statement have coordinates? For example : (41.1496 , -8.6112) (Latitude, Longitude)
If we're going to be correct, as in the case of the Porto city park example, we have to use coordinates to represent a specific point in the park!
>
> **Answer:**  The csv files that we are going to make available for US13 do not provide the coordinates of the vertices, only the information mentioned in US12.
If we want to accurately draw the map of the watering points, we do need the coordinates. As computer scientists/mathematicians, to solve the problem in question, this is unnecessary information/noise.

> **Question:**
Hello client,
Will we be given the distance and/or costs between different water points?
In US12 when we receive only the distance this value will be used as cost in the minimum accumulated cost algorithm developped in US13?
Thank you
>
> **Answer:** You only have one value assigned between different water points, called the "cost", that could mean a distance or something else.

> **Question:** I would like to confirm if, in the csv file, "waterPointX" and "waterPointY" are always whole numbers or can they be letters?
Thank you
>
> **Answer:** They can be letters or words (strings). They are any name that identifies the irrigation point.


### 1.3. Acceptance Criteria 

* **AC1:** All implemented procedures must only use primitive operations, and not existing functions in JAVA libraries.
* **AC2:** The "waterPointX" and "waterPointY" in the csv file can be letters, words (strings) or numbers, something that identifies the irrigation point.



### 1.4. Found out Dependencies

* There is a dependency on "US012 - Importing Water Point Routes and Installation Costs" as there must be enough info in the system (collected in US012) for the algorithm to work.

### 1.5 Input and Output Data

**Input Data:**

* The system automatically retrieves the routes to be opened and the pipes needed to be laid with a minimum accumulated cost, without requiring any manual input.

**Output Data:**

* routes
* minimum cost
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us013-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

