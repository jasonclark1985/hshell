package org.jtc.hshell.shell

import org.jtc.hshell.filesystem.{LocalFilesystem, HadoopFilesystem}
import org.parboiled2.ParseError

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
  val prompt = new Prompt(shouldExit = { s: String => s.startsWith("exit") })

  def start(): Unit = {

    prompt.eachLine(createShellPrompt) { line =>
      val command = parseCommand(line)

      command.map { c =>
        c.command match {
          case "lpwd" => println(localFilesystem.workingDirectory)
          case "lls" => println(localFilesystem.list(c.arguments.headOption))
          case "lcd" => localFilesystem.changeWorkingDirectory(c.arguments.head)
        }
      }.recover { case e:ParseError => println(e.formatTraces) }
    }
  }

  private def createShellPrompt: String = s"[${localFilesystem.workingDirectory}]$$ "
}
