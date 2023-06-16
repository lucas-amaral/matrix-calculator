# Description
It is an application to compute some operations for a matrix, given an uploaded csv file.

## Operations
 - Given
 - Invert
 - Flatten
 - Sum
 - Multiply
  
## Pre-requirements
The input file to these functions is a matrix, of any dimension where the number of rows are equal to the number of columns (square). Each value is an integer, and there is no header row.  

## How to run
As it is a spring-boot based application, to start the service we can run:

```mvn spring-boot:run```

By default, the server will start running on port 8080.

## Extra configuration
By default, the maximum upload size is 1048576 bytes.
If you need add files with more than it, it's necessary add those extra configuration on `applicatrion.properties` file:

```java
spring.servlet.multipart.max-file-size=<new_value>MB
spring.servlet.multipart.max-request-size=<new_value>MB
```
For more information, please check https://spring.io/guides/gs/uploading-files.

## Rest APIs

> NOTE:
> *   **IMPORTANT:** All APIs expect receive a csv file as parameter
> *   E.g. `curl -X POST 'localhost:8080/sum' -F 'file=@"path/matrix.csv"`

Given an uploaded csv file:
```
1,2,3
4,5,6
7,8,9
```

### 1. Echo
- #### Http method `POST`
- #### Path `/echo`
- #### Description `Return the matrix as a string in matrix format.`
- #### Send request `curl -X POST 'localhost:8080/echo' -F 'file=@"/path/matrix.csv"'`
- #### Response body
    ```
    1,2,3
    4,5,6
    7,8,9
    ```

### 2. Invert
- #### Http method `POST`
- #### Path `/invert`
- #### Description `Return the matrix as a string in matrix format where the columns and rows are inverted`
- #### Send request `curl -X POST 'localhost:8080/invert' -F 'file=@"/path/matrix.csv"'`
- #### Response body
    ```
    1,4,7
    2,5,8
    3,6,9
    ```

### 3. Flatten
- #### Http method `POST`
- #### Path `/flatten`
- #### Description `Return the matrix as a 1 line string, with values separated by commas.`
- #### Send request `curl -X POST 'localhost:8080/flatten' -F 'file=@"/path/matrix.csv"'`
- #### Response body
    ```
    1,2,3,4,5,6,7,8,9
    ``` 
### 4. Sum
- #### Http method `POST`
- #### Path `/sum`
- #### Description `Return the sum of the integers in the matrix`
- #### Send request `curl -X POST 'localhost:8080/sum' -F 'file=@"/path/matrix.csv"'`
- #### Response body
    ```
    45
    ``` 
### 5. Multiply
- #### Http method `POST`
- #### Path `/multiply`
- #### Description `Return the product of the integers in the matrix`
- #### Send request `curl -X POST 'localhost:8080/multiply' -F 'file=@"/path/matrix.csv"'`
- #### Response body
    ```
  362880
    ``` 
### APIs Exceptions

| Reason                            | Http response status | Response message                                                            |
|-----------------------------------|:--------------------:|---------------------------------------------------------------------------------|
| Matrix is not square              |        `422`         | Invalid Matrix. Please use a file that contains a square matrix                 |
| Matrix contains non integer value |        `422`         | Invalid Matrix. Please use a file that contains a matrix just with integer values |
| Maximum upload size exceeded      |        `500`         | The field file exceeds its maximum permitted size of 1048576 bytes              |
 | Unsupported csv delimiter         |    `422`             |  Csv file delimiter not accepted. Valid formats: (',', ';', '\t', ' ')|
 | Unsupported file extension        |    `422`             |  File extension not supported. Please use csv file |


### Run tests
Run the command: `mvn clean test`

