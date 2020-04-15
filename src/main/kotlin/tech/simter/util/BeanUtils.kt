package tech.simter.util

import org.modelmapper.ModelMapper
import org.modelmapper.Provider
import java.util.*
import kotlin.reflect.full.memberProperties

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

  /**
   * Compare [newObj] with [oldObj] to find all difference properties in [newObj].
   *
   * Properties in [newObj] treat as difference if not match the [equalizer].
   * Default [equalizer] is simple implemented as value equals, two null value is treated as equals.
   */
  fun diff(
    newObj: Any,
    oldObj: Any,
    equalizer: (propertyName: String, newValue: Any?, oldValue: Any?) -> Boolean = { _, newValue, oldValue ->
      newValue == oldValue
    },
    ignoreNullValue: Boolean = false
  ): List<Diff> {
    var oldValues = if (oldObj is Map<*, *>) oldObj.mapKeys { it.key.toString() }
    else oldObj.javaClass.kotlin.memberProperties
      .filter { it.visibility == kotlin.reflect.KVisibility.PUBLIC } // only public properties
      .associateBy({ it.name }, { it.get(oldObj) })
    if (ignoreNullValue) oldValues = oldValues.filter { it.value != null } // filter not null value

    var newValues = if (newObj is Map<*, *>) newObj.mapKeys { it.key.toString() }
    else newObj.javaClass.kotlin.memberProperties
      .filter { it.visibility == kotlin.reflect.KVisibility.PUBLIC } // only public properties
      .associateBy({ it.name }, { it.get(newObj) })
    if (ignoreNullValue) newValues = newValues.filter { it.value != null } // filter not null value

    return diff(newValues = newValues, oldValues = oldValues, equalizer = equalizer)
  }

  /**
   * Find all difference values from [newValues] compare to [oldValues] by the [equalizer] with same key.
   *
   * Default [equalizer] is simple implemented as value equals, two null value is treated as equals.
   */
  fun diff(
    newValues: Map<String, Any?>,
    oldValues: Map<String, Any?>,
    equalizer: (propertyName: String, newValue: Any?, oldValue: Any?) -> Boolean = { _, newValue, oldValue ->
      newValue == oldValue
    }
  ): List<Diff> {
    return newValues.filterNot { equalizer(it.key, it.value, oldValues[it.key]) }
      .map { Diff(name = it.key, newValue = it.value, oldValue = oldValues[it.key]) }
  }

  /** A new and old value holder */
  data class Diff(
    val name: String,
    val newValue: Any?,
    val oldValue: Any?
  )
}