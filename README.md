# future-transaction-service

This service performs below functions : 
  * Reads Future Transactions input text & generates Daily summary report should be in CSV format
  * Generates text file on Total Transaction Amount of each unique product

## Getting Started
File paths are mentioned in application.properties, which may be updated if required.
Default entries are : 
   * Input file path
        future.transaction.input.file.path=D:/futuretransaction/Input.txt
   * Generated csv file path
        future.transaction.output.summary.file.path=D:/futuretransaction/summary.csv
   * Generated product transaction details file path
        future.transaction.output.product.file.path=D:/futuretransaction/product.txt
  

## To run application
The application can be run in 2 ways after placing input file in specified path:
  1) java -jar transaction-1.0-SNAPSHOT.jar
  
  2) Import the code to IDE (Intellij, Eclipse) as maven project.
  
## How to build software
jar file is attached which can be run in java environment.
To build, run :    mvn clean install

