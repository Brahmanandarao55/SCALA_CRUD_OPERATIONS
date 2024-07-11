package crud_op.Repository

import java.sql.Connection
import crud_op.Entity.Person

trait PersonRepo {

  //  def jdbcConnection():Connection
  def createPerson(): String

  def insertPerson(person: String): String

  def getPerson(id: Option[Int]): String

  def updatePerson(person: Person, id: Int): String

  def deletePerson(id: Option[Int]): String

  def getAll(): String


}