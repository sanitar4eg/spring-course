package edu.learn.util;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

	@Override
	public String marshal(LocalDateTime arg0) throws Exception {
		return arg0.toString();
	}

	@Override
	public LocalDateTime unmarshal(String arg) throws Exception {
		return LocalDateTime.parse(arg);
	}

}
