package crud_op.Repository
import com.typesafe.config.ConfigFactory
import crud_op.Entity.Person
import crud_op.Service.PersonServiceImpl
import org.slf4j.{Logger, LoggerFactory}

import scala.io.StdIn

class Crud_Operations {
  private val config = ConfigFactory.load().getConfig("filepath")
  private val filePath = config.getString("path")
  val service = new PersonServiceImpl
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  def menu(): Unit = {

    logger.info("Choose an operation:")
    logger.info("1. Create Table")
    logger.info("2. Insert Data into Table")
    logger.info("3. Get Data by ID")
    logger.info("4. Update Data into Table")
    logger.info("5. Generate CSV file Data From DataBase")
    logger.info("Enter your choice:")
    val input = StdIn.readInt()
    input match {
      case 1 => logger.info(service.createTable())
      case 2 => service.insert(filePath)
      case 3 =>
        logger.info("Enter ID: ")
        val id = StdIn.readInt()
        service.get(Some(id))
      case 4 =>
        logger.info("Enter Updating details:")
        logger.info("Enter Name: ")
        val name = StdIn.readLine()
        logger.info("Enter Age: ")
        val age = StdIn.readInt()
        logger.info("Enter Salary: ")
        val salary = StdIn.readInt()
        logger.info("Enter Profession: ")
        val profession = StdIn.readLine()
        logger.info("Enter Location: ")
        val location = StdIn.readLine()
        logger.info("Enter the Rank: ")
        val rank = StdIn.readFloat()
        logger.info("Enter the Block: ")
        val block = StdIn.readChar()
        logger.info("Enter Updating ID: ")
        val id = StdIn.readInt()
        service.update(Person(name,age,salary,profession,location,rank,block),id)

      case 5 =>
        service.getAll()
      case _ =>
        logger.info("Invalid choice. Please try again.")


    }
  }
}
