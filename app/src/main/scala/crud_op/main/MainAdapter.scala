package crud_op.main

import com.typesafe.config.ConfigFactory
import crud_op.Service.{Crud_Operations, DatabaseConnection, GenerateRandomData}
import org.slf4j.{Logger, LoggerFactory}
import java.io.File
import scala.util.{Failure, Success, Try}
import java.sql.Connection


object MainAdapter extends App {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  private val config = ConfigFactory.load().getConfig("filepath")
  private val filePath = config.getString("path")
  private val file = new File(filePath)

  private def check(): Unit = {
    val Crud_Object = new Crud_Operations
    val connection: Try[Connection] = Try {
      DatabaseConnection.getConnection
    }

    connection match {
      case Success(_) =>
        logger.info("Connection has been successfully established!")
        if (!file.exists() || file.length() == 0) {
          val x = new GenerateRandomData
          x.generateData()
          Crud_Object.menu()
        }
        else {
          logger.info("File is already there")
          Crud_Object.menu()
        }

      case Failure(exception) =>
        logger.error(s"Connection Failed ${exception.getMessage}")
    }


  }

  check()
}

