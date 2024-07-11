package crud_op.Repository

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.{Logger, LoggerFactory}

import java.sql.{Connection, DriverManager}
import scala.util.Try

object DatabaseConnection {
  private val configurations: Config = ConfigFactory.load().getConfig("database")
  private val url: String = configurations.getString("url")
  private val host: String = configurations.getString("host")
  private val password: String = configurations.getString("password")
  private val driver: String = configurations.getString("driver")
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def getConnection: Connection = {
    Class.forName(driver)
    DriverManager.getConnection(url, host, password)
  }


}
