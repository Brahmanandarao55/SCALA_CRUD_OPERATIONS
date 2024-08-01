package crud_op.UnitTesting

import com.opencsv.CSVWriter
import com.opencsv.bean.processor.PreAssignmentProcessor
import crud_op.Entity.Person
import crud_op.Repository.DataBaseRepoImpl
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{doNothing, times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar

import java.io.FileWriter
import java.sql.{Connection, PreparedStatement, ResultSet, ResultSetMetaData, SQLException, Statement}
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
    when(mockSource.getLines()).thenReturn(Iterator("NAME,AGE,SALARY,PROFESSION,LOCATION,RATING,BLOCK", "Nandha,22,45000,Developer,Hyderabad,3.5f,A"))


    val result = repo.insertData(filepath)

    val person = Person("Nandha",22,45000,"Developer","Hyderabad",3.5f,'A')
    verify(mockConnection).prepareStatement(
      """
        |INSERT INTO PERSONTABLE (NAME, AGE, SALARY, PROFESSION, LOCATION, RATING, BLOCK) values(?,?,?,?,?,?,?);
        |""".stripMargin)
    verify(mockPreparedStatement, times(1)).setString(1, person.Name)
    verify(mockPreparedStatement, times(1)).setInt(2, person.Age)
    verify(mockPreparedStatement, times(1)).setInt(3, person.Salary)
    verify(mockPreparedStatement, times(1)).setString(4, person.Profession)
    verify(mockPreparedStatement, times(1)).setString(5, person.Location)
    verify(mockPreparedStatement,times(1)).setFloat(6, person.Rating)
    verify(mockPreparedStatement, times(1)).setString(7, String.valueOf(person.Block))
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

  "Data" should "retrieve by Id" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(true)

    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    val result = repo.getDataById(Some(1))

    val actual = "Getting Data Successfully"

    verify(mockConnection).prepareStatement(anyString())
    verify(mockResultSet).next()

    result shouldBe actual
  }


  "When Data is None it" should "raise Exception" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

   intercept[IllegalArgumentException] {
      repo.getDataById(None)
    }


    verify(mockConnection).prepareStatement(anyString())



  }


  "When Id is invalid it" should "raise Exception" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    val mockResultSet = mock[ResultSet]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet)
    when(mockResultSet.next()).thenReturn(false)

    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    intercept[IllegalArgumentException] {
      repo.getDataById(Some(-1))
    }

    verify(mockConnection).prepareStatement(anyString())
    verify(mockResultSet).next()


  }


  "SQL Exception" should "be raised when sql syntactical error happen" in {

    val mockConnection: Connection = mock[Connection]
    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("SQL Syntax Error"))

    val repo: DataBaseRepoImpl = new DataBaseRepoImpl {
      override val connection: Connection = mockConnection
    }

    repo.getDataById(Some(1))

  }



  "Data" should "be updated with updated method" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeUpdate()).thenReturn(1).thenReturn(-1)


    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    val person = Person("Kesav",22, 45000,"Developer","Rajahmundry", 3.5f, 'A')

    val actual = "ROW WITH ID 1 IS UPDATED"
    val result = repo.updateData(person,1)


    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).setString(1,"Kesav")
    verify(mockPreparedStatement).setInt(2, 22)
    verify(mockPreparedStatement).setInt(3, 45000)
    verify(mockPreparedStatement).setString(4, "Developer")
    verify(mockPreparedStatement).setString(5,"Rajahmundry")
    verify(mockPreparedStatement).setFloat(6, 3.5f)
    verify(mockPreparedStatement).setString(7, String.valueOf('A'))

    result shouldBe actual
  }



  "Exception" should "be raised when error in SQL syntax" in {
    val mockConnection = mock[Connection]

    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("SQL Error"))

    val person = Person("Kesav",22, 45000,"Developer","Rajahmundry", 3.5f, 'A')
    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }
    val result = repo.updateData(person,1)

    val actual = "ROW WITH ID 1 IS NOT UPDATED"


    result shouldBe actual
  }


  "Data" should "deleted by Id" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeUpdate()).thenReturn(1)


    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    val result = repo.deleteData(Some(1))

    val actual = "ROW DELETED SUCCESSFULLY"

    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).executeUpdate()

    result shouldBe actual
  }

  "When delete Data is None it" should "raise Exception" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    intercept[IllegalArgumentException] {
      repo.deleteData(None)
    }


    verify(mockConnection).prepareStatement(anyString())

  }

  "SQLException" should "be raised when error in SQL syntax" in {
    val mockConnection = mock[Connection]

    when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("SQL Error"))

    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }
    val result = repo.deleteData(Some(-1))

    val actual = "ROW WITH ID Some(-1) IS NOT DELETED "


    result shouldBe actual
  }


  "Data" should "deleted by Id unsuccessfully" in {

    val mockConnection = mock[Connection]
    val mockPreparedStatement = mock[PreparedStatement]

    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement)
    when(mockPreparedStatement.executeUpdate()).thenReturn(0)


    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    repo.deleteData(Some(1))


    verify(mockConnection).prepareStatement(anyString())
    verify(mockPreparedStatement).executeUpdate()


  }

  "All data" should "retrieve by the getAll method" in {

    /*val output = "Nandha.csv"
    val mockConnection = mock[Connection]
    val mockStatement = mock[Statement]
    val mockResultSet = mock[ResultSet]
    val  mockResultSetMetaData = mock[ResultSetMetaData]
    val mockCSVWrite = mock[CSVWriter]
    val mockFileWriter = mock[FileWriter]

    val actual = "The output file name is output.csv"
    val row = 1

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
    when(mockResultSet.getMetaData).thenReturn(mockResultSetMetaData)
    when(mockResultSetMetaData.getColumnCount).thenReturn(1)
    when(mockResultSet.next()).thenReturn(true)


    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    val result = repo.getAll


    result shouldBe actual

    verify(mockConnection).createStatement()
    verify(mockStatement).executeQuery(anyString())
    verify(mockResultSet).getMetaData

  }*/

    val mockConnection = mock[Connection]
    val mockStatement = mock[Statement]
    val mockResultSet = mock[ResultSet]
    val mockMetaData = mock[ResultSetMetaData]
    val filepath = "nandha.csv"
    val mockFileWriter = new FileWriter(filepath)
    val csvWriter = new CSVWriter(mockFileWriter)

    when(mockConnection.createStatement()).thenReturn(mockStatement)
    when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet)
    when(mockResultSet.getMetaData).thenReturn(mockMetaData)


    when(mockMetaData.getColumnCount).thenReturn(2)
    when(mockResultSet.next).thenReturn(true, true, false)


    val repo = new DataBaseRepoImpl{
      override val connection: Connection = mockConnection
    }

    val result = repo.getAll

    val actual = "The output file name is output.csv"

    result shouldBe actual


  }


}