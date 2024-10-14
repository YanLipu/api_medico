# Java 17.0.11 Installation Guide

This guide provides step-by-step instructions for installing Java 17.0.11 on different operating systems.

## Prerequisites

- **Administrative Access**: You may need administrative privileges to install software on your machine.
- **Internet Connection**: Required for downloading the installation files.

## Download Java 17.0.11

1. Visit the official Oracle JDK download page: [Oracle JDK Downloads](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
2. Locate **Java SE 17.0.11** and select the appropriate installer for your operating system.
3. Accept the license agreement and download the installer.

## Installation Instructions

### Windows

1. **Run the Installer**:
   - Locate the downloaded `.exe` file and double-click it to start the installation.

2. **Follow the Installation Wizard**:
   - Click **Next**.
   - Choose the installation folder (default is recommended) and click **Next**.
   - Choose additional tasks (if desired) and click **Next**.
   - Click **Install** to begin the installation.

3. **Set Environment Variables**:
   - Right-click on **This PC** or **My Computer** and select **Properties**.
   - Click on **Advanced system settings**.
   - Click on the **Environment Variables** button.
   - Under **System variables**, find and select the `Path` variable, then click **Edit**.
   - Click **New** and add the path to the JDK `bin` folder (e.g., `C:\Program Files\Java\jdk-17.0.11\bin`).
   - Click **OK** to close all dialog boxes.

4. **Verify Installation**:
   - Open the Command Prompt and type:
     ```bash
     java -version
     ```
   - You should see output similar to:
     ```
     java version "17.0.11" 2022-10-18
     ```

### macOS

1. **Run the Installer**:
   - Locate the downloaded `.dmg` file and double-click it to mount the disk image.
   - Inside the mounted disk, double-click the `.pkg` file to start the installation.

2. **Follow the Installation Wizard**:
   - Click **Continue** to proceed through the installer steps.
   - Accept the license agreement and select the installation destination.
   - Click **Install** to complete the installation.

3. **Set Environment Variables (Optional)**:
   - Open the Terminal and add the following line to your shell profile (e.g., `.bash_profile` or `.zshrc`):
     ```bash
     export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.0.11.jdk/Contents/Home
     ```
   - Run `source ~/.bash_profile` or `source ~/.zshrc` to apply the changes.

4. **Verify Installation**:
   - Open the Terminal and type:
     ```bash
     java -version
     ```
   - You should see output similar to:
     ```
     java version "17.0.11" 2022-10-18
     ```

### Linux

1. **Download the Archive**:
   - Locate the downloaded `.tar.gz` file from the Oracle website.

2. **Extract the Archive**:
   - Open a terminal and run:
     ```bash
     tar -xvzf jdk-17.0.11_linux-x64_bin.tar.gz
     ```

3. **Move the JDK to `/usr/local`**:
   - Move the extracted JDK folder:
     ```bash
     sudo mv jdk-17.0.11 /usr/local/
     ```

4. **Set Environment Variables**:
   - Open your shell profile (e.g., `.bashrc` or `.bash_profile`) and add:
     ```bash
     export JAVA_HOME=/usr/local/jdk-17.0.11
     export PATH=$JAVA_HOME/bin:$PATH
     ```
   - Run `source ~/.bashrc` or `source ~/.bash_profile` to apply the changes.

5. **Verify Installation**:
   - In the terminal, type:
     ```bash
     java -version
     ```
   - You should see output similar to:
     ```
     java version "17.0.11" 2022-10-18
     ```

## Conclusion

You have successfully installed Java 17.0.11 on your machine. You can now start developing Java applications using this version of the JDK.
