package crud_op.UnitTesting

import crud_op.Service.ConsoleUserInput
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{doNothing, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.ByteArrayInputStream

class ConsoleUserInputTest extends FlatSpec with Matchers with MockitoSugar {

  it should "dsf" in {
    val mockPrompt = "Enter Number:"
    val mockLogger = mock[Logger]
    val actual = "1"



    val consoleuserInput = new ConsoleUserInput

    val result = Console.withIn(new ByteArrayInputStream(actual.getBytes)) {
      consoleuserInput.readLine(mockPrompt)
    }


    result shouldBe actual
  }

}
