<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script>
function clos()
{
		var features = "toolbars=no,status=1,location=no,resizable=no,top=0,left=0";
		window.open("IndexPages/index.jsp","_blank",features);
		window.opener=null;
		window.close();
		return false;
}
</script>
</head>
<body onload="clos()">

</body>
</html:html>
