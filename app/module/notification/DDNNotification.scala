package module.notification

import module.common.http._
import play.api.libs.json.Json
import play.api.libs.json.Json.{toJson}
import play.api.libs.json.JsValue

object DDNNotification {

	val app_key = toJson("1afd2cc8-4060-41eb-aa5a-ee9460370156")
	val notification_account = toJson("alfred_test")
	val XMPP = "https://rest.gotye.com.cn/api/"
	var XMPP_access_token : String = ""
  
	def getAuthTokenForXMPP = {
		XMPP_access_token = 
			(HTTP(XMPP + "accessToken").header("Accept" -> "application/json", "Content-Type" -> "application/json").
				post("username" -> toJson("358669625@qq.com"), "grant_type" -> toJson("password"), "password" -> toJson("Abcde196125")) \ "access_token").
				asOpt[String].get
	}

	/**
	 * parameters:
	 * 		senderAccount : notificatioin_account
	 *   	receiverType : 0 => User, 1 => ChatGroup, 2 => UserGroup
	 *    	receiverIds	: []
	 *      isSave : 0 => Not Save, 1 => Save
	 *      msgType : 0 => text, 3 => image, 4 => voice
	 *      content : message content
	 *      thumb : 
	 *      voiceLen : null
	 *      pushFormart :
	 *      extraData :
	 */
	def nofity(pm : Map[String, JsValue]) = {
		var pushMsg = pm
	  
		pushMsg += "senderAccount" -> notification_account
		pushMsg += "appkey" -> app_key
		pushMsg += "extraData" -> toJson("notification from Dongda")

		val result =
		  	(HTTP(XMPP + "SendMsg").header("Accept" -> "application/json", "Content-Type" -> "application/json", "Authorization" -> ("Bearer " + XMPP_access_token)).
		  		post(toJson(pushMsg)) \ "status").asOpt[Int].get
	
		if (result == 200) println("notification sent success")
		else println("notification sent error %d", result)
	}
	
	/**
	 * create chat group
	 * "appkey": "2e6ca10c-a293-41a5-8c9d-5bdc5e768184",
     * "roomName": "test room1",   // chat room name 
     * "head": null,               // chat room photo
     * "roomType": 1,              // 1 => normal chat room (text, image and voice) 2 =>  
     * "scope": 0,                 // 0 => application 1 => developer 
     * "maxUserNumber": 10         // maximum users
	 */
	def createChatGroup(pm : Map[String, JsValue]) : JsValue= {
		var pushMsg = pm
		
		pushMsg += "appkey" -> app_key
		pushMsg += "roomType" -> toJson(1) 
		pushMsg += "scope" -> toJson(0)
		pushMsg += "maxUserNumber" -> toJson(10)
		
		HTTP(XMPP + "CreateIMRoom").header("Accept" -> "application/json", "Content-Type" -> "application/json", "Authorization" -> ("Bearer " + XMPP_access_token)).post(toJson(pushMsg)) 
	}	

	/**
	 * dismiss chat group 
	 * "appkey": "2e6ca10c-a293-41a5-8c9d-5bdc5e768184",
     * "roomId": 340900
	 */
	def dismissChatGroup(pm : Map[String, JsValue]) : JsValue= {
		var pushMsg = pm
		
		pushMsg += "appkey" -> app_key
		
		HTTP(XMPP + "DelIMRoom").header("Accept" -> "application/json", "Content-Type" -> "application/json", "Authorization" -> ("Bearer " + XMPP_access_token)).post(toJson(pushMsg)) 
	}
}