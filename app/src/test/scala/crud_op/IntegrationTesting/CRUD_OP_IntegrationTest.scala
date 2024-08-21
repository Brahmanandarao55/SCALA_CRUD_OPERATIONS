package crud_op.IntegrationTesting

import com.typesafe.config.Config
import crud_op.Entity.Person
import crud_op.Repository.DataBaseRepoImpl
import crud_op.Service.{ConsoleUserInput, Crud_Operations, DatabaseConnection, GenerateRandomData, Start}
import crud_op.main.MainAdapter
import org.mockito.ArgumentMatchers.{anyInt, anyString}
import org.mockito.Mockito.{doNothing, when}
import org.scalatest.{FlatSpec, Matchers, Tag}
import org.scalatest.mockito.MockitoSugar

import java.io.File
import java.sql.Connection

class CRUD_OP_IntegrationTest extends FlatSpec with MockitoSugar with Matchers{

  "Integration Test" should "Starts" taggedAs Tag("IntegrationTest") in {

    val mockConnection = mock[Connection]
    val mockConfig = mock[Config]
    val mockConsoleUserInput = mock[ConsoleUserInput]
    val mockDataBaseRepoImpl = mock[DataBaseRepoImpl]
    val mockGenerateRandomData = mock[GenerateRandomData]
    val mockDatabaseConnection = mock[DatabaseConnection]
    val mockCrudOP = mock[Crud_Operations]
    val mockRepo = mock[DataBaseRepoImpl]
    val mockStartTest = mock[Start]
    val mockcsvFilePath: String = "C:\\Users\\brahmananda Rao\\Desktop\\data2.csv"
    val mockFile = mock[File]
      when(mockDatabaseConnection.getConnection).thenReturn(mockConnection)
      doNothing().when(mockGenerateRandomData).generateData(mockcsvFilePath, mockFile)
      when(mockFile.exists()).thenReturn(true,false)
      when(mockCrudOP.menu()).thenReturn("Choose an operation:")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("1")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("2")
      when(mockConfig.getString("path")).thenReturn("C:\\Users\\brahmananda Rao\\Desktop\\data.csv")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("3")
      when(mockConsoleUserInput.readLine("Enter ID:")).thenReturn("1")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("4")
      when(mockConsoleUserInput.readLine("Enter Name:")).thenReturn("John Doe")
      when(mockConsoleUserInput.readLine("Enter Age: ")).thenReturn("30")
      when(mockConsoleUserInput.readLine("Enter Salary: ")).thenReturn("50000")
      when(mockConsoleUserInput.readLine("Enter Profession: ")).thenReturn("Engineer")
      when(mockConsoleUserInput.readLine("Enter Location: ")).thenReturn("New York")
      when(mockConsoleUserInput.readLine("Enter the Rank: ")).thenReturn("5.0f")
      when(mockConsoleUserInput.readLine("Enter the Block: ")).thenReturn("B")
      when(mockConsoleUserInput.readLine("Enter Updating ID: ")).thenReturn("1")
      when(mockRepo.updateData(Person("John Doe", 30, 50000, "Engineer", "New York", 5.0f, 'B'), 1)).thenReturn("ROW WITH ID 1 IS UPDATED")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("5")
      when(mockRepo.getAll).thenReturn("The output file name is output.csv")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("6")
      when(mockConsoleUserInput.readLine("Enter deleting ID: ")).thenReturn("1")
      when(mockRepo.deleteData(Some(1))).thenReturn("ROW DELETED SUCCESSFULLY")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("7")
      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn("8", "7")





    //      doNothing().when(mockCrudOP).menu()
/*      when(mockConsoleUserInput.readLine("Enter your choice:")).thenReturn(anyInt().toString)
  when(mockRepo.createTable()).thenReturn(anyString())*/


  mockStartTest.check()










}


}
