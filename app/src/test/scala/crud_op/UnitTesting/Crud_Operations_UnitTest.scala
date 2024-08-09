/*package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.Crud_Operations
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.{doNothing, mockingDetails, spy, times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.{ByteArrayInputStream, EOFException}
import scala.io.StdIn


class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {

  it should "dfgs" in{

    val mockRepo = mock[DataBaseRepoImpl]

    when(mockRepo.createTable()).thenReturn("Table Created Successfully")


    val x = new Crud_Operations {
      override val repo: DataBaseRepoImpl = mockRepo
    }

    x.menu()

    verify(mockRepo, times(1)).createTable()
  }
}*/