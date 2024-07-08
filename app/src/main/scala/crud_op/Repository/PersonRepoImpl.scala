package crud_op.Repository
import com.typesafe.config.{Config, ConfigFactory}
import crud_op.Entity.Person
import org.slf4j.{Logger, LoggerFactory}
import java.io.File
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.Source

class PersonRepoImpl extends PersonRepo {

  private val configurations: Config = ConfigFactory.load().getConfig("database")
//  println(configurations.root().render())
  private val url: String = configurations.getString("url")
  private val host: String = configurations.getString("host")
  private val password: String = configurations.getString("pass")
  private val driver: String = configurations.getString("driver")

    Class.forName(driver)
    private val con = DriverManager.getConnection(url,host,password)
  private val logger: Logger = LoggerFactory.getLogger(getClass)



  override def createPerson(): String = {

    try {

      val query: String =
        """
          |CREATE TABLE persontable (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
          |NAME VARCHAR(26),
          |AGE INT);""".stripMargin
      val statement: Statement = con.createStatement()
      statement.executeUpdate(query)

      "Table Created Successfully"
    }
    catch {

      case s:SQLException =>
        s.printStackTrace()

        "Table is not Created"
    }
    finally {

      con.close()

    }


  }

  override def insertPerson(filepath: String): String = {
    try {
      val startTime = System.currentTimeMillis()
      val bufferedSource = Source.fromFile(new File(filepath))
      val lines = bufferedSource.getLines().drop(1)
      val person = lines.map { line =>
        val fields = line.split(",").map(_.trim)
        Person(fields(0), fields(1).toInt)
      }.toList
      con.setAutoCommit(false)
      val query: String =
        """
          |INSERT INTO persontable (NAME, AGE) values(?,?);
          |""".stripMargin
      val statement: PreparedStatement = con.prepareStatement(query)
      val batchSize = 1000
      var count = 0
      person.foreach { persons =>
        statement.setString(1, persons.Name)
        statement.setInt(2, persons.Age)
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
      logger.info(s"Total insertion time: ${totalTimeSeconds} seconds")
      "Data Insertion Successfully"

    }
    catch {
      case e:SQLException => e.printStackTrace()
        if(con != null) con.rollback()
        "Data Insertion Failed Here"
    }
    finally {
      if(con != null) con.close()
    }


  }

  override def getPerson(id: Option[Int]): String = {
    try{
    val query =
      """
        |SELECT * FROM persontable WHERE ID = ?;
        |""".stripMargin
       val statement:PreparedStatement = con.prepareStatement(query)
       statement.setInt(1,id.getOrElse(throw new IllegalArgumentException("GIVEN ID IS INVALID")))
       val resultset: ResultSet = statement.executeQuery()
       if(resultset.next()) {
        val id = resultset.getInt("ID")
        val name = resultset.getString("NAME")
        val age = resultset.getInt("AGE")
        logger.info(s"id is $id")
        logger.info(s"name is $name")
        logger.info(s"age is $age")
         "Getting Data Successfully"

      }
      else{

        throw new IllegalArgumentException(s"Person with ID $id not found")
      }


  }
    catch {
      case e:SQLException => e.printStackTrace()
        "Getting Data Unsuccessfully"
    }
    finally {
      con.close()
    }
  }

  override def updatePerson(person: Person, id:Int): String = {
    try {
      val query: String = "UPDATE personTable SET NAME = ?, AGE = ? WHERE ID = ?"
      val statement: PreparedStatement = con.prepareStatement(query)
      statement.setString(1, person.Name)
      statement.setInt(2, person.Age)
      statement.setInt(3, id)
      statement.executeUpdate()
//      println(s"ROW WITH $id IS UPDATED")
      s"ROW WITH $id IS UPDATED"
    }
    catch {
      case e:SQLException => e.printStackTrace()
        s"ROW WITH $id IS NOT UPDATED"
    }
    finally {
      con.close()
    }
  }

    override def deletePerson(id: Option[Int]): String = {
      try {
        val query: String =
          """
            |DELETE FROM persontable WHERE ID = ?;
            |""".stripMargin
        val statement: PreparedStatement = con.prepareStatement(query)
        statement.setInt(1, id.getOrElse(throw new IllegalArgumentException("ENTERED ID IS NOT VALID")))
        val rowdelete = statement.executeUpdate()
        if(rowdelete > 0 )
        "ROW DELETED SUCCESSFULLY"
        else
          throw new SQLException("ROW IS NOT DELETED SUCCESSFULLY")
      }
      catch {
        case e:SQLException => e.printStackTrace()
          s"ROW WITH ID $id IS NOT DELETED "

      }
     finally {
       con.close()
     }

    }

  override def getAll(): String = {

    try{
      val query = "SELECT * FROM persontable"
      val statement = con.createStatement()
      val resultSet:ResultSet = statement.executeQuery(query)
      while(resultSet.next()){
         val id = resultSet.getInt("ID")
         val name = resultSet.getString("NAME")
         val age = resultSet.getInt("AGE")

          println(s"ID is $id, NAME is $name, AGE is $age\n")

      }
      "Data fetched Successfully"

    }
    catch {
      case e:SQLException =>
        e.printStackTrace()
        "Error retrieving data from database"
    }

    finally {
      con.close()
    }

  }
}
