<%@ page language = "java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManagerHome" %>
<%@ page import="com.savantit.prodacs.facade.SessionProductionDetailsManager" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Map" %>
<useradmin:userrights resource="42"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Posting Rules");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HashMap postRules = new HashMap();
	String value = "";
	String key = "";
	try
	{
		/* 	Setting the JNDI name and Environment 	*/
	   	obj.setJndiName("SessionProductionDetailsManagerBean");
   		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionProductionDetailsManagerHome objprodHome = (SessionProductionDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionProductionDetailsManagerHome.class);
		SessionProductionDetailsManager prodObj = (SessionProductionDetailsManager)PortableRemoteObject.narrow(objprodHome.create(),SessionProductionDetailsManager.class);
		
		postRules = prodObj.currentPostingRule();
		if (BuildConfig.DMODE)
			System.out.println("Current Rules(HM) :"+postRules);
		pageContext.setAttribute("postRules",postRules);

		Set set = postRules.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext())
		{
			Map.Entry me = (Map.Entry)it.next();
			key = me.getKey().toString();
			if (key.equalsIgnoreCase("Id"))
			{
				value = me.getValue().toString();
				pageContext.setAttribute("value",value);
			}
		}
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Error in PostingRule.jsp");
			e.printStackTrace();
		}
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.postingrule.posting.titleheader"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script>
	function applyItem()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('ruleList');
		for(var i = 1; i < obj.children(0).children.length; i++)
		{
			if (obj.children(0).children(i).children(0).children(0).checked)
			{
				temp.appliedRule.value = obj.children(0).children(i).children(0).children(0).value;
				temp.formAction.value = "apply";
				temp.submit();
			}
		}
	}

	function checkRules()
	{
		var temp = document.forms[0];
		var obj = document.getElementById('ruleList');
		//alert('<%= value %>');
		for(var i = 1; i < obj.children(0).children.length; i++)
		{
			if ((parseInt(obj.children(0).children(i).children(0).children(0).value,10)) == (parseInt('<%= value %>',10)))
			{
				obj.children(0).children(i).children(0).children(0).checked = true;
			}
		}
	}
</script>
<body onLoad="checkRules();">
<html:form action="frmPostingRule">
<html:hidden property="appliedRule"/>
<html:hidden property="formAction"/>
<table width="100%" cellpadding="0" cellspacing="0">
<tr>
<td>
<table width="100%" height="100%" cellpadding="10" cellspacing="0">
  <tr>
    <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
        <tr>
          <td><bean:message key="prodacs.postingrule.posting.titleheader"/></td>
        </tr>
      </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" border="0" align="right">
        <tr>
			<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Apply Posting Rule'; return true"  onMouseOut="window.status=''; return true" resourceId="1042" text="[ Apply ]" classId="TopLnk" onClick="javaScript:applyItem();"/>
        </tr>
        </table>
        <br>
        <font class="message">
			<html:messages id="messageid" message="true">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" />
		   </html:messages>
		   </font>
			<table>
				<tr> 
					<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
				</tr>
			</table>
        <br>
        <table width="100%" cellpadding="3" cellspacing="0" id="ruleList">
          <tr>
            <td width="25" class="SortHeader">&nbsp;</td>
            <td class="SortHeader"><bean:message key="prodacs.postingrule.postingrules"/></td>
          </tr>
		  <%
			Set set = postRules.entrySet();
			Iterator it = set.iterator();
			while (it.hasNext())
			{
				Map.Entry me = (Map.Entry)it.next();
				key = me.getKey().toString();
				if (!key.equalsIgnoreCase("Id"))
				{
		  %>
		  <tr>
		  	  <td class="TableItems"><input type="radio" name="ruleNo" value="<%= key %>"></td>
			  <td class="TableItems"><%= me.getValue() %></td>
		  <%
				}
			}
		   %>
		  </tr>
		 </table>
</td>			  
</tr>
</table>
</html:form>
</body>
</html:html>
