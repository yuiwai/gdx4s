version in ThisBuild := "0.1.0"
scalaVersion in ThisBuild := "2.13.1"
organization in ThisBuild := "com.yuiwai"
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint"
)

val gdxVersion = "1.9.10"

lazy val root = project
  .in(file("."))
  .aggregate(core)
  .settings(
    name := "yachiyo",
    publish / skip := true
  )

lazy val core = project
  .in(file("core"))
  .settings(
    name := "gdxs-core",
    crossScalaVersions := Seq("2.11.11", "2.12.9", "2.13.1"),
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx" % gdxVersion,
      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    ),
    publishTo := Some(Resolver.file("file", file("release")))
  )

