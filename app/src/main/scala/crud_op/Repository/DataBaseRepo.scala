package crud_op.Repository


import crud_op.Entity.Person

trait DataBaseRepo {

  def createTable(): String

  def insertData(person: String): String

  def getDataById(id: Option[Int]): String

  def updateData(person: Person, id: Int): String

  def deleteData(id: Option[Int]): String

  def getAll: String


}