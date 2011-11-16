<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails" %>

<%
	UserAuthDetails userDetailsObj = new UserAuthDetails();
	String userName = "";
	if (session.getAttribute("##userRights##") != null)
	{
		userDetailsObj = (UserAuthDetails)session.getAttribute("##userRights##");
		userName = userDetailsObj.getUserId();
	}
%>
<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="300"><img src='<bean:message key="context"/>/images/logo.gif' width="300" height="30"></td>
    <td>&nbsp;</td>
    <td width="360" id="logBg">Welcome, <%= userName %> [ <a href='<bean:message key="context"/>/logoff.jsp' target="_parent">Logoff</a> ]</td>
  </tr>
</table>
</body>
</html:html>
