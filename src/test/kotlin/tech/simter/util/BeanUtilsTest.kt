package tech.simter.util

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test
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

    assertThat<Int>(dto!!.id, `is`<Int>(po.id))
    assertThat<String>(dto.account, `is`<String>(po.account))
    assertThat<Int>(dto.locationId, `is`<Int>(po.location!!.id))
    assertThat<String>(dto.locationName, `is`<String>(po.location!!.name))
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

    assertThat<Int>(dto.id, `is`<Int>(po.id))
    assertThat<String>(dto.account, `is`<String>(po.account))
    assertThat<Int>(dto.locationId, `is`<Int>(po.location!!.id))
    assertThat<String>(dto.locationName, `is`<String>(po.location!!.name))
  }
}