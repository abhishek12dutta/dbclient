package com.dbclient.xmlgenerator;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlGenerator {

	public static void createConnectionXml(String id, String url,
			String hostname, String user_name, String user_password) {

		try {
			File file = new File(
					"file.xml");
			Document doc = null;
			Element connections = null;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			if (file.exists()) {
				doc = docBuilder.parse(file);
				doc.getDocumentElement().normalize();
				System.out.println("Root element :"
						+ doc.getDocumentElement().getNodeName());
				if ("connections"
						.equals(doc.getDocumentElement().getNodeName())) {
					connections = (Element) doc.getElementsByTagName(
							"connections").item(0);
				}
			} else {
				doc = docBuilder.newDocument();
				connections = doc.createElement("connections");
				doc.appendChild(connections);
			}
			createConnection(connections, doc, id, url, hostname, user_name,
					user_password);
			// write the content into xml file
			writeFileToXML(file, doc);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void writeFileToXML(File file, Document doc)
			throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, IOException,
			TransformerException {
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = null;

		file.createNewFile();
		result = new StreamResult(file);
		transformer.transform(source, result);
		System.out.println("File saved!");
	}

	private static void createConnection(Element connections, Document doc,
			String id, String url, String hostname, String user_name,
			String user_password) {

		// create connection
		Element connection = doc.createElement("connection");
		connections.appendChild(connection);

		// set attribute to connection
		Attr attr = doc.createAttribute("id");
		attr.setValue(id);
		connection.setAttributeNode(attr);

		// connection-url
		Element connection_url = doc.createElement("connection-url");
		connection_url.appendChild(doc.createTextNode(url));
		connection.appendChild(connection_url);

		// host
		Element host = doc.createElement("host");
		host.appendChild(doc.createTextNode(hostname));
		connection.appendChild(host);

		// username
		Element username = doc.createElement("username");
		username.appendChild(doc.createTextNode(user_name));
		connection.appendChild(username);

		// password
		Element password = doc.createElement("password");
		password.appendChild(doc.createTextNode(user_password));
		connection.appendChild(password);

	}

	public static void deleteConnection(String id) {

		File file = new File(
				"H:\\Java_work\\Mona_Spring\\SpringProject\\src\\main\\resources\\file.xml");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);
			NodeList nodeList = doc.getElementsByTagName("connection");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String elementIdname = element.getAttribute("id");
					if (elementIdname.equals(id)) {
						Node prev = node.getPreviousSibling();
						if (prev != null
								&& prev.getNodeType() == Node.TEXT_NODE
								&& prev.getNodeValue().trim().length() == 0) {
							doc.getDocumentElement().removeChild(prev);
						}
						doc.getDocumentElement().removeChild(element);
						System.out.println("Delete Done");
					}
				}
			}

			doc.getDocumentElement().normalize();
			writeFileToXML(file, doc);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
