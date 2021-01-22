# MATCH: Friend-Matching Platform

Team: Ivan Chowdhury, Min JO, Sara Huang, Hanoch Goldfarb


## Description
MATCH is a web app that allows users to find friends with similar interests in their area. MATCH's friend-matching algorithm utilizes user geolocation data, hobbies, and other personal preferences to optimize results. The app also features PBKDF2 encryption, cookies, and account management.



## Tech Stack
Web Server: Spark (embedded Jetty web server)

Server: Spark, JDBC

Database: MySQL



## Getting Started

### Prerequisites
- [Java JDK 8 or above](https://www.oracle.com/java/technologies/javase-downloads.html)
- [MySQL](https://www.mysql.com/downloads/)
- [Apache Maven](http://maven.apache.org/download.cgi)

### Running Locally

1. Start MySQL and run a server.
2. Open `pom.xml` in your editor of choice.
3. Edit the credentials in the beginning of `MatchJDBC.java` to match your mysql credentials
4. Run `MatchApp.java`
5. Navigate to `https://localhost:8080` in your browser. If this doesn't work, try https://199.98.27.118:8080/


## Testing
### Integration Tests

To do an integration test, run the bash script `test`. This will try creating a new user and checking that their profile page returns the expected results.

## Instructions

- **Creating a new user.** From the homepage select `Register a new account.`. Fill out all the fields, click `Get Location`, and then click `Register`.

- **Logging in** From the homepage select `Log in`. Enter out the username and password and hit `Log in`. Note that registering an ccount will automatically log you in.

- **Viewing your friend list** After logging in you'll see you list of friends.

- **Viewing a friend's profile** Clicking on a name in your list of friends will show you their profile.

- **Viewing your own profile** Clicking on `View profile` in the top left corner will show you your own profile.

- **Uploading a photo** After logging in, click `choose file`, select a file, and then click `Upload Photo`. The picture will be visible on your profile page.

- **Rejecting a match** When a viewing a friend's profile, select `Reject`. They will no longer appear on your list of friends and you will no longer appear on theirs.

- **Messaging a friend** When a viewing a friend's profile, type in the textbox and click `Send Message`. Previously sent messages will also be shown on the page.

Happy matching!
![logo](https://cdn.pixabay.com/photo/2017/09/02/04/35/fire-2706299_960_720.jpg)
