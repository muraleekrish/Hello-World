<%@ page language = "java" session="true"%>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<bean:parameter id="id" name="id" value="0"/>

<%@ page import = "com.savantit.prodacs.util.EJBLocator"%>
<%@ page import = "javax.rmi.PortableRemoteObject" %>
<%@ page import = "java.util.Vector" %>

<%@ page import = "com.savantit.prodacs.facade.SessionCustomerDetailsManagerHome" %>
<%@ page import = "com.savantit.prodacs.facade.SessionCustomerDetailsManager" %>
<%@ page import = "com.savantit.prodacs.businessimplementation.customer.CustomerDetails" %>
<%@ page import = "com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="2"/>
<% 
	if (BuildConfig.DMODE)
		System.out.println("Customer View");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	CustomerDetails objCustomerDetails = null;
	UserAuthDetails userDets = null;
	Vector vecUserResource = new Vector();
	try
	{
/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionCustomerDetailsManagerBean");
   	obj.setEnvironment();

/* 	Creating the Home and Remote Objects 	*/
		SessionCustomerDetailsManagerHome customerHomeObj = (SessionCustomerDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionCustomerDetailsManagerHome.class);
		SessionCustomerDetailsManager custObj = (SessionCustomerDetailsManager)PortableRemoteObject.narrow(customerHomeObj.create(),SessionCustomerDetailsManager.class);
 		objCustomerDetails = custObj.getCustomerDetails(Integer.parseInt(id));
 		userDets = (UserAuthDetails)pageContext.getSession().getAttribute("##userRights##");

		if(userDets != null)
		{
			vecUserResource = userDets.getVecUserAuth();
		}
 		pageContext.setAttribute("vecUserResource",vecUserResource);
   }catch(Exception e)
  	{
	   if (BuildConfig.DMODE)
	   		e.printStackTrace();
  	}
%>	    
<html:html>
<head>
<title>Prodacs</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script language="Javascript" type="text/Javascript">
	var isValid = <%= objCustomerDetails.getCust_Isvalid() %>;

	function chkMkValid()
	{
		temp = document.forms[0];
		temp.custName.value = '<%= session.getAttribute("listCustomerName") %>';
		temp.custCombo.value = '<%= session.getAttribute("custCombo") %>';
		temp.custType.value = '<%= session.getAttribute("listCustomerType") %>';
		temp.viewAll.value = '<%= session.getAttribute("listValidEntries") %>';
		if (document.getElementById('mkValid') != null)
		{
			if (isValid == 0)
			{
				mkValid.style.display = 'block';
				mkInValid.style.display = 'none';
				}
				else
				{
					mkValid.style.display = 'none';
					mkInValid.style.display = 'block';
				}
		}
	}

	function listCustomer()
	{
		temp = document.forms[0];
		if ((temp.custName.value != "" ) || (temp.custCombo.value != "") || (temp.custType.value != "") || (temp.viewAll.value != ""))
		{
			var custName = temp.custName.value;
			var custCombo = temp.custCombo.value;
			var custType = temp.custType.value;
			var viewAll = temp.viewAll.value;

			temp.action = '<bean:message key="context"/>/frmCustomerInfoList.do?formAction=afterView&custName='+temp.custName.value+'&custCombo='+temp.custCombo.value+'&viewAll='+temp.viewAll.value+'&custType='+temp.custType.value;
			temp.submit();
		}
		else
		{
			temp.action = '<bean:message key="context"/>/Customer/CustomerList.jsp';
			temp.submit();
		}
	}
	
	function addItem()
	{
		document.forms[0].action = '<bean:message key="context"/>/Customer/CustomerAdd.jsp';
		document.forms[0].submit();
	}
	
	function makeInvalidItem()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmCustomerEdit.do?formAction=makeInvalid';
		temp.submit();
	}

	function makeValid()
	{
		temp = document.forms[0];
		temp.action = '<bean:message key="context"/>/frmCustomerEdit.do?formAction=makeValid';
		temp.submit();
	}
	
	function loadToHidden()
	{
		document.forms[0].ids.value = document.forms[0].id.value;
	}
	
	function editItem()
	{
		if (isValid == 0)
		{
			alert("Invalid Item Cannot be Modified! ");
		}
		else
		{
			document.forms[0].action = '<bean:message key="context"/>/frmCustomerEdit.do?formAction=modify&id='+<%= objCustomerDetails.getCust_Id() %>;
			document.forms[0].submit();
		}	
	}
	
