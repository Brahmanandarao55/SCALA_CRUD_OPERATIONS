/*
package crud_op

import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import crud_op.Service.PersonServiceImpl
import org.mockito.Mockito.verify
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

class IntegrationTest extends FlatSpec with Matchers with MockitoSugar {

  "PersonServiceImpl" should "create the person table through PersonRepoImpl" in {
    val mockRepo = mock[PersonRepoImpl]
    val service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }

    service.obj.createPerson()
    verify(mockRepo).createPerson()
  }

  it should "insert persons from a file through PersonRepoImpl" in {
    val mockRepo = mock[PersonRepoImpl]
    val service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }
    val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\main\\scala\\crud_op\\main\\data.csv"

    service.insert(filepath)
    verify(mockRepo).insertPerson(filepath)
  }

  it should "retrieve a person by ID through PersonRepoImpl" in {
    val mockRepo = mock[PersonRepoImpl]
    val service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }

    val personId = Some(1)

    service.get(personId)
    verify(mockRepo).getPerson(personId)
  }

  it should "update a person through PersonRepoImpl" in {
    val mockRepo = mock[PersonRepoImpl]
    val service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }
    val person = Person(1, "John", 26)

    service.update(person)
    verify(mockRepo).updatePerson(person)
  }

  it should "delete a person by ID through PersonRepoImpl" in {
    val mockRepo = mock[PersonRepoImpl]
    val service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }
    val personId = Some(1)

    service.delete(personId)
    verify(mockRepo).deletePerson(personId)
  }

  it should "retrieve all persons through PersonRepoImpl" in {
    val mockRepo = mock[PersonRepoImpl]
    val service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }

    service.personTable()
    verify(mockRepo).personTable()
  }
}
*/
