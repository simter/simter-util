package tech.simter.util

import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.hamcrest.Matchers.emptyArray
import org.junit.Assert.assertThat
import org.junit.Test

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
    assertThat<String>(XPathUtils.getFirstNodeContent(oneBookXml, "/books/notExists"), nullValue())
    assertThat<String>(XPathUtils.getFirstNodeContent(oneBookXml, "/books/book"), `is`("book1"))
    assertThat<String>(XPathUtils.getFirstNodeContent(oneBookXml, "/books/book[1]"), `is`("book1"))

    assertThat<String>(XPathUtils.getFirstNodeContent(oneBookXml, "//book"), `is`("book1"))
    assertThat<String>(XPathUtils.getFirstNodeContent(oneBookXml, "//book[1]"), `is`("book1"))
  }

  @Test
  fun getFirstNodeContentFromTwoBooksXml() {
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/notExists"), nullValue())
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/book"), `is`("book1"))
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/book[2]"), `is`("book2"))

    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "//book"), `is`("book1"))
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "//book[2]"), `is`("book2"))
  }

  @Test
  fun findNodesContentFromOneBookXml() {
    assertThat(XPathUtils.findNodesContent(oneBookXml, "/books/notExists"), emptyArray())
    assertThat(XPathUtils.findNodesContent(oneBookXml, "/books/book"), allOf(
      Matchers.notNullValue(),
      Matchers.arrayWithSize(1),
      Matchers.arrayContaining("book1")
    ))
  }

  @Test
  fun findNodesContentFromTwoBooksXml() {
    assertThat<Array<String?>>(XPathUtils.findNodesContent(twoBooksXml, "/books/notExists"), emptyArray())
    assertThat<Array<String?>>(XPathUtils.findNodesContent(twoBooksXml, "/books/book"), allOf(
      Matchers.notNullValue(),
      Matchers.arrayWithSize(2),
      Matchers.arrayContaining("book1", "book2")
    ))
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
    assertThat<String>(XPathUtils.getChildNodeContent(book!!, "code"), `is`("code"))
    assertThat<String>(XPathUtils.getChildNodeContent(book, "//code"), `is`("code"))
    assertThat<String>(XPathUtils.getChildNodeContent(book, "name"), `is`("name"))
    assertThat<String>(XPathUtils.getChildNodeContent(book, "msg"), `is`(""))
    assertThat<String>(XPathUtils.getChildNodeContent(book, "not-exists"), nullValue())
  }
}
