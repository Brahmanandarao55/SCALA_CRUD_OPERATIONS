package steps

import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import crud_op.Service.PersonServiceImpl
import io.cucumber.scala.{EN, ScalaDsl}
import org.mockito.ArgumentMatchers.{any, anyString}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class PersonSteps extends ScalaDsl with EN with MockitoSugar {
  var mockRepo: PersonRepoImpl = _
  var service: PersonServiceImpl = _

  Given("""a person repository""") { () =>
    mockRepo = mock[PersonRepoImpl]
    service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }
  }

  When("""I create the person table""") { () =>
    service.obj.createPerson()
  }

  Then("""the table should be created""") { () =>
    verify(mockRepo).createPerson()
  }

  When("""I insert persons from the file {string}""") { (filePath: String) =>
    service.insert(filePath)
  }

  Then("""the persons should be inserted""") { () =>
    verify(mockRepo).insertPerson(anyString())
  }

  Given("""a person repository with a person having ID {int}""") { (id: Int) =>

    mockRepo = mock[PersonRepoImpl]
    service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }

  }

  When("""I get the person by ID {int}""") { (id: Int) =>
    service.get(Some(id))
  }

  Then("""the person's details should be retrieved""") { () =>
    verify(mockRepo).getPerson(Some(1))
  }

  When("""I update the person details with ID {int}""") { (id: Int) =>

    val person = Person(id, "John", 26)
    service.update(person)
  }

  Then("""the person's details should be updated""") { () =>
    verify(mockRepo).updatePerson(any[Person])
  }

  When("""I delete the person by ID {int}""") { (id: Int) =>
    service.delete(Some(id))
  }

  Then("""the person should be deleted""") { () =>
    verify(mockRepo).deletePerson(Some(1))
  }

  Given("""a person repository with persons""") { () =>
    // Assume the repo has persons already; mock behavior can be added as needed
    mockRepo = mock[PersonRepoImpl]
    service = new PersonServiceImpl {
      override val obj: PersonRepoImpl = mockRepo
    }

  }

  When("""I retrieve all persons""") { () =>
    service.personTable()
  }

  Then("""all persons' details should be retrieved""") { () =>
    verify(mockRepo).personTable()
  }
}
