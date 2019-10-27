
version in ThisBuild := "0.1.0-SNAPSHOT"
scalaVersion in ThisBuild := "2.13.1"

val gdxVersion = "1.9.10"

lazy val core = project
  .in(file("core"))
  .settings(
    name := "gdxs-core",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx" % gdxVersion,
      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    )
  )
