# Maven 3.9.9 Installation Guide

This guide provides step-by-step instructions for installing Apache Maven 3.9.9 on various operating systems.

## Prerequisites

- **Java Development Kit (JDK)**: Maven requires Java, so make sure you have JDK installed. You can verify your JDK installation by running `java -version` in your terminal or command prompt.
- **Administrative Access**: You may need administrative privileges to install software on your machine.
- **Internet Connection**: Required for downloading the installation files.

## Download Maven 3.9.9

1. Visit the official Maven download page: [Apache Maven Downloads](https://maven.apache.org/download.cgi).
2. Locate **Maven 3.9.9** and download the binary zip archive for your operating system.

## Installation Instructions

### Windows

1. **Extract the Archive**:
   - Locate the downloaded `.zip` file and extract it to a preferred location, such as `C:\Program Files\Maven`.

2. **Set Environment Variables**:
   - Right-click on **This PC** or **My Computer** and select **Properties**.
   - Click on **Advanced system settings**.
   - Click on the **Environment Variables** button.
   - Under **System variables**, click **New** and add the following:
     - **Variable name**: `MAVEN_HOME`
     - **Variable value**: Path to your Maven directory (e.g., `C:\Program Files\Maven`).
   - Find the `Path` variable in the **System variables** section and click **Edit**.
   - Add a new entry for Maven's `bin` directory:
     ```
     %MAVEN_HOME%\bin
     ```
   - Click **OK** to close all dialog boxes.

3. **Verify Installation**:
   - Open the Command Prompt and type:
     ```bash
     mvn -version
     ```
   - You should see output similar to:
     ```
     Apache Maven 3.9.9 (c447c7d2a27cb8df073740b88a663ea1ef857a93; 2023-10-10T18:21:15Z)
     Maven home: C:\Program Files\Maven\apache-maven-3.9.9
     Java version: 17.0.11, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17.0.11\jre
     ```

### macOS

1. **Extract the Archive**:
   - Locate the downloaded `.tar.gz` file and extract it using the Terminal:
     ```bash
     tar -xvzf apache-maven-3.9.9-bin.tar.gz
     ```
   - Move the extracted folder to `/usr/local/`:
     ```bash
     sudo mv apache-maven-3.9.9 /usr/local/
     ```

2. **Set Environment Variables**:
   - Open your shell profile file (e.g., `.bash_profile`, `.bashrc`, or `.zshrc`) in a text editor and add:
     ```bash
     export MAVEN_HOME=/usr/local/apache-maven-3.9.9
     export PATH=$MAVEN_HOME/bin:$PATH
     ```
   - Save the file and run:
     ```bash
     source ~/.bash_profile
     ```
     (or the equivalent for your shell).

3. **Verify Installation**:
   - Open the Terminal and type:
     ```bash
     mvn -version
     ```
   - You should see output similar to:
     ```
     Apache Maven 3.9.9 (c447c7d2a27cb8df073740b88a663ea1ef857a93; 2023-10-10T18:21:15Z)
     Maven home: /usr/local/apache-maven-3.9.9
     Java version: 17.0.11, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-17.0.11.jdk/Contents/Home
     ```

### Linux

1. **Extract the Archive**:
   - Open a terminal and locate the downloaded `.tar.gz` file.
   - Extract the archive:
     ```bash
     tar -xvzf apache-maven-3.9.9-bin.tar.gz
     ```
   - Move the extracted folder to `/opt` or another preferred directory:
     ```bash
     sudo mv apache-maven-3.9.9 /opt/
     ```

2. **Set Environment Variables**:
   - Open your shell profile file (e.g., `.bashrc`, `.bash_profile`, or `.profile`) in a text editor and add:
     ```bash
     export MAVEN_HOME=/opt/apache-maven-3.9.9
     export PATH=$MAVEN_HOME/bin:$PATH
     ```
   - Save the file and run:
     ```bash
     source ~/.bashrc
     ```
     (or the equivalent for your shell).

3. **Verify Installation**:
   - In the terminal, type:
     ```bash
     mvn -version
     ```
   - You should see output similar to:
     ```
     Apache Maven 3.9.9 (c447c7d2a27cb8df073740b88a663ea1ef857a93; 2023-10-10T18:21:15Z)
     Maven home: /opt/apache-maven-3.9.9
     Java version: 17.0.11, vendor: Oracle Corporation, runtime: /usr/lib/jvm/java-17-openjdk-amd64
     ```

## Conclusion

You have successfully installed Apache Maven 3.9.9 on your machine. You can now start using Maven for your Java projects. For more information on how to use Maven, refer to the [Apache Maven Documentation](https://maven.apache.org/guides/index.html).
