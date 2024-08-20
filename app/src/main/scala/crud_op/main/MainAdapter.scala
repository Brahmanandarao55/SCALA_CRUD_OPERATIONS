package crud_op.main

import com.typesafe.config.ConfigFactory
import crud_op.Service.Start


import java.io.File


object MainAdapter extends App {

  val config = ConfigFactory.load().getConfig("filepath")
  val filePath = config.getString("path")
  val file = new File(filePath)
  val csvFilePath = "C:\\Users\\brahmananda Rao\\Desktop\\data.csv"

  val startObject = new Start(file, csvFilePath)
  startObject.check()

}

