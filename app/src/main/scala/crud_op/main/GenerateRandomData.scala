/*
package crud_op.main

import java.io.PrintWriter
import scala.util.Random

object GenerateRandomData {

  case class Person(Id: Int, Name: String, Age: Int)

  def main(args: Array[String]): Unit = {
    val outputFile = "data.csv"
    val numRecords = 1000

    // Create a PrintWriter to write to the output file
    val writer = new PrintWriter(outputFile)

    // Write header to the CSV file
    writer.println("Id,Name,Age")

    // Generate and write random data
    (1 to numRecords).foreach { _ =>
      val id = Random.nextInt(1000) // Generate random Id
      val name = getRandomName()    // Generate random name
      val age = Random.nextInt(100) // Generate random age

      writer.println(s"$id,$name,$age")
    }

    writer.close()
    println(s"Generated $numRecords records in $outputFile")
  }

  // Function to generate random names
  def getRandomName(): String = {
    val names = Array("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack")
    val randomIndex = Random.nextInt(names.length)
    names(randomIndex)
  }
}
*/
