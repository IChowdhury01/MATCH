# MATCH

MATCH is a friend-matching platform that uses user geolocation data, personal hobbies, and various other preferences to match users with new friends near their area.

## Team Members:
Ivan Chowdhury

Min JO

Sara Huang

Hanoch Goldfarb

## Tech Stack
Frontend: HTML/CSS/Javascript

Web Server: Spark 

Server: Java, Spark, JDBC

Database: MySQL

![logo](https://cdn.pixabay.com/photo/2017/09/02/04/35/fire-2706299_960_720.jpg)


## Instuctions to set up locally

1. Install and start mysql
2. open up the project in IntelliJ with the `pom.xml` file
3. Edit the credentials in the beginning of `MatchJDBC.java` to match your mysql credentials
4. Right click on `MatchApp.java` and select `Run 'MatchApp.main()'`
5. Navigate to `https://localhost:8080` in your browser

## Instructions to use the app

This should all be pretty intuitive, but in case you can't figure it out here are some detailed instructions.

**Creating a new user.** From the homepage select `Register a new account.`. Fill out all the fields, click `Get Location`, and then click `Register`.

**Logging in** From the homepage select `Log in`. Enter out the username and password and hit `Log in`. Note that registering an ccount will automatically log you in.

**Viewing your freind list** After logging in you'll see you list of freinds. (Unless you don't have any 😢.)

**Viewing a freind's profile** Clicking on a name in your list of freinds will show you their profile.

**Viewing your own profile** Clicking on `View profile` in the top left corner will show you your own profile.

**Uploading a photo** After logging in, click `choose file`, select a file, and then click `Upload Photo`. The picture will be visible on your profile page.

**Rejecting a match** When a viewing a freind's profile, select `Reject`. They will no longer appear on your list of freinds and you will no longer appear on theirs.

**Messaging a friend** When a viewing a freind's profile, type in the textbox and click `Send Message`. Previously sent messages will also be shown on the page.
