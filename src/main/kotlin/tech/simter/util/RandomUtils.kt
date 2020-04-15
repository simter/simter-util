package tech.simter.util

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

  private var prefixMap = HashMap<String, Int>()
  /**
   * Generate a random string with a specific prefix.
   *
   * Return a random uuid string if not supply the prefix.
   */
  fun randomString(prefix: String = ""): String {
    return if (prefix == "") UUID.randomUUID().toString()
    else {
      if (!prefixMap.containsKey(prefix)) prefixMap.put(prefix, 1)
      else prefixMap.put(prefix, prefixMap[prefix]!! + 1)
      "$prefix${prefixMap[prefix]}"
    }
  }

  private var idTypes = HashMap<String, Long>()
  /** Get the next Long for the specific type */
  fun nextLong(type: String = ""): Long {
    if (!idTypes.containsKey(type)) idTypes.put(type, 1)
    else idTypes.put(type, idTypes[type]!! + 1)
    return idTypes[type]!!
  }

  /** Get the next Int for the specific type */
  fun nextInt(type: String = ""): Int {
    return nextLong(type).toInt()
  }

  /** Get the next Int id for the specific type */
  fun nextId(type: String = ""): Int {
    return nextLong(type).toInt()
  }
}