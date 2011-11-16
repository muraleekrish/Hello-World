<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.Properties" %>

<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" cellpadding="0" cellspacing="0" id="Footer">
  <tr>
    <td align="left">
<%
	 		Properties formProp = new Properties();
	  		formProp.load(getClass().getClassLoader().getResourceAsStream("buildinfo.properties"));
			out.println("Date : " + formProp.getProperty("builddate")+" | "+"Build - "+formProp.getProperty("majorversion")+"."+formProp.getProperty("minorversion")+"."+formProp.getProperty("build.number"));
%>	
	</td>
    <td>&copy; Copyright Datrics 2004</td>
  </tr>
</table>
</body>
</html:html>
