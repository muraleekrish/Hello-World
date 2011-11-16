<%@ page language = "java" %>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>

<%@ page import="java.io.InputStream"%>
<%@ page import="com.savantit.prodacs.util.CastorXML"%>
<%@ page import="com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.RadialProductionEditForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%
    StandardHours objStdHours = new StandardHours();
	try
	{
		InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
		objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
			System.out.println("Error in View Operations.jsp");
	}
%>
<html:html>
<head>
<title><bean:message key="prodacs.production.viewoperations"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	function closeItem()
	{
		window.close();
	}

	function loadDefault()
	{
		temp = document.forms[0];
		temp.sno.value = window.opener.document.forms[0].sno.value;
		temp.name.value = window.opener.document.forms[0].name.value;
		temp.grpId.value = window.opener.document.forms[0].grpId.value;
		temp.grpCode.value = window.opener.document.forms[0].gpCde.value;
		temp.stdHrs.value = window.opener.document.forms[0].stdHrs.value;
	<%
		if (objStdHours.isOpnlevelstdhrs() == true)
		{
	%>
		obj = document.getElementById("opnTbl");
		var opnSno = (temp.sno.value).split("~");
		var opnName = (temp.name.value).split("~");
		var opnGrpId = (temp.grpId.value).split("~");
		var opnGrpCde = (temp.grpCode.value).split("~");
		var opnStdHrs = (temp.stdHrs.value).split("~");
		for(var i = 0; i < opnSno.length; i++)
		{
			if (i==0)
			{
				obj.children(i).children(0).children(0).children(0).value = opnSno[i];
				obj.children(i).children(0).children(1).children(0).value = opnName[i];
				obj.children(i).children(0).children(2).children(0).value = opnStdHrs[i];
				obj.children(i).children(0).children(3).children(0).value = opnGrpCde[i];
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;
				
				obj.children(len-1).children(0).children(0).children(0).value = "";
				obj.children(len-1).children(0).children(1).children(0).value = "";
				obj.children(len-1).children(0).children(2).children(0).value = "";
				obj.children(len-1).children(0).children(3).children(0).value = "";
				
				obj.children(len-1).children(0).children(0).children(0).value = opnSno[i];
				obj.children(len-1).children(0).children(1).children(0).value = opnName[i];
				obj.children(len-1).children(0).children(2).children(0).value = opnStdHrs[i];
				obj.children(len-1).children(0).children(3).children(0).value = opnGrpCde[i];

			}
		}
	<%
		}
		else
		{
	%>
		obj = document.getElementById("opnTblIncntvNo");
		var opnSno = (temp.sno.value).split("~");
		var opnName = (temp.name.value).split("~");
		var opnGrpId = (temp.grpId.value).split("~");
		var opnGrpCde = (temp.grpCode.value).split("~");
		
		for(var i = 0; i < opnSno.length; i++)
		{
			if (i==0)
			{
				obj.children(i).children(0).children(0).children(0).value = opnSno[i];
				obj.children(i).children(0).children(1).children(0).value = opnName[i];
				obj.children(i).children(0).children(2).children(0).value = opnGrpCde[i];
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;
				
				obj.children(len-1).children(0).children(0).children(0).value = "";
				obj.children(len-1).children(0).children(1).children(0).value = "";
				obj.children(len-1).children(0).children(2).children(0).value = "";
				
				obj.children(len-1).children(0).children(0).children(0).value = opnSno[i];
				obj.children(len-1).children(0).children(1).children(0).value = opnName[i];
				obj.children(len-1).children(0).children(2).children(0).value = opnGrpCde[i];
			}
		}
	<%
		}
	%>
	}

</script>
</head>

<body onload="loadDefault();">
<html:form action="frmProdAdd">
<input type="hidden" name="sno" value="<%= frm.getSno() %>"/>
<input type="hidden" name="name" value="<%= frm.getName() %>"/>
<input type="hidden" name="grpId" value="<%= frm.getGrpId() %>"/>
<input type="hidden" name="grpCode" value="<%= frm.getGpCde() %>"/>
<input type="hidden" name="stdHrs" value="<%= frm.getStdHrs() %>"/>
<html:hidden property="formAction"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.production.viewoperations"/></td>
          </tr>
        </table>
        <br> 
        <table>
		  <tr> 
			<td colspan='2'> <font size="1px" face="Verdana">
				<html:errors/></font>
		   </td>
		  </tr> 
	 </table> 
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td class="TopLnk"><a href="#" onclick="javascript:closeItem()" onMouseOver="window.status='Close this Window'; return true" onMouseOut="window.status=''; return true">Close</a></td>
          </tr>
        </table>
        <br>
		<%
			if (objStdHours.isOpnlevelstdhrs())
			{
		%>
			<table width="100%" cellspacing="0" cellpadding="2">
			<tr>
				<td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
				<td width="160" class="Header"><bean:message key="prodacs.job.operationname"/></td>
				<td width="100" class="Header"><bean:message key="prodacs.job.standardhrs"/></td>
				<td class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
			</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="2" id="opnTbl">
			<tr> 
				<td width="109" class="TableItems"><input readonly name="serial" class="TextBoxFull" size="9" maxlength="10"></td>
				<td width="159" class="TableItems"><input readonly name="opnName" class="TextBoxFull" size="9" maxlength="10"></td>
				<td width="99" class="TableItems"><input readonly name="opnStdHrs" class="TextBoxFull" size="9" maxlength="10"></td>
				<td class="TableItems"><input readonly name="grpName" class="TextBoxFull" size="9" maxlength="10"></td>
			</tr>
			</table>
		<%
			}
			else
			{
		%>
			<table width="100%" cellspacing="0" cellpadding="2">
			<tr>
				<td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
				<td width="160" class="Header"><bean:message key="prodacs.job.operationname"/></td>
				<td class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
			</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="2" id="opnTblIncntvNo">
			<tr> 
				<td width="109" class="TableItems"><input readonly name="serial" class="TextBoxFull" size="9" maxlength="10"></td>
				<td width="159" class="TableItems"><input readonly name="opnName" class="TextBoxFull" size="9" maxlength="10"></td>
				<td class="TableItems"><input readonly name="grpName" class="TextBoxFull" size="9" maxlength="10"></td>
			</tr>
			</table>
		 <%
			}
		 %>
		</td>
    </tr>
  </table>
</html:form>
</body>
</html:html>
