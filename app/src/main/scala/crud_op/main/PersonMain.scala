package crud_op.main

import com.typesafe.config.ConfigFactory
import crud_op.Entity.Person
import crud_op.Service.PersonServiceImpl
import org.slf4j.{Logger, LoggerFactory}


object PersonMain extends App {
 private val logger: Logger = LoggerFactory.getLogger(getClass)
 private val config = ConfigFactory.load().getConfig("database")
 private val filePath = config.getString("filepath")

 private val service = new PersonServiceImpl

 logger.info(service.getAll())


}

