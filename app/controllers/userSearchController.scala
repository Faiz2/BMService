package controllers

import play.api.mvc._

import module.usersearch.UserSearchModule

import controllers.common.requestArgsQuery.{requestArgs}

object userSearchController extends Controller {
	def queryRecommandUsers = Action (request => requestArgs(request)(UserSearchModule.queryRecommandUsers))
	def queryUsersWithRoleTag = Action (request => requestArgs(request)(UserSearchModule.queryUsersWithRoleTag))
}