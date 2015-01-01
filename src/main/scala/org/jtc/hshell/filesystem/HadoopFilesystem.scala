package org.jtc.hshell.filesystem

import sys.process._

/**
 * ls (directory)
 * cd (directory)
 * chmod
 * chown
 * chgrp
 * cp
 */
class HadoopFilesystem extends Filesystem {
  override def directoryExists(targetDirectory: String): Boolean = {
    val result = s"hadoop fs -test -d $targetDirectory".!
    result == 0
  }

  def listFiles(path: Option[String] = None): String = s"hadoop fs -ls ${path.getOrElse(workingDirectory)}".!!

}
