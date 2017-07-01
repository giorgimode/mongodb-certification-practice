package excercise;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created by modeg on 6/4/2017.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        Spark.get("/", new Route(){

            public Object handle(Request request, Response response) throws Exception {
                return "Hello World from Spark Java";
            }
        });
    }
}
