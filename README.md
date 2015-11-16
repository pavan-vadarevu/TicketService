# TicketService

Ticket Service has been implemented using

1. Spring Boot - so that application can be demonstrated by making use of in-memory database such as H2 DB and using apache tomcat server just by running mvn spring-boot:run command. Also this Service is an example for implementing Micro Services using Spring Boot.

2. Spring JPA - For all backend transactions

3. Building Restful APIs using Spring (These Restful APIs can also be built using Jersey framework, Spring boot supports Jersey as well)

# Functionality Implemented:

1. GET API to retrieve number of seats available (total number of seats available as well as seats available at each level)

2. POST API to hold seats

3. POST API to reserve seats

4. Asynchronous task to cancel seats that are held, if reservation is not done within 60 seconds after holding the seats. This is implemented using Spring Aysnc annotations (This can also be implemented using Java Future Tasks)

# Instructions to Build and Test Application:

Please make sure you are using Java 1.7 or 1.8

After importing code to local desktop/laptop, run the below commands

1. mvn clean install

2. mvn spring-boot:run

# Get Available Seats:

1. Open Browser and enter the below URL to see the total number of seats available

http://localhost:8080/retail/apis/ticketservice

6250

2. Open Browser and enter the below URL to get the number of seats available per level

http://localhost:8080/retail/apis/?venuelevel=4

1500

# Hold Seats:

1. Use the SOAP-UI project in soap-ui folder of this repository to send HTTP POST request for holding seats

   URL: http://localhost:8080/retail/apis/ticketservice/holdseats
   
   Sample Request: 
   {
      "numSeats": 10,
      "minLevel": "2",
      "maxLevel": "4",
      "customerEmail": "pavan.vadarevu@justyellow.com"
   }

   Sample Response:
   
   {"seatHoldStatus": [
      {
      "seatHoldId": 921994,
      "seatNumber": "A1",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A2",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A3",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A4",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A5",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A6",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A7",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A8",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A9",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   },
      {
      "seatHoldId": 921994,
      "seatNumber": "A10",
      "seatLevel": 2,
      "customerEmail": "pavan.vadarevu@justyellow.com"
   }
]}

# Expire Hold Tickets:

1. When a request is sent to hold tickets, that will also trigger Asynchronous task to see if the seats which are held were not reserved even after 60 seconds, if so those seats will get expired and will be marked as available.

2. You can look at the console and notice the same

   Async task to cancel seats which are held more than 60 seconds - started
   
   Async task to cancel seats which are held more than 60 seconds - ended
   
   Number of seats expired/cancelled = 10

# Reserve Seats:

1. Use the SOAP-UI project in soap-ui folder of this repository to send HTTP POST request for reserving seats

   URL: http://localhost:8080/retail/apis/ticketservice/reserveseats

   Sample Request:
   
   {
     "seatHoldId": 921994,
     "customerEmail": "pavan.vadarevu@justyellow.com"
   }

   Sample Response:
   
   CONF921994
   
# Exception Handling

You can check the below sample error scenarios

1. Request:

   GET http://localhost:8080/retail/apis/ticketservice?venuelevel=10
   
   Response:
   
   HTTP/1.1 400 Bad Request
   
   {"errorId":"200001","errorText":"REQUEST_VALIDATION_ERROR","errorDetail":"VenueLevel should be between 1 and 4"}

2. Request:

   POST http://localhost:8080/retail/apis/ticketservice/reserveseats
   
   Response:
   
   HTTP/1.1 400 Bad Request
   
   {
   "errorId": "200001",
   "errorText": "REQUEST_VALIDATION_ERROR",
   "errorDetail": "Missing Mandatory Input Parameter: seatHoldId and customerEmail are mandatory for reserving seats"
   }

3. Request:

   POST http://localhost:8080/retail/apis/ticketservice/holdseats
   
   Response:
   
   HTTP/1.1 400 Bad Request
   
   {
   "errorId": "200001",
   "errorText": "REQUEST_VALIDATION_ERROR",
   "errorDetail": "Requested seats can not be more than the available seats"
   }

4. Request1:

   POST http://localhost:8080/retail/apis/ticketservice/holdseats
   
   {
    "numSeats": 100,
    "customerEmail": "pavan.vadarevu@justyellow.com"
   }
   
   Response1:
   
   HTTP/1.1 200 OK
   
   {"seatHoldStatus":[{"seatHoldId":835052,"seatNumber":"A1","seatLevel":4,"customerEmail":"pavan.vadarevu@justyellow.com"},..,..]}

Async task to cancel seats which are held more than 60 seconds - started

Async task to cancel seats which are held more than 60 seconds - ended

Number of seats expired/cancelled = 100

   Request2:
   
   POST http://localhost:8080/retail/apis/ticketservice/reserveseats
   
   {
   "seatHoldId": 835052,
   "customerEmail": "pavan.vadarevu@justyellow.com"
   }
   
   HTTP/1.1 500 Internal Server Error
   
   {"errorId":"200011","errorText":"RESERVATION_FULFILLMENT_ERROR","errorDetail":"Seats are not held or could have been expired, please try again"}
   
