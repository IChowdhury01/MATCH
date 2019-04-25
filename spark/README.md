1. Make sure to change the sql credentials in MatchJDBC to match your own.

2. After building and running, go to http://localhost:8080/ 

3. Since you haven't logged in, it redirects you to the sign in page.

4. You can make a new account or you can just sign in as one of the test users. e.g. "test1", pass: "jamessmith"

5. This will take you back to the root directory, but now that you have a valid cookie the server will recognize you're signed in and redirect to your list of freinds.

6. When you log out, the cookie will be removed from the server's list and it will no longer recognize you.

7. Front end is implemented throughout (including a few pages that interpret jsons from the server), but it's all text-based and unpolished.

8. The main things that still need to be implemented are: image uploads, a prettier Front End, unittests, and the VM.