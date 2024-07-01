package crud_op

import crud_op.Configurations.jdbcConnection
import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import org.mockito.ArgumentMatchers.{any, anyInt, anyString}
import org.mockito.Mockito.{doNothing, times, verify, when}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import java.io.File
import java.sql.{Connection, PreparedStatement, SQLException, Statement}
import scala.io.{BufferedSource, Source}

class UnitTest3 extends FlatSpec with Matchers with MockitoSugar{

  val mockConnection: Connection = mock[Connection]
  val mockStatement: Statement = mock[Statement]
  val mockPreparedStatement: PreparedStatement = mock[PreparedStatement]
  var mockBufferedSource: BufferedSource = mock[BufferedSource]
  val filepath2 = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\main\\scala\\crud_op\\main\\data.csv"

  "createPerson" should "create the table if it does not exist" in {

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenReturn(1)

    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }

    repo.createPerson()
    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(" CREATE TABLE IF NOT EXISTS persontable (ID INT PRIMARY KEY, NAME VARCHAR(26), AGE INT);")
    verify(mockConnection).close()

  }

  "insertPerson" should "insert persons from the file into the database" in {


    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

//    val testDataFilePath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\scala\\crud_op\\testfile.csv"

    val mockBufferedSource = mock[BufferedSource]
    val mockFile = mock[File]
    val FileContent =
      """
        |ID,NAME,AGE
        |1,John,25
        |2,Jane,30
        |3,Bob,35
        |""".stripMargin
    val testFilePath = "mockFilePath"
    when(mockFile.getPath).thenReturn(testFilePath)
    when(Source.fromFile(any[File])).thenReturn(mockBufferedSource)
    when(mockBufferedSource.getLines()).thenReturn(FileContent.lines)

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt())
    doNothing().when(mockPreparedStatement).setString(anyInt(), anyString())
    doNothing().when(mockPreparedStatement).addBatch()
    doNothing().when(mockPreparedStatement).executeBatch()
    doNothing().when(mockConnection).setAutoCommit(false)
    doNothing().when(mockConnection).commit()
    doNothing().when(mockConnection).rollback()
    doNothing().when(mockConnection).close()

    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }


    repo.insertPerson(mockFile.getPath)
    verify(mockBufferedSource).getLines()
    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement, times(3)).addBatch() // Assuming 3 lines in FileContent
    verify(mockPreparedStatement).executeBatch()
    verify(mockConnection).commit()
    verify(mockConnection).close()










  }








}
