# TAB2PDF [EECS2311 - Group 2]

# Installation

If an execuatable is not present, that means we could not get one running on time. Please run the project through the Eclipse IDE with the instructions set below.

### Library Setup
1. Download [JavaFX SDK](https://gluonhq.com/products/javafx/) and extract it to your preferred location (remember the filepath)
2. Intall JavaFX plugin (In Eclipse, go to Help --> Eclipse Marketplace --> Search `FX` and install [E(fx)clipse](https://marketplace.eclipse.org/content/efxclipse)
3. Create user library (Window -> Preference --> Java Buildpath --> User Libraries --> New --> Name the libary to `JavaFX` --> Add External Jars --> Find the JavaFX SDK in `step (1)`, select all the files located in the `lib` folder --> Apply and Close)
4. Add user libray (Right click TAB2XML_G14 (Project Folder) --> Build Path --> Add libraries... --> User library --> Select the JavaFX user libary --> Finish)
5. Configure build path (Right click project folder --> Build Path --> Configure Build Path --> Java Build Path --> Place JavaFX under Classpath --> Add library --> User libary --> JavaFX --> Apply)
6. Add VM Arguments (Right click TAB2XML_G14 (Project Folder) --> Run As --> Run Configurations --> Arguments --> Paste the VM Arguments below --> Apply and Close)

### VM Arguments
Replace the filepath, i.e., "/path/to/javafx-sdk-15.0.1/lib" with that of your JavaFX-SDK/lib directory

#### Linux/Mac:
--module-path /path/to/javafx-sdk-15.0.1/lib --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.graphics,javafx.web,javafx.swing
#### WIndows: 
--module-path "\path\to\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.graphics,javafx.web,javafx.swing

![VM Arugment](https://user-images.githubusercontent.com/77293421/155246739-840dcec3-9ee7-4376-b706-62698f955984.PNG)

- [Setup video](https://www.youtube.com/watch?v=_7OM-cMYWbQ)

# Documentation
- [Requirements Document](https://drive.google.com/file/d/1P6PVXSci40jC2ZXHNMJsVcVXKRzrPFJE/view?usp=sharing)
- [User Manual](https://drive.google.com/file/d/145ux549id4GujhYk7V6eDImr-z0rWSo-/view?usp=sharing)

# Contributors
- Micheal Sun
- Patrick Koziarski
- Vikramjeet Singh
- Eromosele Dexter
