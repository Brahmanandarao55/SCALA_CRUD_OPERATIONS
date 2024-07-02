package crud_op
import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import java.io.File
import java.sql.{Connection, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.Source

class UnitTest2 extends FlatSpec with Matchers with MockitoSugar {

  "createPerson" should "create the table if it does not exist" in {

    val mockConnection: Connection = mock[Connection]
    val mockStatement: Statement = mock[Statement]
    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenReturn(1)

    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }
    repo.createPerson()

    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(" CREATE TABLE IF NOT EXISTS persontable (ID INT PRIMARY KEY, NAME VARCHAR(26), AGE INT);")
    verify(mockConnection).close()

  }

  it should "handle SQLException and not propagate it" in {

    val mockConnection: Connection = mock[Connection]
    val mockStatement: Statement = mock[Statement]

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenThrow(new SQLException("hi"))



    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }

    repo.createPerson()

    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(anyString())
    verify(mockConnection).close()
  }


   "insertPerson" should "insert persons from the file into the database" in {

      val mockConnection = mock[Connection]
     val mockPreparedStatement = mock[PreparedStatement]

     when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

     val testDataFilePath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\scala\\crud_op\\testfile.csv"

     val repo = new PersonRepoImpl {
       override val con: Connection = mockConnection
     }
    repo.insertPerson(testDataFilePath)
    val buffersource = Source.fromFile(new File(testDataFilePath))
    val lines = buffersource.getLines().drop(1)
    val person = lines.map { line =>
      val fields = line.split(",").map(_.trim)
      Person(fields(0).toInt, fields(1), fields(2).toInt)
    }.toList

   verify(mockConnection).prepareStatement( """
                                              |INSERT INTO persontable values(?,?,?);
                                              |""".stripMargin)


     verify(mockPreparedStatement, times(4)).addBatch()
     verify(mockPreparedStatement).executeBatch()
     verify(mockConnection).commit()

   }

  "getPerson" should "retrieve and print person details by ID" in {
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(true).thenReturn(false)
    when(mockResultSet.getInt("ID")).thenReturn(1)
    when(mockResultSet.getString("NAME")).thenReturn(anyString())
    when(mockResultSet.getInt("AGE")).thenReturn(25)


    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }
    repo.getPerson(Some(1))

    verify(mockPreparedStatement).setInt(1, 1)
    verify(mockPreparedStatement).executeQuery()
    verify(mockResultSet).getInt("ID")
    verify(mockResultSet).getString("NAME")
    verify(mockResultSet).getInt("AGE")
    verify(mockConnection).close()
  }

  "updatePerson" should "update the person details in the database" in {
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)


    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }
    val person = Person(1, "John", 26)
    repo.updatePerson(person)

    verify(mockPreparedStatement).setString(1, "John")
    verify(mockPreparedStatement).setInt(2, 26)
    verify(mockPreparedStatement).setInt(3, 1)
    verify(mockPreparedStatement).executeUpdate()
    verify(mockConnection).close()

  }

  "deletePerson" should "delete the person from the database by ID" in {
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)


    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }
    repo.deletePerson(Some(1))

    verify(mockPreparedStatement).setInt(1, 1)
    verify(mockPreparedStatement).executeUpdate()
    verify(mockConnection).close()
  }

  "personTable" should "retrieve and print all person details" in {
    val mockConnection = mock[Connection]
    val mockStatement = mock[Statement]
    val mockResultSet = mock[ResultSet]

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(true).thenReturn(false)
    when(mockResultSet.getInt("ID")).thenReturn(1)
    when(mockResultSet.getString("NAME")).thenReturn("John")
    when(mockResultSet.getInt("AGE")).thenReturn(25)

    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }

    repo.personTable()

    verify(mockStatement).executeQuery("SELECT * FROM persontable")
    verify(mockResultSet).getInt("ID")
    verify(mockResultSet).getString("NAME")
    verify(mockResultSet).getInt("AGE")
    verify(mockConnection).close()
  }








}
