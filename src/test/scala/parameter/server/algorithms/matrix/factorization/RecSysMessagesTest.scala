package parameter.server.algorithms.matrix.factorization

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.PropertyChecks
import parameter.server.algorithms.matrix.factorization.RecSysMessages._
import matrix.factorization.types.Vector

class RecSysMessagesTest extends FlatSpec with PropertyChecks with Matchers {

  "parseUserParameter" should "parse a correct line" in {
    val line = "3,0.002371379483316807,0.00928661018743031,-0.005795011413062406,0.009321074965624001,0.00011645821278302112,0.009899240596257266,-0.01043964608650446,-0.007595501042001989,-0.003489446561505383,-0.002836555240540848"

    parseUserParameter(line) shouldBe UserParameter(3, Vector(Array(0.002371379483316807,0.00928661018743031,-0.005795011413062406,0.009321074965624001,0.00011645821278302112,0.009899240596257266,-0.01043964608650446,-0.007595501042001989,-0.003489446561505383,-0.002836555240540848)))
  }

  "parseItemParameter" should "parse a correct line" in {
    val line = "3,0.002371379483316807,0.00928661018743031,-0.005795011413062406,0.009321074965624001,0.00011645821278302112,0.009899240596257266,-0.01043964608650446,-0.007595501042001989,-0.003489446561505383,-0.002836555240540848"

    parseItemParameter(line) shouldBe ItemParameter(3, Vector(Array(0.002371379483316807,0.00928661018743031,-0.005795011413062406,0.009321074965624001,0.00011645821278302112,0.009899240596257266,-0.01043964608650446,-0.007595501042001989,-0.003489446561505383,-0.002836555240540848)))
  }
}
