package crud_op.main

import com.typesafe.config.ConfigFactory
import crud_op.Entity.Person
import crud_op.Service.PersonServiceImpl



object PersonMain extends App {

 private val config = ConfigFactory.load().getConfig("database")
 private val filePath = config.getString("filepath")

 private val service = new PersonServiceImpl

 println(service.getAll())

}

