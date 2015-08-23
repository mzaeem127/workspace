package org.sabby.sql.executor.config;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "databases")
public class Databases {

	private List<Database> databases;

	public Databases() {
		databases = new ArrayList<>();
	}

	@XmlElement(name = "database")
	public List<Database> getDatabases() {
		return databases;
	}

	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}

	public static Databases loadXml(InputStream inputStream) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Databases.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(inputStream);
		return (Databases) obj;
	}

	public String toXml() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Databases.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		marshaller.marshal(this, writer);
		return writer.toString();
	}
}
