package nl.ricoapon.generate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.GregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Person", propOrder = {"name", "startDate", "endDate",
        "location"})
public class Person {

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "StartDate")
    private String startDate;

    @XmlElement(name = "EndDate")
    private String endDate;

    @XmlElement(name = "Location")
    private String location;

    public Person() {
        super();
        SecureRandom random = new SecureRandom();
        name = new BigInteger(20, random).toString(32) + " " + new BigInteger(20, random).toString(32);
        location = new BigInteger(50, random).toString(32);
        startDate = randomDate();
        endDate = randomDate();
    }

    private String randomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.get(gc.YEAR) + "-" + gc.get(gc.MONTH) + "-" + gc.get(gc.DAY_OF_MONTH);
    }

    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
