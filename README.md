# Daniele Dellagiacoma 

## introsde-2016-assignment-2

This client calls the server: **https://github.com/ddellagiacoma/introsde-2016-assignment-2-server**

## IMPLEMENTATION

The client is divided in the following package and classes:

* requests
  * Main.java
  * R1.java
  * R2.java
  * R3.java
  * R4.java
  * R5.java
  * R6.java
  * R7.java
  * R8.java
  * R9.java
  
The main class called **Main** that calls all the other classes of the client.

Each of them send the requests specified in the delivery and print the responses into the files **client-server-json.log** and **client-server-xml.log**

Although the http request do not fail, it is possible that the body response is empty (i.e. not exist a person with such id in the database). In this case the response status of the request will be ERROR.

## DEPLOYMENT

The client can be tested copying the repository in local
```sh
git clone https://github.com/ddellagiacoma/introsde-2016-assignment-2-client
```

And execute it
```sh
ant execute.client
```
