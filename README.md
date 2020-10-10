# Smart Traffic Light

Final academic project at Ariel University 2020.

The purpose of the system is to reduce the waiting time for drivers at traffic lights. 
This process is possible if you find a profitable traffic distribution and change the work of traffic lights.

This project shows how the graph search algorithm can solve this problem and significantly reduce the waiting time.

## External libraries and programs
* [MySQL workbrench](https://dev.mysql.com/downloads/workbench/)
* [JavaFX](https://openjfx.io/)
* [Junit](https://junit.org/junit5/)
* [Excel](https://www.microsoft.com/en-us/microsoft-365/excel)

## GUI
### Start
![image](https://user-images.githubusercontent.com/44946807/95659467-f9f3c800-0b29-11eb-83ad-eaff1c1719e3.png)

### Select a usage type
![image](https://user-images.githubusercontent.com/44946807/95659549-80a8a500-0b2a-11eb-987f-7b833f0e9166.png)

For the explanation a type of **Analyst** is selected. There is not much difference between the types of users - will be expanded later.

### Fill traffic condition
![image](https://user-images.githubusercontent.com/44946807/95660626-c321b000-0b31-11eb-9ae3-3acce5337a35.png)

#### Info button
Clicking the ```Info``` button will provide appropriate guidance for new users in the system: 

![image](https://user-images.githubusercontent.com/44946807/95660398-3e826200-0b30-11eb-9a58-934263397312.png)

Each button displays on the screen a guide and explanations about the components in the system that the user must use or notice.

#### Random button
To create intersections conditions for the simulation the user can by clicking on the ```Random``` button fill in the required fields with random and valid numbers.

![image](https://user-images.githubusercontent.com/44946807/95660679-14ca3a80-0b32-11eb-9d8c-cd088f752ce2.png)

#### Database button
Another option to enter information is given by clicking on the ```Database``` button. Clicking on it will open the user login window for his relational database (MYSQL).

![image](https://user-images.githubusercontent.com/44946807/95661054-dc782b80-0b34-11eb-970f-54b4c57b1406.png)

* If the user and password are correct and the database also exists, you can click on the ```Connect``` button in order to connect to the database and retrieve simulation
  information that has already been stored in the system in the past.
  
  ![image](https://user-images.githubusercontent.com/44946807/95661180-febe7900-0b35-11eb-9796-25239d1c80e9.png)
  
  Then the user can see the **Analytics Results** window(will be expanded later) of chosen simulation and the fields will be filled in according to the data in the database.
  
  ![image](https://user-images.githubusercontent.com/44946807/95661367-61644480-0b37-11eb-970a-59d8cf92c742.png)

* If the database does not exist the user can click on the "Create Database" button and create one (empty).

#### Run button
* Clicking on the "Run" button will take the user to the running window of the simulation where you can visually see the algorithmic decisions about the activity of the traffic
  lights at the traffic junctions according to the traffic conditions entered by the user in the previous steps.

![image](https://user-images.githubusercontent.com/44946807/95661558-f0be2780-0b38-11eb-802a-864b36be50a4.png)

* In this window to start the run the user has to click on "Start".

#### After running 
* After the run, a window will be displayed to the user showing the results of the run and analytical data.
* After the run the user can choose whether to save the simulation data and its results in the database for future uses by clicking on ```Save``` button.

![image](https://user-images.githubusercontent.com/44946807/95661745-59f26a80-0b3a-11eb-8713-83ed543ec778.png)

## Results
In this project, the results were compared according to two criteria:

* Time - total time for all cars to pass the intersection
* AWS - average waiting time for a driver

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

## Prerequisites

What things you need to install the software and how to install them

```
git clone "project URL"
```

