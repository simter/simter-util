package tech.simter.util;

import org.junit.Test;
import tech.simter.util.bean.Location;
import tech.simter.util.bean.UserDto;
import tech.simter.util.bean.UserPo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author RJ
 */
public class BeanUtilsTest {
  private int id = 0;

  @Test
  public void assign2TargetType() {
    UserPo po = new UserPo();
    po.id = ++id;
    po.setAccount("account");
    po.location = new Location();
    po.location.id = ++id;
    po.location.name = "location";
    UserDto dto = BeanUtils.assign(UserDto.class, po);

    assertThat(dto.id, is(po.id));
    assertThat(dto.account, is(po.getAccount()));
    assertThat(dto.locationId, is(po.location.id));
    assertThat(dto.locationName, is(po.location.name));
  }

  @Test
  public void assign2TargetObject() {
    UserPo po = new UserPo();
    po.id = ++id;
    po.setAccount("account");
    po.location = new Location();
    po.location.id = ++id;
    po.location.name = "location";

    UserDto dto = new UserDto();
    BeanUtils.assign(dto, po);

    assertThat(dto.id, is(po.id));
    assertThat(dto.account, is(po.getAccount()));
    assertThat(dto.locationId, is(po.location.id));
    assertThat(dto.locationName, is(po.location.name));
  }
}