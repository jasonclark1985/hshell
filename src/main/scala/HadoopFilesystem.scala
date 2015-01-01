import sys.process._

class HadoopFilesystem extends BasicFilesystem {
  override def directoryExists(targetDirectory: String): Boolean = {
    val result = s"hadoop fs -test -d $targetDirectory".!
    result == 0
  }


}
