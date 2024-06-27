package crud_op.Repository
import crud_op.Entity.Person

import java.io.File
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.Source

class PersonRepoImpl extends PersonRepo {

  private val url = "jdbc:mysql://localhost:3306/person"
  private val host = "root"
  private val pass = "root"
  private val driver = "com.mysql.cj.jdbc.Driver"
  private val connection:Connection =jdbcConnection()



  override def jdbcConnection(): Connection = {
    Class.forName(driver)
    DriverManager.getConnection(url,host,pass)
  }

  override def createPerson(): String = {
    try {
      val query: String = " CREATE TABLE IF NOT EXISTS persontable (ID INT PRIMARY KEY, NAME VARCHAR(26), AGE INT);"
      val statement: Statement = connection.createStatement()
      statement.executeUpdate(query)

    }
    catch {
      case e:Exception => e.printStackTrace()

    }
    "Created Successful"

  }

  override def insertPerson(filepath: String): String = {
    try {
      val startTime = System.currentTimeMillis()
      val bufferedSource = Source.fromFile(new File(filepath))
      val lines = bufferedSource.getLines().drop(1)
      val person = lines.map { line =>
        val fields = line.split(",").map(_.trim)
        Person(fields(0).toInt, fields(1), fields(2).toInt)
      }.toList
      connection.setAutoCommit(false)
      val query: String =
        """
          |INSERT INTO persontable values(?,?,?);
          |""".stripMargin
      val statement: PreparedStatement = connection.prepareStatement(query)
      val batchSize = 1000
      var count = 0
      person.foreach { persons =>
        statement.setInt(1, persons.Id)
        statement.setString(2, persons.Name)
        statement.setInt(3, persons.Age)
        statement.addBatch()
        count+=1
      }
      if (count % batchSize == 0) {
        statement.executeBatch()
      }
      statement.executeBatch()
      bufferedSource.close()
      connection.commit()

      val endTime = System.currentTimeMillis()
      val totalTimeSeconds = (endTime - startTime) / 1000.0
      println(s"Total insertion time: ${totalTimeSeconds} seconds")

//        connection.close()
    }
    catch {
      case e:Exception => e.printStackTrace()
        connection.rollback()
    }

//      connection.close()
      "Data Inserted Successfully!!!!!!!!!!!!!!!"

  }

  override def getPerson(id: Option[Int]): Unit = {
    try{
    val query =
      """
        |SELECT * FROM persontable WHERE ID = ?;
        |""".stripMargin
       val statement:PreparedStatement = connection.prepareStatement(query)
       statement.setInt(1,id.getOrElse(throw new IllegalArgumentException("GIVEN ID IS INVALID")))
       val resultset: ResultSet = statement.executeQuery()
       while (resultset.next()) {
        val id = resultset.getInt("ID")
        val name = resultset.getString("NAME")
        val age = resultset.getInt("AGE")
        println(s"$id, $name, $age")
//         connection.close()
      }

  }
    catch {
      case e:Exception => e.printStackTrace()
    }
  }

  override def updatePerson(person: Person): Unit = {
    try {
      val query: String = "UPDATE personTable SET NAME = ?, AGE = ? WHERE ID = ?"
      val statement: PreparedStatement = connection.prepareStatement(query)
      statement.setString(1, person.Name)
      statement.setInt(2, person.Age)
      statement.setInt(3, person.Id)
      statement.executeUpdate()
      println(s"ROW WITH ${person.Id} IS UPDATED")
//      connection.close()
    }
    catch {
      case e:Exception => e.printStackTrace()
    }
  }

  override def deletePerson(id: Option[Int]): Unit = {
    try {
      val query: String =
        """
          |DELETE FROM persontable WHERE ID = ?;
          |""".stripMargin
      val statement: PreparedStatement = connection.prepareStatement(query)
      statement.setInt(1, id.getOrElse(throw new IllegalArgumentException("ENTERED ID IS NOT VALID")))
      statement.executeUpdate()

      println(s"ROW WITH ID $id IS DELETED")
//      connection.close()

    }
    catch {
      case e:Exception => e.printStackTrace()
    }


  }

  override def personTable(): Unit = {
    try{
      val query = "SELECT * FROM persontable"
      val statement = connection.createStatement()
      val resultSet:ResultSet = statement.executeQuery(query)
      while(resultSet.next()){
         val id = resultSet.getInt("ID")
         val name = resultSet.getString("NAME")
         val age = resultSet.getInt("AGE")
         println(s"ID is $id and NAME is $name and AGE is $age")
      }
//      connection.close()
    }
    catch {
      case e:Exception => e.printStackTrace()
    }
  }
}
