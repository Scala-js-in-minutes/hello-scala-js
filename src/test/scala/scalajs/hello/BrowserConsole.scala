package scalajs.hello

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobalScope

object BrowserConsole {
  private val consoleLogs = js.Array[String]()
  exposeSupportingJSFunctions()

  def record(): Unit = {
    consoleLogs.clear()
    jsEnv.attachConsoleSpy(consoleLogs)
  }

  def stopRecordingAndGetLogs(): List[String] = {
    jsEnv.detachConsoleSpy()
    consoleLogs.toList
  }

  private def exposeSupportingJSFunctions() = js.eval(
    """
      function attachConsoleSpy(array) {
          if (!console.$log) {
            console.$log = console.log;
            console.log = (message) => {
              array.push(message);
              console.$log(message);
            }
          }
      }

      function detachConsoleSpy() {
          if (console.$log) {
            console.log = console.$log;
            delete console.$log;
          }
      }
      """)

  @JSGlobalScope
  @js.native
  private object jsEnv extends js.Object {
    def attachConsoleSpy(array: js.Array[String]): Unit = js.native

    def detachConsoleSpy(): Unit = js.native
  }
}
