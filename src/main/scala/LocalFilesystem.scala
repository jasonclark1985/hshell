import sys.process._

class LocalFilesystem extends BasicFilesystem {
  override def directoryExists(targetDirectory: String): Boolean = {
    val result = s"[ -d $targetDirectory ] && echo 0 || echo 1".!!
    result == "0"
  }
}
