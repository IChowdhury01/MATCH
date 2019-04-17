import static spark.Spark.*;

public class App {
    public static void main(String[] args) {

        get("/", (req, res)->"Successfully reached MATCH - Friend Matching Platform.\n");



        get("/user/:name", (req,res)->{
            return "This is the profile of "+ req.params(":name") + ".  Wow, such empty.";
        });


        post("/login",(req,res)-> {
            String name = req.queryParams("username");
            String pass = req.queryParams("password");
            return "Your username is " + name + " and your password is " + pass + ".";
        });


    }
}