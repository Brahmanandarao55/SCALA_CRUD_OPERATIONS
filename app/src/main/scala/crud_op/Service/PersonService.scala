package crud_op.Service
import crud_op.Entity.Person
trait PersonService {
  def createTable():String
  def insert(filepath:String):String
  def get(id:Option[Int]):String
  def update(person: Person, id:Int):String
  def delete(id:Option[Int]):String
  def getAll():String

}
