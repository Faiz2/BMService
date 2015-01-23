package controllers

import play.api.mvc._
import module.profile.ProfileModule
import controllers.common.requestArgsQuery.{requestArgs}

object profileController extends Controller {
	def like = Action (request => requestArgs(request)(ProfileModule.like))
	def follow = Action (request => requestArgs(request)(ProfileModule.like))
	def userProfile = Action (request => requestArgs(request)(ProfileModule.userProfile))
}