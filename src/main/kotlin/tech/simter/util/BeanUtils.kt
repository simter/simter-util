package tech.simter.util

import org.modelmapper.ModelMapper
import org.modelmapper.Provider
import java.util.*

/**
 * Bean property copy util tools.
 *
 * @author RJ
 */
object BeanUtils {
  /**
   * Get the default [ModelMapper] instance,
   *
   *
   * `mapper.getConfiguration().setFieldMatchingEnabled(true)`
   *
   * @return [ModelMapper] instance
   */
  @Suppress("MemberVisibilityCanBePrivate")
  val defaultMapper: ModelMapper by lazy {
    // initial a ModelMapper instance
    val modelMapper = ModelMapper()
    modelMapper.configuration
      .setFieldMatchingEnabled(true)
      // Construct Set property to LinkedHashedSet instance to keep orders.
      .provider = Provider<Set<Any>> {
      if (it.requestedType.isAssignableFrom(Set::class.java)) LinkedHashSet() else null
    }
    modelMapper
  }

  /**
   * Maps `sources` to an instance of `targetType`.
   *
   * @param targetType targetType type
   * @param sources    objects to copy from
   * @param <T>        type to copy to
   * @return fully mapped instance of `targetType`
   * @see ModelMapper.map
   */
  fun <T> assign(targetType: Class<T>, vararg sources: Any): T? {
    if (sources.isEmpty()) return null

    var target: T? = null
    for (i in sources.indices) {
      if (i == 0)
        target = defaultMapper.map(sources[i], targetType)
      else
        defaultMapper.map(sources[i], target)
    }
    return target
  }

  /**
   * Maps `sources` to a target instance.
   *
   * @param target  target instance
   * @param sources objects to copy from
   * @see ModelMapper.map
   */
  fun assign(target: Any?, vararg sources: Any) {
    if (target == null || sources.isEmpty()) return
    for (source in sources) defaultMapper.map(source, target)
  }
}