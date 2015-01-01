import scala.io.Source
import scala.util.Try

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
 */

// maintain history
// allow session logging
// allow prompt customization
class Shell(val localFilesystem: LocalFilesystem, val hdfs: HadoopFilesystem) {

  def start(): Unit = {
    Iterator.continually(Console.readLine).takeWhile(_ != "exit").foreach { input =>
      val command = new CommandParser(input).Command.run()
      if (command.isFailure) command.failed.get.printStackTrace()
      else println(command)
    }
  }

  private def prompt: String = "[/]$ "
}
