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
   * If not supply the range, it will be [0, 9].
   */
  fun randomInt(start: Int = 0, end: Int = 9): Int = Random().nextInt(end + 1 - start) + start

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

  private var idTypes = HashMap<String, Int>()
  /**
   * Get the next Int for the specific type.
   */
  fun nextId(type: String = ""): Int {
    if (!idTypes.containsKey(type)) idTypes.put(type, 1)
    else idTypes.put(type, idTypes[type]!! + 1)
    return idTypes[type]!!
  }
}