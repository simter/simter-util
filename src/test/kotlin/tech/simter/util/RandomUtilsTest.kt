package tech.simter.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import tech.simter.util.RandomUtils.nextId
import tech.simter.util.RandomUtils.nextInt
import tech.simter.util.RandomUtils.nextLong
import tech.simter.util.RandomUtils.randomBigDecimal
import tech.simter.util.RandomUtils.randomBoolean
import tech.simter.util.RandomUtils.randomInt
import tech.simter.util.RandomUtils.randomLong
import tech.simter.util.RandomUtils.randomString
import java.math.BigDecimal

/**
 * @author RJ
 */
class RandomUtilsTest {
  @Test
  fun testRandomBoolean() {
    assertNotNull(randomBoolean())
  }

  @Test
  fun testRandomString() {
    assertEquals(6, randomString(len = 6).length)
    assertEquals(36, randomString().length)
    assertThat(randomString(prefix = "abc")).startsWith("abc")
    repeat(1000) {
      assertThat(randomString(prefix = "abc", len = 10)).startsWith("abc").hasSizeLessThanOrEqualTo(10)
    }
  }

  @Test
  fun testRandomInt() {
    repeat(1000) {
      assertThat(randomInt())
        .isGreaterThanOrEqualTo(0)
        .isLessThanOrEqualTo(100)
    }
    repeat(1000) {
      assertThat(randomInt(0, 10))
        .isGreaterThanOrEqualTo(0)
        .isLessThanOrEqualTo(10)
    }
    repeat(1000) {
      assertThat(randomInt(100, 110))
        .isGreaterThanOrEqualTo(100)
        .isLessThanOrEqualTo(110)
    }
  }

  @Test
  fun testRandomLong() {
    repeat(1000) {
      assertThat(randomLong())
        .isGreaterThanOrEqualTo(0)
        .isLessThanOrEqualTo(100)
    }
    repeat(1000) {
      assertThat(randomLong(0, 10))
        .isGreaterThanOrEqualTo(0)
        .isLessThanOrEqualTo(10)
    }
    repeat(1000) {
      assertThat(randomLong(100, 110))
        .isGreaterThanOrEqualTo(100)
        .isLessThanOrEqualTo(110)
    }
  }

  @Test
  fun testRandomBigDecimal() {
    repeat(1000) {
      assertThat(randomBigDecimal())
        .isGreaterThanOrEqualTo(BigDecimal.ZERO)
        .isLessThanOrEqualTo(BigDecimal("100.00"))
    }
    repeat(1000) {
      assertThat(randomBigDecimal(0, 10))
        .isGreaterThanOrEqualTo(BigDecimal.ZERO)
        .isLessThanOrEqualTo(BigDecimal("10.00"))
    }
    repeat(1000) {
      assertThat(randomBigDecimal(100, 110))
        .isGreaterThanOrEqualTo(BigDecimal("100.00"))
        .isLessThanOrEqualTo(BigDecimal("110.00"))
    }
  }

  @Test
  fun testNextInt() {
    var currentInt = 0
    repeat(1000) {
      val nextInt = nextInt()
      assertEquals(currentInt + 1, nextInt)
      currentInt = nextInt
    }
  }

  @Test
  fun testNextLong() {
    var currentLong = 0L
    repeat(1000) {
      val nextLong = nextLong()
      assertEquals(currentLong + 1, nextLong)
      currentLong = nextLong
    }
  }

  @Test
  fun testNextId() {
    var currentId = 0
    repeat(1000) {
      val nextId = nextId()
      assertEquals(currentId + 1, nextId)
      currentId = nextId
    }
  }
}