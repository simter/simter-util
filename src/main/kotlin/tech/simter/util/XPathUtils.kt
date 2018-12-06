package tech.simter.util

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.StringReader
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory

/**
 * Xpath util tools.
 *
 * @author RJ
 */
object XPathUtils {
  private fun parse(xml: String): Document {
    try {
      val source = InputSource(StringReader(xml))
      val domFactory = DocumentBuilderFactory.newInstance()
      // domFactory.setNamespaceAware(true); // 设为 true 将导致节点 <xx .../> 找不到
      val builder = domFactory.newDocumentBuilder()
      return builder.parse(source)
    } catch (e: ParserConfigurationException) {
      throw RuntimeException(e)
    } catch (e: SAXException) {
      throw RuntimeException(e)
    } catch (e: IOException) {
      throw RuntimeException(e)
    }
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 匹配指定路径的所有节点，找不到则返回 null
   */
  private fun findNodesInner(xml: String, xpath: String): NodeList {
    try {
      return XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(parse(xml), XPathConstants.NODESET) as NodeList
    } catch (e: XPathExpressionException) {
      throw RuntimeException(e)
    }
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param node  根节点
   * @param xpath 路径
   * @return 匹配指定路径的所有节点，找不到则返回 null
   */
  private fun findNodesInner(node: Node, xpath: String): NodeList {
    try {
      return XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(node, XPathConstants.NODESET) as NodeList
    } catch (e: XPathExpressionException) {
      throw RuntimeException(e)
    }
  }

  /**
   * 删除 xml 中无用的空白字符，包括空格、换行
   *
   * @param xml 原始的 xml
   * @return 去除空白后的 xml
   */
  fun clean(xml: String?): String? {
    return xml?.replace("(?<=>)\\s+(?=<)".toRegex(), "") ?: xml
  }

  /**
   * 获取匹配指定路径的首个节点
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 首个节点，找不到则返回 null
   */
  fun getFirstNode(xml: String, xpath: String): Node? {
    try {
      return XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(parse(xml), XPathConstants.NODE) as Node?
    } catch (e: XPathExpressionException) {
      throw RuntimeException(e)
    }
  }

  /**
   * 获取匹配指定路径的首个节点的文本内容
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 首个节点的文本内容，找不到节点则返回 null
   */
  fun getFirstNodeContent(xml: String, xpath: String): String? {
    return getFirstNode(xml, xpath)?.textContent
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param node  节点
   * @param xpath 路径
   * @return 匹配指定路径的所有节点(按节点出现的顺序)，找不到则返回 null
   */
  fun findNodes(node: Node, xpath: String): List<Node> {
    val nodes = findNodesInner(node, xpath)
    val list = ArrayList<Node>(nodes.length)
    for (i in 0 until nodes.length) list.add(nodes.item(i))
    return list
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 匹配指定路径的所有节点(按节点出现的顺序)，找不到则返回 null
   */
  fun findNodes(xml: String, xpath: String): List<Node> {
    val nodes = findNodesInner(xml, xpath)
    val list = ArrayList<Node>(nodes.length)
    for (i in 0 until nodes.length) list.add(nodes.item(i))
    return list
  }

  /**
   * 获取匹配指定路径的所有节点的文本内容集
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 匹配节点的文本内容集(按节点出现的顺序)，找不到节点则返 empty 数组
   */
  fun findNodesContent(xml: String, xpath: String): Array<String?> {
    val nodes = findNodesInner(xml, xpath)
    val contents = arrayOfNulls<String>(nodes.length)
    for (i in 0 until nodes.length) {
      contents[i] = nodes.item(i).textContent // not n.getNodeValue()
    }
    return contents
  }

  /**
   * 获取匹配指定路径的首个子节点
   *
   * @param node  节点
   * @param xpath 路径
   * @return 首个子节点，找不到则返回 null
   */
  fun getChildNode(node: Node, xpath: String): Node? {
    try {
      return XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(node, XPathConstants.NODE) as Node?
    } catch (e: XPathExpressionException) {
      throw RuntimeException(e)
    }

  }

  /**
   * 获取匹配指定路径的首个子节点的文本内容
   *
   * @param node  节点
   * @param xpath 路径
   * @return 首个子节点的文本内容，找不到子节点则返回 null
   */
  fun getChildNodeContent(node: Node, xpath: String): String? {
    val child = getChildNode(node, xpath)
    return child?.textContent
  }
}