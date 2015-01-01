name := "hadoop-shell"

organization := "org.jtc"

version := "0.0.1"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
	"org.parboiled" %% "parboiled" % "2.0.1" withSources(), 
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" withSources() withJavadoc(),
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test" withSources() withJavadoc()
)

initialCommands := "import org.jtc.hadoopshell._"

