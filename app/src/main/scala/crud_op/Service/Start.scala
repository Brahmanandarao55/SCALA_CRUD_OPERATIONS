package crud_op.Service

import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

import java.io.File
import java.sql.Connection
import scala.util.{Failure, Success, Try}

class Start {
  val config = ConfigFactory.load().getConfig("filepath")
  val filePath = config.getString("path")
  val file = new File(filePath)
  val csvFilePath = "C:\\Users\\brahmananda Rao\\Desktop\\data.csv"
  val logger: Logger = LoggerFactory.getLogger(getClass)
  def check(): Unit = {
    val connection: Try[Connection] = Try {
      val databaseConnection = new  DatabaseConnection
      databaseConnection.getConnection
    }

    connection match {
      case Success(_) =>
        logger.info("Connection has been successfully established!")
        val x = new GenerateRandomData
        x.generateData(csvFilePath, file)

      case Failure(exception) =>
        logger.error(s"Connection Failed ${exception.getMessage}")
    }


  }

}
