package hoahong.trending.video.util;

import android.util.Log;







public class Logger {

	public static void e(String TAG, String message) {
		Log.e("IIINews", TAG + "\n" + message);
	}
	
	public static void i(String TAG, String message) {
		Log.i("IIINews", TAG + "\n" + message);
	}
	
	public static void d(String TAG, String message) {
		Log.d("IIINews", TAG + "\n" + message);
	}
	
	public static void v(String TAG, String message) {
		Log.v("IIINews", TAG + "\n" + message);
	}
	
	public static void w(String TAG, String message) {
		Log.w("IIINews", TAG + "\n" + message);
	}
}
