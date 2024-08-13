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

  it should "sdfsdf" in {
    val mockConfig: Config = mock[Config]
    val mockConsoleUserInput: ConsoleUserInput = mock[ConsoleUserInput]
    val mockLogger = mock[Logger]
    val mockRepo = mock[DataBaseRepoImpl]

    // Mocking the correct prompt used in the menu method
    when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("1")
    when(mockRepo.createTable()).thenReturn("Table Created")

    val x = new Crud_Operations {
      override val repo = mockRepo
      override val userInput_Object: ConsoleUserInput = mockConsoleUserInput
      override val logger: Logger = mockLogger
    }

    x.menu()

    verify(mockConsoleUserInput).readLine("Enter your choice:")
    verify(mockRepo).createTable()
  }
}
