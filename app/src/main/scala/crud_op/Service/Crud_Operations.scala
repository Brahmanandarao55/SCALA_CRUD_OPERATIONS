package crud_op.Service

import com.typesafe.config.{Config, ConfigFactory}
import crud_op.Entity.Person
import crud_op.Repository.DataBaseRepoImpl
import org.slf4j.{Logger, LoggerFactory}


class Crud_Operations {
  val config: Config = ConfigFactory.load().getConfig("filepath")
  val filePath: String = config.getString("path")
  val repo = new DataBaseRepoImpl
  val logger: Logger = LoggerFactory.getLogger(getClass)
  val userInput_Object = new ConsoleUserInput

  def menu(): String = {
    val menuOptions =
      """
    1. Create Table
    2. Insert Data into Table
    3. Get Data by ID
    4. Update Data into Table
    5. Generate CSV file Data From DataBase
    6. Delete Data by ID
    7. Exit
    To Repeat Options Enter Value Greater Than 7
  """
    logger.info(menuOptions)
    try {
      val input = userInput_Object.readLine("Enter your choice:").toInt
      input match {
        case 1 => logger.info(repo.createTable())
        case 2 =>
          logger.info("Insertion Begin")
          repo.insertData(filePath)
          logger.info("Insertion Ended")
        case 3 =>
          val id = userInput_Object.readLine("Enter ID:").toInt
          repo.getDataById(Some(id))
        case 4 =>
          logger.info("Enter Updating details:")
          val name = userInput_Object.readLine("Enter Name:")
          val age = userInput_Object.readLine("Enter Age: ").toInt
          val salary = userInput_Object.readLine("Enter Salary: ").toInt
          val profession = userInput_Object.readLine("Enter Profession: ")
          val location = userInput_Object.readLine("Enter Location: ")
          val rank = userInput_Object.readLine("Enter the Rank: ").toFloat
          val block = userInput_Object.readLine("Enter the Block: ").charAt(0)
          val id = userInput_Object.readLine("Enter Updating ID: ").toInt
          repo.updateData(Person(name, age, salary, profession, location, rank, block), id)

        case 5 =>
          repo.getAll
        case 6 =>
          val id = userInput_Object.readLine("Enter deleting ID: ").toInt
          repo.deleteData(Some(id))
        case 7 =>
          logger.info("Invalid Choice")
        case _ =>
          logger.info("Invalid choice. Please try again.")
          menu()

      }
      "Choose an operation:"
    }
    catch {
      case e: IllegalArgumentException => logger.error(s"Connection Failed ${e.getMessage}")
        "Enter Valid Input"
    }
  }


}

