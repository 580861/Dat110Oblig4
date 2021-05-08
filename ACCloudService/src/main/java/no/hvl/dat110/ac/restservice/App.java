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

	

		accesslog = new AccessLog();
		accesscode = new AccessCode();

		after((req, res) -> {
			res.type("application/json");
		});

		
		get("/accessdevice/hello", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson("IoT Access Control Device");
		});

		
		post("/accessdevice/log/", (req, res) -> {
			Gson gson = new Gson();
			AccessMessage msg = gson.fromJson(req.body(), AccessMessage.class);
			int id = accesslog.add(msg.getMessage());

			return gson.toJson(accesslog.get(id));
		});

		
		get("/accessdevice/log/", (req, res) -> accesslog.toJson());
		
		get("/accessdevice/log/:id", (req, res) -> {
			Gson gson = new Gson();
			int id = Integer.parseInt(req.params(":id"));

			return gson.toJson(accesslog.get(id));
		});

		
		put("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();
			AccessCode code = gson.fromJson(req.body(), AccessCode.class);
			accesscode.setAccesscode(code.getAccesscode());

			return gson.toJson(accesscode);
		});
		
		get("/accessdevice/code", (req, res) -> {
			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});
		
		
		delete("/accessdevice/log/", (req, res) -> {
			accesslog.clear();

			return accesslog.toJson();
		});

	}

}
