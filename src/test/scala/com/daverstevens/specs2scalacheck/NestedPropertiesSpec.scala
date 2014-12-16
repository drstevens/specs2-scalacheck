/*
[info] Compiling 2 Scala sources to /Users/dave/workspace/specs2-scalacheck/target/scala-2.11/test-classes...
[info] ! BrokenEqual.equal.commutativity: Falsified after 0 passed tests.
[info] > ARG_0: 1179661225
[info] > ARG_1: -1
[info] ! BrokenEqual.equal.reflexive: Falsified after 0 passed tests.
[info] > ARG_0: -1
[info] + BrokenEqual.equal.transitive: OK, passed 100 tests.
[info] + BrokenEqual.equal.naturality: OK, passed 100 tests.
[info] NestedPropertiesSpec
[info] x BrokenEqual
[error]  A counter-example is [1346278381, 1944519558] (after 0 try)
[info]
[info]
[info] Total for specification NestedPropertiesSpec
[info] Finished in 47 ms
[info] 1 example, 1 failure, 0 error
[info]
[error] Failed: Total 5, Failed 3, Errors 0, Passed 2
[error] Failed tests:
[error] 	com.daverstevens.specs2scalacheck.NestedProperties
[error] 	com.daverstevens.specs2scalacheck.NestedPropertiesSpec
*/
package com.daverstevens.specs2scalacheck

import org.scalacheck.{Arbitrary, Properties}
import org.specs2.ScalaCheck
import org.specs2.mutable.Specification
import scalaz.{Tag, Order, @@, Equal}
import scalaz.std.anyVal.intInstance
import scalaz.scalacheck.ScalazProperties
import scalaz.syntax.tag._

sealed trait BrokenEqual
object BrokenEqualInstances {
  implicit def brokenEqual[A](implicit ordA: Order[A]): Equal[A @@ BrokenEqual] =
    Equal.equal((a1, a2) => ordA.lessThan(a1.unwrap, a2.unwrap))
  implicit def arbitrary[A](implicit arbA: Arbitrary[A]): Arbitrary[A @@ BrokenEqual] =
    Arbitrary(arbA.arbitrary.map(Tag.apply))
}

object NestedProperties extends Properties("BrokenEqual") {
  import BrokenEqualInstances._
  include(ScalazProperties.equal.laws[Int @@ BrokenEqual])
}

class NestedPropertiesSpec extends Specification with ScalaCheck {
  import BrokenEqualInstances._
  "BrokenEqual" ! ScalazProperties.equal.laws[Int @@ BrokenEqual]
}
