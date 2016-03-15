package controllers

import play.api.mvc._

import module.query.QueryModule
import module.query.CollectionQueryModule

import controllers.common.requestArgsQuery.{requestArgs, requestArgsWithAuthCheck}

object queryController extends Controller {
	def queryHomeContent = Action ( request => requestArgsWithAuthCheck(request)(QueryModule.queryContentWithConditions))
	def queryComments = Action ( request => requestArgs(request)(QueryModule.queryComments))
	def queryLikes = Action ( request => requestArgsWithAuthCheck(request)(QueryModule.queryLikes))
	def queryPush = Action ( request => requestArgsWithAuthCheck(request)(QueryModule.queryUserPush))
	def queryCollections = Action ( request => requestArgs(request)(CollectionQueryModule.queryCollectionContent))

	def downloadFile(name : String) = Action ( Ok(QueryModule.downloadFile(name)).as("image/png"))
}