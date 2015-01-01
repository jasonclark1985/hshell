
trait BasicFilesystem {
  var currentDirectory: String = _

  def directoryExists(targetDirectory: String): Boolean

  def changeWorkingDirectory(targetDirectory: String): Unit = {
    if (directoryExists(targetDirectory)) currentDirectory = targetDirectory
    else Console.println(s"ERROR: Directory does not exist [$targetDirectory] ")
  }
}
