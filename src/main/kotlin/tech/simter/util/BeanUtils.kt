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
  var defaultMapper: ModelMapper? = null
    private set

  /**
   * Construct Set property to LinkedHashedSet instance to keep orders.
   */
  private val provider4ConstructSet2LinkedHashedSet: Provider<Set<Any>> = Provider {
    if (it.requestedType.isAssignableFrom(Set::class.java)) LinkedHashSet() else null
  }

  init {
    // initial {@link ModelMapper} instance
    defaultMapper = ModelMapper()
    defaultMapper!!.configuration
      .setFieldMatchingEnabled(true)
      .setProvider(provider4ConstructSet2LinkedHashedSet)
  }

  /**
   * Maps `sources` to an instance of `targetType`.
   *
   * @param targetType targetType type
   * @param sources    objects to copy from
   * @param <T>        type to copy to
   * @return fully mapped instance of `targetType`
   * @see ModelMapper.map
  </T> */
  fun <T> assign(targetType: Class<T>, vararg sources: Any): T? {
    if (sources.isEmpty()) return null

    var target: T? = null
    for (i in sources.indices) {
      if (i == 0)
        target = defaultMapper!!.map(sources[i], targetType)
      else
        defaultMapper!!.map(sources[i], target)
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
    for (source in sources) defaultMapper!!.map(source, target)
  }
}