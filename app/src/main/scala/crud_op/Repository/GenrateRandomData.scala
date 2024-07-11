package crud_op.Repository

import com.opencsv.CSVWriter
import org.slf4j.{Logger, LoggerFactory}

import java.io.FileWriter
import scala.util.Random

class GenrateRandomData {

  val logger: Logger = LoggerFactory.getLogger(getClass)
  def genrateData():Unit= {
    val numRows = 100000
    val names = Array("John", "Jane", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hank")
    val professions = Array("Engineer", "Doctor", "Teacher", "Artist", "Musician", "Lawyer", "Scientist", "Chef", "Writer", "Designer")
    val locations = Array("New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego", "Dallas", "San Jose")
    val blocks = Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')

    val csvFilePath = "C:\\Users\\brahmananda Rao\\Desktop\\data.csv"
    val writer = new CSVWriter(new FileWriter(csvFilePath))

    // Write the header
    writer.writeNext(Array("Name", "Age", "Salary", "Profession", "Location", "Rating", "Block"))

    // Generate random data and write to the CSV
    for (_ <- 1 to numRows) {
      val name = names(Random.nextInt(names.length))
      val age = Random.nextInt(48) + 18 // Age between 18 and 65
      val salary = Random.nextInt(120001) + 30000 // Salary between 30000 and 150000
      val profession = professions(Random.nextInt(professions.length))
      val location = locations(Random.nextInt(locations.length))
      val rating = "%.1f".format(Random.nextDouble() * 4.0 + 1.0).toFloat // Rating between 1.0 and 5.0
      val block = blocks(Random.nextInt(blocks.length))

      writer.writeNext(Array(name, age.toString, salary.toString, profession, location, rating.toString, block.toString))
    }

    writer.close()
    logger.info(s"CSV file generated at $csvFilePath")
  }

}
