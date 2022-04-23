package com.example.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * @author Alexanrd Spaskin
 */
public class LocalDateXMLAdapter extends XmlAdapter<String, LocalDate> {

    public String marshal(LocalDate date) {
        return date.toString();
    }

    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date);
    }


}
