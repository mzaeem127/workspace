package com.sabby.date.format;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) throws Exception {
		String amanda6Date = "2015-08-04T04:00:00.000Z";
		String amanda7Date = "2015-08-04T23:00:00-04:00";

		Date date6 = javax.xml.bind.DatatypeConverter.parseDateTime(amanda6Date).getTime();
		System.out.println(amanda6Date + "  =  " + date6);
		Date date7 = javax.xml.bind.DatatypeConverter.parseDateTime(amanda7Date).getTime();
		System.out.println(amanda7Date + "  =  " + date7);
	}
}
