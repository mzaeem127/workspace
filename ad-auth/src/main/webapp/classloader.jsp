<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ page import="java.io.*"%>
<html>
<head><title>Class Loader Debugger</title></head>
<%!public static String findClass(String className) {
		try {
			Class clazz = Class.forName(className);
			String location = "Unknown";
			if (clazz.getProtectionDomain().getCodeSource() != null) {
				location = clazz.getProtectionDomain().getCodeSource().getLocation().toString();
			}
			return "Found: " + location+"<br>ClassLoader: "+clazz.getClassLoader().getClass().getName();
		} catch (Throwable ex) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			//ex.printStackTrace();
			return "<pre>" + writer.toString() + "</pre>";
		}
	}

	public String resourceExists(String resource) {
		try {
			URL url = this.getClass().getResource(resource);
			if (url != null) {
				return url.toString();
			}
			return "Resource Not Found: " + resource;
		} catch (Throwable ex) {
			StringWriter writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			//ex.printStackTrace();
			return "<pre>" + writer.toString() + "</pre>";
		}
	}%>


<body>
<h1>Class Loader Debugger</h1>
<%
	List clazzes = new ArrayList();
	// External libraries check
	clazzes.add("com.lowagie.text.Document");
	clazzes.add("org.apache.poi.hssf.usermodel.HSSFWorkbook");
	clazzes.add("com.sun.pdfview.PDFRenderer");
	clazzes.add("javax.activation.CommandMap");
	clazzes.add("oracle.jdbc.driver.OracleDriver");
	clazzes.add("javax.xml.parsers.DocumentBuilder");
	clazzes.add("javax.mail.Transport");
	clazzes.add("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	clazzes.add("org.apache.log4j.Category");
	clazzes.add("org.apache.commons.logging.Log");

	// Internal API checks
	clazzes.add("com.company.product.util.YourUtility");

	Iterator iterator = clazzes.iterator();
	while (iterator.hasNext()) {
		String clazz = (String) iterator.next();
		out.print("<strong>" + clazz + "</strong><br>");
		out.println(findClass(clazz));
		out.println("<br><hr>");
	}

	// the below code is to make sure all the required important resources are available in the classpath
	List resources = new ArrayList();
	resources.add("/system.properties");
	iterator = resources.iterator();
	while (iterator.hasNext()) {
		String resource = (String) iterator.next();
		out.print("<strong>" + resource + "</strong><br>");
		out.println(resourceExists(resource));
		out.println("<br><hr>");
	}
%>
</body>
</html>
