package crud_op.Service

import com.typesafe.config.{Config, ConfigFactory}

import java.sql.{Connection, DriverManager}


object DatabaseConnection {
  private val configurations: Config = ConfigFactory.load().getConfig("database")
  private val url: String = configurations.getString("url")
  private val host: String = configurations.getString("host")
  private val password: String = configurations.getString("password")
  private val driver: String = configurations.getString("driver")


  def getConnection: Connection = {
    Class.forName(driver)
    DriverManager.getConnection(url, host, password)
  }


}
