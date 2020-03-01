package scalajs.hello

import scala.collection.mutable._
import scala.scalajs.js

object BrowserConsoleSpy {
  private val consoleLogs = ListBuffer.empty[String]

  def record(): Unit = {
    consoleLogs.clear()
    attachConsoleSpy()
  }

  def stopRecordingAndGetLogs(): List[String] = {
    detachConsoleSpy()
    consoleLogs.toList
  }

  private val console: js.Dynamic = js.Dynamic.global.selectDynamic("console")
  private var targetLogFn: js.Function1[js.Any, Unit] = _

  private def isAttached: Boolean = targetLogFn != null

  private def attachConsoleSpy(): Unit =
    if (!isAttached) {
      targetLogFn = console.log.asInstanceOf[js.Function1[js.Any, Unit]]
      console.log = (message: String) => {
        consoleLogs += message
        targetLogFn(message)
      }
    }

  private def detachConsoleSpy(): Unit =
    if (isAttached) {
      console.log = targetLogFn
      targetLogFn = null
    }
}
