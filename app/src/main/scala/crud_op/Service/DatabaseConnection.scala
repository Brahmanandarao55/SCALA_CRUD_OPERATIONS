package crud_op.Service

import com.typesafe.config.{Config, ConfigFactory}

import java.sql.{Connection, DriverManager}


object DatabaseConnection {
   val configurations: Config = ConfigFactory.load().getConfig("database")
   val url: String = configurations.getString("url")
   val host: String = configurations.getString("host")
   val password: String = configurations.getString("password")
   val driver: String = configurations.getString("driver")


  def getConnection: Connection = {
    Class.forName(driver)
    DriverManager.getConnection(url, host, password)
  }


}
