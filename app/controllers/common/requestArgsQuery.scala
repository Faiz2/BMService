package controllers.common

import play.api._
import play.api.mvc._
import play.api.libs.json.JsValue
import play.api.libs.Files.TemporaryFile

import util.errorcode.ErrorCode

import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.Await

import dongdamessages.MessageRoutes
import dongdamessages.excute
import pattern.RoutesActor

import com.mongodb.casbah.Imports._

object requestArgsQuery extends Controller {
	implicit val t = Timeout(3 seconds)

	def requestArgsWithAuthCheck(request : Request[AnyContent])(func : JsValue => (MongoDBObject => JsValue)) : Result = {
  		try {
  			request.body.asJson.map { x => 
  			   Ok((new authCheck)(x)(func))
  			}.getOrElse (BadRequest("Bad Request for input"))
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input")
  		}  		   
	}
  	
  	def requestArgs(request : Request[AnyContent])(func : JsValue => JsValue) : Result = {
  		try {
  			request.body.asJson.map { x => 
  				Ok(func(x))
  			}.getOrElse (BadRequest("Bad Request for input"))
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input")
  		}  		   
	}
	
  	def requestArgsV2(request : Request[AnyContent])(func : JsValue => MessageRoutes) : Result = {
  		try {
  			request.body.asJson.map { x => 
  				Ok(commonExcution(func(x)))
  			}.getOrElse (BadRequest("Bad Request for input"))
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input")
  		}  		   
	}
  	
  	def commonExcution(msr : MessageRoutes) : JsValue = {
		val act = ActorSystem("sys").actorOf(Props[RoutesActor], "main")
		val r = act ? excute(msr)
		Await.result(r.mapTo[JsValue], t.duration)
	}
 
  	def uploadRequestArgs(request : Request[AnyContent])(func : MultipartFormData[TemporaryFile] => JsValue) : Result = {
  		try {
   			request.body.asMultipartFormData.map { x => 
   				Ok(func(x))
  			}.getOrElse (BadRequest("Bad Request for input")) 			  
  		} catch {
  			case _ : Exception => BadRequest("Bad Request for input")
  		}
  	}
}