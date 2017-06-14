package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtils {
	public static Document getDocument(String fileName){
		if(isXML(fileName)){
			SAXReader reader = new SAXReader();
			try {
				FileInputStream ifile = new FileInputStream(fileName); 
				InputStreamReader ir = new InputStreamReader(ifile, "utf-8"); 
				return reader.read(ir);
			} catch (DocumentException e) {
				e.printStackTrace();
				FileUtils.writeString("alog.txt",e.toString());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				FileUtils.writeString("alog.txt",e.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				FileUtils.writeString("alog.txt",e.toString());
			}
		}
		
		return null;
	}
	
	public static String getValueFromElementName(Document document,String elementName){
		if(document == null) return null;
		
		Element rootElement = document.getRootElement();
		
		@SuppressWarnings("rawtypes")
		Iterator elementIt = rootElement.elementIterator();
		
		while (elementIt.hasNext()) {
			Element element = (Element) elementIt.next();
			System.out.println(element.getName());
			if(element.getName().equals(elementName)){
				return element.getStringValue();
			}
		}
		
		return null;
	}
	
	public static String getValueFromElementAttribute(Document document,String name){
		if(document == null) return null;
		
		Element rootElement = document.getRootElement();
		
		@SuppressWarnings("rawtypes")
		Iterator elementIt = rootElement.elementIterator();
		
		while (elementIt.hasNext()) {
			Element element = (Element) elementIt.next();
			
			@SuppressWarnings("rawtypes")
			Iterator attribute = element.attributeIterator();
			
			while(attribute.hasNext()){
				Attribute a  = (Attribute) attribute.next();
				if("name".equals(a.getName()) && a.getValue() != null && a.getValue().equals(name)){
					try {
						return element.getText();
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}

			}
		}
		
		return null;
	}
	
	public static String getAttributeValueFromRootNode(Document document,ArrayList<String> nodes, String name){
		if(document == null) return null;
		
		Element rootElement = document.getRootElement();
		
		if(nodes.size() == 0){
			return rootElement.attributeValue(name);
		}
		
		return null;
	}
	
	public static boolean isXML(String fileName){
		if(fileName == null || fileName.isEmpty()){
			return false;
		}
		
		if(fileName.endsWith(".xml")){
			if(new File(fileName).exists())
				return true;
		}
		
		return false;
	}
}
