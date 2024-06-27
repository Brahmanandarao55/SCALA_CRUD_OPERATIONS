package crud_op.Repository

import java.sql.Connection
import crud_op.Entity.Person
trait PersonRepo {

  def jdbcConnection():Connection
  def createPerson():String
  def insertPerson(person: String): String
  def getPerson(id:Option[Int]):Unit
  def updatePerson(person: Person):Unit
  def deletePerson(id:Option[Int]):Unit
  def personTable():Unit



}