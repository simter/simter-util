package tech.simter.util

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Random util tools.
 *
 * @author RJ
 */
object RandomUtils {
  /** Generate a random Boolean value. */
  fun randomBoolean(): Boolean = Random().nextBoolean()

  /**
   * Generate a random Int with a specific range.
   *
   * If not supply the range, it will be [0, 100).
   */
  fun randomInt(from: Int = 0, until: Int = 100): Int {
    return kotlin.random.Random.Default.nextInt(from, until)
  }

  /**
   * Generate a random Long with a specific range.
   *
   * If not supply the range, it will be [0, 100).
   */
  fun randomLong(from: Long = 0, until: Long = 100): Long {
    return kotlin.random.Random.Default.nextLong(from, until)
  }

  /**
   * Generate a random BigDecimal with a specific scale.
   */
  fun randomBigDecimal(from: Int = 0, until: Int = 100, scale: Int = 2): BigDecimal {
    return BigDecimal(kotlin.random.Random.nextDouble(
      from = from.toDouble(),
      until = until.toDouble()
    )).setScale(scale, RoundingMode.HALF_UP)
  }

  private var prefixMap = HashMap<String, Int>()
  /**
   * Generate a random string with a specific prefix.
   *
   * Return a random uuid string if not supply the prefix.
   * Truncate the return to the limit len if [len] greater than zero.
   */
  fun randomString(len: Int = 0, prefix: String = ""): String {
    val s = if (prefix == "") UUID.randomUUID().toString()
    else {
      if (!prefixMap.containsKey(prefix)) prefixMap[prefix] = 1
      else prefixMap[prefix] = prefixMap[prefix]!! + 1
      "$prefix${prefixMap[prefix]}"
    }

    return if (len > 0 && s.length > len) s.substring(0, len)
    else s
  }

  private var intTypes = HashMap<String, Int>()
  private var longTypes = HashMap<String, Long>()
  /** Get the next Long for the specific type */
  fun nextLong(type: String = "Long"): Long {
    if (!longTypes.containsKey(type)) longTypes[type] = 1L
    else longTypes[type] = longTypes[type]!! + 1L
    return longTypes[type]!!
  }

  /** Get the next Int for the specific type */
  fun nextInt(type: String = "Int"): Int {
    if (!intTypes.containsKey(type)) intTypes[type] = 1
    else intTypes[type] = intTypes[type]!! + 1
    return intTypes[type]!!
  }

  /** Get the next Int id for the specific type */
  fun nextId(type: String = "ID"): Int {
    return nextInt(type)
  }
}