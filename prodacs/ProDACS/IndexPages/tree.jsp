<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ page import="com.savantit.prodacs.presentation.menuconfiguration.MenuConfiguration" %>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails" %>

<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="JavaScript" src='<bean:message key="context"/>/library/tree.js'></script>
<script language="JavaScript" src='<bean:message key="context"/>/library/tree_tpl.js'></script>
</head>
<%
	UserAuthDetails userDetails= (UserAuthDetails)request.getSession().getAttribute("##userRights##");
	MenuConfiguration mc = new MenuConfiguration();
	String tree_items = mc.getMenuItems(userDetails);
%>
<body class="NavigationTree">
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td valign="top" class="TreeItems">
	  <script language="JavaScript">
		<!--
	  
			new tree(<%=tree_items%>, tree_tpl);
		//-->
	  </script>
	</td>
  </tr>
</table>
</body>
</html:html>
