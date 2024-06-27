/*package crud_op

import crud_op.Entity.Person
import crud_op.Repository.PersonRepoImpl
import org.mockito.ArgumentMatchers.anyString

import java.io.File
import scala.io.{BufferedSource, Source}

//import crud_op.main.PersonMain.filepath
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

import java.sql.{Connection, PreparedStatement, ResultSet, Statement}


class UnitTest2  extends FlatSpec with Matchers with MockitoSugar {
  val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\main\\scala\\crud_op\\main\\data.csv"
  val person: Person = Person(1, "John Doe", 25)
  val connection: Connection = mock[Connection]
  val statement: Statement = mock[Statement]
  val preparedStatement: PreparedStatement = mock[PreparedStatement]
  val resultSet: ResultSet = mock[ResultSet]
  val repo: PersonRepoImpl = new PersonRepoImpl {
    override def jdbcConnection(): Connection = connection
  }

  "PersonRepoImpl" should "create person table successfully" in {
    when(connection.createStatement()).thenReturn(statement)
    when(statement.executeUpdate(anyString())).thenReturn(1)

    val result = repo.createPerson()
    result shouldBe "Created Successful"

    verify(connection).createStatement()
    verify(statement).executeUpdate(" CREATE TABLE IF NOT EXISTS persontable (ID INT PRIMARY KEY, NAME VARCHAR(26), AGE INT);")
  }

  "createPerson" should "handle other exceptions during table creation" in {

    // Set up mock behaviors
    when(connection.createStatement()).thenReturn(statement)
    when(statement.executeUpdate(anyString())).thenAnswer(_ => throw new RuntimeException("Some unexpected error"))

    // Verify interactions
    verify(connection, times(1)).createStatement() // Ensure createStatement() is called exactly once
    verify(statement).executeUpdate(anyString()) // Ensure executeUpdate() is called with any string

    // Check result handling
//    result should include("Exception") // Modify this to match the actual behavior of your printStackTrace() call
  }

 "insertPerson" should "inserted data from a file to persontable successfully" in {

     val filePath = "path/to/file.csv"
     val lines = List("1,Nandha,22","2,Kesav,21")
      val result = lines.map{ line =>
        val feild = line.split(",")
        Person(feild(0).toInt,feild(1),feild(2).toInt)

      }

//    var source = mock[Source]
//   when(source.fromFile(any[F ile])).thenReturn(BufferedSource)









  }
















































  /*it should "insert person successfully" in {
    when(connection.prepareStatement(any[String])).thenReturn(preparedStatement)
    when(preparedStatement.executeBatch()).thenReturn(Array(1,1))
    noException should be thrownBy repo.insertPerson(filepath:String)
    verify(preparedStatement).setInt(1, person.Id)
    verify(preparedStatement).setString(2, person.Name)
    verify(preparedStatement).setInt(3, person.Age)
    verify(preparedStatement).executeUpdate()
    verify(connection).close()
  }

  it should "get person successfully" in {
    val connection = mock[Connection]
    val preparedStatement = mock[PreparedStatement]
    val resultSet = mock[ResultSet]
    when(connection.prepareStatement(any[String])).thenReturn(preparedStatement)
    when(preparedStatement.executeQuery()).thenReturn(resultSet)
    when(resultSet.next()).thenReturn(true, false)
    when(resultSet.getInt("ID")).thenReturn(1)
    when(resultSet.getString("NAME")).thenReturn("John Doe")
    when(resultSet.getInt("AGE")).thenReturn(25)


    val repo = new PersonRepoImpl {
      override def jdbcConnection(): Connection = connection
    }

    noException should be thrownBy repo.getPerson(Some(1))
    verify(preparedStatement).setInt(1, 1)
    verify(preparedStatement).executeQuery()
    verify(resultSet, times(2)).next()
    verify(connection).close()
  }

  it should "update person successfully" in {
    val connection = mock[Connection]
    val preparedStatement = mock[PreparedStatement]
    when(connection.prepareStatement(any[String])).thenReturn(preparedStatement)

    val repo = new PersonRepoImpl {
      override def jdbcConnection(): Connection = connection
    }

    val person = Person(1, "John Doe", 30)
    when(preparedStatement.executeUpdate()).thenReturn(1)
    noException should be thrownBy repo.updatePerson(person)
    verify(preparedStatement).setString(1, person.Name)
    verify(preparedStatement).setInt(2, person.Age)
    verify(preparedStatement).setInt(3, person.Id)
    verify(preparedStatement).executeUpdate()
    verify(connection).close()
  }

  it should "delete person successfully" in {
    when(connection.prepareStatement(any[String])).thenReturn(preparedStatement)
    when(preparedStatement.executeUpdate()).thenReturn(1)
    noException should be thrownBy repo.deletePerson(Some(1))
    verify(preparedStatement).setInt(1, 1)
    verify(preparedStatement).executeUpdate()
    verify(connection).close()
  }

  it should "fetch all persons successfully" in {
    when(connection.createStatement()).thenReturn(statement)
    when(statement.executeQuery(any[String])).thenReturn(resultSet)
    when(resultSet.next()).thenReturn(true, false)
    when(resultSet.getInt("ID")).thenReturn(1)
    when(resultSet.getString("NAME")).thenReturn("John Doe")
    when(resultSet.getInt("AGE")).thenReturn(25)

    noException should be thrownBy repo.personTable()
    verify(statement).executeQuery(any[String])
    verify(resultSet, times(2)).next()
    verify(connection).close()
  }


*/
}*/