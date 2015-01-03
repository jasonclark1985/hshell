package org.jtc.hshell.shell

import org.jtc.hshell.filesystem.{LocalFilesystem, HadoopFilesystem}
import org.parboiled2.ParseError
import org.jtc.hshell.command.CommandParser.Command

/**
 * Hadoop interaction:
 * ls (directory)
 * cd (directory)
 * chmod
 * chown
 * chgrp
 *
 * Local interaction:
 * lls (directory)
 * lcd (directory)
 * put (local, remote) (defaults to same filename on remote)
 * get (remote, local) - defaults to same filename on local
 *
 * any unknown command gets pushed through to the local shell
 */

// maintain history
// allow session logging
// allow prompt customization
// allow running things in background
class Shell {
  val parseCommand = new ParseCommand
  val localFilesystem = new LocalFilesystem
  val hdfs = new HadoopFilesystem
  val prompt = new Prompt(shouldExit = { s: String => s.startsWith("exit")})
  val lpwd: PartialFunction[Command, Option[String]] = {
    case c: Command if c.command == "lpwd" => Option(localFilesystem.workingDirectory)
  }
  val lls: PartialFunction[Command, Option[String]] = {
    case c: Command if c.command == "lls" => Option(localFilesystem.list(c.arguments.headOption))
  }
  val lcd: PartialFunction[Command, Option[String]] = {
    case c: Command if c.command == "lcd" => localFilesystem.changeWorkingDirectory(c.arguments.head); None
  }
  val commandHandlers = Iterable[PartialFunction[Command, Option[String]]](lpwd, lls, lcd)

  def start(): Unit = {
    def executeHandler(c: Command)(handler: PartialFunction[Command, Option[String]]) {
      if (handler.isDefinedAt(c)) {
        val result = handler(c)
        result.foreach(println)
      }
    }

    prompt.eachLine(createShellPrompt) { line =>
      parseCommand(line).map { c =>
        val maybeExecuteHandler = executeHandler(c) _
        commandHandlers.foreach(maybeExecuteHandler)
      }.recover { case e: ParseError => println(e.formatTraces)}
    }
  }

  private def createShellPrompt: String = s"[${localFilesystem.workingDirectory}]$$ "
}
