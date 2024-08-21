package crud_op.main

import com.typesafe.config.ConfigFactory
import crud_op.Service.Start


import java.io.File


object MainAdapter extends App {

  val startObject = new Start
  startObject.check()

}

