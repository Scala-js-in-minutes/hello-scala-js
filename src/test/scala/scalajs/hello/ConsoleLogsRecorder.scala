package scalajs.hello

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

object ConsoleLogsRecorder {
  private val consoleLogs = js.Array[String]()
  exposeSupportingJSFunctions()

  def start(): Unit = {
    consoleLogs.clear()
    jsEnv.attachConsoleSpy(consoleLogs)
  }

  def stopAndGetLogs(): List[String] = {
    jsEnv.detachConsoleSpy()
    consoleLogs.toList
  }

  private def exposeSupportingJSFunctions() = js.eval(
    """
      function attachConsoleSpy(array) {
          console.$log = console.log;
          console.log = (log) => {
            array.push(log);
            console.$log(log);
          }
      }

      function detachConsoleSpy() {
          console.log = console.$log;
      }
      """)

  @JSGlobalScope
  @js.native
  private object jsEnv extends js.Object {
    def attachConsoleSpy(array: js.Array[String]): Unit = js.native

    def detachConsoleSpy(): Unit = js.native
  }
}
