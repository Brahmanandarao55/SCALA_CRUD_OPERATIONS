/*
package crud_op.UnitTesting

import crud_op.Service.Start
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.File

class StartTest extends FlatSpec with MockitoSugar with Matchers{

  val mockFile: File = mock[File]
  val mockLogger = mock[Logger]
  val mockcsvFilePath = "C:\\Users\\brahmananda Rao\\Desktop\\data2.csv"


  val TestObject = new Start(mockFile,mockcsvFilePath)



}
*/



/*
package crud_op.UnitTesting

import crud_op.Service.{DatabaseConnection, GenerateRandomData, Start}
import org.mockito.ArgumentMatchers.{any, anyString}
import org.mockito.Mockito.{doNothing, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.File
import java.sql.Connection


class StartTest extends FlatSpec with Matchers with MockitoSugar {

  "Start" should "log a success message and call generateData when the connection is successful" in {
    // Arrange
    val mockFile = mock[File]
    val csvFilePath = "dummy/path/to/csv"
    val mockConnection = mock[Connection]
    val mockLogger = mock[Logger]

    val start = new Start(mockFile, csvFilePath) {
      override val logger: Logger = mockLogger
    }

    // Mocking DatabaseConnection.getConnection to return a successful connection
    when(DatabaseConnection.getConnection).thenReturn(mockConnection)

    // Injecting the mock GenerateRandomData instance into the Start class
    val mockGenerateRandomDataInstance = mock[GenerateRandomData]
    doNothing().when(mockGenerateRandomDataInstance).generateData(anyString(), any[File])

    // Act
    start.check()

    // Assert
    verify(mockLogger).info("Connection has been successfully established!")
    verify(mockGenerateRandomDataInstance).generateData(csvFilePath, mockFile)
  }

  it should "log an error message when the connection fails" in {
    // Arrange
    val mockFile = mock[File]
    val csvFilePath = "dummy/path/to/csv"
    val mockLogger = mock[Logger]
    val start = new Start(mockFile, csvFilePath) {
      override val logger: Logger = mockLogger
    }

    val exception = new RuntimeException("Connection error")

    // Mocking DatabaseConnection.getConnection to return a failed connection
    when(DatabaseConnection.getConnection).thenThrow(exception)

    // Act
    start.check()

    // Assert
    verify(mockLogger).error(s"Connection Failed ${exception.getMessage}")
  }
}
*/
