package crud_op.main

import com.sun.net.httpserver.Authenticator.Success
import com.typesafe.config.ConfigFactory
import crud_op.Entity.Person
import crud_op.Repository.DatabaseConnection
import crud_op.Service.PersonServiceImpl
import org.slf4j.{Logger, LoggerFactory}

import java.sql.Connection
import scala.io.StdIn
import scala.reflect.runtime.universe.Try
import scala.util.{Failure, Success}


object MainAdapter extends App {
 private val logger: Logger = LoggerFactory.getLogger(getClass)
 private val config = ConfigFactory.load().getConfig("filepath")
 private val filePath = config.getString("path")

 private val service = new PersonServiceImpl





}

