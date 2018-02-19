package com.dbclient.xmlgenerator;

public class TestXMLGenerator {
	
	public static void main(String[] args) {
		
		XmlGenerator.createConnectionXml("monalisaDB","url","hostname","username","password");
		XmlGenerator.createConnectionXml("abhiDB","url","hostname","username","password");
		XmlGenerator.deleteConnection("monalisaDB");
	}

}
