package tech.simter.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import tech.simter.util.StringUtils.CaseType.UpperCase
import tech.simter.util.StringUtils.underscore

/**
 * Test [StringUtils].
 *
 * @author RJ
 */
class StringUtilsTest {
  @Test
  fun `underscore - lower-case`() {
    assertEquals("my_work", underscore("myWork"))
    assertEquals("my_work", underscore("MyWork"))

    assertEquals("my_office_work", underscore("myOfficeWork"))
    assertEquals("my_office_work", underscore("MyOfficeWork"))

    assertEquals("a", underscore("a"))
    assertEquals("a", underscore("A"))
    assertEquals("abc", underscore("Abc"))
    assertEquals("abc", underscore("ABC"))
    assertEquals("ab_car", underscore("ABCar"))
    assertEquals("user_dto4_form", underscore("UserDto4Form"))
  }

  @Test
  fun `underscore - upper-case`() {
    assertEquals("MY_WORK", underscore(source = "myWork", caseType = UpperCase))
    assertEquals("MY_WORK", underscore(source = "MyWork", caseType = UpperCase))

    assertEquals("MY_OFFICE_WORK", underscore(source = "myOfficeWork", caseType = UpperCase))
    assertEquals("MY_OFFICE_WORK", underscore(source = "MyOfficeWork", caseType = UpperCase))

    assertEquals("A", underscore(source = "a", caseType = UpperCase))
    assertEquals("A", underscore(source = "A", caseType = UpperCase))
    assertEquals("ABC", underscore(source = "Abc", caseType = UpperCase))
    assertEquals("ABC", underscore(source = "ABC", caseType = UpperCase))
    assertEquals("AB_CAR", underscore(source = "ABCar", caseType = UpperCase))
    assertEquals("USER_DTO4_FORM", underscore(source = "UserDto4Form", caseType = UpperCase))
  }
}