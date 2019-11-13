package com.pingan.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import java.io.InputStream;

/**
 * @description:
 * @author: shouwangqingzhong
 * @date: 2019/11/13 15:26
 */
public class XmlUtils {

    public static Object xml2Object(String inputXml, Class<?> type) throws Exception {
        if (null == inputXml || "".equals(inputXml)) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("xml", type);
        return xstream.fromXML(inputXml);
    }

    public static Object xml2Object(InputStream inputStream, Class<?> type) throws Exception {
        if (null == inputStream) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("xml", type);
        return xstream.fromXML(inputStream, type);
    }


    public static String object2Xml(Object ro, Class<?> types) throws Exception {
        if (null == ro) {
            return null;
        }
        XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("xml", types);
        return xstream.toXML(ro);
    }
}
