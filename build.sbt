
version in ThisBuild := "0.1.0-SNAPSHOT"
scalaVersion in ThisBuild := "2.13.1"
organization in ThisBuild := "com.yuiwai"

val gdxVersion = "1.9.10"

lazy val core = project
  .in(file("core"))
  .settings(
    name := "gdxs-core",
    crossScalaVersions := Seq("2.11.11", "2.12.9", "2.13.1"),
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx" % gdxVersion,
      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test",
      "org.mockito" %% "mockito-scala" % "1.7.1" % "test"
    )
  )

lazy val example = project
  .in(file("example"))
  .settings(
    name := "gdxs-example",
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % gdxVersion,
      "com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop"
    )
  )
  .dependsOn(core)