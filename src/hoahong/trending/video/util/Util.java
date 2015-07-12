package hoahong.trending.video.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class Util {
	
	public static final String TAG = Util.class.getSimpleName();
	

	public static String validateThumbnailURL(String thumbnail) {
		return thumbnail.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
	}
	
	/**
     * Generate the multi-part post body providing the parameters and boundary
     * string
     * 
     * @param parameters the parameters need to be posted
     * @param boundary the random string as boundary
     * @return a string of the post body
     */
    public static String encodePostBody(Bundle parameters, String boundary) {
        if (parameters == null) return "";
        StringBuilder sb = new StringBuilder();

        for (String key : parameters.keySet()) {
            Object parameter = parameters.get(key);
            if (!(parameter instanceof String)) {
                continue;
            }

            sb.append("Content-Disposition: form-data; name=\"" + key +
                    "\"\r\n\r\n" + (String)parameter);
            sb.append("\r\n" + "--" + boundary + "\r\n");
        }

        return sb.toString();
    }

    public static String encodeUrl(Bundle parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : parameters.keySet()) {
            Object parameter = parameters.get(key);
            if (!(parameter instanceof String)) {
                continue;
            }

            if (first) first = false; else sb.append("&");
            sb.append(URLEncoder.encode(key) + "=" +
                      URLEncoder.encode(parameters.getString(key)));
        }
        return sb.toString();
    }

    public static Bundle decodeUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String array[] = s.split("&");
            for (String parameter : array) {
                String v[] = parameter.split("=");
                if (v.length == 2) {
//                	Logger.e(TAG, v[0] + " - " + v[1]);
                    params.putString(URLDecoder.decode(v[0]),
                                     URLDecoder.decode(v[1]));
                }
            }
        }
        return params;
    }
    
    public static Bundle decodeNavigationUrl(String s) {
        Bundle params = new Bundle();
        if (s != null) {
            String array[] = s.split("\\?");
            for (String parameter : array) {
                String v[] = parameter.split("=");
//                if (v.length == 2) {
//                	Logger.e(TAG, v[0] + " - " + v[1]);
                    params.putString(URLDecoder.decode(v[0]),
                                     URLDecoder.decode(v[1]));
//                }
            }
        }
        return params;
    }

    /**
     * Parse a URL query and fragment parameters into a key-value bundle.
     *
     * @param url the URL to parse
     * @return a dictionary bundle of keys and values
     */
    public static Bundle parseUrl(String url) {
        // hack to prevent MalformedURLException
        url = url.replace("fbconnect", "http");
        try {
            URL u = new URL(url);
            Bundle b = decodeUrl(u.getQuery());
            b.putAll(decodeUrl(u.getRef()));
            return b;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    
    /**
     * Connect to an HTTP URL and return the response as a string.
     *
     * Note that the HTTP method override is used on non-GET requests. (i.e.
     * requests are made as "POST" with method specified in the body).
     *
     * @param url - the resource to open: must be a welformed URL
     * @param method - the HTTP method to use ("GET", "POST", etc.)
     * @param params - the query parameter for the URL (e.g. access_token=foo)
     * @return the URL contents as a String
     * @throws MalformedURLException - if the URL format is invalid
     * @throws IOException - if a network problem occurs
     */
    public static String openUrl(String url, String method, Bundle params)
          throws MalformedURLException, IOException {
        // random string as boundary for multi-part http post
        String strBoundary = "3i2ndDfv2rTHiSisAbouNdArYfORhtTPEefj3q2f";
        String endLine = "\r\n";

        OutputStream os;

        if (method.equals("GET")) {
            url = url + "?" + encodeUrl(params);
        }
        HttpURLConnection conn =
            (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", System.getProperties().
                getProperty("http.agent") + " FacebookAndroidSDK");
        if (!method.equals("GET")) {
            Bundle dataparams = new Bundle();
            for (String key : params.keySet()) {
                Object parameter = params.get(key);
                if (parameter instanceof byte[]) {
                    dataparams.putByteArray(key, (byte[])parameter);
                }
            }

            // use method override
            if (!params.containsKey("method")) {
                params.putString("method", method);
            }

            if (params.containsKey("access_token")) {
                String decoded_token =
                    URLDecoder.decode(params.getString("access_token"));
                params.putString("access_token", decoded_token);
            }

            conn.setRequestMethod("POST");
            conn.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data;boundary="+strBoundary);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.connect();
            os = new BufferedOutputStream(conn.getOutputStream());

            os.write(("--" + strBoundary +endLine).getBytes());
            os.write((encodePostBody(params, strBoundary)).getBytes());
            os.write((endLine + "--" + strBoundary + endLine).getBytes());

            if (!dataparams.isEmpty()) {

                for (String key: dataparams.keySet()){
                    os.write(("Content-Disposition: form-data; filename=\"" + key + "\"" + endLine).getBytes());
                    os.write(("Content-Type: content/unknown" + endLine + endLine).getBytes());
                    os.write(dataparams.getByteArray(key));
                    os.write((endLine + "--" + strBoundary + endLine).getBytes());

                }
            }
            os.flush();
        }

        String response = "";
        try {
            response = read(conn.getInputStream());
        } catch (FileNotFoundException e) {
            // Error Stream contains JSON that we can parse to a FB error
            response = read(conn.getErrorStream());
        }
        return response;
    }

    private static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    public static void clearCookies(Context context) {
        // Edge case: an illegal state exception is thrown if an instance of
        // CookieSyncManager has not be created.  CookieSyncManager is normally
        // created by a WebKit view, but this might happen if you start the
        // app, restore saved state, and click logout before running a UI
        // dialog in a WebView -- in which case the app crashes
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr =
            CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }
    
    public static String getShortStringNumber (int number){// 1.8K 2M
    	if (number < 1000)
    		return String.valueOf(number);
    	else if (number > 1000000){
    		double dNumber = (double) number;
    		return String.valueOf(Math.round(dNumber/100000)/10) + "M";
    	}
    	else{
    		double dNumber = (double) number;
    		return String.valueOf(Math.round(dNumber/100)/10) + "K";
    	}
    } 
    
    
    
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	/**
	 * Generate a value suitable for use in {@link #setId(int)}.
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	public static int generateViewId() {
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	            return result;
	        }
	    }
	}
	
	/**
	 * @param context used to check the device version and DownloadManager information
	 * @return true if the download manager is available
	 */
	public static boolean isDownloadManagerAvailable(Context context) {
	    try {
	        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD) {
	            return false;
	        }
	        Intent intent = new Intent(Intent.ACTION_MAIN);
	        intent.addCategory(Intent.CATEGORY_LAUNCHER);
	        intent.setClassName("com.android.providers.downloads.ui", "com.android.providers.downloads.ui.DownloadList");
	        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent,
	                PackageManager.MATCH_DEFAULT_ONLY);
	        return list.size() > 0;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	
	public static String unescapeHtml(String url){
		return url.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&");
	}
	
	public static boolean isEmpty (String s){
		return s == null || s.length() == 0;
	}
	
	private final static String URL_REGEX = "((www\\.[\\s]+)|(https?://[^\\s]+))";
	private final static String CONSECUTIVE_CHARS = "([a-z])\\1{1,}";
	private final static String STARTS_WITH_NUMBER = "[1-9]\\s*(\\w+)";
	
	public static String cleanTweet (String tweet){
		if (isEmpty(tweet)) return "";
		tweet = tweet.replace("RT ", "").replace("#", "");
		
		// Remove urls
				tweet = tweet.replaceAll(URL_REGEX, "");

				// Remove @username
				tweet = tweet.replaceAll("@([^\\s]+)", "");

				// Remove character repetition
				tweet = tweet.replaceAll(CONSECUTIVE_CHARS, "$1");

				// Remove words starting with a number
				//tweet = tweet.replaceAll(STARTS_WITH_NUMBER, "");

				// Escape HTML
				//tweet = tweet.replaceAll("&amp;", "&");
				//tweet = unescapeHtml(tweet);
		return tweet;
	}
	
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	public static float getSimilarityShortString(final String string1, final String string2) {
    	if (string1 == null  || string2 == null)
    		return 0;
    	short s1 = 1;
    	final HashMap<String, Short> str1TokensMap = new HashMap<String, Short>();
    	for (String token : string1.split(" ")){
			Short count = str1TokensMap.get(token);
			if (count == null)
				str1TokensMap.put(token, s1);
			else
				str1TokensMap.put(token, ++count);

    	}
    	final HashMap<String, Short> str2TokensMap = new HashMap<String, Short>();
    	for (String token : string2.split(" ")){
    		Short count = str2TokensMap.get(token);
    		if (count == null)
				str2TokensMap.put(token, s1);
			else
				str2TokensMap.put(token, ++count);
    	}
    	
        final Set<String> mutualTokens = new HashSet<String>();
        mutualTokens.addAll(str1TokensMap.keySet());
        mutualTokens.retainAll(str2TokensMap.keySet());
        
        float multiple = 0; //A.B
        float module1 = 0; //|A|
        float module2 = 0; //|B|
        for (String element : str1TokensMap.keySet()){
        	Short freq1 = str1TokensMap.get(element);
        	module1 += freq1 * freq1;
        }
        
        for (String element : str2TokensMap.keySet()){
        	Short freq = str2TokensMap.get(element);
        	module2 += freq * freq;
        }
        
        for (String element : mutualTokens){
        	Short freq1 = str1TokensMap.get(element);
        	Short freq2 = str2TokensMap.get(element);
        	multiple += freq1 * freq2;
        }
        float multiModule =  (float) (Math.sqrt(module1) * Math.sqrt(module2));
        if ( multiModule == 0 ) return 0;
        //System.out.println("Similar: " + string1.substring(0, string1.length() > 30 ?30:string1.length() - 1) + "\t" + string2.substring(0, string2.length() > 30 ?30:string2.length() - 1) + ":\t\t" + (multiple/multiModule));
        
        return (float) (multiple/multiModule);
        
    }
}
