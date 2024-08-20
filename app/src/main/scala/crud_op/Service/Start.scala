package crud_op.Service

import org.slf4j.{Logger, LoggerFactory}

import java.io.File
import java.sql.Connection
import scala.util.{Failure, Success, Try}

class Start(file:File,csvFilePath:String) {
  val logger: Logger = LoggerFactory.getLogger(getClass)
  def check(): Unit = {
    val connection: Try[Connection] = Try {
      DatabaseConnection.getConnection
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
