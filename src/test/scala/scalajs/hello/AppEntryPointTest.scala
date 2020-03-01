package scalajs.hello

import org.scalatest.funsuite.AnyFunSuite

import scala.scalajs._
import scala.scalajs.js.JavaScriptException

class AppEntryPointTest extends AnyFunSuite {
  val expectedWelcomeMessage = "Hello scala.js developer! :D"

  test("Welcome message should greet a developer") {
    assert(AppEntryPoint.welcomeMessage() === expectedWelcomeMessage)
  }

  test("A function can be accessed from javascript") {
    assert(js.eval("app.welcomeMessage()") === expectedWelcomeMessage)
  }

  test("Ensure that 'eval' fails for an undefined function") {
    assertThrows[JavaScriptException] {
      js.eval("app.unknownFunction()")
    }
  }

  test("The application should successfully run and print a greeting message in the browser's console") {
    BrowserConsoleSpy.record()
    AppEntryPoint.main(Array())
    assert(BrowserConsoleSpy.stopRecordingAndGetLogs() === List(expectedWelcomeMessage))
  }
}
