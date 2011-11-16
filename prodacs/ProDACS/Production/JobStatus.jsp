<%@ page language = "java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>
<%@ page import="com.savantit.prodacs.businessimplementation.securityadmin.UserAuthDetails"%>
<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.production.WorkOrderJobStatusListForm" />
<jsp:setProperty name="frm" property="*" /> 
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.facade.SessionShipmentDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionShipmentDetailsManager"%>
<%@ page import="java.util.StringTokenizer"%>
<useradmin:userrights resource="50"/>
<%
   /*
    *
    * Author: Baskaran Kannusamy
    **/
    
   
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	int totalPages = 0;
	int pageCount = 1 ;
	
	boolean order = true;
	String sortFieldName = "JB_NAME";
    GregorianCalendar cal = null;
       
	UserAuthDetails userDetails = (UserAuthDetails)session.getAttribute("##userRights##");
	String userName = userDetails.getUserId();
	
   GregorianCalendar proFDate = new GregorianCalendar();
 
   proFDate.set(proFDate.get(Calendar.YEAR),proFDate.get(Calendar.MONTH),1);
   
   GregorianCalendar proTDate = new GregorianCalendar();
	
	try
	{
	   	obj.setJndiName("SessionShipmentDetailsManager");
   		obj.setEnvironment();
		SessionShipmentDetailsManagerHome proHomeObj = (SessionShipmentDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionShipmentDetailsManagerHome.class);
		SessionShipmentDetailsManager productionObj = (SessionShipmentDetailsManager)PortableRemoteObject.narrow(proHomeObj.create(),SessionShipmentDetailsManager.class);
		

		Vector filter = new Vector();
		if(frm.getFormAction().equals("") || frm.getFormAction().equals("add") || frm.getFormAction().equals("modify") || frm.getFormAction().equals("update"))
		{  
			frm.setFormAction("");
			Filter[] temp = new Filter[1];
			
			temp[0] = new Filter();			
		  	temp[0].setFieldName("USER_NAME");
		  	temp[0].setFieldValue(userName);
		  	
	 		session.setAttribute("objFilter",temp);
  		}
  		else if(frm.getFormAction().equalsIgnoreCase("search"))
  		{
  		  
			Filter becicFilter = new Filter();
		  	becicFilter.setFieldName("USER_NAME");
		  	becicFilter.setFieldValue(userName);
            filter.add(becicFilter);
            
            			
			/* By Job name */
			if (!frm.getProJobName().equalsIgnoreCase(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("JB_NAME");
				tempFilter.setFieldValue(frm.getProJobName());
			   	String specialFunction="";
				if(frm.getJobSelect().equals("0"))
				{
				   specialFunction = "Starts With";
				}
				else if(frm.getJobSelect().equals("1"))
				{
				   specialFunction = "Ends With";
				}
				else if(frm.getJobSelect().equals("2"))
				{
				   specialFunction = "Exactly";
				}
				else if(frm.getJobSelect().equals("3"))
				{
				   specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}
			
			if (!frm.getProDrawingNo().equalsIgnoreCase(""))
			{
	   			Filter tempFilter = new Filter();
		   		tempFilter.setFieldName("JB_DWG_NO");
				tempFilter.setFieldValue(frm.getProDrawingNo());
			   	String specialFunction="";
				if(frm.getDrawingSelect().equals("0"))
				{
				   specialFunction = "Starts With";
				}
				else if(frm.getDrawingSelect().equals("1"))
				{
				   specialFunction = "Ends With";
				}
				else if(frm.getDrawingSelect().equals("2"))
				{
				   specialFunction = "Exactly";
				}
				else if(frm.getDrawingSelect().equals("3"))
				{
				   specialFunction = "Anywhere";
				}
				tempFilter.setSpecialFunction(specialFunction);
				filter.add(tempFilter);
			}			

			
			/* By Production Current Date */
			
 
			Filter objFilter[] = new Filter[filter.size()];
  			filter.copyInto(objFilter);
  			session.setAttribute("objFilter",objFilter);
		}
		
		

			if ((!frm.getProFromDate().equalsIgnoreCase("")))
			{
			
				StringTokenizer frmDate = new StringTokenizer((frm.getProFromDate()).trim(),"-");
				String tempFrmDate[] = new String[frmDate.countTokens()];
				int i=0;
				while(frmDate.hasMoreTokens())
				{
					tempFrmDate[i] = frmDate.nextToken();
					i++;
				}
				
				StringTokenizer toDate = new StringTokenizer((frm.getProToDate()).trim(),"-");
				String tempToDate[] = new String[toDate.countTokens()];
				i = 0;
				while (toDate.hasMoreTokens())
				{
					tempToDate[i] = toDate.nextToken();
					i++;
				}
				
				proFDate = new GregorianCalendar(Integer.parseInt(tempFrmDate[0]), Integer.parseInt(tempFrmDate[1])-1, Integer.parseInt(tempFrmDate[2]));
				proTDate = new GregorianCalendar(Integer.parseInt(tempToDate[0]), Integer.parseInt(tempToDate[1])-1, Integer.parseInt(tempToDate[2]));
				
			}		
		
		
		int maxItems = Integer.parseInt(frm.getMaxItems());
	   	int pagecount = Integer.parseInt(frm.getPage());
		
  		Filter objFilter[] = (Filter[])session.getAttribute("objFilter");
  		pageCount = Integer.parseInt(frm.getPage());

 		HashMap hmjobStatus = productionObj.getAllJobStatusDetails(userName, proFDate.getTime(), proTDate.getTime(), objFilter, sortFieldName, order, ((pageCount-1)*maxItems)+1, maxItems);
		
		Vector vec_JobStatusDetails = (Vector)hmjobStatus.get("JobStatusDetails");
		
	 	pageContext.setAttribute("vec_JobStatusDetails",vec_JobStatusDetails);
	 	


	 	
	  	int totalCount = Integer.parseInt((hmjobStatus.get("TotalRecordCount")).toString()); 
  		if(totalCount%maxItems == 0)
  		{
	  		totalPages = (totalCount/maxItems);
  		}
  		else
  		{
	  		totalPages = (totalCount/maxItems) + 1;
	  	}
		if(totalPages==0)
			totalPages = 1;
			

	}
	catch(Exception e)
	{
   		e.printStackTrace();
	}
%>	     

<html:html>
<head>
<title><bean:message key="prodacs.production.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/calendar_previous_dc.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">
	
	function loadDefault()
	{
		temp = document.forms[0];
		var formAction = '<%=frm.getFormAction()%>';
		var maxItems = '<%=frm.getMaxItems()%>';
		var page = '<%=frm.getPage()%>';
		
	
		temp.proFromDate.value = <%=proFDate.get(Calendar.YEAR)%> + "-" + <%=proFDate.get(Calendar.MONTH)+1%> + "-" + <%=proFDate.get(Calendar.DAY_OF_MONTH)%>;
		temp.proToDate.value = <%=proTDate.get(Calendar.YEAR)%> + "-" + <%=proTDate.get(Calendar.MONTH)+1%> + "-" + <%=proTDate.get(Calendar.DAY_OF_MONTH)%>;		
		
		temp.page.value = page;
	
		if(maxItems=="15")
		{
			temp.maxItems[0].checked = true;
		}
		else if(maxItems=="25")
		{
			temp.maxItems[1].checked = true;
		}
		else if(maxItems=="50")
		{
			temp.maxItems[2].checked = true;
		}
		else if(maxItems=="100")
		{
			temp.maxItems[3].checked = true;
		}
	}
	
	
	function pageDecrement()
	{
		temp = document.forms[0];
		var page = 1;
		page = parseInt(temp.page.value,10) - 1;
		temp.page.value  = page;
		temp.submit();
		//navigation();
	}
	
	function pageIncrement()
	{
		temp = document.forms[0];
		var page = 1;
		page = parseInt(temp.page.value,10) + 1;
		temp.page.value  = page;
		temp.submit();
		//navigation();
	}
	
	function navigation()
	{
		temp = document.forms[0];
		if(temp.proFromDate.value == "")		
		 {
		   alert("Please specify Production Start Date");
		   return false;
		 }
		temp.formAction.value="search";
		temp.page.value = 1;
		temp.submit();
	}
	
	function changePage()
	{
		temp = document.forms[0];
		temp.formAction.value = "search";
		temp.page.value = parseInt(temp.page.value,10);
		temp.submit();
	}
	
</script>
</head>

<body onLoad="loadDefault(); init();">
<html:form action="frmWorkOrderJobStatusList">
<html:hidden property="formAction"/>
	<table width="100%" cellspacing="0" cellpadding="10" id="tbProduction">
	<tr>
      	<td>
  		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
          	<tr>
            	<td><bean:message key="prodacs.workorder.jobstatus"/></td>
	 	</tr>
    		</table><br>
		<table width="100%" cellspacing="0" cellpadding="0">
          		<tr> 
				<td width="20" id="FilterTip">&nbsp;</td>
            		<td class="FilterTitle"><a href="javascript:showFilter(document.getElementById('Filter'), document.getElementById('FilterImg'))"><img src='<bean:message key="context"/>/images/hide.gif' width="9" height="5" border="0" align="absmiddle" id="FilterImg"> 
              		<bean:message key="prodacs.common.filter"/></a></td>
		            <td width="20" height="20" id="FilterEnd">&nbsp;</td>
          		</tr>
		</table>
		<table width="100%" cellspacing="0" cellpadding="0" id="Filter" border="0">
          	<tr> 
            	<td width="70"><bean:message key="prodacs.workorder.fromdate"/></td>
	            <td width="1">:</td>
      	      <td width="100"><html:text property="proFromDate" styleClass="TextBox" size="12" readonly="true"/> 
            	  <img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar_previous("proFromDate",WorkOrderJobStatusList.proFromDate.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
	            <td><bean:message key="prodacs.workorder.todate"/></td>
      	      <td width="1">:</td>
            	<td width="100"><html:text property="proToDate" styleClass="TextBox" size="12" readonly="true"/>
	              </td>
			</tr>
          	<tr> 
            	<td width="70"><bean:message key="prodacs.job.jobname"/></td>
	            <td>:</td>
      			<td><html:text property="proJobName" styleClass="TextBox"/>
				<html:select property="jobSelect" styleClass="Combo">
      	      	    	<html:option value="0">Starts With</html:option>
      	      	    	<html:option value="1">Ends With</html:option>
      	      	    	<html:option value="2">Exactly</html:option>
      	      	    	<html:option value="3">AnyWhere</html:option>
	      			</html:select></td>
            	<td width="70"><bean:message key="prodacs.drawingno"/></td>
	            <td>:</td>
      			<td><html:text property="proDrawingNo" styleClass="TextBox"/>
				<html:select property="drawingSelect" styleClass="Combo">
      	      	    	<html:option value="0">Starts With</html:option>
      	      	    	<html:option value="1">Ends With</html:option>
      	      	    	<html:option value="2">Exactly</html:option>
      	      	    	<html:option value="3">AnyWhere</html:option>
	      			</html:select>	      			
				<html:button styleClass="Button" value="Go" property="go" onclick="navigation()"/>
				</td>
			</tr>
        	</table><br>
        	<table>
		<tr>
			<td>
			<html:messages id="messageid" message="true">
				<font class="message">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="messageid" /><br>
			</html:messages></font>
			</td>
		</tr>
		</table>
		<table>
			<tr> 
				<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
			</tr>
		</table>

      <logic:iterate id="objCustomerViewJobDetails" name="vec_JobStatusDetails">
      <bean:define id="objJobReceiptDetails" name="objCustomerViewJobDetails" property="objJobReceiptDetails"/>
      <bean:define id="objJobStatusDetails" name="objCustomerViewJobDetails" property="objJobStatusDetails"/>


        <table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="FieldTitle">
			  <fieldset id="FieldSet">
			  <legend title="Job Name - Drawing Number"><bean:write name="objCustomerViewJobDetails" property="jobName"/> - <bean:write name="objCustomerViewJobDetails" property="dwgNo"/> </legend>
			    <br>
				<table width="100%" cellpadding="2" cellspacing="0">
				  <tr>
				    <td width="50%" valign="top"><table width="100%"  border="0" cellspacing="0" cellpadding="2">
                      <tr>
                        <td class="TableItems3">Job Receipt</td>
                      </tr>
                    </table>
				      <table width="100%"  border="0" cellspacing="0" cellpadding="2">
                        <tr>
                          <bean:define id="vecCustomerOrderDetails" name="objJobReceiptDetails" property="vecOrderDetails"/>
                          <td width="60" rowspan="<%=((Vector)vecCustomerOrderDetails).size()+1%>" class="SortHeader">Receipt</td>
                          <td width="100" class="SortHeader">W.O.Date</td>
                          <td class="SortHeader">P.O. No </td>
                          <td width="60" class="SortHeader">Qty</td>
                        </tr>
				      <logic:iterate id="objCustomerOrderDetails" name="objJobReceiptDetails" property="vecOrderDetails">
                        <tr>
                          <td class="TableItems">
								<bean:define id="objDate" name="objCustomerOrderDetails" property="woDate"/>
    							<%
								cal = new GregorianCalendar();
								cal.setTime(((Date)objDate));
								out.println(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1) +"-"+cal.get(Calendar.DAY_OF_MONTH));
    							%>
								&nbsp;
						  </td>                          
                          <td class="TableItems"><bean:write name="objCustomerOrderDetails" property="custDCNo"/>&nbsp;</td>
                          <td class="TableItems"><bean:write name="objCustomerOrderDetails" property="jbQty"/>&nbsp;</td>
                        </tr>
                        </logic:iterate>
                      </table>				      
                      </td>
				    <td valign="top" style="border-left:outset 2px #CCCCCC"><table width="100%"  border="0" cellspacing="0" cellpadding="2">
                      <tr>
                        <td class="TableItems3">Job Status </td>
                      </tr>
                    </table>			        
				      <table width="100%"  border="0" cellspacing="0" cellpadding="2">
                        <tr>
                          <bean:define id="vecDesDetails" name="objJobStatusDetails" property="vecDespatchDetails"/>                        
                          <td width="60" rowspan="<%=((Vector)vecDesDetails).size()+1%>" class="SortHeader">Despatched</td>
                          <td width="100" class="SortHeader">Date</td>
                          <td class="SortHeader">PMAC.DC.No </td>
                          <td width="60" class="SortHeader">Qty</td>
                        </tr>
				      <logic:iterate id="objDespatchDetails" name="objJobStatusDetails" property="vecDespatchDetails">
                        <tr>
                          <td class="TableItems">
								<bean:define id="objDate" name="objDespatchDetails" property="despatchDate"/>
    							<%
								cal = new GregorianCalendar();
								cal.setTime(((Date)objDate));
								out.println(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1) +"-"+cal.get(Calendar.DAY_OF_MONTH));
    							%>
								&nbsp;
						  </td>
                          <td class="TableItems"><bean:write name="objDespatchDetails" property="companyDCNo"/>&nbsp;</td>
                          <td class="TableItems"><bean:write name="objDespatchDetails" property="jbQty"/>&nbsp;</td>
                        </tr>
                        </logic:iterate>
                      </table>
                    </td>
			      </tr>
				  <tr>
				    <td valign="bottom"><table width="100%"  border="0" cellspacing="0" cellpadding="2">
                      <tr>
                        <td align="right" class="TableItems">Brought Forward</td>
                        <td width="59" class="TableItems"><bean:write name="objJobReceiptDetails" property="balanceForward"/></td>
                      </tr>
                      <tr>
                        <td align="right" class="TableItems">Total</td>
                        <td class="TableItems"><bean:write name="objJobReceiptDetails" property="totalQty"/></td>
                      </tr>
                    </table></td>
				    <td valign="bottom" style="border-left:outset 2px #CCCCCC"><table width="100%"  border="0" cellspacing="0" cellpadding="2">
                      <tr>
                        <td align="right" class="TableItems">Not Taken up </td>
                        <td width="59" class="TableItems"><bean:write name="objJobStatusDetails" property="notTakenUp"/></td>
                      </tr>                      				    
                      <tr>
                        <td align="right" class="TableItems">Work In Progress </td>
                        <td width="59" class="TableItems"><bean:write name="objJobStatusDetails" property="workInProcess"/></td>
                      </tr>
                      <tr>
                        <td align="right" class="TableItems">Ready to Despatch </td>
                        <td class="TableItems"><bean:write name="objJobStatusDetails" property="readyToDespatch"/></td>
                      </tr>
                      <tr>
                        <td align="right" class="TableItems">Total</td>
                        <td class="TableItems"><bean:write name="objJobStatusDetails" property="totQty"/></td>
                      </tr>
                    </table></td>
			      </tr>
				</table>
			  </fieldset>
			</td>
          </tr>
        </table>




     </logic:iterate>


        	<table width="100%" cellpadding="0" cellspacing="0" id="Filter">
			<tr>&nbsp;</tr>
          	<tr> 
            	<td width="100"><bean:message key="prodacs.common.page"/> <%=pageCount%> <bean:message key="prodacs.common.pageof"/> <%=totalPages%> </td>
			<td><bean:message key="prodacs.common.maxitem"/>
      		<html:radio property="maxItems" value="3"/>3 
            	<html:radio property="maxItems" value="6"/>6 
      	      <html:radio property="maxItems" value="9"/>9 
            	<html:radio property="maxItems" value="12"/>12 
	            <html:button property="showPage" styleClass="Button" onclick="navigation()"> Show</html:button></td>
            <td>
<%
	if (Integer.parseInt(frm.getPage()) > 1)
      	{
%>
            	<a href="javascript:pageDecrement()"><img src='<bean:message key="context"/>/images/prev.gif' width="15" height="15" border="0" align="absmiddle">
            	<bean:message key="prodacs.common.previous"/></a> 
<%
		}
            if ((Integer.parseInt(frm.getPage()) > 1) && (Integer.parseInt(frm.getPage()) < totalPages ))
            {
%>| 
<%
		}
            if (Integer.parseInt(frm.getPage()) < totalPages )
            {
%>
	            <a href="javascript:pageIncrement()"><bean:message key="prodacs.common.next"/>
      	      <img src='<bean:message key="context"/>/images/next.gif' width="15" height="15" border="0" align="absmiddle"></a> | 
<%
		}
%>
	            <bean:message key="prodacs.common.goto"/>
      	        <html:select property="page" styleClass="Combo" onchange="changePage()">
<%
		for(int i=0;i<totalPages;i++)
		{
%>
		   	<option value='<%=(i+1)%>'><%=(i+1)%></option>
<%
	   	}
%>	
            	</html:select></td>
          	</tr>
         	</table>
	</td>
	</tr>
  	</table>
</html:form>
</body>
</html:html>
