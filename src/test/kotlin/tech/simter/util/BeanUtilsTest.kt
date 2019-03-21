package tech.simter.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
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
}