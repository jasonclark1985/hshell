package org.jtc.hshell.filesystem

import sys.process._
import java.nio.file.{Paths, Files}

/**
 * Local interaction:
 * lls (directory)
 * lcd (directory)
 * lcp (src) (dest)
 */
class LocalFilesystem extends Filesystem {
  lazy val homeDirectory = System.getProperty("user.home")
  changeWorkingDirectory(homeDirectory)

  override def directoryExists(targetDirectory: String): Boolean = Files.exists(Paths.get(targetDirectory))

  def list(path: Option[String] = None): String = {
    val dirPath = path.getOrElse(workingDirectory)
    val directory = Paths.get(dirPath).toFile
    if (!directory.isDirectory) s"$dirPath is not a directory."
    else directory.list().sorted.mkString("\n")
  }

}
