package tresorkey;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
@RequestMapping("gfms/key")
public class WebhookController {

	static Logger log = LoggerFactory.getLogger(WebhookController.class);

	@GetMapping("kaba")
	public String getKey() {

		Logger log = LoggerFactory.getLogger(MonkeyIslandApiExample.class);
		
		int sumMagicNumbers = 0;
		Response response = null;
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url("https://gfms-sandbox-monkeyisland.azurewebsites.net/key0729")
				.method("GET", null).addHeader("Accept", "application/json").build();
		try {
			response = client.newCall(request).execute();
			String res = response.body().string();
			log.info("Resultat : " + res);
			JSONObject jobject = new JSONObject(res);
			JSONArray jArray = jobject.getJSONArray("magicNumbers");
			log.info("jArray : " + jArray);

			for (int i = 0; i < jArray.length(); i++) {
				log.info("jArray : " + jArray.getInt(i));
				sumMagicNumbers += jArray.getInt(i);
			}

			log.info("sumMagicNumbers : " + sumMagicNumbers);

		} catch (IOException e) {

			log.error(e.getMessage());
		}

		postDataForKey(sumMagicNumbers);

		return "Got Magic Numbers!";
	}

	/*
	 * @PostMapping() public String keyContentWebhook() { return
	 * " key content from Partner api!"; }
	 */

	@PostMapping()
	String keyContentWebhook(HttpServletRequest httpServletRequest) {
		String content = getPostData(httpServletRequest);
		log.info("content  " + content);
		return content;
	}

	public static String getPostData(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = req.getReader();
			reader.mark(10000);

			String line;
			do {
				line = reader.readLine();
				sb.append(line).append("\n");
			} while (line != null);
			reader.reset();
		} catch (IOException e) {
			log.warn("getPostData couldn't.. get the post data", e); 
		}

		return sb.toString();
	}

	public static void postDataForKey(int sum) {

		Response response = null;
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/json");
		@SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(mediaType,
				"{\n  \"key\": " + sum + ",\n  \"callBackUrl\": \"http://clbck.pariskryptokloud.com/gfms/key\"\n}");
		Request request = new Request.Builder().url("https://gfms-sandbox-monkeyisland.azurewebsites.net/key0729")
				.method("POST", body).addHeader("Content-Type", "application/json").addHeader("Accept", "text/plain")
				.build();
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		try {
			log.info("reponse recue " + response.body().string());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

}