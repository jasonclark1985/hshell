package org.jtc.hshell.shell

class Prompt(shouldExit: (String => Boolean) = { s => false }) {

  def eachLine(prefix: => String = "$ ")(action: (String => Unit)): Unit = {
    var in = Console.readLine(prefix)
    while (in != null && !shouldExit(in)) {
      action(in)
      in = Console.readLine(prefix)
    }
  }
}
