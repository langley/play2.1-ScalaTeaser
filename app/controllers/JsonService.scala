package controllers
 
import play.api._
import play.api.mvc._
import play.api.libs.json._
// you need this import to have combinators
import play.api.libs.functional.syntax._
 
// Add this to your conf/routes
// POST	/JsSayHello					controllers.JsonServices.sayHello
// Test it with curl
// curl --header "Content-type: application/json" --request POST --data '{"name": "Toto", "age": 32}' http://localhost:9000/JsSayHello
 
object JsonServices extends Controller {
 
  // Json to Scala converter... using generic Tuple
  implicit val rds = (
    (JsPath \ 'name).read[String] and
    (JsPath \ 'age).read[Long]
  ) tupled
  
  // Json input, Json output with Error handling
  // and a chance to talk about higher order functions and pattern maching 
  def sayHello = Action(parse.json) { request =>
    request.body.validate[(String, Long)].map {  // this is receiving a function literal (actually a partial function)
      case (name, age) => Ok(Json.obj("status" ->"OK", "message" -> ("Hello "+name+" , you're "+age) ))
    }.recoverTotal{
      e => BadRequest(Json.obj("status" -> "KO", "message" -> JsError.toFlatJson(e)))
    }
  }
}