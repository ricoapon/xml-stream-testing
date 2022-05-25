package nl.ricoapon.generate;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "root", propOrder = {"person"})
public class Root {

    @XmlElement(name = "Person")
    private List<Person> person;

    public Root() {
        super();
        this.person = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            person.add(new Person());
        }

    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

}
