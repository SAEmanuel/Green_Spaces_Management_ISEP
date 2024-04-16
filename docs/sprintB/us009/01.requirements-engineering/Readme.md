# US009 - Water consumption of green spaces


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to know the exact costs referring to water
consumption of specific green space so that I may manage these expenses efficiently.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document and client meetings:**

> The aim is to carry out a statistical analysis concerning the water consumption costs in all parks.

> The ”WaterUsed.csv” file provide the necessary data to carry out the study.
> This file records daily water consumption (in m3) since the day
each park opened. 
>>The amount paid for water is 0.7 $/m3, up to a
consumption of 50 m3, with a fee of 15% added for higher consumption
levels.
>
>>The data file contains records of the following information: ”Park Identification”, ”Year”, ”Month”, ”Day”, ”Consumption”.



**From forum:**

> **Question:** The file ”WaterUsed.csv” should be given by the user?
>
> **Answer:** Yes.

> **Question:** When the user enters the park identification, should this be by name or through an ID?
>
> **Answer:** Users will answer the survey but anonymously.

> **Question:** On creating the barplot, what's the reason behind asking for the time period (StartMonth, EndMonth) since the barplot is a monthly-based graph? Wouldn't it make more sense just to have the name of the desired month instead?
>
> **Answer:** The barplot represents a bar for each month requested. For example, I might only be interested in knowing consumption in the summer months.

> **Question:** Regarding the third "task", the park with the highest and the lowest water consumption, does it refer to the total consumption (sum of the consumptions for each park or simply from a day that is recorded)?
>
> **Answer:** You will choose two parks. The one that had a day with a higher consumption than all those registered (from all the parks) and another park that had a day with a lower consumption than all those registered (from all the parks).

> **Question:** Also, to calculate the mean, median, standard deviation and coefficient of skewness, is it from the daily data (days that are recorded)?
>
> **Answer:** Calculations are made for all selected park records.

> **Question:** Is the consumption limit of 50m^3 calculated per day or per month?
>
> **Answer:** By month. 

> **Question:** Is the 15% rate when consumption exceeds 50 m^3 applied to the entire volume of water or only to the volume that was exceeded?
>
> **Answer:** To volume exceeded.

> **Question:** How will the average monthly water consumption costs be presented? Is the value saved in a specific file or will it simply be presented to the user?
>
> **Answer:** It will be presented to the user/client.





### 1.3. Acceptance Criteria 

* **AC1:** There must exist a file "WaterUsed.csv".
* **AC2:** The file "WaterUsed.csv" must have the specific information (year,month...).
* **AC3:** Implement statistical analysis on monthly water consumption.
* **AC4:** Calculate average monthly costs based on specified parameters.
* **AC5:** Compare statistical indicators between parks with highest and lowest water consumption.


### 1.4. Found out Dependencies

* Data file "WaterUsed.csv" provided by the user.

### 1.5 Input and Output Data

**Input Data:**

* Information from file "WaterUsed.csv".
* Time period (StartMonth, EndMonth).
* Park identification.
* Number of parks to be analyzed.

**Output Data:**

* Barplot representing monthly water consumption
* Average monthly costs related to water consumption.
* Statistical indicators for parks with highest and lowest water consumption.
  * mean, median, standard deviation, and the coefficient of skewness
  * build relative and absolute frequency tables
  * Graphically represent data through histograms
* Costs referring to water consumption.
* Error messages
* Success of secondary operations
* Success of the operation

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us009-system-sequence-diagram-alternative-one-System_Sequence_Diagram__SSD____Alternative_One.svg)

