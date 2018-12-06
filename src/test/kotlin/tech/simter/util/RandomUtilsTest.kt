package tech.simter.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.simter.util.RandomUtils.nextId
import tech.simter.util.RandomUtils.randomBoolean
import tech.simter.util.RandomUtils.randomInt
import tech.simter.util.RandomUtils.randomString

/**
 * @author RJ
 */
class RandomUtilsTest {
  @Test
  fun testRandomBoolean() {
    assertNotNull(randomBoolean())
    //(0..9).forEach { println(randomBoolean()) }
  }

  @Test
  fun testRandomString() {
    assertNotNull(randomString())
    //(0..9).forEach { println(randomBoolean()) }
  }

  @Test
  fun testRandomInt() {
    (0..1000).forEach {
      val i = randomInt()
      assertTrue(i >= 0)
      assertTrue(i <= 9)
    }
    (0..1000).forEach {
      val i = randomInt(0, 99)
      assertTrue(i >= 0)
      assertTrue(i <= 100)
    }
    (0..1000).forEach {
      val i = randomInt(100, 999)
      assertTrue(i >= 100)
      assertTrue(i <= 999)
    }
  }

  @Test
  fun testNextId() {
    var currentId = 0
    (0..1000).forEach {
      val nextId = nextId()
      assertEquals(currentId + 1, nextId)
      currentId = nextId
    }
  }
}