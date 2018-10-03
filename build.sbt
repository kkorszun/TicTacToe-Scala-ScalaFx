name := "TicTacToeScala"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))