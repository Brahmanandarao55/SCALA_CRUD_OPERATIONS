/*
package crud_op

import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import crud_op.Service.PersonServiceImpl
import org.mockito.Mockito.{when,verify}
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.mockito.MockitoSugar

class IntegrationTesting extends FlatSpec with Matchers with MockitoSugar {
  behavior of "ServicePersonImplTest"
  val person: Person = Person(1, "ravi", 22)
  val repo: PersonRepoImpl =  mock[PersonRepoImpl]
  val service: PersonServiceImpl = new PersonServiceImpl {
    override val obj: PersonRepoImpl = repo
  }
  "The Person Table Was Created" should "insert into table" taggedAs Tag("IntegrationTesting") in {

    when(repo.createPerson()).thenReturn("Created Successful")
    println(repo)
    service.obj.createPerson()
    verify(repo).createPerson()

  }


    //For data Insert

    "The person details" should "inserted successfully" in {
      when(repo.insertPerson(person)).thenReturn("Data Inserted Successfully!!!!!!!!!!!!!!!")
      service.insert(person)
      verify(repo).insertPerson(person)
    }

    //For get method
    it should "get a person by id" in {
      val id = Some(person.Id)
      service.get(id)
      //    println(service)
      //    println(repo)
      verify(repo).getPerson(id)
    }

    //For Update
    it should "update a Person" in {
      service.update(person)
      verify(repo).updatePerson(person)

    }

    //For Delelte
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
