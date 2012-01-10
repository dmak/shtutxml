/*
 * This file is a part of ShtutXML
 * (c) Barak Itkin <lightningismyname at gmail dot com>, 2011
 *
 * ShtutXML is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.google.shtutxml;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * An example class that shows how to use this package
 */
public class Example {

    public static void printDoc(Node e) {
        new DocumentPrinter().visit(e, DepthDocumentVisitor.VisitOrder.DEPTH_FIRST);
    }

    public static Document parse(InputStream in) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        return db.parse(in);
    }

    public static void main(String[] args) {
        try {
            Document xmlDocument = parse(Example.class.getResourceAsStream("test.xml"));
            StrXML demo = new StrXML(xmlDocument);

            System.out.println("************* BASE DOCUMENT *****************");
            printDoc(xmlDocument);

            System.out.println("\n*** Text ***");
            System.out.println("\"" + demo.getText() + "\"");

            System.out.println("\n*** Node of each text segment ***");
            System.out.println(demo.toString());

            System.out.println("\n*** Offset testing ***");
            System.out.println("offset " + 0 + " is at " + demo.getOffset(0));
            System.out.println("offset " + 40 + " is at " + demo.getOffset(40));
            System.out.println("offset " + 80 + " is at " + demo.getOffset(80));

            System.out.println();

            System.out.println("*********** Manipulation *****************");
            // demo.GetOffset(3).textSection.wrapWith(demo.XMLDocument.createElement("u"));
            // demo.splitSectionUpTo(demo.GetOffset(2), demo.GetOffset(2).getParentNode().getParentNode());
            demo.insertAndSplitInserted(xmlDocument.createElement("u"), demo.getOffset(2), demo.getOffset(9));
            demo.insertAndSplitInserted(xmlDocument.createElement("k"), demo.getOffset(1), demo.getOffset(4));

            printDoc(xmlDocument);

            demo.insertAndSplitParent(xmlDocument.createElement("span"), demo.getOffset(0), demo.getOffset(6));

            printDoc(xmlDocument);
            System.out.println("\n*** Text ***");
            System.out.println("\"" + demo.getText() + "\"");

            System.out.println("\n*** Node of each text segment ***");
            System.out.println(demo.toString());

            System.out.println("\n*** Offset testing ***");
            System.out.println("offset " + 0 + " is at " + demo.getOffset(0));
            System.out.println("offset " + 40 + " is at " + demo.getOffset(40));
            System.out.println("offset " + 80 + " is at " + demo.getOffset(80));
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Example.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
