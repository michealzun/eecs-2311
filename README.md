# TAB2PDF [EECS2311 - Group 2]

# Installation

If an execuatable is not present, that means we could not get one running on time. Please run the project through the Eclipse IDE with the instructions set below.

### Library Setup
1. Intall JavaFX plugin (In Eclipse, go to Help --> Eclipse Marketplace --> Search `FX` and install [E(fx)clipse](https://marketplace.eclipse.org/content/efxclipse)
2. Create user library (Window -> Preference --> Java Buildpath --> User Libraries --> New --> Name the libary to `JavaFX` --> Add Jars --> Find the JavaFX SDK in src/lib/javafx-sdk-17.0.2/lib, select all the jar files located in the `lib` folder --> Apply and Close)
3. Add user libray (Right click TAB2XML_G14 (Project Folder) --> Build Path --> Add libraries... --> User library --> Select the JavaFX user libary --> Finish)
4. Configure build path (Right click project folder --> Build Path --> Configure Build Path --> Java Build Path --> Place JavaFX under Classpath --> Add library --> User libary --> JavaFX --> Apply)
5. repeat step 2 to 4 to Add the other library in src/lib aswell
6. Add VM Arguments (Right click TAB2XML_G14 (or src/main.java,GUI/MainApp) --> Run As --> Run Configurations --> Arguments --> Paste the VM Arguments below --> Apply and Close)

### VM Arguments
--module-path "src/lib/javafx-sdk-17.0.2/lib" --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.graphics,javafx.web,javafx.swing


![VM Arugment!](https://user-images.githubusercontent.com/24886370/155257434-938e4e1c-ceb4-4dbf-b766-0891a7f5bb82.png)

- [Setup video](https://www.youtube.com/watch?v=_7OM-cMYWbQ) note we already have JavaFX folder in the project so it's slightly different

# Documentation
- [Requirements Document](https://drive.google.com/file/d/1P6PVXSci40jC2ZXHNMJsVcVXKRzrPFJE/view?usp=sharing)
- [User Manual](https://drive.google.com/file/d/145ux549id4GujhYk7V6eDImr-z0rWSo-/view?usp=sharing)

# Contributors
- Micheal Sun
- Patrick Koziarski
- Vikramjeet Singh
- Eromosele Dexter
