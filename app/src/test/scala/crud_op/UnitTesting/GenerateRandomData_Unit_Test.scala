package crud_op.UnitTesting


import crud_op.Service.GenerateRandomData
import org.mockito.Mockito.{verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.File

class GenerateRandomData_Unit_Test extends FlatSpec with Matchers with MockitoSugar {

  "GenerateRandomData" should "generate and write random data with mocked CSVWriter" in {

     val csvFilePath = "C:\\Users\\brahmananda Rao\\Desktop\\dummy_data.csv"

     val service = new GenerateRandomData
     service.generateData(csvFilePath)

     val file = new File(csvFilePath)

     file.exists() shouldBe true



  }





}