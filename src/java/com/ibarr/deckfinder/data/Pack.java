package com.ibarr.deckfinder.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javax.json.JsonObject;

public class Pack {
	
	private final String name;
	private final String code;
	private final int position;
	private final int cyclePosition;
	private Optional<Date> available;
	private final int known;
	private final int total;
	private final String url;
	private final int id;

	public Pack(JsonObject json) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		name = json.getString("name", "");
		code = json.getString("code", "");
		position = json.getInt("position", 0);
		cyclePosition = json.getInt("cycle_position", 0);
		try {
			available = Optional.of(sdf.parse(json.getString("available", "")));
		} catch (ParseException ex) {
			available = Optional.empty();
		}
		known = json.getInt("known", 0);
		total = json.getInt("total", 0);
		url = json.getString("url", "");
		id = json.getInt("id", 0);
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public int getPosition() {
		return position;
	}

	public int getCyclePosition() {
		return cyclePosition;
	}

	public Optional<Date> getAvailable() {
		return available;
	}

	public int getKnown() {
		return known;
	}

	public int getTotal() {
		return total;
	}

	public String getUrl() {
		return url;
	}

	public int getId() {
		return id;
	}
	
}
