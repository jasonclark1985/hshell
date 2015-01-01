package org.jtc.hshell.shell

import scala.util.Try
import org.jtc.hshell.command.CommandParser.Command
import org.jtc.hshell.command.CommandParser

class ParseCommand extends (String => Try[Command]) {

  def apply(input: String): Try[Command] = new CommandParser(input).CommandEntry.run()

}
