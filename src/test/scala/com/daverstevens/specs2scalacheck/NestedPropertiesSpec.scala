/*
> test
[info] ! BrokenEqual-no-include.equal: Falsified after 0 passed tests.
[info] > ARG_0: -1889221378
[info] > ARG_1: 2147483647
[info] ! BrokenEqual-include.equal.commutativity: Falsified after 0 passed tests.
[info] > ARG_0: 1
[info] > ARG_1: -1
[info] ! BrokenEqual-include.equal.reflexive: Falsified after 0 passed tests.
[info] > ARG_0: 1
[info] + BrokenEqual-include.equal.transitive: OK, passed 100 tests.
[info] + BrokenEqual-include.equal.naturality: OK, passed 100 tests.
[info] NestedPropertiesSpec
[info] x BrokenEqual-specs2
[error]  A counter-example is [-1344045865, 1336869792] (after 0 try)
[info]
[info]
[info] Total for specification NestedPropertiesSpec
[info] Finished in 15 ms
[info] 1 example, 1 failure, 0 error
[info]
[error] Failed: Total 6, Failed 4, Errors 0, Passed 2
[error] Failed tests:
[error] 	com.daverstevens.specs2scalacheck.NestedProperties
[error] 	com.daverstevens.specs2scalacheck.NestedPropertiesNoInclude
[error] 	com.daverstevens.specs2scalacheck.NestedPropertiesSpec
[error] (test:test) sbt.TestsFailedException: Tests unsuccessful
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

object NestedProperties extends Properties("BrokenEqual-include") {
  import BrokenEqualInstances._
  include(ScalazProperties.equal.laws[Int @@ BrokenEqual])
}

object NestedPropertiesNoInclude extends Properties("BrokenEqual-no-include") {
  import BrokenEqualInstances._
  property("equal") = ScalazProperties.equal.laws[Int @@ BrokenEqual]
}

class NestedPropertiesSpec extends Specification with ScalaCheck {
  import BrokenEqualInstances._
  "BrokenEqual-specs2" ! ScalazProperties.equal.laws[Int @@ BrokenEqual]
}
