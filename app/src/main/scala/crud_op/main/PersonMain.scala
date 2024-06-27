package crud_op.main

import crud_op.Entity.Person
import crud_op.Service.PersonServiceImpl

import java.io.File
import scala.io.Source

object PersonMain extends App {

 val filepath = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\main\\scala\\crud_op\\main\\data.csv"
 private val service = new PersonServiceImpl
 service.insert(filepath)

}

