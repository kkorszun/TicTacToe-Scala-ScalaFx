name := "TicTacToeScala"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))