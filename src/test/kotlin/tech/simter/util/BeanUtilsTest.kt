package tech.simter.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.simter.util.BeanUtils.Diff
import tech.simter.util.bean.Location
import tech.simter.util.bean.UserDto
import tech.simter.util.bean.UserPo

/**
 * @author RJ
 */
class BeanUtilsTest {
  private var id = 0

  @Test
  fun assign2TargetType() {
    val po = UserPo()
    po.id = ++id
    po.account = "account"
    po.location = Location()
    po.location!!.id = ++id
    po.location!!.name = "location"
    val dto = BeanUtils.assign(UserDto::class.java, po)

    assertEquals(po.id!!, dto!!.id)
    assertEquals(po.account, dto.account)
    assertEquals(po.location!!.id, dto.locationId)
    assertEquals(po.location!!.name, dto.locationName)
  }

  @Test
  fun assign2TargetObject() {
    val po = UserPo()
    po.id = ++id
    po.account = "account"
    po.location = Location()
    po.location!!.id = ++id
    po.location!!.name = "location"

    val dto = UserDto()
    BeanUtils.assign(dto, po)

    assertEquals(po.id!!, dto.id)
    assertEquals(po.account, dto.account)
    assertEquals(po.location!!.id, dto.locationId)
    assertEquals(po.location!!.name, dto.locationName)
  }

  @Test
  fun testDiffMaps() {
    assertThat(
      BeanUtils.diff(
        newValues = mapOf("k1" to "v1"),
        oldValues = emptyMap()
      )
    ).isEqualTo(listOf(Diff(name = "k1", newValue = "v1", oldValue = null)))

    assertThat(
      BeanUtils.diff(
        newValues = mapOf("k1" to "v1"),
        oldValues = mapOf("k1" to "v2")
      )
    ).isEqualTo(listOf(Diff(name = "k1", newValue = "v1", oldValue = "v2")))

    assertThat(
      BeanUtils.diff(
        newValues = mapOf("k1" to "v1"),
        oldValues = mapOf("k2" to "v2")
      )
    ).isEqualTo(listOf(Diff(name = "k1", newValue = "v1", oldValue = null)))
  }

  @Test
  fun testDiffBeans() {
    data class Bean(val p1: String?, val p2: Int?)

    assertThat(
      BeanUtils.diff(
        newObj = Bean(p1 = "v1", p2 = 2),
        oldObj = emptyMap<String, Any?>()
      )
    ).isEqualTo(listOf(
      Diff(name = "p1", newValue = "v1", oldValue = null),
      Diff(name = "p2", newValue = 2, oldValue = null)
    ))

    assertThat(
      BeanUtils.diff(
        newObj = Bean(p1 = "v1", p2 = 2),
        oldObj = Bean(p1 = "v1", p2 = 2)
      )
    ).isEmpty()

    assertThat(
      BeanUtils.diff(
        newObj = Bean(p1 = "v1", p2 = 2),
        oldObj = Bean(p1 = "v2", p2 = 2)
      )
    ).isEqualTo(listOf(
      Diff(name = "p1", newValue = "v1", oldValue = "v2")
    ))

    assertThat(
      BeanUtils.diff(
        newObj = Bean(p1 = null, p2 = 2),
        oldObj = Bean(p1 = null, p2 = 2)
      )
    ).isEmpty()

    assertThat(
      BeanUtils.diff(
        newObj = Bean(p1 = null, p2 = 2),
        oldObj = Bean(p1 = "v1", p2 = 2)
      )
    ).isEqualTo(listOf(
      Diff(name = "p1", newValue = null, oldValue = "v1")
    ))
  }
}