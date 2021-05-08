package no.hvl.dat110.ac.restservice;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.gson.Gson;

public class AccessLog {

	
	private AtomicInteger cid;
	protected ConcurrentHashMap<Integer, AccessEntry> log;

	public AccessLog() {
		this.log = new ConcurrentHashMap<Integer, AccessEntry>();
		cid = new AtomicInteger(0);
	}

	
	public int add(String message) {
		int id = cid.getAndIncrement();
		log.put(id, new AccessEntry(id, message));

		return id;
	}

	
	public AccessEntry get(int id) {

		AccessEntry entry = null;
		try {
			entry = log.get(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entry;

	}

	
	public void clear() {
		log.clear();
	}

	
	public String toJson() {

		Gson gson = new Gson();

		return gson.toJson(log);
	}
}
