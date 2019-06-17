package tech.simter.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import tech.simter.util.AssertUtils.assertSamePropertyHasSameValue
import java.math.BigDecimal

/**
 * @author RJ
 */
class AssertUtilsTest {
  @Test
  fun `Null value`() {
    data class A(val p: String?)
    data class B(val p: String?)

    // both null
    assertSamePropertyHasSameValue(A(null), B(null))

    // one null
    assertEquals(
      "property 'p' has difference value: one=null,two=",
      assertThrows<AssertionError> { assertSamePropertyHasSameValue(A(null), B("")) }.message
    )

    // two null
    assertEquals(
      "property 'p' has difference value: one=,two=null",
      assertThrows<AssertionError> { assertSamePropertyHasSameValue(A(""), B(null)) }.message
    )
  }

  @Test
  fun `Without same name property`() {
    data class A(val pa: String = "")
    data class B(val pb: String = "")

    assertEquals(
      "No same name property: one=pa,two=pb",
      assertThrows<AssertionError> { assertSamePropertyHasSameValue(A(), B()) }.message
    )
  }

  @Test
  fun `Diff value type`() {
    data class A(val p: String = "")
    data class B(val p: Int = 1)

    assertEquals(
      "property 'p' has incompatible type: one=kotlin.String,two=kotlin.Int",
      assertThrows<AssertionError> { assertSamePropertyHasSameValue(A(), B()) }.message
    )
  }

  @Test
  fun `Diff value`() {
    data class A(val p: String = "a")
    data class B(val p: String = "b")

    // diff value
    assertEquals(
      "property 'p' has difference value: one=a,two=b",
      assertThrows<AssertionError> { assertSamePropertyHasSameValue(A(), B()) }.message
    )

    // same value 》 no error
    assertSamePropertyHasSameValue(A(p = "p"), B(p = "p"))
  }

  @Test
  fun `Same value`() {
    data class A(val p1: String, val s: String, val i: Int, val b: Boolean, val bd: BigDecimal)
    data class B(val p2: String, val s: String, val i: Int, val b: Boolean, val bd: BigDecimal)
    assertSamePropertyHasSameValue(
      A(p1 = "", s = "p1", i = 2, b = true, bd = BigDecimal("1.00")),
      B(p2 = "", s = "p1", i = 2, b = true, bd = BigDecimal("1.00"))
    )
  }

  @Test
  @Suppress("UNCHECKED_CAST")
  fun `Same BigDecimal value but not same scale`() {
    data class A(val p: BigDecimal)
    // default comparer
    val exception = assertThrows<AssertionError> {
      assertSamePropertyHasSameValue(
        one = A(BigDecimal("1.00")),
        two = A(BigDecimal("1")))
    }
    assertEquals("property 'p' has difference value: one=1.00,two=1", exception.message)

    // custom comparer
    assertSamePropertyHasSameValue(
      one = A(BigDecimal("1.00")),
      two = A(BigDecimal("1")),
      equalComparer = { _, value1, value2 -> (value1 as BigDecimal).compareTo(value2 as BigDecimal) == 0 }
    )
  }

  @Test
  fun `Delegate property`() {
    data class A(val p: String)
    @Suppress("unused")
    class B(map: Map<String, Any?> = emptyMap()) {
      val p: String? by map
    }

    // same value 》 no error
    assertSamePropertyHasSameValue(
      one = A(p = "v"),
      two = B(mapOf("p" to "v"))
    )

    // diff value
    val exception = assertThrows<AssertionError> {
      assertSamePropertyHasSameValue(
        one = A(p = "v1"),
        two = B(mapOf("p" to "v2"))
      )
    }
    assertEquals("property 'p' has difference value: one=v1,two=v2", exception.message)
  }
}