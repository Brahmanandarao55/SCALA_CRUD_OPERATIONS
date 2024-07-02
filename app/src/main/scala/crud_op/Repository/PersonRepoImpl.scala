package crud_op.Repository
import crud_op.Configurations.jdbcConnection
import crud_op.Entity.Person

import java.io.File
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.Source

class PersonRepoImpl extends PersonRepo {

  val dbobj = new jdbcConnection
  val con: Connection = dbobj.connection
  override def createPerson(): Unit = {
    try {
      val query: String = " CREATE TABLE IF NOT EXISTS persontable (ID INT PRIMARY KEY, NAME VARCHAR(26), AGE INT);"
      val statement: Statement = con.createStatement()
      statement.executeUpdate(query)

    }
    catch {
      case s:SQLException =>
        s.printStackTrace()
    }
    finally {
      con.close()
    }


  }

  override def insertPerson(filepath: String): Unit = {
    try {
      val startTime = System.currentTimeMillis()
      val bufferedSource = Source.fromFile(new File(filepath))
      val lines = bufferedSource.getLines().drop(1)
      val person = lines.map { line =>
        val fields = line.split(",").map(_.trim)
        Person(fields(0).toInt, fields(1), fields(2).toInt)
      }.toList
      con.setAutoCommit(false)
      val query: String =
        """
          |INSERT INTO persontable values(?,?,?);
          |""".stripMargin
      val statement: PreparedStatement = con.prepareStatement(query)
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
      con.commit()

      val endTime = System.currentTimeMillis()
      val totalTimeSeconds = (endTime - startTime) / 1000.0
      println(s"Total insertion time: ${totalTimeSeconds} seconds")

    }
    catch {
      case e:Exception => e.printStackTrace()
        con.rollback()
    }
    finally {
      con.close()
    }


  }

  override def getPerson(id: Option[Int]): Unit = {
    try{
    val query =
      """
        |SELECT * FROM persontable WHERE ID = ?;
        |""".stripMargin
       val statement:PreparedStatement = con.prepareStatement(query)
       statement.setInt(1,id.getOrElse(throw new IllegalArgumentException("GIVEN ID IS INVALID")))
       val resultset: ResultSet = statement.executeQuery()
       while (resultset.next()) {
        val id = resultset.getInt("ID")
        val name = resultset.getString("NAME")
        val age = resultset.getInt("AGE")
        println(s"$id, $name, $age")

      }

  }
    catch {
      case e:Exception => e.printStackTrace()
    }
    finally {
      con.close()
    }
  }

  override def updatePerson(person: Person): Unit = {
    try {
      val query: String = "UPDATE personTable SET NAME = ?, AGE = ? WHERE ID = ?"
      val statement: PreparedStatement = con.prepareStatement(query)
      statement.setString(1, person.Name)
      statement.setInt(2, person.Age)
      statement.setInt(3, person.Id)
      statement.executeUpdate()
      println(s"ROW WITH ${person.Id} IS UPDATED")

    }
    catch {
      case e:Exception => e.printStackTrace()
    }
    finally {
      con.close()
    }
  }

  override def deletePerson(id: Option[Int]): Unit = {
    try {
      val query: String =
        """
          |DELETE FROM persontable WHERE ID = ?;
          |""".stripMargin
      val statement: PreparedStatement = con.prepareStatement(query)
      statement.setInt(1, id.getOrElse(throw new IllegalArgumentException("ENTERED ID IS NOT VALID")))
      statement.executeUpdate()

      println(s"ROW WITH ID $id IS DELETED")


    }
    catch {
      case e:Exception => e.printStackTrace()
    }
   finally {
     con.close()
   }

  }

  override def personTable(): Unit = {
    try{
      val query = "SELECT * FROM persontable"
      val statement = con.createStatement()
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
    finally {
      con.close()
    }
  }
}
