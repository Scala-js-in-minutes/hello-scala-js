package scalajs.hello

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("app")
object AppEntryPoint {
  @JSExport("welcomeMessage")
  def welcomeMessage(): String = "Hello scala.js developer! :D"

  def main(args: Array[String]): Unit = {
    println(welcomeMessage())
  }
}
