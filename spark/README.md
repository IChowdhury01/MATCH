#Local Instructions

1. Make sure to change the sql credentials in MatchJDBC to match your own.

2. After building and running, go to https://localhost:8080/

#Server Instructions

1. ssh into the server: `ssh -p5122 cooper@199.98.27.118`
2. kill current java processes: `killall -9 java`
3. exit: `exit`
4. navigate to local /target folder
5. scp jar to server: `scp -P5122 MATCH-services-1.0-SNAPSHOT.jar cooper@199.98.27.118:~`
6. ssh into server `ssh -p5122 cooper@199.98.27.118`
7. run as background process `nohup java -jar MATCH-services-1.0-SNAPSHOT.jar &`
8. exit: `exit`
9. Go to https://199.98.27.118:8080/ in browser and click through any warnings

#Running

1. Since you haven't logged in, it redirects you to the sign in page.

2. You can make a new account or you can just sign in as one of the test users. e.g. "test1", pass: "jamessmith"

3. This will take you back to the root directory, but now that you have a valid cookie the server will recognize you're signed in and redirect to your list of freinds.

4. When you log out, the cookie will be removed from the server's list and it will no longer recognize you.

5. Front end is implemented throughout (including a few pages that interpret jsons from the server), but it's all text-based and unpolished.

6. The main things that still need to be implemented are: image uploads, a prettier Front End, and unittests
