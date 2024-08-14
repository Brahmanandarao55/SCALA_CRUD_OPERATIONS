package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.{ConsoleUserInput, Crud_Operations}
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger


class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {

  val mockConsoleUserInput: ConsoleUserInput = mock[ConsoleUserInput]
  val mockRepo: DataBaseRepoImpl = mock[DataBaseRepoImpl]
  val mockConfig = mock[Config]

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
    val crud_Operations_object = new Crud_Operations{
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



}