name := """GenServer"""

version := "0.1.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")
resolvers += Resolver.sbtPluginRepo("releases")

PlayKeys.externalizeResources := false

lazy val root = (project in file(".")).enablePlugins(PlayJava, LauncherJarPlugin)

libraryDependencies += "works.maatwerk" % "gameLogic" % "1.6"
libraryDependencies += javaJpa
libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "6.1.0.jre7"
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

libraryDependencies += "works.maatwerk" % "gameLogic" % "1.6"

libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.2"
libraryDependencies += "org.webjars" % "flot" % "0.8.3"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += ehcache

libraryDependencies += "org.assertj" % "assertj-core" % "3.8.0" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "3.0.0" % Test

// Needed to make JUnit report the tests being run
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

sources in(Compile, doc) := Seq.empty

publishArtifact in(Compile, packageDoc) := false