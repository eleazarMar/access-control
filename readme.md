# access-control
"it really kept my fingers on my hands!" - anonymous happy user

## What is it?
The hackspace houses dangerous machinery which should only be activated by authorised people (e.g. they have training, and have paid for membership).  The current power switches will be replaced by an ESP32-controlled relay, which will interrogate a user's RFID card to decide whether to activate.

This service will provide a simple http api to query a database to decide whether a presented card will result in a successful activation.  It will also record a very simple audit of who activates what, and for how long.


## What isn't it?
Anything else.  There is no frontend, membership control, training record, payment system etc.
There may be future membership management systems, and it is expected they will eventually take over the responsibilities of this system, with their own database

## Developing and Building
The system is written in Kotlin, a JVM-based language chosen by the developer for familiarity.  All building and testing is performed in docker, so should be machine-agnostic

run `make build` in your system of choice to compile and package the system