# SudokuFX2024

![License](https://img.shields.io/badge/license-MIT-brightgreen.svg)
![GitHub release](https://img.shields.io/github/v/release/lob2018/SudoFX2024)
![Windows](https://img.shields.io/badge/Windows-Compatible-brightgreen)
![Linux](https://img.shields.io/badge/Linux-Incompatible-red)
![MacOS](https://img.shields.io/badge/MacOS-Incompatible-red)
![Open Issues](https://img.shields.io/github/issues/lob2018/SudoFX2024)
![Open Pull Requests](https://img.shields.io/github/issues-pr/lob2018/SudoFX2024)
[![OpenSSF Scorecard](https://api.scorecard.dev/projects/github.com/Lob2018/SudoFX2024/badge)](https://scorecard.dev/viewer/?uri=github.com/Lob2018/SudoFX2024)

<p align="center">
  <img alt="SudokuFX2024 in action" src="./.myresources/design%20and%20modeling/images/SudokuFX2024_in_action.jpg">
</p>

## Contents

- [Description](#description)
- [Build with](#build-with)
- [Project](#project)
  - [Roadmap](#roadmap)
  - [Mockup](#mockup)
  - [Required Application Properties to Run](#required-application-properties-to-run)
  - [Windows how to run in IntelliJ IDEA](#windows-how-to-run-in-intellij-idea)
- [Installation](#installation)
- [Use](#use)
- [Examples](#examples)
- [Update](#update)
- [Uninstallation](#uninstallation)
- [Documentation](#documentation)
- [Contributors](#contributors)
- [Feedback](#feedback)
- [Licence](#licence)

## Description

SudokuFX2024 is a Sudoku game, allowing you to create several player profiles and save and restore their games. The
application allows you to solve the current Sudoku grid, but also other entries manually.

## Build with

- Java LTS (e.g. 21)
- JavaFX LTS (e.g. 21)
- WiX Toolset v3.11
- Dependencies :
    - Development
        - javafx-controls
        - javafx-fxml
    - DTOs
      - MapStruct
    - SGBDR & SPRING BOOT
        - HSQLDB
        - Spring boot
          - Starter
          - Gluon Ignite with Spring
          - Starter data JPA
          - Starter validation
        - flyway (database migration)
        - passay (generate and validate secrets)
    - Logs
        - logback from Spring Boot
    - Build dependencies :
        - maven-compiler-plugin
          - annotationProcessorPaths: 
            - MapStruct processor (for code generation)
            - Lombok (for generating boilerplate code)
            - Lombok MapStruct Binding (to integrate Lombok with MapStruct)
        - javafx-maven-plugin
        - spring-boot-maven-plugin (create the uber JAR)
        - exec-maven-plugin (batch to generate MSI and uber JAR with his run batch)
    - Test dependencies :
        - spring boot starter test (JUnit, Mockito, Hamcrest)
        - surefire
        - jacoco
        - testfx-junit5 (ex. : FxRobot to execute actions within the UI, or custom Hamcrest matchers org.testfx.matcher.*.)

## Project

![Windows Dev](https://img.shields.io/badge/Windows_Dev-Compatible-brightgreen)
![Linux Dev](https://img.shields.io/badge/Linux_Dev-Incompatible-red)
![MacOS Dev](https://img.shields.io/badge/MacOS_Dev-Incompatible-red)

### Roadmap

- [The project roadmap](https://github.com/users/Lob2018/projects/4)

### Mockup

- [The app mockup (Figma)](https://www.figma.com/file/GiSwlg2mZofXalf1Quaa5w/SudokuFX2024?type=design&node-id=0%3A1&mode=design&t=D4N04MRdsbArO6fu-1)

> [!IMPORTANT]
>
>### Required Application Properties to Run
>
>For the application to work properly, the following application properties must be set at the JVM level:
>
>- app.name: This property specifies the name of the application.
>- app.version: This property specifies the version of the application.
>   - This SemVer-like format is only numeric MAJOR.MINOR.PATCH (e.g., 1.0.0, 2.1.3).
>
>These properties are applied automatically (either by Maven or by the .bat file) but can also be set manually if needed, using the -D flag after extracting the jar file, for example for version 1.0.8:
>
>     # Extract the contents of the SudokuFX JAR file to the 'SudokuFX' directory
>     java -Djarmode=tools -jar SudokuFX-1.0.8.jar extract --destination SudokuFX
>     # Change the current working directory to 'SudokuFX'
>     cd SudokuFX
>     # Perform a training run on your SudokuFX (long process, the application closes at the end)
>     java -Xmx2048m -XX:ArchiveClassesAtExit=SudokuFX.jsa -Dspring.context.exit=onRefresh -Dapp.name=SudokuFX -Dapp.version=1.0.8 -jar SudokuFX-1.0.8.jar
>     # Run the SudokuFX game (using the shared archive file and cache for faster startup)
>     java -Xmx2048m -XX:SharedArchiveFile=SudokuFX.jsa -Dapp.name=SudokuFX -Dapp.version=1.0.8 -jar SudokuFX-1.0.8.jar
>
>Ensuring these properties are correctly set will help maintain a consistent and secure configuration for the application.

### Windows how to run in IntelliJ IDEA

- Download and install [the version LTS (e.g. 21) of the JDK Adoptium Temurin JDK](https://adoptium.net)
- Download and install WiX Toolset v3.11
  - Activate .NET framework 3.5.1 (Control Panel > Programs > Programs and Features > Turn Windows features on or off)
  - Launch [wix311.exe](https://github.com/wixtoolset/wix3/releases/tag/wix3112rtm)
- Configured the necessary environment variables
  - JDK
    - name : JAVA_HOME
    - value LTS (e.g. 21) : C:\Program Files\Eclipse Adoptium\jdk-21.0.3.9-hotspot
  - WiX
    - name : WIX
    - value : C:\Program Files (x86)\WiX Toolset v3.11\
- IntelliJ IDEA
  - Clone the repository
  - Select the project's JDK
    - File > Project Structure > SDK > Add SDK from disk (select the JDK)
  - Run the app at top right corner (SudoMain.java is the main class)
    - Run / Debug Configurations > Edit Configurations...
      - Run (in order to run the application)
        - Add New Configuration > Maven > Run: clean javafx:run > Apply
        <p>
          <img alt="Run with Maven for JavaFX" src="./.myresources/design%20and%20modeling/images/Run_with_Maven_for_JavaFX.jpg">
        </p>
                
        - Open and select SudoMain.java then run **SudokuFX [clean,javafx:run]**
      - Run with details (optional)
        - Add New Configuration > Maven > Run: clean -X javafx:run -Dprism.verbose=true -f pom.xml > Apply
        - Open and select SudoMain.java then run **SudokuFX [run details]**  
- Available Maven commands:

>     #Run
>     mvn clean javafx:run
>     #Clean
>     mvn clean -f pom.xml
>     #Validate
>     mvn validate -f pom.xml
>     #Compile
>     mvn compile -f pom.xml
>     #Test
>     mvn test -f pom.xml
>     #Package (WiX is required)
>     mvn package -f pom.xml

## Installation

![Windows](https://img.shields.io/badge/Windows-Compatible-brightgreen)
![Linux](https://img.shields.io/badge/Linux-Incompatible-red)
![macOS](https://img.shields.io/badge/macOS-Incompatible-red)

- Windows
  - Application without Java Runtime Environment included ([the latest JRE must be installed on your machine](https://adoptium.net))

    [Download, unzip, and keep all the files together (SudokuFX-v.v.v.bat to launch), from the latest Windows version of the file **SudokuFX-v.v.v.zip
**, available in Assets.](https://github.com/Lob2018/SudoFX2024/releases/latest)

  - Application with Java Runtime Environment included

    [Download and install the latest Windows version of the file **SudokuFX_JVM-v.v.v.msi
**, available in Assets.](https://github.com/Lob2018/SudoFX2024/releases/latest)

    The MSI file does not have a code signing certificate (paid service), Microsoft Defender SmartScreen informs you of this
during installation; to continue the installation click on **additional information**, then **Run anyway**.

  <sub>[This other software may be useful to you to check the integrity of the downloaded file (see the version fingerprints inside hash.txt).](https://www.clubic.com/telecharger-fiche56914-hashtab.html)</sub>

## Use

## Examples

## Update

- Windows
  - Application without Java Runtime Environment included (ZIP file with the .bat file and the JAR)
    - [Delete your old unzipped folder from SudokuFX-v.v.v.zip, and follow the installation instructions](#installation)
  - Application with Java Runtime Environment included (from MSI file)
    - [Follow the installation instructions](#installation)

## Uninstallation

- Windows
  - Application without Java Runtime Environment included (ZIP file with the .bat file and the JAR)
    - **Delete your unzipped folder from SudokuFX-v.v.v.zip**
  - Application with Java Runtime Environment included (from MSI file)
    - **Uninstall from the Control Panel (for programs)**
        1. In the search box on the taskbar, type **Control Panel** and select it from the results.
        2. Select **Programs > Programs and Features**.
        3. Press and hold (or right-click) on the program you want to remove and select **Uninstall** or *
           *Uninstall/Change**. Then follow the directions on the screen.
> [!IMPORTANT]
>- Windows
>   - To completely delete your app data and logs, you need to manually delete this folder:
>     
>     **C:/Users/\<USERNAME\>**[^1]**/AppData/Local/Soft64.fr/SudokuFX/**.

[^1]: Replace \<USERNAME\> with your Windows username

## Documentation

- <a href="https://lob2018.github.io/SudoFX2024/" target="_blank">Javadoc</a>

## Contributors

[Lob2018](https://github.com/Lob2018)

## Feedback

- [File an issue](https://github.com/Lob2018/SudoFX2024/issues)
    - If you want you can attach the application logs you find :
        - Windows
            - Inside C:/Users/\<USERNAME\>**[^1]**/AppData/Local/Soft64.fr/SudokuFX/logs-sudofx

## Licence

This project is mainly under MIT license.

- Parts of this software are licensed under :
  - Apache 2.0:
    - Sudoku Copyright 2014 Microsoft Corporation
      - Licensed under the Apache License, Version 2.0 (the "License")
        - you may not use this file except in compliance with the License.
        - You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
      - Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
      - Modifications made :
        - Lob2018 - Code transpiled from JavaScript to Java - 2024
  - CC BY 4.0 licence:
    - Luciole typeface
      - These fonts are freely available under Creative Commons Attribution 4.0 International Public License: https://creativecommons.org/licenses/by/4.0/legalcode
      - Luciole © Laurent Bourcellier & Jonathan Perez

Copyright (c) the owner Lob2018. All rights reserved.

Licensed under the [MIT](LICENSE.txt) license.
