package crud_op.UnitTesting

import crud_op.Service.{DatabaseConnection, GenerateRandomData, Start}
import org.mockito.Mockito.{doNothing,when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.File
import java.sql.Connection

class StartTest extends FlatSpec with MockitoSugar with Matchers {

  val mockFile: File = mock[File]
  val mockLogger: Logger = mock[Logger]
  val mockcsvFilePath: String = "C:\\Users\\brahmananda Rao\\Desktop\\data2.csv"
  val mockDatabaseConnection: DatabaseConnection = mock[DatabaseConnection]
  val mockGenerateRandomDataInstance: GenerateRandomData = mock[GenerateRandomData]
  val mockConnection: Connection = mock[Connection]

  it should "connection successful case" in {
    when(mockDatabaseConnection.getConnection).thenReturn(mockConnection)
    doNothing().when(mockGenerateRandomDataInstance).generateData(mockcsvFilePath, mockFile)

    val obj = new Start{
      override val file = mockFile
      override val csvFilePath: String = mockcsvFilePath
    }
    obj.check()

  }

/*it should "connection unsuccessful case" in {


    when(mockDatabaseConnection.getConnection).thenReturn(mockConnection)
    val obj = new Start(mockFile, mockcsvFilePath)
    intercept[Exception]{
      obj.check()
    }

  }*/


}

