package crud_op

import crud_op.Configurations.jdbcConnection
import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import org.mockito.ArgumentMatchers.{any, anyInt, anyString}
import org.mockito.Mockito.{doNothing, times, verify, when}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import java.io.File
import java.sql.{Connection, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.{BufferedSource, Source}


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
    val mockPreparedStatement = mock[PreparedStatement]
    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenThrow(new SQLException("SQL Exception"))
    when(mockPreparedStatement.setInt(1, 1)).thenThrow(new SQLException("SQL error"))



    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }


    repo.createPerson()


    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(anyString())


  }


/*   "insertPerson" should "insert persons from the file into the database" in {

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

   }*/


  "Connection Failed Error" should "Raised when Connection Failed for InsertPerson" in{

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\resources\\test.csv"
    when(mockConnection.prepareStatement(anyString)).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeBatch()).thenThrow(new SQLException("SQL EXCEPTION"))

    val repo = new PersonRepoImpl{
      override val con:Connection = mockConnection
    }

    repo.insertPerson(filepath)

    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).executeBatch()

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
    when(mockResultSet.getInt("AGE")).thenReturn(anyInt())


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

  "Connection Failed Error" should "Raised when Connection Failed" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.setInt(1, 1)).thenThrow(new SQLException("SQL error"))


    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }

    repo.getPerson(Some(1))

    verify(mockConnection).prepareStatement(anyString())



  }


  "When Id is None method" should "Raise Illegal Exception" in {
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }
    intercept[IllegalArgumentException]{
      repo.getPerson(None)

    }

    verify(mockConnection).prepareStatement(anyString())

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


  "Connection Failed Error" should "Raised when Connection Failed at UpdatePerson method" in{

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]


    when(mockConnection.prepareStatement(anyString)).thenReturn(mockPreparedStatement)
    val person = Person(1,"Nandha",22)
    when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("SQL EXCEPTION"))

    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }

    repo.updatePerson(person)

    verify(mockConnection).prepareStatement(anyString())

    verify(mockPreparedStatement).executeUpdate()

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

  "Connection Failed Error" should "Raised when Connection Failed at DeletePerson method" in{

    val mockConnection= mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("SQL EXCEPTION"))

    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }

    repo.deletePerson(Some(1))

    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).executeUpdate()

  }


  "When Id is None method for Delete" should "Raise Illegal Exception" in {
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)

    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }
    intercept[IllegalArgumentException]{
      repo.deletePerson(None)

    }
    verify(mockConnection).prepareStatement(anyString())

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


  "Connection Failed Error" should "Raised when Connection Failed at PersonTable method" in{

    val mockConnection = mock[Connection]
    val mockStatement = mock[Statement]

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeQuery(anyString())).thenThrow(new SQLException("SQL EXCEPTION"))


    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }

    repo.personTable()

    verify(mockConnection).createStatement()
    verify(mockStatement).executeQuery("SELECT * FROM persontable")


  }

}
