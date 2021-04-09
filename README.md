## Account Statement Service

**Overview**

Account Statement Service is RESTFull API which can be consumed for getting Account Statement information.

**Features**

1. Authenticate and authorize users.
2. REST API to get statement based on account id.
3. Supports filter based download on parameters like Statement Date and Account.

**Technology Stack**

1. Java 8
2. Spring/ Spring Boot
3. JWT Token For Authentication
4. HSQL in-memory database

**Code Setup Steps**

1. git clone https://github.com/amansabir/account-statement-server.git

2. cd account-statement-server

3. execute "mvn clean compile" in terminal
   ![clean-compile](/images/clean-compile.png)

4. execute "mvn test" in terminal
   ![clean-compile](/images/test.png)

**Docker Images Generation Steps**

*Generate Image and Run Container*

1. docker build -f DockerFile -t account-statement-service:0.0.1 .
2. docker run -p 8080:8080 account-statement-service:0.0.1

*Pull Image from Docker Hub and Run Container*

1. docker pull docker.io/amansabir/account-statement-service:0.0.1
2. docker run -p 8080:8080 amansabir/account-statement-service:0.0.1

**Sample Rest Uri**

*Sign In*

http://localhost:8080/users/signin

*Date Based Search*

http://localhost:8080/api/account/1201/statement?fromDate=2021-01-01&toDate=2021-02-01

*Date And Amount Based Search*

http://localhost:8080/api/account/1201/statement?fromDate=2021-01-01&toDate=2021-02-01&toAmount=35000&fromAmount=20000

**Sample Data**

*data.sql

**Test Evidence**

Test Evidence.pdf







 

