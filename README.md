## Description

Simple app that contains some basic functionality. It connects to the server for login and fetch products, also the app provides create and save order to local database.

## Architecture Design

I used MVVM design patterns with Android Architecture Components.

- I have five packages are:

  - `commmon`: contains common view state class, extensions.
  
  - `di`: di package which contains all dependency injection concept.

  - `data`: The responsibility of this layer is to expose the data through the repository to the upper layer.
  
  - `domain`: This layer sits on top of the data and is responsible for coordinating the data and presentation layers.

  - `presentation`: The responsibility of this later is to building the objects the views are going to consume and processing the actions performed in this views.

## Tech Stack:

Kotlin - Programming language.

Retrofit 2 - OkHttp3 - request/response API.

RxJava 2 - reactive programming paradigm.

LiveData - use LiveData to see UI update with data changes.

Room - for local storage.

Coil - for image loading.

MockK - for testing.

## TODO
- [ ] add product detail screen
- [ ] add order detail screen
- [ ] write unit test for all ViewModel and local storage
