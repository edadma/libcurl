name := "libcurl"

version := "0.0.1"

scalaVersion := "3.1.3"

enablePlugins(ScalaNativePlugin)

nativeLinkStubs := true

nativeMode := "debug"

nativeLinkingOptions := Seq(s"-L${baseDirectory.value}/native-lib")

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-language:existentials",
)

organization := "io.github.edadma"

githubOwner := "edadma"

githubRepository := name.value

Global / onChangedBuildSource := ReloadOnSourceChanges

resolvers += Resolver.githubPackages("edadma")

resolvers += Resolver.githubPackages("spritzsn")

licenses := Seq("ISC" -> url("https://opensource.org/licenses/ISC"))

homepage := Some(url("https://github.com/edadma/" + name.value))

libraryDependencies ++= Seq(
  "io.github.spritzsn" %%% "libuv" % "0.0.22",
  "io.github.spritzsn" %%% "async" % "0.0.9",
)

publishMavenStyle := true

Test / publishArtifact := false
