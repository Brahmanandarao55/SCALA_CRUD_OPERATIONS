package crud_op.Service
import org.slf4j.LoggerFactory


class ConsoleUserInput extends UserInput {

  override def readLine(prompt: String): String = {
    val logger = LoggerFactory.getLogger(getClass)
    logger.info(prompt)
    scala.io.StdIn.readLine()
  }
}
