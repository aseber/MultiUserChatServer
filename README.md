# Multi User Chat Server

This program functons as a server for my socket based chat server-client interface. Clients can connect to this server and communicate.

**In order to compile this program, you will need to include the libraries in the /libs/ folder**

## Starting the server

In order for people to connect from the outside world, you need to forward port 25566, if you do know how to do this, you can view this guide:
http://portforward.com/english/routers/port_forwarding/

Without port forwarding, you can still connect to the server through localhost or 127.0.0.1

## The server view

* The left contains an interface of all the people currently connected to the server
* The center window shows all of the messages being transmitted through the server
* The right window contains log information including attempted connections
* The bottom textbox allows the concole to talk to other people in the server if necessary

## Permission system

* Console - The host console, this user is not shown on any client
* Administrator - The highest level that user can achieve on the permission hierarchy. Has the ability to disconnect all lower users
* Moderator - The second highest level that a user can achieve. Has the ability to remove all lower users
* User - A standard user, can chat but is not allowed to remove anybody from the server

## User information
User information regarding usernames and passwords are saved to a folder /Saved User Data/ that contains all usernames and passwords as unencrypted .dat files
I understand that they should, be encrypted, but that was not the point of this program!

## Please note that this project is not completed
I did not finish this project and there are many things that are only partially implemented.
* Bookmarks do not work
* left clicking users and selecting their profile does nothing