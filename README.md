# Vestasim

## What is Vestsim?

Vestasim is a Java application that simulates a Flagship black Vestaboard. It has two components:

* a GUI window replicating the view of the physical Vestaboard
* a server which listens to API calls to update the data being displayed on the board

This project is in no way endorsed by Vestaboard. Users of it do so at their own risk.

## Who is Vestasim for?

Vestasim is primarily aimed at users developing their own Vestaboard integrations using Vestaboard's
Local API. It provides a local development environment without the need to connect to a physical board,
simplifying development and debugging.

## Prereqs

You will need the following to build and run Vestasim:

* a JRE v21 or later
* Maven

## How do I build it?

Clone the repository and run `mvn clean install`.

## How do I run it?

Run `java -jar target\vestasim.jar`.

## How do I use it?

Once running, the application reponds to HTTP `POST`s to `<machine-name>:7000/local-api/message`.

Details of the Vestaboard Local API can be found [here](https://docs-v1.vestaboard.com/local). Note that Vestasim only implements the single `POST` endpoint that sets
the message the board is displaying.

An HTTP header of `X-Vestaboard-Local-Api-Key` is expected, but its value is in
no way validated. Omitting it will result in a `401 Unauthorized` response code.

If the payload is not of the correct format you will get a `400 Bad Request` response code.

# Version History

* 1 - 19 November 2025 - Initial version