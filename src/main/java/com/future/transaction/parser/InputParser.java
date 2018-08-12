package com.future.transaction.parser;

import com.future.transaction.exception.TransactionServiceException;
import com.future.transaction.model.mapping.RecordMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

@Component
public class InputParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    PropertyResourceBundle propertyResourceBundle;
    File file;

    public InputParser() {

        try {
            log.info("Reading xml record file");
            ClassLoader classLoader = getClass().getClassLoader();
            file = new File(classLoader.getResource("xml/record.xml").getFile());
            InputStream inputStream = new FileInputStream(file);

            propertyResourceBundle = new PropertyResourceBundle(inputStream);

        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    public List<RecordMap> loadRecordMapping() throws TransactionServiceException {

        List<RecordMap> recordMappingList = null;

        try {
            final Document document = parseXML();

            final NodeList attributeNodeList = document
                    .getElementsByTagName("attribute");
            recordMappingList = new ArrayList<>();

            for (int attributeNodeTags = 0; attributeNodeTags <
                    attributeNodeList.getLength(); attributeNodeTags++) {
                final Node attributeNode = attributeNodeList.item(attributeNodeTags);
                if (attributeNode.getNodeType() == Node.ELEMENT_NODE) {
                    final RecordMap recordMap = new RecordMap();
                    final Element attributeElement = (Element) attributeNode;
                    recordMap.setAttributeName(attributeElement
                            .getAttribute("name"));
                    recordMap.setAttributeSize(Integer.valueOf(attributeElement
                            .getAttribute("size")));
                    recordMap.setAttributeType(attributeElement
                            .getAttribute("type"));
                    recordMap.setAttributeDesc(attributeElement
                            .getAttribute("desc"));
                    recordMappingList.add(recordMap);
                }
            }
        } catch (org.w3c.dom.DOMException e) {
            throw new TransactionServiceException(e.getMessage());

        } catch (NumberFormatException e) {

            throw new TransactionServiceException(e.getMessage());
        }

        return recordMappingList;
    }


    public Document parseXML() throws TransactionServiceException {

        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();
            log.debug("Root element {} ", document.getDocumentElement().getNodeName());

        } catch (ParserConfigurationException e) {
            throw new TransactionServiceException(e.getMessage());

        } catch (SAXException e) {
            throw new TransactionServiceException(e.getMessage());

        } catch (IOException e) {
            throw new TransactionServiceException(e.getMessage());

        }

        return document;
    }

}