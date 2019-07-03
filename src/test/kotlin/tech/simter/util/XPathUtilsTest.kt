package tech.simter.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

/**
 * http://rongjih.blog.163.com/blog/static/33574461201552932312681/
 *
 * @author RJ 2017-03-27
 */
class XPathUtilsTest {
  private val oneBookXml = "" +
    "<books>" +
    "  <book>book1</book>" +
    "</books>"

  private val twoBooksXml = "" +
    "<books>" +
    "  <book>book1</book>" +
    "  <book>book2</book>" +
    "</books>"

  @Test
  fun getFirstNodeContentFromOneBookXml() {
    assertNull(XPathUtils.getFirstNodeContent(oneBookXml, "/books/notExists"))
    assertEquals("book1", XPathUtils.getFirstNodeContent(oneBookXml, "/books/book"))
    assertEquals("book1", XPathUtils.getFirstNodeContent(oneBookXml, "/books/book[1]"))

    assertEquals("book1", XPathUtils.getFirstNodeContent(oneBookXml, "//book"))
    assertEquals("book1", XPathUtils.getFirstNodeContent(oneBookXml, "//book[1]"))
  }

  @Test
  fun getFirstNodeContentFromTwoBooksXml() {
    assertNull(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/notExists"))
    assertEquals("book1", XPathUtils.getFirstNodeContent(twoBooksXml, "/books/book"))
    assertEquals("book2", XPathUtils.getFirstNodeContent(twoBooksXml, "/books/book[2]"))

    assertEquals("book1", XPathUtils.getFirstNodeContent(twoBooksXml, "//book"))
    assertEquals("book2", XPathUtils.getFirstNodeContent(twoBooksXml, "//book[2]"))
  }

  @Test
  fun findNodesContentFromOneBookXml() {
    assertThat(XPathUtils.findNodesContent(oneBookXml, "/books/notExists")).isEmpty()
    assertThat(XPathUtils.findNodesContent(oneBookXml, "/books/book"))
      .isNotNull
      .hasSize(1)
      .contains("book1")
  }

  @Test
  fun findNodesContentFromTwoBooksXml() {
    assertThat(XPathUtils.findNodesContent(twoBooksXml, "/books/notExists")).isEmpty()
    assertThat(XPathUtils.findNodesContent(twoBooksXml, "/books/book"))
      .isNotNull
      .hasSize(2)
      .contains("book1", "book2")
  }

  @Test
  fun getChildNode() {
    val xml = "" +
      "<book>" +
      "  <name>name</name>" +
      "  <code>code</code>" +
      "  <msg/>" +
      "</book>"
    val book = XPathUtils.getFirstNode(xml, "/book")
    assertEquals("code", XPathUtils.getChildNodeContent(book!!, "code"))
    assertEquals("code", XPathUtils.getChildNodeContent(book, "//code"))
    assertEquals("name", XPathUtils.getChildNodeContent(book, "name"))
    assertEquals("", XPathUtils.getChildNodeContent(book, "msg"))
    assertNull(XPathUtils.getChildNodeContent(book, "not-exists"))
  }
}
