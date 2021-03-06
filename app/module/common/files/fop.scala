package module.common.files

import java.io.File
import play.api.libs.Files
import java.io.FileInputStream

import play.api.mvc.MultipartFormData
import play.api.libs.Files.TemporaryFile

import play.api.libs.json.Json
import play.api.libs.json.Json._
import play.api.libs.json.JsValue

import util.errorcode.ErrorCode

object fop {
	def uploadFile(data : MultipartFormData[TemporaryFile]) : JsValue = {
	  	data.file("upload").map { x =>
	  	  	Files.moveFile(x.ref.file, new File("upload/" + x.filename), true, true)
	  
			Json.toJson(Map("status" -> toJson("ok"), "result" -> toJson("success")))
	  	  	
	  	}.getOrElse {
			ErrorCode.errorToJson("post image error")
	  	} 
	}

	def downloadFile(name : String) : Array[Byte] = {
	  	val file = new File("upload/" + name)
		val reVal : Array[Byte] = new Array[Byte](file.length.intValue)
		new FileInputStream(file).read(reVal)
		reVal
	}
}