# NetChat app

Java console app that allow to communicate over LAN using TCP and multithreading.

## Description

Write a program that will allow to communicate on LAN. Here's an example how it should work:

Server:

```
$ java NetChat server 9000
* What's your name: Bob

* Waiting for a client on port 9000...
* Client connected! Chat starts...

* Type your message: 
Hello!
you> Hello!
How are You?
you> How are You?
Alice> Yo bro. GR8. Wsup?
* Client has disconnected!
* Waiting for a client on port 9000...
```

Client:

```
$ java NetChat client localhost 9000
* What's your name: Alice

* Connected to localhost:9000!
* Type your message:
Bob> Hello!
Bob> How are you?
Yo bro. GR8. Wsup?
.quit!
* Bye bye!
$ 
```

## Requirements

- [x] Command signature should look in a following way: java NetChat mode [address] port
- [x] Application should have to modes: client, server.
- [x] Application should allow bi-directional communication between server and client.
- [x] Application should use TCP protocol.
- [x] Communication should be done through exchanging Message objects not Strings.
- [x] Message class has to be left unchanged. (You may temporary change it for debugging purposes).
- [x] Application should use serialization
- [x] When a client disconnects from a server, server should wait for the next client. 
- [x] When a server disconnects, client should be turned off gracefully.

## More info

Project made for [Codecool](https://codecool.com/) programming course.
