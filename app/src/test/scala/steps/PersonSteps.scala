/*

package steps
import crud_op.Entity.Person
import io.cucumber.scala.{EN, ScalaDsl}
import crud_op.Repository.PersonRepoImpl
import crud_op.Service.PersonServiceImpl
import org.mockito.Mockito.{verify, when}
import org.scalatest.Matchers
import org.scalatest.mockito.MockitoSugar

import java.sql.{Connection, PreparedStatement, ResultSet, Statement}

class PersonSteps extends ScalaDsl with EN with Matchers with MockitoSugar {
  var mockConnection: Connection = _
  var mockStatement: Statement = _
  var mockPreparedStatement: PreparedStatement = _
  var mockResultSet: ResultSet = _
  var personRepo:PersonRepoImpl = mock[PersonRepoImpl]
  val personService:PersonServiceImpl = new PersonServiceImpl{
    override val obj: PersonRepoImpl = personRepo
}

  //For Create
  Given("""a Person Repository"""){ () =>
    when(personRepo.createPerson()).thenReturn("Created Successful")
  }

  Given("""a person with ID {int}, name {string}, and age {int}"""){
    (id:Int,name:String,age:Int)=>
      personService.insert(Person(id,name,age))
  }

  When("""I created a person table"""){
    () =>
      personService.obj.createPerson()
  }  
  Then("""the table should be created"""){
    () =>
      verify(personRepo).createPerson()
  }

  //For Insert
  When("""I insert the person into the table""") { () =>
    personService.insert(Person(1,"Nandha",22))

  }
  Then("""the person should be inserted successfully"""){
    () =>
      verify(personRepo).insertPerson(Person(1,"Nandha",22))
  }

  //For Get person

  When("""I get the person with ID {int}"""){ (id:Int) =>
    personService.get(Some(id))

  }

  Then("""the person details should be displayed"""){
    () =>
      verify(personRepo).getPerson(Some(1))
  }

 // For Update

  When("""I update the person with ID {int} to have name {string} and age {int}"""){
    (id:Int,name:String,age:Int) =>
      personService.update(Person(id,name,age))
  }

  Then("""the person details should be updated successfully"""){
    () =>
      verify(personRepo).updatePerson(Person(1,"Nandha",22))
  }

  //For delete

  When("""I delete the person with ID {int}"""){
    (id:Int) =>
      personService.delete(Some(id))
  }
  Then("""the person should be deleted successfully"""){
    () =>
      verify(personRepo).deletePerson(Some(1))
  }

  When("""I display all persons"""){
    () =>
      personService.personTable()
  }
  Then("""all persons should be displayed"""){
    ()=>
      verify(personRepo).personTable()
  }

  }

*/
