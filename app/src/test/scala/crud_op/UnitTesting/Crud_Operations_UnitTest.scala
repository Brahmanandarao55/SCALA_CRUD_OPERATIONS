package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.Crud_Operations
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger

import scala.Unit

class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {

  "Crud_Operations" should " execute successfully" in {
    val mockConfig = mock[Config]
    val mockLogger = mock[Logger]

    println(mockConfig)
    println(mockLogger)


    val crud_operations_object = new Crud_Operations{
      override val logger: Logger = mockLogger
    }

    crud_operations_object.menu()

    println(mockLogger)

    verify(mockLogger, times(2)).info(anyString())



  }

  it should "Crud_Operations" in {
    val mockConfig = mock[Config]
    val mockLogger = mock[Logger]
    val mockRepo = mock[DataBaseRepoImpl]
    val mockFile = "C:\\Users\\Brahmananda Rao\\Desktop\\TASK\\CRUD_OP\\app\\src\\test\\resources\\testfile.csv"

    val crud_operations_object = new Crud_Operations{
      override val logger: Logger = mockLogger
      override val repo: DataBaseRepoImpl = mockRepo
      override val config: Config = mockConfig
      override val filePath: String = mockFile

    }



    when(mockRepo.createTable()).thenReturn("Table Created Successfully")

    crud_operations_object.menu()

    verify(mockRepo).createTable()
    verify(mockRepo).insertData(mockFile)
    verify(mockLogger).info("Table created successfully")
  }


















}
