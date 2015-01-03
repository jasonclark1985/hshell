package org.jtc.hshell.filesystem


trait Filesystem {
  var workingDirectory: String = _

  def directoryExists(targetDirectory: String): Boolean

  def list(path: Option[String]): String

  def changeWorkingDirectory(targetDirectory: String): Unit = {
    val path =
      if (!targetDirectory.startsWith("/")) s"$workingDirectory/$targetDirectory"
      else if (targetDirectory.startsWith("./")) targetDirectory.replaceFirst("./", workingDirectory + "/")
      else targetDirectory

    if (directoryExists(path)) workingDirectory = path
    else Console.println(s"ERROR: Directory does not exist [$targetDirectory] ")
  }
}
