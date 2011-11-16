<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%
	if(session!=null)
	{
		session.setMaxInactiveInterval(1);
		session.removeAttribute("##userrights##");
	}
%>
<html:html locale="true">
<script>
	window.open("<bean:message key="context"/>/login.jsp","_top")
</script>
</html:html>
