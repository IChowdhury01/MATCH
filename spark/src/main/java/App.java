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
            if (name.equals("Ivan") && pass.equals("123van"))
                return "Correct! Your username is " + name + " and your password is " + pass + ".";
            else
                return "You entered the username " + name + " and the password " + pass + ". It's wrong. Try \"Ivan\" and \"123van\".";

        });

        post("/register",(req,res)-> {
            String name = req.queryParams("username");
            String pass = req.queryParams("password");
            String displayName = req.queryParams("displayName");
            String maxTravelDistance = req.queryParams("maxTravelDistance");
            String aboutMe = req.queryParams("aboutMe");
            return "Registration successful!";
        });



    }
}