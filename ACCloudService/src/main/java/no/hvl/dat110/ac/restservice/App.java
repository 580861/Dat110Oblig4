package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import com.google.gson.Gson;

import static spark.Spark.put;;

/**
 * Hello world!
 *
 */
public class App {

	static AccessLog accesslog = null;
	static AccessCode accesscode = null;

	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service

		accesslog = new AccessLog();
		accesscode = new AccessCode();

		after((req, res) -> {
			res.type("application/json");
		});

		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson("IoT Access Control Device");
		});

		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description
		
		// Post , record an access entry in the log
		post("/accessdevice/log/", (req, res) -> {
			Gson gson = new Gson();
			AccessMessage msg = gson.fromJson(req.body(), AccessMessage.class);
			int id = accesslog.add(msg.getMessage());

			return gson.toJson(accesslog.get(id));
		});

		// get
		// retrieve the access log
		get("/accessdevice/log/", (req, res) -> accesslog.toJson());
		//retrive a specific entry in the access log
		get("/accessdevice/log/:id", (req, res) -> {
			Gson gson = new Gson();
			int id = Integer.parseInt(req.params(":id"));

			return gson.toJson(accesslog.get(id));
		});

		// put 
		// updates the access code
		put("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();
			AccessCode code = gson.fromJson(req.body(), AccessCode.class);
			accesscode.setAccesscode(code.getAccesscode());

			return gson.toJson(accesscode);
		});
		//  return the current access code stored in the server
		get("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});
		
		// delete
		//delete all log entries
		delete("/accessdevice/log/", (req, res) -> {
			accesslog.clear();

			return accesslog.toJson();
		});

	}

}
