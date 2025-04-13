# Hotel-Management-Application (HVA)

## Overview

The Hotel Management Application (HVA) is a modular Java-based system designed to manage various aspects of a hotel, including animals, habitats, employees, and vaccines. The project is divided into two main components:

- **Core (`hva-core`)**: Contains the domain classes and business logic for managing the hotel.
- **App (`hva-app`)**: Provides the user interface for interacting with the hotel management system.

## Features

- **Animal Management**: Register animals, transfer them between habitats, and calculate their satisfaction levels.
- **Habitat Management**: Register habitats, manage their areas, and associate trees with habitats.
- **Employee Management**: Register employees, assign responsibilities, and calculate their satisfaction levels.
- **Vaccine Management**: Register vaccines, vaccinate animals, and track vaccination records.
- **Seasonal Management**: Advance the season and calculate global satisfaction levels.
- **Persistence**: Save and load the application's state using serialization.

```
Hotel_Java/
├── .gitignore
├── Makefile
├── README.md
├── hva-app/
│   ├── hva-app.jar
│   ├── Makefile
│   └── src/
│       └── hva/
│           └── app/
│               ├── animal/
│               ├── employee/
│               ├── exceptions/
│               ├── habitat/
│               ├── main/
│               ├── search/
│               └── vaccine/
├── hva-core/
│   ├── hva-core.jar
│   ├── Makefile
│   └── src/
│       └── hva/
│           ├── animal/
│           ├── employee/
│           ├── exceptions/
│           ├── habitat/
│           ├── season/
│           ├── specie/
│           └── vaccine/
└── uml/
    ├── UML-hva-app.pdf
    ├── UML-hva-core.pdf
    └── UML-po-uilib.pdf
````
## How to Build

The project uses a `Makefile` to build both the core and app components. To build the project, run:

```bash
make
```
This will compile the Java source files and generate the following JAR files:
- hva-core/hva-core.jar
- hva-app/hva-app.jar

## How to Clean
To clean the project (remove compiled files and JARs), run:
`make clean`

## How to Install
To install the project, run:
`make install`
This will install the necessary components for running the application.

## How to Run
Running the Application
1. Navigate to the hva-app directory:
`cd hva-app`
2. Run the application:
`java -cp hva-app.jar:../hva-core/hva-core.jar hva.app.App`

### Example Usage
The application provides a menu-driven interface for managing the hotel. Use the menu options to perform tasks such as registering animals, managing habitats, and tracking vaccinations.

## Dependencies
- Java 17 or later
- Make: For building the project.

## UML Diagrams
The uml/ directory contains UML diagrams for the application:

- `UML-hva-app.pdf`: Diagrams for the app component.
- `UML-hva-core.pdf`: Diagrams for the core component.
- `UML-po-uilib.pdf`: Diagrams for the UI library.

##Author
Developed by Guilherme Monteiro. For more information, visit [my GitHub profile](https://github.com/Monteir016).
