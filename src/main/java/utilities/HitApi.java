package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

public class HitApi {
	public static HttpURLConnection http;
	static BufferedReader reader;
	static JSONObject jobj;
	static JSONArray arr;
	static String line;
	/*public static JSONObject hitAPIPost(String url, String Request) throws ClientProtocolException, IOException {
		
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		StringEntity params = new StringEntity(Request);
		request.addHeader("content-type", "application/json");
		request.setEntity(params);
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity= response.getEntity();
		if(entity!=null) {
			java.io.InputStream instream = entity.getContent();
			String Result=convertStreamToString(instream);
			System.out.println();
			instream.close();
			jobj= new JSONObject(Result);
		}
		
		
		return jobj;
	}*/
	
	public static JSONObject hitAPIPost(String RequestUrl) throws IOException {
		
		URL url = new URL(RequestUrl);
	    http=(HttpURLConnection)url.openConnection();
		//URLConnection conn = url.openConnection();
	    http.setDoOutput(true);
	    http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	    //http.setRequestProperty("Content-Type", "application/json");
	    OutputStreamWriter writer = new OutputStreamWriter(http.getOutputStream());
	    writer.flush();	    	
	    if((RequestUrl.contains("ADMOB"))&&(http.getResponseCode()==401)) {
	    	line="{status : true}";
	     }
	    else if(RequestUrl.contains("Unity")&&(http.getResponseCode()==401))
	    	line="{status : true}";
	    else {
	    reader = new BufferedReader(new InputStreamReader(http.getInputStream(),Charset.forName("UTF-8")));
	    line=readAll(reader);
	    }
	    //================================
	    //This is for APIs that don't return anything
	    if(line.length()==0&&(http.getResponseCode()==200))
	    	line="{status : true}";
	    //In case the response is a JSON Array, this code will convert it into a JSONObject with key arrayName
	    else if(line.charAt(0)=='[') {
	    	jobj=new JSONObject();
	    	arr=new JSONArray(line);
	    	jobj.put("arrayName", arr);
	    	return jobj;
	    }
	   
	    //================================
	    jobj=new JSONObject(line);
	    reader.close();
	    
	    return jobj;
	    
	}
	
	/*private static String convertStreamToString(java.io.InputStream is) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while((line=br.readLine())!=null) {
				sb.append(line + "\n");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}*/
	
	public static JSONObject hitGetAPI(String url) throws MalformedURLException, IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}finally {
			is.close();
		}
	}
	private static String readAll(Reader rd)throws IOException{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp=rd.read())!=-1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
}