</script>
</head>

<body onLoad="chkMkValid();loadToHidden();">
<html:form action="frmCustomerEdit">
<html:hidden property="id"/>
<html:hidden property="ids"/>
<html:hidden property="formAction"/>
<input type="hidden" name="custName"/>
<input type="hidden" name="custCombo"/>
<input type="hidden" name="custType"/>
<input type="hidden" name="viewAll"/>
  <table width="100%" height="100%" cellpadding="10" cellspacing="0">
    <tr> 
      <td valign="top"><table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          <tr>
            <td><bean:message key="prodacs.customer.customerview.header"/></td>
          </tr>
        </table>
        <br>
        <table width="100" cellspacing="0" cellpadding="0" align="right">
          <tr> 
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Customer Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
 		<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Modify Customer Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ Modify ]" classId="TopLnk" onClick="javaScript:editItem();"/>          
 		<menuconfig:userRights url="#" id="mkInValid" onMouseOver="window.status='Make Customer Info InValid'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ MakeInValid ]" classId="TopLnk" onClick="javaScript:makeInvalidItem();"/>
 		<menuconfig:userRights url="#" id="mkValid" onMouseOver="window.status='Make Customer Info Valid'; return true"  onMouseOut="window.status=''; return true" resourceId="1002" text="[ MakeValid ]" classId="TopLnk" style="display:none" onClick="javaScript:makeValid();"/>
          </tr>
        </table>
        <br>
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="220" class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
            <td width="1" class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Typ_Name() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.customerinservice"/></td>
            <td class="FieldTitle">:</td> 
<%
	if (objCustomerDetails.getCust_Insrvce().equals("1"))
	{
%>
            <td class="ViewData"><img src='<bean:message key="context"/>/images/active.gif'></td>
<%
	}else 
	{
%>           
            <td class="ViewData"><img src='<bean:message key="context"/>/images/inactive.gif'></td>
<%
	}
%>             
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.address"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Addr1() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle">&nbsp;</td>
            <td class="FieldTitle">&nbsp;</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Addr2() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.city"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_City() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.state"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_State() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.pincode"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Pcode() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.country"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Country() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.contactfirstname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Cntct_Fname() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.contactlastname"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Cntct_Lname() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.designation"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Cntct_Designation() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline1"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Phone1() %></td>
		  </tr>
		  <tr>
            <td class="FieldTitle"><bean:message key="prodacs.customer.extension"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Extension1() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline2"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Phone2() %></td>
		  </tr>
		  <tr>
            <td class="FieldTitle"><bean:message key="prodacs.customer.extension"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Extension2() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.phoneline3"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Phone3() %></td>
		  </tr>
		  <tr>
            <td class="FieldTitle"><bean:message key="prodacs.customer.extension"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Extension3() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.fax"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Fax() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.mobile"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Mobile() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.email"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Email() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.website"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_Website() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.lastorderplaced"/></td>
            <td class="FieldTitle">:</td>
		<% 
			if (objCustomerDetails.getCust_LOPD() == null)
			{
		%>		<td class="ViewData">&nbsp;</td>
		<%
			}
			else
			{
		%>
			<td class="ViewData"><%= objCustomerDetails.getCust_LOPD().toString().substring(0,10) %></td>
		<%
			}
		%>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.lastorderdelivered"/></td>
            <td class="FieldTitle">:</td>
		<% 
			if (objCustomerDetails.getCust_LODD() == null)
			{
		%>		<td class="ViewData">&nbsp;</td>
		<%
			}
			else
			{
		%>
			<td class="ViewData"><%= objCustomerDetails.getCust_LODD().toString().substring(0,10) %></td>
		<%
			}
		%>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.workorderreference"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_LWOR() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.ordervalue"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_YTD_OrdrVal() %></td>
          </tr>
          <tr> 
            <td class="FieldTitle"><bean:message key="prodacs.customer.totalorders"/></td>
            <td class="FieldTitle">:</td>
            <td class="ViewData"><%= objCustomerDetails.getCust_YTD_TotOrdrs() %></td>
          </tr>
        </table>
        <br>
        <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
          <tr>
            <td> 
              <html:button property="viewCustomerList" styleClass="Button" onclick="javascript:listCustomer()">Return to Customer List</html:button></td>
          </tr>
        </table></td>
    </tr>
  </table>
</html:form>
</body>
</html:html>