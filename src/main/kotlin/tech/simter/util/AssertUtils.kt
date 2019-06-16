package tech.simter.util

import kotlin.reflect.KVisibility.PUBLIC
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties

/**
 * Assert util tools.
 *
 * @author RJ
 */
object AssertUtils {
  /**
   * Verify same name public property has same value.
   *
   * @param[one] the bean instance to be asserted
   * @param[two] the bean instance to be asserted
   * @param[exclusion] exclusive property names
   * @param[equalComparer] not null value equal comparer:
   * 1. name - the property name.
   * 2. value1 - the property value of [one]
   * 3. value2 - the property value of [two]
   */
  fun assertSamePropertyHasSameValue(
    one: Any, two: Any,
    exclusion: List<String> = emptyList(),
    equalComparer: (name: String, value1: Any, value2: Any) -> Boolean = { _, value1, value2 ->
      value1 == value2
    }
  ) {
    val properties1 = one.javaClass.kotlin.memberProperties
      .filter { it.visibility == PUBLIC }
      .associateBy { it.name }
    val properties2 = two.javaClass.kotlin.memberProperties
      .filter { it.visibility == PUBLIC }
      .associateBy { it.name }
    val sameNameProperties = properties1.keys
      .filter { properties2.containsKey(it) && !exclusion.contains(it) }

    // should has one same name property at least
    if (sameNameProperties.isEmpty())
      throw AssertionError("No same name property: " +
                             "one=${properties1.keys.joinToString(",")}," +
                             "two=${properties2.keys.joinToString(",")}")

    // verify has same value
    properties1.filter { sameNameProperties.contains(it.key) }
      .forEach { (name, p1) ->
        val p2 = properties2.getValue(name)

        // assert value type should be incompatible
        if (!p1.returnType.isSubtypeOf(p2.returnType) && !p2.returnType.isSubtypeOf(p1.returnType))
          throw AssertionError("property '$name' has incompatible type: " +
                                 "one=${p1.returnType}," +
                                 "two=${p2.returnType}")

        // assert value should be eq
        val v1: Any? = p1.get(one)
        val v2: Any? = p2.get(two)
        if (
          !(v1 == null && v2 == null)
          && ((v1 == null && v2 != null) || (v1 != null && v2 == null) || !equalComparer(name, v1!!, v2!!))
        ) throw AssertionError("property '$name' has difference value: one=$v1,two=$v2")
      }
  }
}