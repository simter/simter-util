package tech.simter.util;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.w3c.dom.Node;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.emptyArray;
import static org.junit.Assert.assertThat;

/**
 * http://rongjih.blog.163.com/blog/static/33574461201552932312681/
 *
 * @author RJ 2017-03-27
 */
public class XPathUtilsTest {
  private String oneBookXml = "" +
    "<books>" +
    "  <book>book1</book>" +
    "</books>";

  private String twoBooksXml = "" +
    "<books>" +
    "  <book>book1</book>" +
    "  <book>book2</book>" +
    "</books>";

  @Test
  public void getFirstNodeContentFromOneBookXml() {
    assertThat(XPathUtils.getFirstNodeContent(oneBookXml, "/books/notExists"), nullValue());
    assertThat(XPathUtils.getFirstNodeContent(oneBookXml, "/books/book"), is("book1"));
    assertThat(XPathUtils.getFirstNodeContent(oneBookXml, "/books/book[1]"), is("book1"));

    assertThat(XPathUtils.getFirstNodeContent(oneBookXml, "//book"), is("book1"));
    assertThat(XPathUtils.getFirstNodeContent(oneBookXml, "//book[1]"), is("book1"));
  }

  @Test
  public void getFirstNodeContentFromTwoBooksXml() {
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/notExists"), nullValue());
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/book"), is("book1"));
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "/books/book[2]"), is("book2"));

    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "//book"), is("book1"));
    assertThat(XPathUtils.getFirstNodeContent(twoBooksXml, "//book[2]"), is("book2"));
  }

  @Test
  public void findNodesContentFromOneBookXml() {
    assertThat(XPathUtils.findNodesContent(oneBookXml, "/books/notExists"), emptyArray());
    assertThat(XPathUtils.findNodesContent(oneBookXml, "/books/book"), allOf(
      Matchers.notNullValue(),
      Matchers.arrayWithSize(1),
      Matchers.arrayContaining("book1")
    ));
  }

  @Test
  public void findNodesContentFromTwoBooksXml() {
    assertThat(XPathUtils.findNodesContent(twoBooksXml, "/books/notExists"), emptyArray());
    assertThat(XPathUtils.findNodesContent(twoBooksXml, "/books/book"), allOf(
      Matchers.notNullValue(),
      Matchers.arrayWithSize(2),
      Matchers.arrayContaining("book1", "book2")
    ));
  }

  @Test
  public void getChildNode() {
    String xml = "" +
      "<book>" +
      "  <name>name</name>" +
      "  <code>code</code>" +
      "  <msg/>" +
      "</book>";
    Node book = XPathUtils.getFirstNode(xml, "/book");
    assertThat(XPathUtils.getChildNodeContent(book, "code"), is("code"));
    assertThat(XPathUtils.getChildNodeContent(book, "//code"), is("code"));
    assertThat(XPathUtils.getChildNodeContent(book, "name"), is("name"));
    assertThat(XPathUtils.getChildNodeContent(book, "msg"), is(""));
    assertThat(XPathUtils.getChildNodeContent(book, "not-exists"), nullValue());
  }
}
