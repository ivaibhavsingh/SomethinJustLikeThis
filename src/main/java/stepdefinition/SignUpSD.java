package stepdefinition;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import utilities.AES;
import utilities.ApiRequests;
import utilities.HitApi;

public class SignUpSD {

	static String Status, UserId, OTP, DeviceID, Email, AndroidId, Operator, DeviceToken, AppVersion, AndroidVersion=null;
	static JSONObject ResponseString;
	static String coins, word, eOtp, eUserId, level=null; 
	static Logger log;
	@Given("^I want to write test Apis for \"([^\"]*)\"$")
	public void initialiseTestForApiCalls(String DeviceId) throws Exception {
	    DeviceID=DeviceId;
	    PropertyConfigurator.configure("props/log4j.properties");
	    log=Logger.getLogger("devpinoyLogger");
	}

	@When("^I send \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" (.*)$")
	public void i_send(String email, String androidId, String operator, String deviceToken, String appVersion, String androidVersion) throws Exception {
	   
		Email=email;
		AndroidId=androidId;
		Operator=operator;
		DeviceToken=deviceToken;
		AppVersion=appVersion;
		AndroidVersion=androidVersion;
		
		//makeRegisterRequest
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeAppRegisterReuest(DeviceID, Email, AndroidId, Operator, DeviceToken, AppVersion, AndroidVersion));
		eOtp=ResponseString.get("otp").toString();
		eUserId=ResponseString.get("userId").toString();
		OTP=AES.decrypt(ResponseString.getString("otp").toString(), "walletplay");
		UserId=AES.decrypt(ResponseString.getString("userId"), "walletplay");
		if(ResponseString.toString().contains("verified"))
			Assert.assertEquals("0", "0");
		else
			Assert.fail();
		
		//makeVerifyRequest
		log.debug("User Registered");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeVerifyRequest(UserId, OTP));
		Assert.assertEquals("true", ResponseString.get("status").toString());
		
		//makeLevelCrossEdrRequestLevel22
		log.debug("User Verified");
		word=AES.encrypt("PROFIT", "walletplay");
		coins=AES.encrypt("5", "walletplay");
		level=AES.encrypt("22", "walletplay");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeEDRReuest(coins, level, eUserId, eOtp, word));
		Assert.assertEquals("true", ResponseString.get("status").toString());		
		
		//AppLaunchRequest	
		log.debug("Level Cross EDR verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeAppLaunchRequest());
		Assert.assertEquals("true", ResponseString.get("appPurchaseEnable").toString());
		
		//makeReportEDRRequest
		log.debug("App Launch Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeReportEDRRequest());
		Assert.assertEquals("true", ResponseString.get("status").toString());
		
		//makeRedeemRequest
		log.debug("Report EDR Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeRedeemRequest());
	    Assert.assertEquals("true", ResponseString.get("paytmEnabled").toString());
			
		//makeGetProductsRequest
	    log.debug("Redeem Request Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeGetProductsRequest());
				if(ResponseString.length()>0)
					Assert.assertEquals("1", "1");
				else
					Assert.fail("There was an error in the API's response");
		
		//makeAdmobAdRequest
		log.debug("Get Products Verified");		
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeAdmobAdRequest());
			if(ResponseString.toString().contains("message")) {
				Assert.assertEquals("ok", ResponseString.get("message").toString());
				//insert log here
			}
			else {
				Assert.assertEquals("true", ResponseString.get("status").toString());
				//insert log here
			}
		
		//makeUnityAdRequest
			log.debug("Admob Request Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeUnityAdRequest());
		if(ResponseString.toString().contains("message")) {
			Assert.assertEquals("ok", ResponseString.get("message").toString());
			//insert log here
		}
		else {
			Assert.assertEquals("true", ResponseString.get("status").toString());
			//insert log here
		}
		//makeGetGameDetailsRequest
		log.debug("Unity Ad request verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeGetGameDetailsRequest(22));
		if(ResponseString.toString().contains("arrayName"))
			Assert.assertEquals("0", "0");
		else
			Assert.fail("Error in API Response");
		
		//makeReportEDRRateRequest
		log.debug("Get Game Detail Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeReportEDRRateRequest());
		Assert.assertEquals("true", ResponseString.get("status").toString());
		
		//makeGetHelpEDRRequest
		log.debug("Report EDR Rating request Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeGetHelpEDRRequest());
		Assert.assertEquals("true", ResponseString.get("status").toString());
		
		//makeFeedbackRequest
		log.debug("Get Help EDR Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeFeedbackRequest());
		Assert.assertEquals("true", ResponseString.get("status").toString());
		
		//makeRateRequest
		log.debug("Feedback Request Verified");
		ResponseString=HitApi.hitAPIPost(ApiRequests.makeRateRequest());
		
	}

	@Then("^I verify the \"([^\"]*)\"$")
	public void lastValidation(String status) throws Exception {
		log.debug("Rate Request Verified");
		Assert.assertEquals(status, ResponseString.get("status").toString());
		log.debug("ALL SYSTEMS: GO");
		
	}

	
}
