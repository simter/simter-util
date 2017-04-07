package tech.simter.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 出租车监控调度系统通用工具方法
 *
 * @author RJ 2017-03-24
 */
public class XPathUtils {
  private static Document parse(String xml) {
    try {
      InputSource source = new InputSource(new StringReader(xml));
      DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
      // domFactory.setNamespaceAware(true); // 设为true将导致节点 <xx .../> 找不到
      DocumentBuilder builder = domFactory.newDocumentBuilder();
      return builder.parse(source);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 匹配指定路径的所有节点，找不到则返回 null
   */
  private static NodeList findNodesInner(String xml, String xpath) {
    try {
      return (NodeList) XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(parse(xml), XPathConstants.NODESET);
    } catch (XPathExpressionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param node  根节点
   * @param xpath 路径
   * @return 匹配指定路径的所有节点，找不到则返回 null
   */
  private static NodeList findNodesInner(Node node, String xpath) {
    try {
      return (NodeList) XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(node, XPathConstants.NODESET);
    } catch (XPathExpressionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 删除 xml 中无用的空白字符，包括空格、换行
   *
   * @param xml 原始的 xml
   * @return 去除空白后的 xml
   */
  public static String clean(String xml) {
    return xml == null ? xml : xml.replaceAll("(?<=>)\\s+(?=<)", "");
  }

  /**
   * 获取匹配指定路径的首个节点
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 首个节点，找不到则返回 null
   */
  public static Node getFirstNode(String xml, String xpath) {
    try {
      return (Node) XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(parse(xml), XPathConstants.NODE);
    } catch (XPathExpressionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取匹配指定路径的首个节点的文本内容
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 首个节点的文本内容，找不到节点则返回 null
   */
  public static String getFirstNodeContent(String xml, String xpath) {
    Node node = getFirstNode(xml, xpath);
    return node == null ? null : node.getTextContent();
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param node  节点
   * @param xpath 路径
   * @return 匹配指定路径的所有节点(按节点出现的顺序)，找不到则返回 null
   */
  public static List<Node> findNodes(Node node, String xpath) {
    NodeList nodes = findNodesInner(node, xpath);
    List<Node> list = new ArrayList<>(nodes.getLength());
    for (int i = 0; i < nodes.getLength(); i++) list.add(nodes.item(i));
    return list;
  }

  /**
   * 获取匹配指定路径的所有节点
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 匹配指定路径的所有节点(按节点出现的顺序)，找不到则返回 null
   */
  public static List<Node> findNodes(String xml, String xpath) {
    NodeList nodes = findNodesInner(xml, xpath);
    List<Node> list = new ArrayList<>(nodes.getLength());
    for (int i = 0; i < nodes.getLength(); i++) list.add(nodes.item(i));
    return list;
  }

  /**
   * 获取匹配指定路径的所有节点的文本内容集
   *
   * @param xml   文档
   * @param xpath 路径
   * @return 匹配节点的文本内容集(按节点出现的顺序)，找不到节点则返 empty 数组
   */
  public static String[] findNodesContent(String xml, String xpath) {
    NodeList nodes = findNodesInner(xml, xpath);
    String[] contents = new String[nodes.getLength()];
    for (int i = 0; i < nodes.getLength(); i++) {
      contents[i] = nodes.item(i).getTextContent(); // not n.getNodeValue()
    }
    return contents;
  }

  /**
   * 获取匹配指定路径的首个子节点
   *
   * @param node  节点
   * @param xpath 路径
   * @return 首个子节点，找不到则返回 null
   */
  public static Node getChildNode(Node node, String xpath) {
    try {
      return (Node) XPathFactory.newInstance().newXPath().compile(xpath)
        .evaluate(node, XPathConstants.NODE);
    } catch (XPathExpressionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取匹配指定路径的首个子节点的文本内容
   *
   * @param node  节点
   * @param xpath 路径
   * @return 首个子节点的文本内容，找不到子节点则返回 null
   */
  public static String getChildNodeContent(Node node, String xpath) {
    Node child = getChildNode(node, xpath);
    return child == null ? null : child.getTextContent();
  }
}