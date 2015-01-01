package org.jtc.hshell

import org.jtc.hshell.shell.Shell
import org.jtc.hshell.filesystem.{HadoopFilesystem, LocalFilesystem}


object Main {

  def main(args: Array[String]) {
    val shell = new Shell
    shell.start()

    return 0
  }

}
