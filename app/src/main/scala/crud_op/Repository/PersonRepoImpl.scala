package crud_op.Repository

import com.opencsv.CSVWriter
import com.typesafe.config.{Config, ConfigFactory}
import crud_op.Entity.Person
import org.slf4j.{Logger, LoggerFactory}

import java.io.{File, FileWriter}
import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet, SQLException, Statement}
import scala.io.Source

class PersonRepoImpl extends PersonRepo {



  private val logger: Logger = LoggerFactory.getLogger(getClass)
  val connection: Connection = DatabaseConnection.getConnection
  override def createPerson(): String = {

    try {

      val query: String =
        """
          |CREATE TABLE PERSONTABLE (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
          |NAME VARCHAR(26),
          |AGE INT,
          |SALARY INT,
          |PROFESSION VARCHAR(40),
          |LOCATION VARCHAR(30),
          |RATING FLOAT,
          |BLOCK CHAR(3));
          |""".stripMargin

      val statement: Statement = connection.createStatement()
      statement.executeUpdate(query)

      "Table Created Successfully"
    }
    catch {

      case s: SQLException =>
        s.printStackTrace()

        "Table is not Created"
    }
   /* finally {

      connection.close()

    }*/


  }

  override def insertPerson(filepath: String): String = {
    try {
      val startTime = System.currentTimeMillis()
      val bufferedSource = Source.fromFile(new File(filepath))
      val lines = bufferedSource.getLines().drop(1)
      val person = lines.map { line =>
        val fields = line.split(",").map(_.trim.stripPrefix("\"").stripSuffix("\""))
        Person(fields(0), fields(1).toInt, fields(2).toInt, fields(3), fields(4), fields(5).toFloat, fields(6).charAt(0))
      }.toList
      connection.setAutoCommit(false)
      val query: String =
        """
          |INSERT INTO PERSONTABLE (NAME, AGE, SALARY, PROFESSION, LOCATION, RATING, BLOCK) values(?,?,?,?,?,?,?);
          |""".stripMargin
      val statement: PreparedStatement = connection.prepareStatement(query)
      val batchSize = 1000
      var count = 0
      person.foreach { persons =>
        statement.setString(1, persons.Name)
        statement.setInt(2, persons.Age)
        statement.setInt(3, persons.Salary)
        statement.setString(4, persons.Profession)
        statement.setString(5, persons.Location)
        statement.setFloat(6, persons.Rating)
        statement.setString(7,String.valueOf(persons.Block))
        statement.addBatch()
        count += 1
      }
      if (count % batchSize == 0) {
        statement.executeBatch()
      }
      statement.executeBatch()
      bufferedSource.close()
      connection.commit()

      val endTime = System.currentTimeMillis()
      val totalTimeSeconds = (endTime - startTime) / 1000.0
      logger.info(s"Total insertion time: $totalTimeSeconds seconds")
      "Data Insertion Successfully"

    }
    catch {
      case e: SQLException => e.printStackTrace()
        if (connection != null) connection.rollback()
        "Data Insertion Failed Here"
    }



  }

  override def getPerson(id: Option[Int]): String = {
    try {
      val query =
        """
          |SELECT * FROM PERSONTABLE WHERE ID = ?;
          |""".stripMargin
      val statement: PreparedStatement = connection.prepareStatement(query)
      statement.setInt(1, id.getOrElse(throw new IllegalArgumentException("GIVEN ID IS INVALID")))
      val resultSet: ResultSet = statement.executeQuery()
      if (resultSet.next()) {
        val id = resultSet.getInt("ID")
        val name = resultSet.getString("NAME")
        val age = resultSet.getInt("AGE")
        val salary = resultSet.getString("SALARY")
        val profession = resultSet.getString("PROFESSION")
        val location = resultSet.getString("LOCATION")
        val rating = resultSet.getFloat("RATING")
        val block = resultSet.getString("BLOCK")
        logger.info(s"id is $id")
        logger.info(s"name is $name")
        logger.info(s"age is $age")
        logger.info(s"salary is $salary")
        logger.info(s"profession is $profession")
        logger.info(s"location is $location")
        logger.info(s"rating is $rating")
        logger.info(s"block is $block")

        "Getting Data Successfully"

      }
      else {

        throw new IllegalArgumentException(s"Person with ID $id not found")


      }


    }
    catch {
      case e: SQLException => e.printStackTrace()
        "Getting Data Unsuccessfully"
    }

  }

  override def updatePerson(person: Person, id: Int): String = {
    val ID = id
    try {
      val query: String =
        """
          |UPDATE PERSONTABLE SET NAME = ?, AGE = ?, SALARY = ?, PROFESSION = ?, LOCATION= ?, RATING = ?, BLOCK = ?
          | WHERE ID = ?;
          | """.stripMargin
      val statement: PreparedStatement = connection.prepareStatement(query)
      statement.setString(1, person.Name)
      statement.setInt(2, person.Age)
      statement.setInt(3, person.Salary)
      statement.setString(4, person.Profession)
      statement.setString(5, person.Location)
      statement.setFloat(6, person.Rating)
      statement.setString(7, String.valueOf(person.Block))
      statement.setInt(8, ID)
      val result = statement.executeUpdate()
      if(result >0)
        s"ROW WITH ID $id IS UPDATED"
      else
        throw new SQLException("ROW IS NOT AVAILABLE")

    }
    catch {
      case e: SQLException => e.printStackTrace()
        s"ROW WITH ID $id IS NOT UPDATED"
    }

  }

  override def deletePerson(id: Option[Int]): String = {
    try {
      val query: String =
        """
          |DELETE FROM PERSONTABLE WHERE ID = ?;
          |""".stripMargin
      val statement: PreparedStatement = connection.prepareStatement(query)
      statement.setInt(1, id.getOrElse(throw new IllegalArgumentException("ENTERED ID IS NOT VALID")))
      val rowDelete = statement.executeUpdate()
      if (rowDelete > 0)
        "ROW DELETED SUCCESSFULLY"
      else
        throw new SQLException("ROW IS NOT AVAILABLE")
    }
    catch {
      case e: SQLException => e.printStackTrace()
        s"ROW WITH ID $id IS NOT DELETED "

    }


  }
  /*
    override def getAll(): String = {

      try{
        val query = "SELECT * FROM persontable"
        val statement = con.createStatement()
        val resultSet:ResultSet = statement.executeQuery(query)
        while(resultSet.next()){
           val id = resultSet.getInt("ID")
           val name = resultSet.getString("NAME")
           val age = resultSet.getInt("AGE")

           logger.info(s"ID is $id, NAME is $name, AGE is $age\n")

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

    }*/

  override def getAll(): String = {
    val startTime = System.currentTimeMillis()
    try {
      connection.setAutoCommit(false)

      val query = "SELECT * FROM PERSONTABLE"
      val statement = connection.createStatement()
      val result = statement.executeQuery(query)
      val filepath = "output.csv"
      val csvWriter = new CSVWriter(new FileWriter(filepath))
      val metadata = result.getMetaData
      val columnCount = metadata.getColumnCount
      while (result.next()) {
        val row = (1 to columnCount).map(result.getString).toArray
        csvWriter.writeNext(row)
      }
      csvWriter.close()
      val endTime = System.currentTimeMillis()
      val totalTimeSeconds = (endTime - startTime) / 1000.0

      logger.info(s"Total retrieving time: $totalTimeSeconds seconds")
      "The output file name is output.csv"

    }
    catch {
      case e: SQLException =>
        e.printStackTrace()
        "The output file is not generated and and data is not retried"
    }



  }
}
