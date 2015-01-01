
object Main {
  val shell = new Shell(new LocalFilesystem, new HadoopFilesystem)

  def main(args: Array[String]) {
    shell.start()
  }

}
