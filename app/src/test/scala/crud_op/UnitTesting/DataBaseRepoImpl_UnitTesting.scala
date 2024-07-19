package crud_op.UnitTesting

import crud_op.Repository.DataBaseRepoImpl
import org.apache.poi.ss.formula.functions.Intercept
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{doNothing, doReturn, times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar

import java.io.File
import java.sql.{Connection, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.Source

class DataBaseRepoImpl_UnitTesting extends FlatSpec with MockitoSugar with Matchers {


  "Table" should "create if it doesn't exists" in {
    val mockConnection: Connection = mock[Connection]
    val mockStatement: Statement = mock[Statement]
    val repo: DataBaseRepoImpl = new DataBaseRepoImpl {
      override val connection: Connection = mockConnection
    }
    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeUpdate(anyString())).thenReturn(1)

    val result = repo.createTable()
    val actual = "Table Created Successfully"

    verify(mockConnection).createStatement()
    verify(mockStatement).executeUpdate(
      """
        |CREATE TABLE PERSONTABLE (ID INT NOT NULL IDENTITY PRIMARY KEY,
        |NAME VARCHAR(26),
        |AGE INT,
        |SALARY INT,
        |PROFESSION VARCHAR(40),
        |LOCATION VARCHAR(30),
        |RATING FLOAT,
        |BLOCK CHAR(3));
        |""".stripMargin)


    actual shouldBe result

  }

  "Exception" should "raise when table is already exists" in {

    val mockConnection: Connection = mock[Connection]
    val repo: DataBaseRepoImpl = new DataBaseRepoImpl {
      override val connection: Connection = mockConnection
    }

    when(mockConnection.createStatement()).thenThrow(new SQLException("Table is already exists"))

    val actual = "Table is not Created"

    val result = repo.createTable()


    result shouldBe actual
  }


  "insertion data" should "come from csv file and insert into database" in {
    val mockConnection: Connection = mock[Connection]
    val mockPreparedStatement: PreparedStatement = mock[PreparedStatement]
    val mockSource: Source = mock[Source]
    val repo: DataBaseRepoImpl = new DataBaseRepoImpl {
      override val connection: Connection = mockConnection
    }

    val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\resources\\testfile.csv"

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeBatch()).thenReturn(Array(1))
    when(mockSource.getLines()).thenReturn(Iterator("NAME,AGE,SALARY,PROFESSION,LOCATION", "Nandha,22,45000,Developer,Hyderabad"))


    val result = repo.insertData(filepath)


    verify(mockConnection).prepareStatement(
      """
        |INSERT INTO PERSONTABLE (NAME, AGE, SALARY, PROFESSION, LOCATION, RATING, BLOCK) values(?,?,?,?,?,?,?);
        |""".stripMargin)
    verify(mockPreparedStatement, times(1)).setString(1, "Nandha")
    verify(mockPreparedStatement, times(1)).setInt(2, 22)
    verify(mockPreparedStatement, times(1)).setInt(3, 45000)
    verify(mockPreparedStatement, times(1)).setString(4, "Developer")
    verify(mockPreparedStatement, times(1)).setString(5, "Hyderabad")
    verify(mockPreparedStatement, times(1)).addBatch()
    verify(mockPreparedStatement, times(1)).executeBatch()
    verify(mockConnection).commit()


    result should be("Data Insertion Successfully")

  }

  "Insertion" should "fail when SQL syntax error happened" in {
    val mockConnection: Connection = mock[Connection]
    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("SQL Syntax Error"))

    val repo: DataBaseRepoImpl = new DataBaseRepoImpl {
      override val connection: Connection = mockConnection
    }

    val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\resources\\testfile.csv"


    val actual = "Data Insertion Failed Here"

    val result = repo.insertData(filepath)

    verify(mockConnection).rollback()

    result shouldBe actual
  }

/*  "Data" should "retrieve by ID" in {
    val mockConnection: Connection = mock[Connection]
    val mockPreparedStatement: PreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery(anyString())).thenReturn(mockResultSet)

    when(mockResultSet.next()).thenReturn(true)
    when(mockResultSet.getInt("ID")).thenReturn(1)
    when(mockResultSet.getString("NAME")).thenReturn("Nandha")
    when(mockResultSet.getInt("AGE")).thenReturn(22)
    when(mockResultSet.getInt("SALARY")).thenReturn(45000)
    when(mockResultSet.getString("PROFESSION")).thenReturn("Developer")
    when(mockResultSet.getString("LOCATION")).thenReturn("Hyderabad")

    println("Creating repo...")
    val repo: DataBaseRepoImpl = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }
    println("Repo created: " + repo)



    val result = repo.getDataById(Some(1))


    val actual = "Getting Data Successfully"




    verify(mockConnection).prepareStatement(anyString())


    verify(mockPreparedStatement).setInt(1,1)
    verify(mockPreparedStatement).executeQuery()
    verify(mockResultSet).next()
    verify(mockResultSet).getInt("ID")
    verify(mockResultSet).getString("NAME")
    verify(mockResultSet).getInt("AGE")
    verify(mockResultSet).getInt("SALARY")
    verify(mockResultSet).getString("PROFESSION")
    verify(mockResultSet).getString("LOCATION")


    result should be(actual)

  }*/


 /* "When Data is None it" should "raise Exception" in {

    val mockConnection = mock[Connection]

    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Error"))

    val result = intercept[SQLException] {
      repo.getDataById(None)
    }

    println(result)

    verify(mockConnection).prepareStatement(anyString())

  }
*/

}