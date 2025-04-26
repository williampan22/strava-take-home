# Log Storage Analyzer

A Java application that analyzes log storage data to identify optimization opportunities for Strava's logging infrastructure.

## Project Overview

This tool analyzes log storage collections and provides:
- The top 5 largest collections by size (in GB)
- The top 5 collections with the most partitions
- The top 5 collections with suboptimal partition distribution, with recommendations

## Project Structure

![image](https://github.com/user-attachments/assets/8188320d-7888-4572-ae2b-c6207d02ecd7)

## Component Descriptions

- **LogStorageAnalyzerApplication**: Main class that coordinates the application flow
- **CommandLineOptions**: Stores user-specified options like source, file path, API endpoint, and date
- **CommandLineParser**: Validates and processes command line arguments (uses a bit of regex)
- **DataLoader**: Handles loading data from files or API endpoints
- **JsonParser**: Parses JSON data into Java objects (uses library javax.json)
- **LogInfoDTO**: Data Transfer Object that stores and calculates metrics for log collections
- **ResultsPrinter**: Formats and displays the analysis results

## Prerequisites

- Java 11 or higher (make sure java home variable is set correctly) - help at https://docs.oracle.com/cd/E19182-01/821-0917/inst_jdk_javahome_t/index.html
- Maven (for building and running the application)

Please note that maven is needed to setup the repository, and also that maven is needed to also first get needed dependencies (javax.json library parsing) and also run the program. 
Here is a look at the dependencies in the pom.xml file from maven. 
```
 <dependencies>
        <!-- Javax JSON API -->
        <dependency>
            <groupId>javax.json</groupId>
            <artifactId>javax.json-api</artifactId>
            <version>1.1.4</version>
        </dependency>

        <!-- Javax JSON Implementation (Glassfish) -->
        <dependency>
            <groupId>org.glassfish</groupId>
            <artifactId>javax.json</artifactId>
            <version>1.1.4</version>
        </dependency>
    </dependencies>
```

## Resources Used 

How Do I execute a file using maven? https://stackoverflow.com/questions/2472376/how-do-i-execute-a-program-using-maven

https://www.freecodecamp.org/news/regex-for-date-formats-what-is-the-regular-expression-for-matching-dates/

https://docs.oracle.com/javaee/7/api/javax/json/package-summary.html (Used for JSON library parsing)

https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html (Sending HTTP requests to a server)

## Setting up Maven

1. **Install Maven**:

Useful links: 
https://maven.apache.org/install.html
https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
https://phoenixnap.com/kb/install-maven-windows

   **For macOS**:
   ```bash
   # Install Homebrew if not already installed
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   
   # Install Maven
   brew install maven
   
   # Verify installation
   mvn -version
   ```

   **For Windows**:
   - Download the Maven zip file from [Maven's official site](https://maven.apache.org/download.cgi)
   - Extract it to a directory (e.g., `C:\Program Files\Maven`)
   - Add Maven's bin directory to your PATH environment variable
   - Verify installation by running `mvn -version` in Command Prompt
   ```
## Building and Running the Application

1. **Compile the project**:
   ```bash
   mvn compile
   ```

2. **Run the application**:
   
   **Using file input**:
   ```bash
   mvn exec:java -Dexec.mainClass="LogStorageAnalyzerApplication" -Dexec.args="--file src/main/resources/example-in.json"
   ```

   **Using API with specific date**:
   ```bash
   mvn exec:java -Dexec.mainClass="LogStorageAnalyzerApplication" -Dexec.args="--endpoint https://api-endpoint.example.com --year 2025 --month 04 --day 24"
   ```

   **Additional options**:
   - `--source <file|api>`: Use file for a json file input, and api for testing against the server.

## Input Format

The application expects JSON input in the following format:
```json
[
  {
    "index": "logs-2025-04-24-metrics",
    "pri.store.size": "12784825600",
    "pri": "1"
  },
  {
    "index": "logs-2025-04-24-errors",
    "pri.store.size": "44151900000",
    "pri": "13"
  },
  {
    "index": "logs-2025-04-24-web",
    "pri.store.size": "119403538870",
    "pri": "20"
  },
  {
    "index": "logs-2025-04-24-mobile",
    "pri.store.size": "506280870000",
    "pri": "1"
  },
  {
    "index": "logs-2025-04-24-events",
    "pri.store.size": "8194014",
    "pri": "1"
  },
  {
    "index": "logs-2025-04-24-backend",
    "pri.store.size": "689537000000",
    "pri": "1"
  },
  {
    "index": "logs-2025-04-24-auth",
    "pri.store.size": "1410107",
    "pri": "5"
  },
  {
    "index": "logs-2025-04-24-debug",
    "pri.store.size": "195229",
    "pri": "1"
  },
  {
    "index": "logs-2025-04-24-api",
    "pri.store.size": "537619000000",
    "pri": "7"
  },
  {
    "index": "logs-2025-04-24-production",
    "pri.store.size": "901261200000",
    "pri": "2"
  }
]
```

## Output Example

```
Printing largest indexes by storage size
Index: logs-2025-04-24-production
Size: 901.26 GB
Index: logs-2025-04-24-backend
Size: 689.54 GB
Index: logs-2025-04-24-api
Size: 537.62 GB
Index: logs-2025-04-24-mobile
Size: 506.28 GB
Index: logs-2025-04-24-web
Size: 119.40 GB

Printing largest indexes by shard count
Index: logs-2025-04-24-web
Shards: 20
Index: logs-2025-04-24-errors
Shards: 13
Index: logs-2025-04-24-api
Shards: 7
Index: logs-2025-04-24-auth
Shards: 5
Index: logs-2025-04-24-production
Shards: 2

Printing least balanced indexes
Index: logs-2025-04-24-backend
Size: 689.54 GB
Shards: 1
Balance Ratio: 689
Recommended shard count is 22
Index: logs-2025-04-24-mobile
Size: 506.28 GB
Shards: 1
Balance Ratio: 506
Recommended shard count is 16
Index: logs-2025-04-24-production
Size: 901.26 GB
Shards: 2
Balance Ratio: 450
Recommended shard count is 30
Index: logs-2025-04-24-api
Size: 537.62 GB
Shards: 7
Balance Ratio: 77
Recommended shard count is 17
Index: logs-2025-04-24-metrics
Size: 12.78 GB
Shards: 1
Balance Ratio: 12
Recommended shard count is 1
```
