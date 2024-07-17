# SudokuFX2024

<p align="center">
  <img alt="SudokuFX2024 in action" src="https://github.com/Lob2018/SudoFX2024/blob/main/.myresources/design%20and%20modeling/images/SudokuFX2024_in_action.jpg">
</p>

## Contents

- [Description](#description)
- [Build with](#build-with)
- [Project](#project)
- [Installation](#installation)
- [Use](#use)
- [Examples](#examples)
- [Uninstallation](#uninstallation)
- [Documentation](#documentation)
- [Contributors](#contributors)
- [Feedback](#feedback)
- [Licence](#licence)

## Description

SudokuFX2024 is a Sudoku game, allowing you to create several player profiles and save and restore their games. The
application allows you to solve the current Sudoku grid, but also other entries manually.

## Build with

- Java 21
- JavaFX 21
- WiX Toolset v3.11
- Dependencies :
    - Development
        - javafx-controls
        - javafx-fxml
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
        - javafx-maven-plugin
        - maven-shade-plugin (create the uber JAR)
        - exec-maven-plugin (batch to generate MSI and uber JAR with his run batch)
    - Test dependencies :
        - spring boot starter test (JUnit, Mockito, Hamcrest)
        - surefire
        - jacoco
        - testfx-junit5 (ex. : FxRobot to execute actions within the UI, or custom Hamcrest matchers org.testfx.matcher.*.)

## Project

- [The project roadmap](https://github.com/users/Lob2018/projects/4)
- [The app mockup (Figma)](https://www.figma.com/file/GiSwlg2mZofXalf1Quaa5w/SudokuFX2024?type=design&node-id=0%3A1&mode=design&t=D4N04MRdsbArO6fu-1)

## Installation (Windows)

- Application without Java Runtime Environment included ([the latest JRE must be installed on your machine](https://adoptium.net))

  [Download, unzip, and keep the two files together (SudokuFX-v.v.v.bat to launch), from the latest Windows version of the file **SudokuFX-v.v.v.zip
**, available in Assets.](https://github.com/Lob2018/SudoFX2024/releases/latest)

- Application with Java Runtime Environment included

  [Download and install the latest Windows version of the file **SudokuFX-v.v.v.msi
**, available in Assets.](https://github.com/Lob2018/SudoFX2024/releases/latest)

  The MSI file does not have a code signing certificate (paid service), Microsoft Defender SmartScreen informs you of this
during installation; to continue the installation click on **additional information**, then **Run anyway**.

  <sub>[This other software may be useful to you to check the integrity of the downloaded file (see the version fingerprints inside hash.txt).](https://www.clubic.com/telecharger-fiche56914-hashtab.html)</sub>

## Use

## Examples

## Uninstallation (Windows)

- For Windows :
    - **Uninstall from the Control Panel (for programs)**
        1. In the search box on the taskbar, type **Control Panel** and select it from the results.
        2. Select **Programs > Programs and Features**.
        3. Press and hold (or right-click) on the program you want to remove and select **Uninstall** or *
           *Uninstall/Change**. Then follow the directions on the screen.
    - To completely delete your app data and logs, you need to manually delete this folder:
        - **C:/Users/\<USERNAME\>**[^1]**/AppData/Local/Soft64.fr/SudokuFX/**.

[^1]: Replace \<USERNAME\> with your Windows username

## Documentation

- [JavaDoc](https://lob2018.github.io/SudoFX2024/)

## Contributors

Lob2018.

## Feedback

- [File an issue](https://github.com/Lob2018/SudoFX2024/issues)
    - If you want you can attach the application logs you find :
        - For Windows
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
