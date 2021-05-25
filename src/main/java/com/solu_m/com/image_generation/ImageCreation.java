package com.solu_m.com.image_generation;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.xml.sax.SAXException;

@Component
public class ImageCreation {

    public byte[] convertXMLandXSLToImage(String xmlData, String xsl) throws SAXException, TransformerException, IOException, FontFormatException {
        FopFactory fopFactory = FopFactory.newInstance(new File("/home/vishal/Desktop/Image_generation/src/main/resources/fop.xconf"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PNG, foUserAgent, os);
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(IOUtils.toInputStream(xsl, StandardCharsets.UTF_8)));
        Source src = new StreamSource(IOUtils.toInputStream(xmlData, StandardCharsets.UTF_8));
        Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(src, res);
        os.close();
        return os.toByteArray();
    }

}
