package module.profile.v2

import play.api.libs.json.JsValue
import dongdamessages.CommonMessage

import util.errorcode.ErrorCode

abstract class msg_ProfileCommand extends CommonMessage

object ProfileMessages {
	case class msg_CreateProfile(data : JsValue) extends msg_ProfileCommand
	case class msg_UpdateProfile(data : JsValue) extends msg_ProfileCommand
	case class msg_QueryProfile(data : JsValue) extends msg_ProfileCommand
}