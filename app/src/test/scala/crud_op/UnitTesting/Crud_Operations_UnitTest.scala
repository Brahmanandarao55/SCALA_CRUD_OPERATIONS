package crud_op.UnitTesting

import com.typesafe.config.Config
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.Crud_Operations
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar


class Crud_Operations_UnitTest extends FlatSpec with MockitoSugar with Matchers {

  "Crud_Operations" should "show menu options" in {

    val crudOps = new Crud_Operations
    crudOps.menu()

  }

}
