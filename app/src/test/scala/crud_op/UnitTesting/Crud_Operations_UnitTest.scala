package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Entity.Person
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.{ConsoleUserInput, Crud_Operations}
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.EOFException


class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {

  val mockConsoleUserInput: ConsoleUserInput = mock[ConsoleUserInput]
  val mockRepo: DataBaseRepoImpl = mock[DataBaseRepoImpl]
  val mockConfig: Config = mock[Config]
  val mockLogger: Logger = mock[Logger]

  it should "call create table method" in {

    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("1")

    val crud_Operations_object = new Crud_Operations {
      override val repo: DataBaseRepoImpl = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput

    }

    val result = crud_Operations_object.menu()
    val actual = "Choose an operation:"
    verify(mockConsoleUserInput, times(1)).readLine("Enter your choice:")
    verify(mockRepo).createTable()

    result shouldBe actual
  }

  it should "call Insert method" in {

    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("2")
    when(mockConfig.getString("path")).thenReturn("C:\\Users\\brahmananda Rao\\Desktop\\data.csv")
    val crud_Operations_object = new Crud_Operations {
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
      override val repo: DataBaseRepoImpl = mockRepo
    }

    val result = crud_Operations_object.menu()

    val actual = "Choose an operation:"


    verify(mockRepo).insertData("C:\\Users\\brahmananda Rao\\Desktop\\data.csv")

    result shouldBe actual
  }


  it should "call getDataById method with correct ID" in {

    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("3")
    when(mockConsoleUserInput.readLine("Enter ID:")).thenReturn("1")

    val crud_Operations_object = new Crud_Operations {
      override val repo: DataBaseRepoImpl = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput

    }


    val result = crud_Operations_object.menu()

    verify(mockRepo).getDataById(Some(1))
    val actual = "Choose an operation:"
    result shouldBe actual
  }


  it should "call updateData method with correct parameters" in {

    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("4")
    when(mockConsoleUserInput.readLine("Enter Name:")).thenReturn("John Doe")
    when(mockConsoleUserInput.readLine("Enter Age: ")).thenReturn("30")
    when(mockConsoleUserInput.readLine("Enter Salary: ")).thenReturn("50000")
    when(mockConsoleUserInput.readLine("Enter Profession: ")).thenReturn("Engineer")
    when(mockConsoleUserInput.readLine("Enter Location: ")).thenReturn("New York")
    when(mockConsoleUserInput.readLine("Enter the Rank: ")).thenReturn("5.0f")
    when(mockConsoleUserInput.readLine("Enter the Block: ")).thenReturn("B")
    when(mockConsoleUserInput.readLine("Enter Updating ID: ")).thenReturn("1")
    val expectedPerson = Person("John Doe", 30, 50000, "Engineer", "New York", 5.0f, 'B')
    when(mockRepo.updateData(expectedPerson, 1)).thenReturn(anyString())

    val crud_Operations_object = new Crud_Operations {
      override val repo: DataBaseRepoImpl = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }


    val result = crud_Operations_object.menu()


    verify(mockRepo).updateData(expectedPerson, 1)
    val actual = "Choose an operation:"
    result shouldBe actual

  }

  it should "call getAll method" in {

    when(mockRepo.getAll).thenReturn(anyString())
    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("5")

    val crud_Operations_object = new Crud_Operations {
      override val repo: DataBaseRepoImpl = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }

    val result = crud_Operations_object.menu()

    verify(mockRepo).getAll
    val actual = "Choose an operation:"
    result shouldBe actual
  }


  it should "call deleteData method" in {

    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("6")
    when(mockConsoleUserInput.readLine("Enter deleting ID: ")).thenReturn("1")
    when(mockRepo.deleteData(Some(1))).thenReturn(anyString())

    val crud_Operations_object = new Crud_Operations {
      override val repo: DataBaseRepoImpl = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }
    val result = crud_Operations_object.menu()

    verify(mockRepo).deleteData(Some(1))
    val actual = "Choose an operation:"
    result shouldBe actual


  }

  it should "call invaild case" in {
    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("7")

    val crud_Operations_object = new Crud_Operations {
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }

    val result = crud_Operations_object.menu()

    val actual = "Choose an operation:"

    result shouldBe actual
  }

  it should "call again menu method" in {
    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("8", "7")


    val crud_Operations_object = new Crud_Operations {
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }
    val result = crud_Operations_object.menu()

    val actual = "Choose an operation:"


    result shouldBe actual
  }

  it should "raise exception" in {
    when(mockConsoleUserInput.readLine("Enter your choice:")).thenThrow(new IllegalArgumentException(anyString()))
    val crud_Operations_object = new Crud_Operations {
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }
    val result = {
      crud_Operations_object.menu()
    }
    val actual = "Enter Valid Input"

//    verify(mockLogger).error(anyString())
    println(result)
    result shouldBe actual
  }

}