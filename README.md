# Daniele Dellagiacoma 

## introsde-2016-assignment-2

I worked in pair with: **Davide Lissoni**

The URL of my server is: **https://dellagiacomaintrosde2.herokuapp.com/sdelab**

Server URL of Davide Lissoni is: **https://lissonidavideapp.herokuapp.com/assignment2**

Link to Davide Lissoni's Github repository: **https://github.com/DavideLissoni/introsde-2016-assignment-2**

## CLIENT
The server has a main class called **Main** that calls all the other classes of the client.

The other classes of the client are **R1**, **R2**, **R3**, **R4**, **R5**, **R6**, **R7**, **R8** and **R9**.

Each of them send the requests specified in the delivery and print the responses into the files **client-server-json.log** and **client-server-xml.log**

Although the http request do not fail, it is possible that the body response is empty (i.e. not exist a person with such id in the database). In this case the response status of the request will be ERROR.

## SERVER
The project seen during the laboratory session 7 has been used as starting point for the implementation of server.

The server includes the **App** and **MyApplicationConfig** classes in order to load resource classes and additional features provided by Jersey.

The **LifeCoachDao** class is used to connect the project model to the database, whereas **HealthMisureHistory**, **HealthProfile** **MeasureDefinition** and **Person** classes are the entities.

Finally, **MeasureResource**, **PersonCorrelationResource** and **PersonResource** classes expose the services through RESTful API.

Moreover, the **lifecoach.sqlite** database has been modified to fit the requests of the assignment.

## DEPLOYMENT

The server WAR file was deployed to Heroku including the database in this way
```sh
heroku war:deploy IntroSdeAss2.war --includes lifecoach.sqlite --app dellagiacomaintrosde2
```

On the other hand, the client can be tested copying the repository in local
```sh
git clone https://github.com/DanieleDellagiacoma/introsde-2016-assignment-2
```

And execute it
```sh
ant execute.client
```
