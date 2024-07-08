package crud_op.Service
import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
class PersonServiceImpl extends PersonService {

  private val obj = new PersonRepoImpl

  override def createTable(): String = {
    obj.createPerson()
  }
  override def insert(person: String): String = {

    obj.insertPerson(person:String)

  }

  override def get(id: Option[Int]): String = {
    obj.getPerson(id)
  }

  override def update(person: Person, id:Int): String = {
    obj.updatePerson(person:Person, id:Int)
  }

override def delete(id: Option[Int]): String = {
    obj.deletePerson(id)
  }

  override def getAll(): String = {
    obj.getAll()
  }
}
