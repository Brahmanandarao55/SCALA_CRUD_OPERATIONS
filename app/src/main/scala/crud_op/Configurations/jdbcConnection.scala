package crud_op.Configurations

import java.sql.{Connection, DriverManager}

class jdbcConnection {
  private val url = "jdbc:mysql://localhost:3306/person"
  private val host = "root"
  private val pass = "root"
  private val driver = "com.mysql.cj.jdbc.Driver"
   val connection:Connection =jdbcConnection()
    def jdbcConnection(): Connection = {
    Class.forName(driver)
    DriverManager.getConnection(url,host,pass)
  }
}
