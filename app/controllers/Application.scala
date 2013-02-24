package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def helloWorld = Action { 
	Ok("Hello world.")
  }

  def echo = Action { request => 
	Ok("echo says your param was: " + request.getQueryString("qp").getOrElse("what, nothing to say?"))
  }
  
 
  def proxyGet(urlToProxy: String) = Action {
    Async {
      WS.url("http://" + urlToProxy).get().map { response =>
        val contentType = response.header("Content-Type").getOrElse("text/plain")
        Ok(response.body).as(contentType)
      }     
    }
  }   

}
