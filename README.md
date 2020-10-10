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

