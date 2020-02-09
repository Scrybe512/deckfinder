package com.ibarr.deckfinder.adb;

import com.ibarr.deckfinder.data.Pack;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class ArkhamDB {

	private final String BASE_URL = "https://arkhamdb.com/api";
	private final String PACKS_URL = BASE_URL + "/public/packs";
	
	private HttpClient httpClient;
	private HttpResponse response;
	
	public ArkhamDB() {
		httpClient = HttpClients.createDefault();
	}
	
	public List<Pack> getPacks() {
		// create a list
		List<Pack> packList = new ArrayList<>();
		
		// get Packs JSON from ArkhamDB
		HttpGet get = new HttpGet(PACKS_URL);
		try {
			response = httpClient.execute(get);
		} catch (IOException ex) {
			Logger.getLogger(ArkhamDB.class.getName()).log(Level.WARNING, null, ex);
			return packList;	// return empty list
		}
		if(response != null && response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    // do something useful
                    try (javax.json.JsonReader jr = javax.json.Json.createReader(new java.io.InputStreamReader(instream, "UTF-8"))) {
                        if(jr != null) {
							JsonArray json = jr.readArray();    

							// for each pack object in the json
							json.stream().filter((j) -> (j.getValueType().equals(JsonValue.ValueType.OBJECT))).forEachOrdered((j) -> {
								// create a Pack
								// add it to the list
								packList.add(new Pack((JsonObject) j));
							});
                        }
                    }
                } catch (IOException | UnsupportedOperationException ex) {
                    Logger.getLogger(ArkhamDB.class.getName()).log(Level.WARNING, null, ex);
					return packList;	// return empty list, or list so far
                }
            }
		}
		
		// return the list
		return packList;
	}
	
}
