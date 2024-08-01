package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.Crud_Operations
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{doNothing, mockingDetails, spy, times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar
import org.slf4j.Logger

import java.io.EOFException
import scala.io.StdIn


class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {


  val crud_Operations_object: Crud_Operations = mock[Crud_Operations]

  doNothing().when(crud_Operations_object).menu()

  crud_Operations_object.menu()

  verify(crud_Operations_object).menu()


}
