package org.apache.ibatis.configuration;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用DOM4J解析XML配置文件
 */
public class XmlMapperBuilder {

    /**
     * 配置数据封装对象
     */
    private Configuration configuration;

    /**
     * 有参的构造方法
     * @param configuration
     */
    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 传入配置文件的字节流，解析配置文件
     * @param inputStream
     */
    public void parse(InputStream inputStream) throws DocumentException {
        SAXReader saxReader=new SAXReader();
        //XML文档对象
        Document document = saxReader.read(inputStream);
        //获取文档的根节点
        Element rootElement = document.getRootElement();
        //获取根节点的属性对象
        Attribute attributeNamespace = rootElement.attribute("namespace");
        //com.xhc.mapper.UserMapper
        String namespace = attributeNamespace.getValue();

        //xpath解析，解析XML配置文件 获取所有的查询相关配置节点，获取的是个集合所以是List<Element>
        List<Element> selectList = rootElement.selectNodes("//select");
        //xpath解析，解析XML配置文件 获取所有的新增相关配置节点
        List<Element> insertList = rootElement.selectNodes("//insert");
        //xpath解析，解析XML配置文件 获取所有的更新相关配置节点
        List<Element> updateList = rootElement.selectNodes("//update");
        //xpath解析，解析XML配置文件 获取所有的删除相关配置节点
        List<Element> deleteList = rootElement.selectNodes("//delete");

        //将所有的集合放进大集合中
        List<Element> allList=new ArrayList<Element>();
        allList.addAll(selectList);
        allList.addAll(insertList);
        allList.addAll(updateList);
        allList.addAll(deleteList);

        for (Element element : allList) {
            //每一条SQL的ID属性值
            String id = element.attributeValue("id");
            //获取返回的返回值类型
            String resultType=element.attributeValue("resultType"); //增删改都默认返回int，不一定都是int，可能有对象，所以用string
            //获取参数类型
            String parameterType=element.attributeValue("parameterType");
            //获取每个mapper节点中的SQL语句（Trim去掉前后空格）
            String sqlText = element.getTextTrim();

            //封装对象
            MappedStatement mappedStatement=new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);
            mappedStatement.setSqlType(element.getName());//拿到每个SQL语句的标签：update、select、insert...

            String key=namespace+"."+id;
            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

    }

}
