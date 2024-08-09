package crud_op.UnitTesting


import com.opencsv.CSVWriter
import crud_op.Service.{Crud_Operations, GenerateRandomData}
import org.mockito.ArgumentMatchers.{any, anyString}
import org.mockito.Mockito.{doThrow, times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.{Logger, LoggerFactory}

import java.io.{File, FileWriter, IOException}


class GenerateRandomData_Unit_Test extends FlatSpec with Matchers with MockitoSugar {

  "GenerateRandomData" should "generate and write random data with mocked CSVWriter" in {

    val mockFile = mock[File]
    val csvFilePath = "test.csv"
    val mockObject = mock[Crud_Operations]

    when(mockFile.exists()).thenReturn(true,false)
    val x = new GenerateRandomData {
      override val Crud_Object: Crud_Operations = mockObject
    }
    x.generateData(csvFilePath, mockFile)


    verify(mockFile).exists()


  }

  it should "If File path is Doesn't exists" in {
    val mockFile = mock[File]
    val csvFilePath = " "
    val mockObject = mock[Crud_Operations]

    val x = new GenerateRandomData {
      override val Crud_Object: Crud_Operations = mockObject
    }
    x.generateData(csvFilePath, mockFile)


  }

  it should "print message if file already exists" in {
    val mockFile = mock[File]
    val csvFilePath = "test.csv"
    val mockObject = mock[Crud_Operations]

    val generateRandomData = new GenerateRandomData {
      override val Crud_Object: Crud_Operations = mockObject
    }

    when(mockFile.exists()).thenReturn(true)
    when(mockFile.length()).thenReturn(1)


    generateRandomData.generateData(csvFilePath, mockFile)


    verify(mockFile).exists()
    verify(mockFile).length()
    verify(mockObject).menu()


  }


}