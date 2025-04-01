# MovieApp

<p align="center">
  <img src="https://github.com/user-attachments/assets/31aec808-7e09-4f0e-8e0b-b3bde6c0d11f"/>
  <img src="https://github.com/user-attachments/assets/394ae60e-ad93-4a0d-a9d9-99ddc49e2389"/>
</p>

## Introduction
MovieApp is a mobile application developed for Android that allows users to visualize movie information.
The application is built following **Clean Architecture** principles, using **RxKotlin** for data stream and fragment management for modular and flexible navigation.

## Architecture
The application implements `Clean Architecture`, an approach that separates the business logic, data layer and user interface into well-defined modules:
- **Presentation Layer**: Implemented with Fragments, following the **MVP** pattern.
- **Domain Layer**: Defines the use cases and business logic using **SOLID** principles.
- **Data Layer**: Uses **Repositories** to access APIs and local databases, with **Retrofit** and **Room** as main tools.
- **Flow Management**: **RxJava** is used to handle asynchronous events and provide a seamless user experience.


Pattern desing

## Main Modules

- [ ] Movie search, `work in progress`.
- [x] Display details, trailers and descriptions.
- [x] Fragment Animation

## Main Features
- [x] Movie display and scrolling. `work in progress`
- [x] Details/description display . `working on updating it`
- [x] Fragment Animation


## Contributions

Contributions are welcome. To do so:
1. perform a fork of the repository.
2. Create a new branch (feature/new-functionality).
3. Send a Pull Request explaining the changes made.

### Collaborators 
- 1 :  [joelglobant](https://github.com/joelglobant)




![gid](https://lh6.googleusercontent.com/U2WKp4jn8iQWfzwq5vXE9kLZu_JC5LRfxl8QDYANPHfYhBQCOK9v4D7UIIbTIZNC4VURD_csWn2C0oUn_hLvgjPtQMy9AsrRbYCO0Q6cj8tPu75VvNtbQEeYTfqOZ9qtQTzUx_-9AppUkGNCnxpdmQ)
