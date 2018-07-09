package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiRequests {

	static String UserID, OTP, deviceId, utm_content, networkType, source, deviceType, email, androidId, operator, deviceToken, appVersion, country, androidVersion, deviceName=null;
	static String filename=null;
	static Properties propObj = new Properties();
	
	public static String makeAppRegisterReuest(String DEVICEID, String EMAIL, String ANDROIDID, String OPERATOR, String DEVICETOKEN, String APPVERSION, String ANDROIDVERSION) throws IOException {
		
		filename="props/project.properties";
		File file = new File(filename);
		FileInputStream fileInput = new FileInputStream(file);
		propObj.load(fileInput);
		
		utm_content=propObj.getProperty("UTM_CONTENT");
		networkType=propObj.getProperty("NETWORKTYPE");
		source=propObj.getProperty("SOURCE");
		deviceType=propObj.getProperty("DEVICETYPE");
		country=propObj.getProperty("COUNTRY");
		deviceName=propObj.getProperty("DEVICENAME");
		deviceId=DEVICEID;
		email=EMAIL;
		androidId=ANDROIDID;
		operator=OPERATOR;
		deviceToken=DEVICETOKEN;
		appVersion=APPVERSION;
		androidVersion=ANDROIDVERSION;
		
		String Request="http://pic2word.com/pic2word/api/v1/user/register/?&deviceId="+deviceId+"&utm_content="+utm_content+"&networkType="+networkType+"&source="+source+"&deviceType="+deviceType+"&email="+email+"&androidId="+androidId+"&operator="+operator+"&deviceToken="+deviceToken+"&appVersion="+appVersion+"&country="+country+"&androidVersion="+androidVersion+"&deviceName="+deviceName+"";
		return Request;
	}
	
		public static String makeVerifyRequest(String userID, String otp) {
			UserID=userID;
			OTP=otp;
			String Request="http://pic2word.com/pic2word/api/v1/user/verify/?&otp="+OTP+"&userId="+UserID+"";
			return Request;
			
		}
		
		public static String makeEDRReuest(String coins,String level,String eUserId,String eOtp,String word) {
			String Request="http://pic2word.com/pic2word/api/v1/game/levelCrossEdr/?coins="+coins+"&word="+word+"&otp="+eOtp+"&userId="+eUserId+"&level="+level+"";
			return Request;
		}
		
		public static String makeAppLaunchRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/user/appLaunch/?networkType="+networkType+"&appVersion="+appVersion+"&isOpen=1&otp="+OTP+"&userId="+UserID+"";
			return Request;
			
		}
		
		public static String makeReportEDRRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/game/reportEdr/?otp="+OTP+"&userId="+UserID+"&type=INVITE";
			return Request;
		}
		
		public static String makeRedeemRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/user/redeem/info/?otp="+OTP+"&userId="+UserID+"";
			return Request;
		}
		
		public static String makeGetProductsRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/game/getProducts/?otp="+OTP+"&userId="+UserID+"&appVersion="+appVersion+"";
			return Request;
		}
	
		public static String makeAdmobAdRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/user/ad/video/?value=30&otp="+OTP+"&id=ADMOB&userId="+UserID+"";
			return Request;
		}
		public static String makeUnityAdRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/user/ad/video/?value=40&otp="+OTP+"&id=Unity&userId="+UserID+"";
			return Request;
		}
		//
		public static String makeGetGameDetailsRequest(int level) {
			String Request="http://pic2word.com/pic2word/api/v1/user/gameDetails/?userId="+UserID+"&otp="+OTP+"&level="+level+"";
			return Request;
		}
		//
		public static String makeReportEDRRateRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/game/reportEdr/?userId="+UserID+"&otp="+OTP+"&type=RATE";
			return Request;
		}
		//
		public static String makeGetHelpEDRRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/game/helpEdr/?helpData=O&helpType=HELP_1&otp="+OTP+"&userId="+UserID+"&level=23";
			return Request;
		}
		//
		public static String makeFeedbackRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/user/feedback/?userId="+UserID+"&otp="+OTP+"&feedbackType=query&text=GetHELP";
			return Request;
		}
		//
		public static String makeRateRequest() {
			String Request="http://pic2word.com/pic2word/api/v1/user/rate/?userId="+UserID+"&otp="+OTP+"&rate=5";
			return Request;
		}
		
		
		}
