/*
package crud_op


import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import org.mockito.ArgumentMatchers.{any, anyInt, anyString}
import org.mockito.Mockito.{doNothing, times, verify, when}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

import java.io.File
import java.sql.{Connection, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io. Source


class UnitTest2 extends FlatSpec with Matchers with MockitoSugar {

  "Table" should "created if it does not exist" in {

    val mockConnection: Connection = mock[Connection]
    val mockStatement: Statement = mock[Statement]
    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenReturn(1)

    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }
    repo.createPerson()

    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(
      """
        |CREATE TABLE PERSONTABLE (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        |NAME VARCHAR(26),
        |AGE INT,
        |SALARY INT,
        |PROFESSION VARCHAR(40),
        |LOCATION VARCHAR(30));
        |""".stripMargin)
    verify(mockConnection).close()

  }


  it should "handle SQLException during table creation" in {

    val mockConnection: Connection = mock[Connection]
    val mockStatement: Statement = mock[Statement]

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenThrow(new SQLException("SQL Exception"))

    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }
   val result = repo.createPerson()

    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(
      """
        |CREATE TABLE PERSONTABLE(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        |NAME VARCHAR(26),
        |AGE INT,
        |SALARY INT,
        |PROFESSION VARCHAR(40),
        |LOCATION VARCHAR(30));
        |""".stripMargin)
    result should be ("Table is not Created")

  }

  "insertion data" should "come from CSV and inserted into  " in {

    val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\scala\\crud_op\\testfile.csv"
    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockFile = mock[File]
    val mockSource = mock[Source]
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeBatch()).thenReturn(Array(1))
    when(mockSource.getLines()).thenReturn(Iterator("NAME,AGE,SALARY,PROFESSION,LOCATION","Nandha,22,45000,Developer,Hyderabad"))
    when(mockFile.exists()).thenReturn(true)
    when(mockFile.isFile).thenReturn(true)

    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }
    val result = repo.insertPerson(filepath)


    verify(mockPreparedStatement, times(1)).setString(1, "Nandha")
    verify(mockPreparedStatement, times(1)).setInt(2, 22)
    verify(mockPreparedStatement, times(1)).setInt(3, 45000)
    verify(mockPreparedStatement, times(1)).setString(4, "Developer")
    verify(mockPreparedStatement, times(1)).setString(5, "Hyderabad")
    verify(mockPreparedStatement,times(1)).addBatch()
    verify(mockPreparedStatement).executeBatch()
    verify(mockConnection).commit()

    result should be ("Data Insertion Successfully")



  }

  it should "handle SQLException during Insertion" in{

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\scala\\crud_op\\testfile.csv"

    when(mockConnection.prepareStatement(anyString)).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeBatch()).thenThrow(new SQLException("SQL EXCEPTION"))

    val repo = new PersonRepoImpl {
      override val con: Connection = mockConnection
    }

    repo.insertPerson(filepath)

    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).executeBatch()

  }

  "GetPerson" should "retrieve and print person details by ID" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(true)
    when(mockResultSet.getInt("ID")).thenReturn(1)
    when(mockResultSet.getString("NAME")).thenReturn("Nandha")
    when(mockResultSet.getInt("AGE")).thenReturn(22)
    when(mockResultSet.getInt("SALARY")).thenReturn(45000)
    when(mockResultSet.getString("PROFESSION")).thenReturn("Developer")
    when(mockResultSet.getString("LOCATION")).thenReturn("Hyderabad")


    val repo = new PersonRepoImpl{
      override val con: Connection = mockConnection
    }

    repo.getPerson(Some(1))



    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).setInt(1, 1)
    verify(mockPreparedStatement).executeQuery()
    verify(mockResultSet).next()
    verify(mockResultSet).getInt("ID")
    verify(mockResultSet).getString("NAME")
    verify(mockResultSet).getInt("AGE")
    verify(mockResultSet).getInt("SALARY")
    verify(mockResultSet).getString("PROFESSION")
    verify(mockResultSet).getString("LOCATION")

    verify(mockConnection).close()


  }


}

/*
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

}*/
*/
