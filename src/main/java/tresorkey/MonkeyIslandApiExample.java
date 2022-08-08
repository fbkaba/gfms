package tresorkey;


import java.io.File;
import java.io.IOException;
import java.util.*;


import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SpringBootApplication
public class MonkeyIslandApiExample {

    public static void main(String[] args) {
    	
    	SpringApplication.run(MonkeyIslandApiExample.class, args);
    	
    	Logger log = LoggerFactory.getLogger(MonkeyIslandApiExample.class);
    	
    	Response response = null;
    	OkHttpClient client = new OkHttpClient().newBuilder().build();
    			MediaType mediaType = MediaType.parse("text/plain");
    			RequestBody body = RequestBody.create(mediaType, "");
    			Request request = new Request.Builder()
    			  .url("https://gfms-sandbox-monkeyisland.azurewebsites.net/key0729")
    			  .method("GET", null)
    			  .addHeader("Accept", "application/json")
    			  .build();
    			try {
					response = client.newCall(request).execute();
					String res = response.body().string();
					log.info("Resultat : " + res);
					JSONObject jobject = new JSONObject(res); 
					JSONArray jArray = jobject.getJSONArray("magicNumbers");
					log.info("jArray : " + jArray);
					int sumMagicNumbers =0;
					 for (int i = 0; i < jArray.length(); i++) {
						 log.info("jArray : " + jArray.getInt(i));
						 sumMagicNumbers += jArray.getInt(i);
					 }
					 
					 log.info("sumMagicNumbers : " + sumMagicNumbers);
					
				} catch (IOException e) {
					
					log.error(e.getMessage());
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} 	
    	
    
    }
}