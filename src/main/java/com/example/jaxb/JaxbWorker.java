package com.example.jaxb;

import com.example.repository.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;


/**
 * @author Alexanrd Spaskin
 */
public class JaxbWorker {

    public boolean saveRepository(Repository repository){
        try {
            JAXBContext jc = JAXBContext.newInstance(Repository.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(repository, new File("./JAXB.xml"));
            return true;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public Repository recoveryRepository(){
        try {
            JAXBContext jc = JAXBContext.newInstance(Repository.class);
            Unmarshaller um = jc.createUnmarshaller();
            return (Repository) um.unmarshal(new File("./JAXB.xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
