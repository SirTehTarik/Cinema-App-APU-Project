# Cinema Booking System

A Java Swing desktop application for managing cinema movies, customer accounts, and ticket bookings. The app provides separate workflows for customers, clerks, and managers.

## Features

- Customer registration, login, profile, booking, and booking history
- Movie browsing, seat selection, checkout, and booking cancellation or modification
- Clerk tools for creating customer bookings
- Manager tools for managing movies and users, viewing bookings, and generating reports
- Local file-based storage for application data

## How the app works

The application opens at a home screen where a customer can register or an existing user can log in. After login, the system checks the account's role and opens the matching dashboard:

- **Customers** can browse available and upcoming movies, choose seats and complete a booking, review or manage their bookings, and update their account details.
- **Clerks** can create bookings for customers, modify or cancel bookings, view bookings, and check upcoming movies.
- **Managers** can add, update, and remove movies; manage user accounts; view bookings and revenue reports; and review login history.

Booking and account changes are saved locally as the user works. The app loads saved records when needed, so data is available again the next time it runs from the same working directory. Login history is recorded after a successful login.

## Requirements

- Java Development Kit (JDK) 8 or later

## Run

The Java source files use the `CinemaAPP` package. From the project directory, compile them into an output folder and launch the homepage:

```bash
mkdir out
javac -d out *.java
java -cp out CinemaAPP.Homepage
```

On Windows PowerShell, the same commands work in the project directory. If `out` already exists, skip `mkdir out`.

## Data files

The application reads and writes local text files in its working directory, including `Movies.txt` for movies, `users.txt` for accounts, `bookings.txt` for bookings, and `login_history.txt` for login records. Some files are created when the app first saves data. Keep these files if you want to retain the corresponding data; deleting them removes the data stored in them.

## Project structure

Java source files are in the project root and belong to the `CinemaAPP` package. `Homepage.java` contains the application entry point.
