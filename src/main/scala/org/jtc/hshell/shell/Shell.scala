package org.jtc.hshell.shell

import org.jtc.hshell.filesystem.{LocalFilesystem, HadoopFilesystem}

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
  val prompt = new Prompt({s => s.startsWith("exit") })

  def start(): Unit = {
    // read shell input
    prompt.eachLine { line =>
      val command = parseCommand(line)
      println(s"Result of command parsing: $command")

      command.map { c =>
        println(s"Executing command: ${c.command}")
        c.command match {
          case "lls" => println(localFilesystem.listFiles())
          case "lcd" => localFilesystem.changeWorkingDirectory(c.arguments.head)
        }
      }.recover { case e:RuntimeException => e.printStackTrace() }

      // identify command function
      // execute and report result
    }
  }
}
