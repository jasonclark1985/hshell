package org.jtc.hshell.shell

class Prompt(shouldExit: (String => Boolean) = { s => false }) {

  def eachLine(action: (String => Unit)): Unit = {
    var in = Console.readLine(prompt)

    while (in != null && !shouldExit(in)) {
      action(in)
      in = Console.readLine(prompt)
    }
  }

  private def prompt = "$ "

}
