package nl.ricoapon.generate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Main {

    public static void main(String[] args) {
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(Root.class);
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,"UTF-8");
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
            OutputStream os = new FileOutputStream("./test.xml");
            marshaller.marshal(new Root(), os);

        } catch (javax.xml.bind.JAXBException | FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}
