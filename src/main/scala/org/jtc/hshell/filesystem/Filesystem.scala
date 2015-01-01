package org.jtc.hshell.filesystem


trait Filesystem {
  var workingDirectory: String = _

  def directoryExists(targetDirectory: String): Boolean

  def changeWorkingDirectory(targetDirectory: String): Unit = {
    if (directoryExists(targetDirectory)) workingDirectory = targetDirectory
    else Console.println(s"ERROR: Directory does not exist [$targetDirectory] ")
  }
}
