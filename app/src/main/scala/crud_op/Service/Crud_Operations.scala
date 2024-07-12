package crud_op.Service

import com.typesafe.config.{Config, ConfigFactory}
import crud_op.Entity.Person
import crud_op.Repository.DataBaseRepoImpl
import org.slf4j.{Logger, LoggerFactory}

import scala.io.StdIn

class Crud_Operations {
  val config: Config = ConfigFactory.load().getConfig("filepath")
  val filePath: String = config.getString("path")
  val repo = new DataBaseRepoImpl
  val logger: Logger = LoggerFactory.getLogger(getClass)

  def menu(): Unit = {

    logger.info("Choose an operation:")
    logger.info("1. Create Table")
    logger.info("2. Insert Data into Table")
    logger.info("3. Get Data by ID")
    logger.info("4. Update Data into Table")
    logger.info("5. Generate CSV file Data From DataBase")
    logger.info("6. Delete Data by ID")
    logger.info("7. Exit")
    logger.info("To Repeat Options Enter Value Greater Than 6")
    logger.info("Enter your choice:")
    val input = StdIn.readInt()
    input match {
      case 1 => logger.info(repo.createTable())
      case 2 =>
        logger.info("Insertion Begin")
        repo.insertData(filePath)
        logger.info("Insertion Ended")
      case 3 =>
        logger.info("Enter ID: ")
        val id = StdIn.readInt()
        repo.getDataById(Some(id))
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
        repo.updateData(Person(name, age, salary, profession, location, rank, block), id)

      case 5 =>
        repo.getAll
      case 6 =>
        logger.info("Enter deleting ID")
        val id = StdIn.readInt()
        repo.deleteData(Some(id))
      case 7 =>
        logger.info("Invalid Choice")
      case _ =>
        logger.info("Invalid choice. Please try again.")
        menu()


    }
  }
}