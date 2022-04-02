package com.xhc.test;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.util.List;

public class TestBook {

    /**
     * 测试DOM4J  Parsing XML(解析book.xml——xml文件能被读取，并且被解析)
     */
    @Test
    public void test1(){
        try {
            //创建一个解析器
            SAXReader saxReader = new SAXReader();
            //获取一个文档对象
            Document document = saxReader.read("D:\\hand-write-ssm\\hand-write-mybatis\\src\\test\\resources\\book.xml");
            //获取XML文件的根节点(book.xml中的<books>)
            Element rootElement = document.getRootElement();
            System.out.println("根节点的名字是:"+rootElement.getName());
            //获取子节点的集合
            List<Element> elements = rootElement.elements();
            System.out.println("子节点的个数为:"+elements.size());
            //遍历子节点集合
            for (Element element : elements) {
                //节点属性的对象(解析属性对应的值)
                Attribute attribute = element.attribute("id");
                String value = attribute.getValue();
                System.out.println("id="+value);
                //book节点下的子节点集合
                List<Element> children = element.elements();
                for (Element child : children) {
                    //标签的名字
                    String tagName = child.getName();
                    //标签内部的值
                    String text = child.getText();
                    System.out.println(tagName+"="+text);
                }
                System.out.println("---------------------------");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
