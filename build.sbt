resolvers ++= Seq(
  "Typesafe Relases" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases")


scalacOptions ++= Seq("-language:postfixOps", "-language:implicitConversions", "-language:higherKinds", "-deprecation", "-feature")
 
libraryDependencies ++= Seq(
  "org.scalaz"          %% "scalaz-core"               % "7.1.0",
  "org.specs2"          %% "specs2"                    % "2.4.15"  % "test",
  "org.scalacheck"      %% "scalacheck"                % "1.12.1" % "test"
)
 
initialCommands in console := "import scalaz._, Scalaz._"
