# Daniele Dellagiacoma 

## introsde-2016-assignment-2

This client calls the server: **https://github.com/ddellagiacoma/introsde-2016-assignment-2-server**

## IMPLEMENTATION
The server has a main class called **Main** that calls all the other classes of the client.

The other classes of the client are **R1**, **R2**, **R3**, **R4**, **R5**, **R6**, **R7**, **R8** and **R9**.

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
