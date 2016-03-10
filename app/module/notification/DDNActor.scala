package module.notification

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.Actor
import akka.actor.Inbox
import scala.concurrent.duration._

import play.api.libs.json.Json
import play.api.libs.json.Json.{toJson}
import play.api.libs.json.JsValue

case object DDNInit
case class 	DDNNotifyUsers(val parameters : (String, JsValue)*)
case class 	DDNCreateChatRoom(val parameters : (String, JsValue)*)
case class 	DDNDismissChatRoom(val parameters : (String, JsValue)*)
case class 	DDNCreateChatGroup(val parameters : (String, JsValue)*)
case class 	DDNDismissChatGroup(val parameters : (String, JsValue)*)

class DDNActor extends Actor {
	
	def parameters2Map(parameters : List[(String, JsValue)]) : Map[String, JsValue] = {
		var pm : Map[String, JsValue] = Map.empty
		for ((key, value) <- parameters) pm += key -> value
		pm
	}
	
	def receive = {
	  case DDNInit => DDNNotification.getAuthTokenForXMPP
	  case notify : DDNNotifyUsers => {
		  DDNNotification.nofity(parameters2Map(notify.parameters.toList))
	  }
	  case create : DDNCreateChatRoom => {
		  sender ! DDNNotification.createChatRoom(parameters2Map(create.parameters.toList))
	  }
	  case dismiss : DDNDismissChatRoom => {
		  sender ! DDNNotification.dismissChatRoom(parameters2Map(dismiss.parameters.toList))
	  }
	  case cg : DDNCreateChatGroup => {
	    sender ! DDNNotification.createChatGroup(parameters2Map(cg.parameters.toList))     
	  }
	  case dg : DDNDismissChatGroup => { 
	    sender ! DDNNotification.dismissChatGroup(parameters2Map(dg.parameters.toList))     
	  }
	  case _ => 
	}
}