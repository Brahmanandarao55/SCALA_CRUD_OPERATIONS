/*

package crud_op

import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import crud_op.Service.PersonServiceImpl
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{verify, when}
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.mockito.MockitoSugar

class UnitTest extends FlatSpec with Matchers with MockitoSugar {
  behavior of "ServicePersonImplTest"
  val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\main\\scala\\crud_op\\main\\data.csv"
  val person: Person = Person(1, "ravi", 22)
  val repo: PersonRepoImpl = mock[PersonRepoImpl]
  val service: PersonServiceImpl = new PersonServiceImpl{
    override val obj: PersonRepoImpl = repo
  }
  "The Person Table Was Created" should "insert into table" taggedAs Tag("UnitTest") in {


    service.obj.createPerson()

    verify(repo).createPerson()


  }

  //For data Insert

  "The person details" should "inserted successfully" in{



    service.insert(filepath)
    verify(repo).insertPerson(filepath)
  }

  //For get method
  it should "get a person by id" in {
    val id = Some(person.Id)
    service.get(id)
    verify(repo).getPerson(id)
  }

  //For Update
  it should "update a Person" in{
    service.update(person)
    verify(repo).updatePerson(person)

  }

  //For Delete
  it should "delete a person by id" in {
    val id = Some(1)
    service.delete(id)
    verify(repo).deletePerson(id)


  }

  //For accessing
  it should "Select person table" in {
    service.personTable()
    verify(repo).personTable()
  }
}



*/
