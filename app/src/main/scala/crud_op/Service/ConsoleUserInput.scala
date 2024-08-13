package crud_op.Service


class ConsoleUserInput extends UserInput {

  override def readLine(prompt: String): String = {
    println(prompt)
    scala.io.StdIn.readLine()
  }
}
