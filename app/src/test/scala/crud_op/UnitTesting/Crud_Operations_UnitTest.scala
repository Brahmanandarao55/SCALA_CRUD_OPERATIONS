package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.{ConsoleUserInput, Crud_Operations}
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger


class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {

  val mockConfig: Config = mock[Config]
  val mockConsoleUserInput: ConsoleUserInput = mock[ConsoleUserInput]
  val mockLogger = mock[Logger]
  val mockRepo = mock[DataBaseRepoImpl]
  it should "sdfsdf" in {

    when(mockConsoleUserInput.readLine("")).thenReturn("Nandha")

    val x = new Crud_Operations{
      override val repo = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
    }

     x.menu()
//    val actual = "Choose an operation:"

    verify(mockLogger).info(anyString())
//    verify(mockConsoleUserInput).readLine("")

//    result shouldBe actual
  }
}