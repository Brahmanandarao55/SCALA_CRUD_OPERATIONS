package crud_op.Service
import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
class PersonServiceImpl extends PersonService {

  val obj = new PersonRepoImpl

  override def insert(person: String): Unit = {
    obj.createPerson()
    obj.insertPerson(person:String)

  }

  override def get(id: Option[Int]): Unit = {
    obj.getPerson(id)
  }

  override def update(person: Person): Unit = {
    obj.updatePerson(person:Person)
  }

  override def delete(id: Option[Int]): Unit = {
    obj.deletePerson(id)
  }

  override def personTable(): Unit = {
    obj.personTable()
  }
}
