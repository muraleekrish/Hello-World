<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	if(session!=null)
	{
		session.removeAttribute("##userrights##");
	}
%>
<html>
<head>
<title> Authorization Error Page </title>
<!--meta http-equiv="Refresh" content="5;"_top";URL='../login.jsp';charset=iso-8909-1"-->
<script>
	setTimeout("delay()",1200);
	function delay()
	{
		window.open("<bean:message key="context"/>/login.jsp","_top")
	}
</script>
</head>
<body>
<b><font color="red">Your Session Timed Out! </font><br>
 Login again. </b>
</body>
<html>