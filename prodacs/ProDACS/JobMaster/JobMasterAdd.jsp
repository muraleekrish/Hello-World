<%@ page language = "java" %>

<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<jsp:useBean id="frm" class="com.savantit.prodacs.presentation.tableadmin.jobmaster.JobMasterAddForm" />
<jsp:setProperty name="frm" property="*" />

<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.facade.SessionJobDetailsManager"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import ="java.io.InputStream" %>
<%@ page import="com.savantit.prodacs.businessimplementation.job.JobDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.job.OperationDetails"%>
<%@ page import="com.savantit.prodacs.util.CastorXML"%>
<%@ page import="com.savantit.prodacs.presentation.tableadmin.jobmaster.StandardHours"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<useradmin:userrights resource="1011"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Job Master Add");
	if (frm.getFormAction().equalsIgnoreCase("") || request.getParameter("formAction").equalsIgnoreCase("addNew"))
	{
		/*frm.setCheckJobOperations("");frm.setCustomerId("");frm.setCustomerName("");
		frm.setCustomerType("");frm.setDate("");frm.setDrawing("");frm.setGeneralName("");
		frm.setGrpId("");frm.setIncentive("");frm.setJobName("");frm.setJobOperationName("");
		frm.setJobStandardHrs("");frm.setMaterialType("");frm.setOpGrpName("");frm.setOpName("");
		frm.setOpnLevelIncentive("");frm.setOpnLevelIncntv("");frm.setRevision("");frm.setSelectJobOperations("");
		frm.setSno("");frm.setStdHrs("");frm.setTotStdHrs("");frm.setTotStdHrsIncntvYes("");frm.setTransCustomerName("");
		frm.setTransCustomerType("");frm.setTransferOperations("");frm.setTransGeneralName("");frm.setTransJobName("");
		frm.setTrCustId("");frm.setTrCustomerId("");*/frm.setTrGenId("0");frm.setTrJobId("0");
	}
	int strDefault = 0;
	String radioChecked="";
	Vector vecJbDet = new Vector();
	Vector vecTrJbDet = new Vector();
	JobDetails objJbDet = null;
	JobDetails objTrJbDet = null;
	OperationDetails objTrOperDet = null;
    StandardHours objStdHours = new StandardHours();
	try
	{
    InputStream it = getClass().getClassLoader().getResourceAsStream("jobconfig.xml");
    objStdHours = (StandardHours)CastorXML.fromXML(it,objStdHours.getClass());
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	try
   {
		/* 	Setting the JNDI name and Environment 	*/
	   obj.setJndiName("SessionJobDetailsManagerBean");
   	   obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionJobDetailsManagerHome jobHomeObj = (SessionJobDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionJobDetailsManagerHome.class);
		SessionJobDetailsManager jobObj = (SessionJobDetailsManager)PortableRemoteObject.narrow(jobHomeObj.create(),SessionJobDetailsManager.class);

		/* For Customer Type */
		HashMap customerType = jobObj.getCustomerTypes();
		pageContext.setAttribute("customerType",customerType);

		HashMap customerName = new HashMap();

		/* For Customer Name */
		if (!frm.getCustomerId().equalsIgnoreCase(""))
		{
			customerName = jobObj.getCustomerNameByType(Integer.parseInt(frm.getCustomerId()));
			pageContext.setAttribute("customerName",customerName);
		}
		else
		{
			pageContext.setAttribute("customerName",customerName);
		}

		/* For General Name */
		HashMap generalName = jobObj.getAllJobGeneralName();
		pageContext.setAttribute("generalName",generalName);

		/* For Operation Group Name */
		HashMap operationGrpName = jobObj.getAllOperationGroupCodes();
		pageContext.setAttribute("operationGrpName",operationGrpName);

		/* Customer Name for TransferOperations */
		HashMap transCustName = new HashMap();
		if (!frm.getTrCustomerId().equalsIgnoreCase(""))
		{
			transCustName = jobObj.getCustomerNameByType(Integer.parseInt(frm.getTrCustomerId()));
			pageContext.setAttribute("transCustName",transCustName);
		}
		else
		{
			pageContext.setAttribute("transCustName",transCustName);
		}

		/* General Name for TransferOperations */
		HashMap transGenName = new HashMap();
		if (!frm.getTrCustId().equalsIgnoreCase(""))
		{
			transGenName = jobObj.getGeneralNameByCustomer(Integer.parseInt(frm.getTrCustId()));
			pageContext.setAttribute("transGenName",transGenName);
		}
		else
		{
			pageContext.setAttribute("transGenName",transGenName);
		}

		/* Getting the different jobs within a JobName */
		vecJbDet = (Vector) jobObj.getJobDetailsByGeneralNameWithCustomer(Integer.parseInt(frm.getTrCustId()),Integer.parseInt(frm.getTrGenId()));
		
		/* Listing all the JobOperations under a particular JobName */
		if (!frm.getTrJobId().equalsIgnoreCase("0"))
		{
			objTrJbDet = (JobDetails) jobObj.getJobDetails(Integer.parseInt(frm.getTrJobId()));
			vecTrJbDet = (Vector) objTrJbDet.getVec_OpnDetails();
		}	
		
		/* Initializing the checked Radio Button */
		radioChecked = request.getParameter("trRadio");

	}catch (Exception e)
	{
		if (BuildConfig.DMODE)
		{
			System.out.println("Problem in JobMasterAdd.Jsp");
			e.printStackTrace();
		}
	}
	}catch (Exception ex)
	{
		if (BuildConfig.DMODE)
		{
			ex.printStackTrace();
		}
	}
%>

<html:html>
<head>
<title><bean:message key="prodacs.common.header"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
<script src='<bean:message key="context"/>/library/jscripts.js'></script>
<script src='<bean:message key="context"/>/library/datetime.js'></script>
<script>
var oPopup;
	function init()
	 {
		oPopup = window.createPopup();
	 }
</script>
<script language="Javascript" type="text/Javascript">
var flag = 0; /* This flag is for finding the Empty values*/
function listItem()
{
	document.forms[0].action = '<bean:message key="context"/>/JobMaster/JobMasterList.jsp';
	document.forms[0].submit();
}

	function appendRow(formObj)
	{
		addRow(formObj);
		if (parseInt(document.forms[0].txtRowCount.value) > 0)
			{
				document.forms[0].txtRowCount.value=parseInt(document.forms[0].txtRowCount.value)+1;
			}
	}

	function DefaultRows(numRows)
	{
		for(i=0;i<numRows;i++)
			{
				addRow(document.forms[0]);
			}
	}

	function addRow(formObj)
	{
		var numFields = 0;
		var j = 1;
		var k = new Array();
		var l = 0;
		obj = document.getElementById('tbl');

		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == ""||
			obj.children(i).children(0).children(4).children(0).value == "")
			{
				alert("Row adding failed! One among the value(s) are/is empty!");
				return false;
			}
		}
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
			{
				k[l] = formObj.elements[i].value;
				l++;
			}
		}
		var obj = document.getElementById("tbl");		
		var newNode = obj.children(0).cloneNode(true);
		obj.appendChild(newNode);
		for (var i = 0; i < formObj.length; i++) // for Serial Number
		numFields++;
		for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = j;
						j++;
					}
				if (formObj.elements[i].name == 'opnGrpName')
					{
						formObj.elements[i].value = "";
					}
			}
		l = 0;
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
				{
					formObj.elements[i].value = k[l];
					l++;
					if(formObj.elements[i].value == "undefined")
					formObj.elements[i].value = "";
				}
		}
		/* Remove the extra values on creation of new childRow */
		var a = obj.children.length;
		obj.children(a-1).children(0).children(2).children(0).value = "";
		obj.children(a-1).children(0).children(3).children(0).value = "";
		obj.children(a-1).children(0).children(4).children(0).value = "";
		obj.children(a-1).children(0).children(5).children(0).checked = false;
	}

	function addRowIncntvYes(formObj)
	{
		var numFields = 0;
		var j = 1;
		var k = new Array();
		var l = 0;
		obj = document.getElementById('tblIncntvYes');

		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == "")
			{
				alert("Row adding failed! One among the value(s) are/is empty!");
				return false;
			}
		}
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
			{
				k[l] = formObj.elements[i].value;
				l++;
			}
		}
		var obj = document.getElementById("tblIncntvYes");		
		var newNode = obj.children(0).cloneNode(true);
		obj.appendChild(newNode);
		for (var i = 0; i < formObj.length; i++) // for Serial Number
		numFields++;
		for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = j;
						j++;
					}
				if (formObj.elements[i].name == 'opnGrpName')
					{
						formObj.elements[i].value = "";
					}
			}
		l = 0;
		for (var i = 0; i < formObj.length; i++)
		{
			if (formObj.elements[i].name == 'opnGrpName')
				{
					formObj.elements[i].value = k[l];
					l++;
					if(formObj.elements[i].value == "undefined")
					formObj.elements[i].value = "";
				}
		}
		/* Remove the extra values on creation of new childRow */
		var a = obj.children.length;
		obj.children(a-1).children(0).children(2).children(0).value = "";
		obj.children(a-1).children(0).children(3).children(0).value = "";
	}

	function delRow(formObj , cRows)
	{
		var numFields = 0;
		var d=1;
		var obj=document.getElementById("tbl");
		var temp=document.forms[0];
		if (obj.children.length>1)
			{
					for(var i = 0; i < obj.children.length; i++)
					{
						if(temp.CheckValue[i].checked)
						{
							obj.removeChild(obj.children(i));
							i=0;
						}
						if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
						{
							obj.children(0).children(0).children(0).children(0).value = "";
							obj.children(0).children(0).children(2).children(0).value = "";
							obj.children(0).children(0).children(3).children(0).value = "";
							obj.children(0).children(0).children(4).children(0).value = "";
							obj.children(0).children(0).children(5).children(0).value = "";
							obj.children(0).children(0).children(5).children(0).checked = false;
							obj.children(0).children(0).children(0).children(0).checked = false;
						}
					}
					for (var i = 0; i < formObj.length; i++)
						numFields++;
					for (var i = 0; i < formObj.length; i++)
					{
						if (formObj.elements[i].name == 'serial')
							{
								formObj.elements[i].value = d;
								d++;
							}
					}
			/* if (obj.children.length<2) 
				{
					for(var i = obj.children.length; i < 2; i++)
						{
									addRow(temp);
						}
				} */

			}
		else if (obj.children(0).children(0).children(0).children(0).checked)
			{
				obj.children(0).children(0).children(0).children(0).value = "";
				obj.children(0).children(0).children(2).children(0).value = "";
				obj.children(0).children(0).children(3).children(0).value = "";
				obj.children(0).children(0).children(4).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).value = "";
				obj.children(0).children(0).children(5).children(0).checked = false;
				obj.children(0).children(0).children(0).children(0).checked = false;
			}
	} 

	function delRowIncntvYes(formObj , cRows)
	{
		var numFields = 0;
		var d=1;
		var obj=document.getElementById("tblIncntvYes");
		var temp=document.forms[0];
		if (obj.children.length>1)
		{
			for(var i = 0; i < obj.children.length; i++)
			{
				if(temp.CheckValue[i].checked)
				{
					obj.removeChild(obj.children(i));
					i=0;
				}
				if ((obj.children.length == 1) && (obj.children(0).children(0).children(0).children(0).checked))
				{
					obj.children(0).children(0).children(0).children(0).value = "";
					obj.children(0).children(0).children(2).children(0).value = "";
					obj.children(0).children(0).children(3).children(0).value = "";
					obj.children(0).children(0).children(0).children(0).checked = false;
				}
			}
			for (var i = 0; i < formObj.length; i++)
				numFields++;
			for (var i = 0; i < formObj.length; i++)
			{
				if (formObj.elements[i].name == 'serial')
					{
						formObj.elements[i].value = d;
						d++;
					}
			}
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
		{
			obj.children(0).children(0).children(0).children(0).value = "";
			obj.children(0).children(0).children(2).children(0).value = "";
			obj.children(0).children(0).children(3).children(0).value = "";
			obj.children(0).children(0).children(0).children(0).checked = false;
		}
	} 
	
	function opnName(formObj)
	{
		var ind = formObj.parentNode.parentNode.parentNode.children(0).children(1).children(0).value;
		var temp = document.forms[0];
		var obj = document.getElementById("tbl");
		if (temp.selectJobOperations.value != "0")
		{
			obj.children(ind-1).children(0).children(2).children(0).value = temp.selectJobOperations.options[temp.selectJobOperations.selectedIndex].text;
			obj.children(ind-1).children(0).children(0).children(0).value = temp.selectJobOperations.value;
		}
	}
	
	function opnNameIncntnvYes(formObj)
	{
		var ind = formObj.parentNode.parentNode.parentNode.children(0).children(1).children(0).value;
		var temp = document.forms[0];
		var obj = document.getElementById("tblIncntvYes");
		if (temp.selectJobOperations.value != "0")
		{
			obj.children(ind-1).children(0).children(2).children(0).value = temp.selectJobOperations.options[temp.selectJobOperations.selectedIndex].text;
			obj.children(ind-1).children(0).children(0).children(0).value = temp.selectJobOperations.value;
		}
	}

	function isNumber(formObj)
	{
		if (((event.keyCode > 47) && (event.keyCode < 58)) || (event.keyCode == 46))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	function getCustomer()
	{
		<%
			if (objStdHours.isOpnlevelstdhrs() == true)
			{
		%>
		loadJobOperations();
		<%
			}
			else
			{
		%>
		loadJobOperationsIncntvYes();
		<%
			}
		%>
		document.forms[0].customerId.value = document.forms[0].customerType.value;
		document.forms[0].formAction.value = "getCustomer";
		document.forms[0].focusPoint.value = 3;
		document.forms[0].submit();
	}

	function trCustType()
	{
		<%
			if (objStdHours.isOpnlevelstdhrs() == true)
			{
		%>
		loadJobOperations();
		<%
			}
			else
			{
		%>
		loadJobOperationsIncntvYes();
		<%
			}
		%>
		document.forms[0].trCustomerId.value = document.forms[0].transCustomerType.value;
		document.forms[0].formAction.value = "getCustomerType";
		document.forms[0].focusPoint.value = 1;
		document.forms[0].submit();
	}
	
	function trCustName()
	{
		<%
			if (objStdHours.isOpnlevelstdhrs() == true)
			{
		%>
		loadJobOperations();
		<%
			}
			else
			{
		%>
		loadJobOperationsIncntvYes();
		<%
			}
		%>
		document.forms[0].trCustId.value = document.forms[0].transCustomerName.value;
		document.forms[0].formAction.value = "getCustomerName";
		temp.focusPoint.value = 2;
		document.forms[0].submit();
	}

	function trGenName()
	{
		<%
			if (objStdHours.isOpnlevelstdhrs() == true)
			{
		%>
		loadJobOperations();
		<%
			}
			else
			{
		%>
		loadJobOperationsIncntvYes();
		<%
			}
		%>
		document.forms[0].trGenId.value = document.forms[0].transGeneralName.value;
		document.forms[0].formAction.value = "getCustomerGeneralName";
		document.forms[0].submit();
	}
	
	function addItem()
	{
		temp = document.forms[0];
		temp.action =  '<bean:message key="context"/>/JobMaster/JobMasterAdd.jsp?formAction=addNew';
		temp.submit();
	}

	function submitItem()
	{
		var obj = document.getElementById('tbl');
		//alert(obj.children.length);
		loadJobOperations();
		if (flag != 0)
		{
			alert ("Empty Values are not Allowed!");
			return false;
		}
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(4).children(0).value > 999)
			{
				alert("Invalid Standard Hours!");
				obj.children(i).children(0).children(4).children(0).value = "";
				obj.children(i).children(0).children(4).children(0).focus(); 
				return false;
			}
		}
		temp = document.forms[0];
		temp.formAction.value = "add";
		temp.submit();
	}

	function submitItemIncntvYes()
	{
		var obj = document.getElementById('tblIncntvYes');
		//alert(obj.children.length);
		loadJobOperationsIncntvYes();
		if (flag != 0)
		{
			alert ("Empty Values are not Allowed!");
			return false;
		}
		/*for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(4).children(0).value > 999)
			{
				alert("Invalid Standard Hours!");
				obj.children(i).children(0).children(4).children(0).value = "";
				obj.children(i).children(0).children(4).children(0).focus(); 
				return false;
			}
		}*/
		temp = document.forms[0];
		temp.formAction.value = "add";
		temp.submit();
	}

	function listAllJobs()
	{
		var temp = document.forms[0];
		obj = document.getElementById('tblFetch');
		/*alert(obj.children(0).children.length);
		alert(obj.innerHTML);*/
		temp.trCustId.value = temp.transCustomerName.value;
		temp.trGenId.value = temp.transGeneralName.value;
		if (temp.trRadio != undefined)
		{
			if (temp.trRadio.length != undefined)
			{
				for (var i = 0; i < temp.trRadio.length; i++)
				{
					if (temp.trRadio[i].checked == true)
					{
						temp.trJobId.value = temp.trRadio[i].value;
						temp.trRadioCheck.value = temp.trRadio[i].value;
						temp.radioRowCount.value = i;
						temp.formAction.value = "getAllRecords";
						temp.showCount.value = "1";
						for (var i = 1; i < obj.children(0).children.length; i++)
						{
							if(obj.children(0).children(i).children(0).children(0).checked == true)
							{
								temp.trRadioCheck.value = obj.children(0).children(i).children(0).children(0).value;
								temp.jbName.value = obj.children(0).children(i).children(1).innerText;
								temp.dwgNo.value = obj.children(0).children(i).children(2).innerText;
								temp.revNo.value = obj.children(0).children(i).children(3).innerText;
								temp.matlTyp.value = obj.children(0).children(i).children(4).innerText;
							}
						}
						temp.submit();
					}
				}
			}
			else if (temp.trRadio.checked)
			{
				temp.trJobId.value = temp.trRadio.value;
				temp.trRadioCheck.value = temp.trRadio.value;
				temp.radioRowCount.value = 0;
				temp.formAction.value = "getAllRecords";
				temp.showCount.value = "1";
				temp.jbName.value = obj.children(0).children(1).children(1).innerText;
				temp.dwgNo.value = obj.children(0).children(1).children(2).innerText;
				temp.revNo.value = obj.children(0).children(1).children(3).innerText;
				temp.matlTyp.value = obj.children(0).children(1).children(4).innerText;
				temp.submit();
			}
		}
		if (obj.children(0).children.length == 1)
		{
			alert("There's no Job to Fetch!")
				return false;
		}
	}
	
	function transOperations(formObj) /* Transfering the Operations to Job Operations table */
	{ 
		var temp = document.forms[0];
		var obj = document.getElementById("transOprns");
		var jobOprObj = document.getElementById("tbl");
		var opGrpName = new Array();
		var opGrpId = new Array();
		var opName = new Array();
		var stdHrs = new Array();
		var opnIncntv = new Array();
		var count = 0;
		var ct = 0;
		var ckd = 0;
		if (obj.children(0).children.length != undefined)
		{
			for (var i = 0; i < obj.children(0).children.length; i++)
				if (obj.children(0).children(i).children(0).children(0).checked)
					ckd++;
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
			ckd++
		
		if (ckd == 0)
		{
			alert ("U should Check atleast one Operation to Transfer!");
			return false;
		}

		if (obj.children(0).children.length != undefined)
		{
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					for (var j = 0; j < jobOprObj.children.length; j++)
					{
						if (parseInt(jobOprObj.children(j).children(0).children(0).children(0).value) == parseInt(obj.children(0).children(i).children(0).children(0).value))
						{
							obj.children(0).children(i).children(0).children(0).checked = false;
							ct++;
						}
					}
				}
			}
		}
		if (ct > 0)
		{
			alert ("The Selected Operation already Shifted!");
			return false;
		}
		
		/* Storing the details for Operation Details */
		if (obj.children(0).children.length != undefined)
		{
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					opGrpId[count] = obj.children(0).children(i).children(0).children(0).value;
					opGrpName[count] = obj.children(0).children(i).children(2).children(0).value;
					opName[count] = obj.children(0).children(i).children(3).children(0).value;
					stdHrs[count] = obj.children(0).children(i).children(4).children(0).value;
					opnIncntv[count] = obj.children(0).children(i).children(5).children(0).checked;
					count++;
				}
			}
		}

		for (var i = 0; i < opGrpId.length; i++)
		{
			if ((jobOprObj.children.length == "1") && (jobOprObj.children(0).children(0).children(0).children(0).value == ""))
			{
				jobOprObj.children(0).children(0).children(0).children(0).value = opGrpId[i];
				jobOprObj.children(0).children(0).children(1).children(0).value = jobOprObj.children.length;
				jobOprObj.children(0).children(0).children(2).children(0).value = opGrpName[i];
				jobOprObj.children(0).children(0).children(3).children(0).value = opName[i];
				jobOprObj.children(0).children(0).children(4).children(0).value = stdHrs[i];
				if (opnIncntv[i] == true)
				{
					jobOprObj.children(0).children(0).children(5).children(0).checked = true;
				}
				else
				{
					jobOprObj.children(0).children(0).children(5).children(0).checked = false;
				}
			}
			else
			{
				var newNode = jobOprObj.children(0).cloneNode(true);
				jobOprObj.appendChild(newNode);
				var le = jobOprObj.children.length;

				jobOprObj.children(le-1).children(0).children(0).children(0).value = opGrpId[i];
				jobOprObj.children(le-1).children(0).children(1).children(0).value = jobOprObj.children.length;
				jobOprObj.children(le-1).children(0).children(2).children(0).value = opGrpName[i];
				jobOprObj.children(le-1).children(0).children(3).children(0).value = opName[i];
				jobOprObj.children(le-1).children(0).children(4).children(0).value = stdHrs[i];
				if (opnIncntv[i] == true)
				{
					jobOprObj.children(le-1).children(0).children(5).children(0).checked = true;
				}
				else
				{
					jobOprObj.children(le-1).children(0).children(5).children(0).checked = false;
				}
			}
		}
		temp.showCount.value = "2";
	}
	
	function transOperations1(formObj) /* Transfering the Operations to Job Operations table */
	{ 
		var temp = document.forms[0];
		var obj = document.getElementById("transOprns1");
		var jobOprObj = document.getElementById("tblIncntvYes");
		var opGrpName = new Array();
		var opGrpId = new Array();
		var opName = new Array();
		var count = 0;
		var ct = 0;
		var ckd = 0;
		if (obj.children(0).children.length != undefined)
		{
			for (var i = 0; i < obj.children(0).children.length; i++)
				if (obj.children(0).children(i).children(0).children(0).checked)
					ckd++;
		}
		else if (obj.children(0).children(0).children(0).children(0).checked)
			ckd++
		
		if (ckd == 0)
		{
			alert ("U should Check atleast one Operation to Transfer!");
			return false;
		}
		
		if (obj.children(0).children.length != undefined)
		{
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					for (var j = 0; j < jobOprObj.children.length; j++)
					{
						if (parseInt(jobOprObj.children(j).children(0).children(0).children(0).value) == parseInt(obj.children(0).children(i).children(0).children(0).value))
						{
							obj.children(0).children(i).children(0).children(0).checked = false;
							ct++;
						}
					}
				}
			}
		}
		/* Storing the details for Operation Details */
		if (obj.children(0).children.length != undefined)
		{
			for (var i = 1; i < obj.children(0).children.length; i++)
			{
				if (obj.children(0).children(i).children(0).children(0).checked)
				{
					opGrpId[count] = obj.children(0).children(i).children(0).children(0).value;
					opGrpName[count] = obj.children(0).children(i).children(2).children(0).value;
					opName[count] = obj.children(0).children(i).children(3).children(0).value;
					count++;
				}
			}
		}

		if (ct > 0)
		{
			alert ("The Selected Operation already Shifted!");
			return false;
		}

		for (var i = 0; i < opGrpId.length; i++)
		{
			if ((jobOprObj.children.length == "1") && (jobOprObj.children(0).children(0).children(0).children(0).value == ""))
			{
				jobOprObj.children(0).children(0).children(0).children(0).value = opGrpId[i];
				jobOprObj.children(0).children(0).children(1).children(0).value = jobOprObj.children.length;
				jobOprObj.children(0).children(0).children(2).children(0).value = opGrpName[i];
				jobOprObj.children(0).children(0).children(3).children(0).value = opName[i];
			}
			else
			{
				var newNode = jobOprObj.children(0).cloneNode(true);
				jobOprObj.appendChild(newNode);
				var le = jobOprObj.children.length;

				jobOprObj.children(le-1).children(0).children(0).children(0).value = opGrpId[i];
				jobOprObj.children(le-1).children(0).children(1).children(0).value = jobOprObj.children.length;
				jobOprObj.children(le-1).children(0).children(2).children(0).value = opGrpName[i];
				jobOprObj.children(le-1).children(0).children(3).children(0).value = opName[i];
			}
		}
	}
	
	function chkAll(formObj)
	{
		var obj = document.getElementById("tbl");
		var count = obj.children.length;
		if (document.forms[0].CheckAll.checked == true)
		{
			for (var i = 0; i < count; i++)
			{
				obj.children(i).children(0).children(0).children(0).checked = true;
			}
		}
		else
		{
			for (var i = 0; i < count; i++)
			{
				obj.children(i).children(0).children(0).children(0).checked = false;
			}
		}
	}
	
	function chkAll1(formObj)
	{
		var obj = document.getElementById("tblIncntvYes");
		var count = obj.children.length;
		if (document.forms[0].CheckAll.checked == true)
		{
			for (var i = 0; i < count; i++)
			{
				obj.children(i).children(0).children(0).children(0).checked = true;
			}
		}
		else
		{
			for (var i = 0; i < count; i++)
			{
				obj.children(i).children(0).children(0).children(0).checked = false;
			}
		}
	}

	function trCheckAll(formObj)
	{
		var obj = document.getElementById("transOprns");
		var count = obj.children(0).children.length;
		if (document.forms[0].trCheckHead.checked == true)
		{
			for (var i = 1; i < count; i++)
			{
				obj.children(0).children(i).children(0).children(0).checked = true;
			}
		}
		else
		{
			for (var i = 0; i < count; i++)
			{
				obj.children(0).children(i).children(0).children(0).checked = false;
			}
		}
	}

	function trCheckAll1(formObj)
	{
		var obj = document.getElementById("transOprns1");
		var count = obj.children(0).children.length;
		if (document.forms[0].trCheckHead.checked == true)
		{
			for (var i = 1; i < count; i++)
			{
				obj.children(0).children(i).children(0).children(0).checked = true;
			}
		}
		else
		{
			for (var i = 0; i < count; i++)
			{
				obj.children(0).children(i).children(0).children(0).checked = false;
			}
		}
	}

	function loadDefault()
	{
		var temp = document.forms[0];
		var crntDate = new Date();
		var dateVal = crntDate.getFullYear()+"-"+parseInt(crntDate.getMonth()+1)+"-"+crntDate.getDate();
		temp.date.value = dateVal;
		var j = temp.radioRowCount.value;
		if (temp.trRadio != undefined)
		{
			for (var i = 0; i < temp.trRadio.length; i++)
			{
				if (temp.trRadio[i].value == j)
				{
					temp.trRadio[i].checked = true;
				}
			}
		}
	}

	function loadJobOperations()
	{
		var obj = document.getElementById("tbl");
		var temp = document.forms[0];
		flag = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if ((obj.children(i).children(0).children(0).children(0).value == "") || 
				(obj.children(i).children(0).children(2).children(0).value == "") ||
				(obj.children(i).children(0).children(3).children(0).value == "") ||
				(obj.children(i).children(0).children(4).children(0).value == ""))
			{
				flag++;
			}
		}
		temp.sno.value = "";
		temp.opGrpName.value = "";
		temp.opName.value = "";
		temp.stdHrs.value = "";
		temp.grpId.value = "";
		temp.opnLevelIncntv.value = "";

		for (var i = 0; i < obj.children.length; i++)
		{
			if (i == 0)
			{
				temp.grpId.value = obj.children(i).children(0).children(0).children(0).value;
				temp.sno.value = obj.children(i).children(0).children(1).children(0).value;
				temp.opGrpName.value = obj.children(i).children(0).children(2).children(0).value;
				temp.opName.value = obj.children(i).children(0).children(3).children(0).value;
				temp.stdHrs.value =  obj.children(i).children(0).children(4).children(0).value;
				temp.opnLevelIncntv.value = obj.children(i).children(0).children(5).children(0).checked;
			} 
			else
			{
				temp.grpId.value = temp.grpId.value + "~" + obj.children(i).children(0).children(0).children(0).value;
				temp.sno.value = temp.sno.value + "~" + obj.children(i).children(0).children(1).children(0).value;
				temp.opGrpName.value = temp.opGrpName.value + "~" + obj.children(i).children(0).children(2).children(0).value;
				temp.opName.value = temp.opName.value + "~" + obj.children(i).children(0).children(3).children(0).value;
				temp.stdHrs.value =  temp.stdHrs.value + "~" + obj.children(i).children(0).children(4).children(0).value;
				temp.opnLevelIncntv.value =  temp.opnLevelIncntv.value + "~" + obj.children(i).children(0).children(5).children(0).checked;
			}
		}
		temp.showCount.value = "4";
	}

	function loadJobOperationsIncntvYes()
	{
		var obj = document.getElementById("tblIncntvYes");
		var temp = document.forms[0];
		flag = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if ((obj.children(i).children(0).children(0).children(0).value == "") || 
				(obj.children(i).children(0).children(2).children(0).value == "") ||
				(obj.children(i).children(0).children(3).children(0).value == ""))
			{
				flag++;
			}
		}
		temp.sno.value = "";
		temp.opGrpName.value = "";
		temp.opName.value = "";
		temp.grpId.value = "";
		//temp.jobStandardHrs.value = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (i == 0)
			{
				temp.grpId.value = obj.children(i).children(0).children(0).children(0).value;
				temp.sno.value = obj.children(i).children(0).children(1).children(0).value;
				temp.opGrpName.value = obj.children(i).children(0).children(2).children(0).value;
				temp.opName.value = obj.children(i).children(0).children(3).children(0).value;
			} 
			else
			{
				temp.grpId.value = temp.grpId.value + "~" + obj.children(i).children(0).children(0).children(0).value;
				temp.sno.value = temp.sno.value + "~" + obj.children(i).children(0).children(1).children(0).value;
				temp.opGrpName.value = temp.opGrpName.value + "~" + obj.children(i).children(0).children(2).children(0).value;
				temp.opName.value = temp.opName.value + "~" + obj.children(i).children(0).children(3).children(0).value;
			}
		}
		temp.showCount.value = "4";
	}

	function existingJobs()
	{
		var temp = document.forms[0];

		/* For Serial No */
		var serielNo = new String(temp.sno.value);
		var eSno = serielNo.split("~");

		/* For OperationGroupId */
		var ogId = new String(temp.grpId.value);
		var eOGId = ogId.split("~");

		/* For OperationGroupName */
		var ogName = new String(temp.opGrpName.value);
		var eOGName = ogName.split("~");

		/* For OperationName */
		var oName = new String(temp.opName.value);
		var eOName = oName.split("~");
		
		<%
			if (objStdHours.isOpnlevelstdhrs() == true)
			{
		%>
		/* For StdHrs */
		var hrs = new String(temp.stdHrs.value);
		var eStdHrs = hrs.split("~");
		<%
			if (objStdHours.isJobopnlevelincentive() == true)
			{
		%>
		/* For Operation Level Incentive */
		var opnIncntv = new String(temp.opnLevelIncntv.value);
		var opnIncntive = opnIncntv.split("~");

		var obj = document.getElementById("tbl");
		for (var i = 0; i < eSno.length; i++)
		{
			if (i == 0)
			{
				obj.children(i).children(0).children(0).children(0).value = eOGId[i];
				obj.children(i).children(0).children(1).children(0).value = i+1;
				obj.children(i).children(0).children(2).children(0).value = eOGName[i];
				obj.children(i).children(0).children(3).children(0).value = eOName[i];
				obj.children(i).children(0).children(4).children(0).value = eStdHrs[i];
				if (opnIncntive[i] == "true")
				{
					obj.children(i).children(0).children(5).children(0).checked = true;
				}
				else
				{
					obj.children(i).children(0).children(5).children(0).checked = false;
				}
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;

				obj.children(len-1).children(0).children(0).children(0).value = "";
				obj.children(len-1).children(0).children(1).children(0).value = i+1;
				obj.children(len-1).children(0).children(2).children(0).value = "";
				obj.children(len-1).children(0).children(3).children(0).value = "";
				obj.children(len-1).children(0).children(4).children(0).value = "";
				obj.children(len-1).children(0).children(5).children(0).checked = false;

				obj.children(len-1).children(0).children(0).children(0).value = eOGId[i];
				obj.children(len-1).children(0).children(1).children(0).value = i+1;
				obj.children(len-1).children(0).children(2).children(0).value = eOGName[i];
				obj.children(len-1).children(0).children(3).children(0).value = eOName[i];
				obj.children(len-1).children(0).children(4).children(0).value = eStdHrs[i];
				if (opnIncntive[i] == "true")
				{
					obj.children(len-1).children(0).children(5).children(0).checked = true;
				}
				else
				{
					obj.children(len-1).children(0).children(5).children(0).checked = false;
				}

			}
		}
	<%
	}
		}
		else
		{
	%>
		var obj = document.getElementById("tblIncntvYes");
		for (var i = 0; i < eSno.length; i++)
		{
			if (i == 0)
			{
				obj.children(i).children(0).children(0).children(0).value = eOGId[i];
				obj.children(i).children(0).children(1).children(0).value = i+1;
				obj.children(i).children(0).children(2).children(0).value = eOGName[i];
				obj.children(i).children(0).children(3).children(0).value = eOName[i];
			}
			else
			{
				var newNode = obj.children(0).cloneNode(true);
				obj.appendChild(newNode);
				var len = obj.children.length;

				obj.children(len-1).children(0).children(0).children(0).value = "";
				obj.children(len-1).children(0).children(1).children(0).value = i+1;
				obj.children(len-1).children(0).children(2).children(0).value = "";
				obj.children(len-1).children(0).children(3).children(0).value = "";

				obj.children(len-1).children(0).children(0).children(0).value = eOGId[i];
				obj.children(len-1).children(0).children(1).children(0).value = i+1;
				obj.children(len-1).children(0).children(2).children(0).value = eOGName[i];
				obj.children(len-1).children(0).children(3).children(0).value = eOName[i];
			}
		}
	<%
		}
	%>
	}

	function newGeneralName()
	{
		var features = "toolbars=no,status=no,resizable=no,height=350px,width=350px,top=100px,left=600px";
		window.open('<bean:message key="context"/>/JobMaster/NewGeneralName.jsp?',"NewGeneralName",features);
	}

	function typeValue(val1,val2)
	{
		var temp = document.forms[0];
		//alert(val1+"-"+val2);
		temp.generalName.options[temp.generalName.options.length] = new Option(val2,val1,false,true);
		/*document.forms[0].action = '<bean:message key="context"/>/JobMaster/JobMasterAdd.jsp';
		document.forms[0].submit();
		location.reload(); */
	}

	function checkRadio()
	{
		temp = document.forms[0];
		var obj = document.getElementById('tblFetch');
		if (temp.trRadio == undefined)
			return false;
		if (temp.trRadio.length == undefined)
		{
			if (temp.trRadio.checked == false)
			{
				temp.trRadio.checked = true;
			}
			else
			{
				temp.trRadio[0].checked = true;
				return false;
			}
		}
		for (var i = 0; i < temp.trRadio.length; i++)
		{
			if ('<%= request.getParameter("trRadioCheck") %>' == temp.trRadio[i].value)
			{
				temp.trRadio[i].checked = true;
				return false;
			}
			else
			{
				temp.trRadio[0].checked = true;
			}
		}
	}

	function setFocusPoint()
	{
		var temp = document.forms[0];
		if ('<%= request.getParameter("focusPoint") %>' == 'null')
		{
			temp.jobName.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == '1')
		{
			temp.transCustomerType.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == '2')
		{
			temp.transCustomerName.focus();
		}
		else if ('<%= request.getParameter("focusPoint") %>' == '3')
		{
			temp.customerType.focus();
		}
	}

	function insertRow(formObj)
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		var newNode = obj.children(0).cloneNode(true);
		var opns = "";
		//alert(obj.children.length);

		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == ""||
			obj.children(i).children(0).children(4).children(0).value == "")
			{
				alert("Insertion failed! One among the value(s) are/is empty!");
				return false;
			}

			if (i != 0)
			{
				opns = opns + "^";
			}

			opns = opns + obj.children(i).children(0).children(0).children(0).value+"~"+
					obj.children(i).children(0).children(1).children(0).value+"~"+
					obj.children(i).children(0).children(2).children(0).value+"~"+
					obj.children(i).children(0).children(3).children(0).value+"~"+
					obj.children(i).children(0).children(4).children(0).value+"~"+
					obj.children(i).children(0).children(5).children(0).checked;
		}

		var count = 0;
		var chkd = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				count++;
				chkd = i;
			}
		}

		if (count == 0)
		{
			alert ("Check a Row to Insert!");
			return false;
		}
		else if (count > 1)
		{
			alert ("Check Only one Item to Insert!");
			return false;
		}
		else if (count == 1)
		{
			obj.appendChild(newNode);
			var opnRec = opns.split("^");
			var k = 0;
			for (var i = 0; i < opnRec.length+1; i++)
			{
				if (i != chkd)
				{
					var opnFlds = opnRec[k].split("~");
					obj.children(i).children(0).children(0).children(0).value = opnFlds[0];
					obj.children(i).children(0).children(1).children(0).value = opnFlds[1];
					obj.children(i).children(0).children(2).children(0).value = opnFlds[2];
					obj.children(i).children(0).children(3).children(0).value = opnFlds[3];
					obj.children(i).children(0).children(4).children(0).value = opnFlds[4];
					if (opnFlds[5] == "true")
					{
						obj.children(i).children(0).children(5).children(0).checked = true;
					}
					else
					{
						obj.children(i).children(0).children(5).children(0).checked = false;
					}
					k++;
				}
				else
				{
					obj.children(i).children(0).children(0).children(0).value = "";
					obj.children(i).children(0).children(1).children(0).value = "";
					obj.children(i).children(0).children(2).children(0).value = "";
					obj.children(i).children(0).children(3).children(0).value = "";
					obj.children(i).children(0).children(4).children(0).value = "";
					obj.children(i).children(0).children(5).children(0).checked = false;
				}
			}
		}
		for (var x = 0; x < obj.children.length; x++)
		{
			obj.children(x).children(0).children(1).children(0).value = x+1;
		}
	}

	function insertRowIncntvYes(formObj)
	{
		temp = document.forms[0];
		obj = document.getElementById('tblIncntvYes');
		var newNode = obj.children(0).cloneNode(true);
		var opns = "";
		//alert(obj.children.length);

		/*Checking for empty Job Operations*/
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).value == ""|| 
			obj.children(i).children(0).children(1).children(0).value == ""||
			obj.children(i).children(0).children(2).children(0).value == ""||
			obj.children(i).children(0).children(3).children(0).value == "")
			{
				alert("Insertion failed! One among the value(s) are/is empty!");
				return false;
			}

			if (i != 0)
			{
				opns = opns + "^";
			}

			opns = opns + obj.children(i).children(0).children(0).children(0).value+"~"+
					obj.children(i).children(0).children(1).children(0).value+"~"+
					obj.children(i).children(0).children(2).children(0).value+"~"+
					obj.children(i).children(0).children(3).children(0).value;
		}

		var count = 0;
		var chkd = "";
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(0).children(0).checked)
			{
				count++;
				chkd = i;
			}
		}

		if (count == 0)
		{
			alert ("Check a Row to Insert!");
			return false;
		}
		else if (count > 1)
		{
			alert ("Check Only one Item to Insert!");
			return false;
		}
		else if (count == 1)
		{
			obj.appendChild(newNode);
			var opnRec = opns.split("^");
			var k = 0;
			for (var i = 0; i < opnRec.length+1; i++)
			{
				if (i != chkd)
				{
					var opnFlds = opnRec[k].split("~");
					obj.children(i).children(0).children(0).children(0).value = opnFlds[0];
					obj.children(i).children(0).children(1).children(0).value = opnFlds[1];
					obj.children(i).children(0).children(2).children(0).value = opnFlds[2];
					obj.children(i).children(0).children(3).children(0).value = opnFlds[3];
					k++;
				}
				else
				{
					obj.children(i).children(0).children(0).children(0).value = "";
					obj.children(i).children(0).children(1).children(0).value = "";
					obj.children(i).children(0).children(2).children(0).value = "";
					obj.children(i).children(0).children(3).children(0).value = "";
				}
			}
		}
		for (var x = 0; x < obj.children.length; x++)
		{
			obj.children(x).children(0).children(1).children(0).value = x+1;
		}
	}

	function sumHrs()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		var sum = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(4).children(0).value != "")
			{
				sum += parseFloat(obj.children(i).children(0).children(4).children(0).value,10);
			}
			else
			{
				return false;
			}
			if (obj.children(i).children(0).children(4).children(0).id == 'selected')
				break;
		}
		temp.totStdHrs.value = parseFloat(sum,10);
		for (var i = 0; i < obj.children.length; i++)
			obj.children(i).children(0).children(4).children(0).id = "";
	}

	function sumHrs1()
	{
		temp = document.forms[0];
		obj = document.getElementById('tbl');
		var sum = 0;
		for (var i = 0; i < obj.children.length; i++)
		{
			if (obj.children(i).children(0).children(4).children(0).value != "")
			{
				sum += parseFloat(obj.children(i).children(0).children(4).children(0).value,10);
			}
		}
		temp.totStdHrs.value = parseFloat(sum,10);
	}

	function isFloat(formObj)
	{
		if ((event.keyCode > 47) && (event.keyCode < 58) || (event.keyCode == 46) || (event.keyCode == 8))
		{
			sumHrs();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	function hideNshow(val)
	{
		if ('<%= request.getParameter("showCount") %>' == "1")
		{
			jbSelected.style.display = 'block';
			var obj = document.getElementById("jbSelected");
			obj.children(0).children(0).children(2).innerText = '<%= request.getParameter("jbName") %>';
			obj.children(0).children(0).children(5).innerText = '<%= request.getParameter("dwgNo") %>';
			obj.children(0).children(1).children(2).innerText = '<%= request.getParameter("revNo") %>';
			obj.children(0).children(1).children(5).innerText = '<%= request.getParameter("matlTyp") %>';
			fetching.style.display = 'none';
			jobOprns.style.display = 'none';
		}
		else if ('<%= request.getParameter("showCount") %>' == "4")
		{
			jobOprns.style.display = 'none';
			jbSelected.style.display = 'none';
			transOprns.style.display = 'none';
		}
		if (val == "2")
		{
			transInfo.style.display = 'none';
			jobOprns.style.display = 'block';
		}
		if (val == "3")
		{
			fetching.style.display = 'block';
		}
		if (val = "")
		{
			transInfo.style.display = 'block';
		}
	}
	
	function hideOpns()
	{
		temp = document.forms[0];
		if (temp.transCustomerType.options[temp.transCustomerType.selectedIndex].value != '<%= request.getParameter("transCustomerType")%>')
		{
			fetching.style.display = 'none';
			transOprns.style.display = 'none';
		}
		if (temp.transCustomerName.options[temp.transCustomerName.selectedIndex].value != '<%= request.getParameter("transCustomerName")%>')
		{
			fetching.style.display = 'none';
			transOprns.style.display = 'none';
		}
		if (temp.transGeneralName.options[temp.transGeneralName.selectedIndex].value != '<%= request.getParameter("transGeneralName")%>')
		{
			fetching.style.display = 'none';
			transOprns.style.display = 'none';
		}
	}

</script>
</head>
<body onLoad="init(); loadDefault(); existingJobs(); checkRadio(); setFocusPoint(); hideNshow();hideOpns();">
<html:form action="frmJobMaster" >
<html:hidden property="customerId"  value="<%= frm.getCustomerId() %>"/>
<html:hidden property="trCustomerId"  value="<%= frm.getTrCustomerId() %>"/>
<html:hidden property="trCustId"  value="<%= frm.getTrCustId() %>"/>
<html:hidden property="trGenId"  value="<%= frm.getTrGenId() %>"/>
<html:hidden property="trJobId"  value="<%= frm.getTrJobId() %>"/>
<html:hidden property="formAction"/>
<html:hidden property="grpId"/>
<html:hidden property="sno"/>
<html:hidden property="opGrpName"/>
<html:hidden property="opName"/>
<html:hidden property="stdHrs"/>
<html:hidden property="opnLevelIncntv"/>
<input type="hidden" name="radioRowCount" value="<%= radioChecked %>"/>
<input type="hidden" name="showCount"/>
<table width="100%" cellspacing="0" cellpadding="10">
<tr>
	<td>
		<table width="100%" cellpadding="0" cellspacing="0" id="PageTitle">
      	<tr>
            <td><bean:message key="prodacs.job.header"/></td>
         </tr>
		</table>
        <br>
		<table width="100" cellspacing="0" cellpadding="0" align="right">
        <tr> 
			<menuconfig:userRights url="#" id="#" onMouseOver="window.status='Add New Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="1011" text="[ Add ]" classId="TopLnk" onClick="javaScript:addItem();"/>
			<menuconfig:userRights url="#" id="#" onMouseOver="window.status='List Job Info'; return true"  onMouseOut="window.status=''; return true" resourceId="11" text="[ List ]" classId="TopLnk" onClick="javaScript:listItem();"/>
		</tr>
        </table><br><br>
        <table>
		<tr> 
			<td colspan='2'> <font size="1px" face="Verdana"><html:errors/></font></td>
		</tr> 
		</table> 
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
        <tr> 
			<td width="120" class="FieldTitle" nowrap><bean:message key="prodacs.job.date"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td width="150" class="FieldTitle"><html:text property="date" styleClass="TextBox" size="12" readonly="true"/> 
				<img src='<bean:message key="context"/>/images/calendar.gif' width="16" height="15" align="absmiddle" onClick='show_calendar("date",JobMasterAdd.date.value,event.clientX,event.clientY,false)' onMouseOver="this.style.cursor='hand'"></td>
			<td width="110" class="FieldTitle" nowrap><bean:message key="prodacs.job.jobname"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td colspan="4" class="FieldTitle"><html:text property="jobName" styleClass="TextBox" size="25" maxlength="50"/></td>
		</tr>
        <tr> 
			<td class="FieldTitle"><bean:message key="prodacs.customer.customertype"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="customerType" styleClass="Combo" onchange="getCustomer();">
										<html:option value="0">-- Customer Type --</html:option>
										<html:options collection="customerType" property="key" labelProperty="value"/>
									</html:select></td>
			<td class="FieldTitle" nowrap><bean:message key="prodacs.customer.customername"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td colspan="4" class="FieldTitle"><html:select property="customerName" styleClass="Combo">
													<html:option value="0">-- Customer Name --</html:option>
													<html:options collection="customerName" property="key" labelProperty="value"/>
												</html:select>
			</td>
        </tr>
        <tr> 
			<td class="FieldTitle"><bean:message key="prodacs.job.generalname"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:select property="generalName" styleClass="Combo">
										<html:option value="0">-- General Name --</html:option>
										<html:options collection="generalName" property="key" labelProperty="value"/>
									</html:select>&nbsp;<span class="TopLnk"><a href="#" onclick="newGeneralName();">New</a></span></td>
			<td class="FieldTitle"><bean:message key="prodacs.job.drawing"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td width="140" class="FieldTitle"><html:text property="drawing" styleClass="TextBox" size="15" maxlength="15"/></td>
		</tr>
	    <tr> 
			<td width="90" class="FieldTitle"><bean:message key="prodacs.job.revision"/><span class="mandatory">*</span></td>
			<td width="1" class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="revision" styleClass="TextBox" size="10" maxlength="5"/></td>
			<td class="FieldTitle"><bean:message key="prodacs.job.materialtype"/><span class="mandatory">*</span></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:text property="materialType" styleClass="TextBox" maxlength="10"/></td>
		</tr>
		<tr>
			<%
				if (objStdHours.isStdhrs())
				{
					if (objStdHours.isJoblevelstdhrs())
					{
						if (objStdHours.isIncentive())
						{
							if (objStdHours.isJoblevelincentive())
							{
			%>
			<td class="FieldTitle"><bean:message key="prodacs.job.incentive"/></td>
			<td class="FieldTitle">:</td>
			<td class="FieldTitle"><html:checkbox property="incentive"/></td>
			<%
							}
						}
					}
				}
				if (objStdHours.isStdhrs())
				{
					if (objStdHours.isJoblevelstdhrs())
					{
			%>
			<td class="FieldTitle" nowrap><bean:message key="prodacs.job.standardhrs"/></td>
			<td class="FieldTitle">:</td>
			<td colspan="4" class="FieldTitle"><html:text property="jobStandardHrs" styleClass="TextBox" size="10" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);" maxlength="6"/></td>
			<%
					}
				}
			%>
		</tr>
      	</table>
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
				<tr>
					<td width="20" valign="top" onClick="showHide(document.getElementById('jobOprns'), document.getElementById('jobOprnsImg'))"><img src='<bean:message key="context"/>/images/hide.gif' id="jobOprnsImg"></td>
				</tr>
				</table>
			</td>
		</tr>
		<tr id="jobOprns">
			<td class="FieldTitle">
				 <%
					if (objStdHours.isOpnlevelstdhrs())
					{
				 %>
				<fieldset id="FieldSet"><legend><bean:message key="prodacs.job.joboperations"/></legend>
            	<table width="100%" cellspacing="0" cellpadding="5">
                <tr> 
               		<td class="TopLnk">[ <a href="#" onClick="addRow(document.forms[0]);"><bean:message key="prodacs.common.add"/></a> ]
					[ <a href="#" onClick="insertRow(document.forms[0]);"><bean:message key="prodacs.common.insert"/></a> ]
		           	[ <a href="#" onClick="delRow(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
               </tr>
               <tr>
	              	<td class="FieldTitle"><bean:message key="prodacs.job.operationgroupname"/>&nbsp;&nbsp; : &nbsp;&nbsp;
	              	<html:select property="selectJobOperations" styleClass="Combo">
		               <html:option value="0">-- Operation Group Name --</html:option>
		               <html:options collection="operationGrpName" property="key" labelProperty="value"/>
	               </html:select></td>
               </tr>
               </table>
			   <br>
               <table width="100%" cellspacing="0" cellpadding="0">
               <tr> 
					<td width="27" class="Header"><input type="checkbox" name="CheckAll" value="checkbox" onClick="chkAll(document.forms[0]);"></td>
	                <td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>	                 
	                <td width="225" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
	                <td width="172" class="Header"><bean:message key="prodacs.job.operationname"/></td>
	                <td width="100" class="Header"><bean:message key="prodacs.job.standardhrs"/></td>
					<td class="Header"><bean:message key="prodacs.job.incentive"/></td>
			   </tr>
	           </table>
	           <table width="100%" cellspacing="0" cellpadding="0" id="tbl">
               <tr> 
                  <td width="24" class="TableItems"><input type="checkbox" name="CheckValue"></td>
                  <td width="107" class="TableItems"><input readonly name="serial" class="TextBoxFull" size="9" maxlength="10" value="1"></td>
                  <td width="222" class="TableItems"><input readonly name="opnGrpName" class="TextBoxFull" size="10" maxlength="50"></td>
                  <td width="169" class="TableItems"><html:text property="jobOperationName" styleClass="thinTbox" onfocus="opnName(this);"/></td>
                  <td width="97" class="TableItems"><html:text property="jobStandardHrs" styleClass="thinTbox"  onkeypress="this.id='selected';return isFloat(this);" onkeyup="this.id='selected'; return isFloat(this);" onclick="this.id='selected';sumHrs();" onblur="sumHrs1();" maxlength="6" /></td>
				  <td align="center" class="TableItems"><html:checkbox property="opnLevelIncentive"/></td>
               </tr>
               </table>
			   <table width="100%" cellpadding="0" cellspacing="0">
			   <tr>
					<td colspan="5">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td colspan="1" width="100" class="FieldTitle" nowrap>Total Std. Hrs </td>
					<td colspan="3" width="1" class="FieldTitle">:</td>
					<td colspan="2"><html:text readonly="true" property="totStdHrs" size="10" styleClass="TextBox" onkeypress="return isNumber(this);" onkeyup="return isNumber(this);"/></td>
			   </tr>
			   </table>
               <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
               <tr>
					<td><html:button property="saveOperations" styleClass="Button" onclick="submitItem();">Save</html:button></td>
               </tr>
               </table>
               </fieldset>
			   <%
				}
				else
				{
			 %>
				<fieldset id="FieldSet"><legend><bean:message key="prodacs.job.joboperations"/></legend>
            	<table width="100%" cellspacing="0" cellpadding="5">
                <tr> 
               		<td class="TopLnk">[ <a href="#" onClick="addRowIncntvYes(document.forms[0]);"><bean:message key="prodacs.common.add"/></a> ]
					[ <a href="#" onClick="insertRowIncntvYes(document.forms[0]);"><bean:message key="prodacs.common.insert"/></a> ]
		           	[ <a href="#" onClick="delRowIncntvYes(document.forms[0]);"><bean:message key="prodacs.common.delete"/></a> ]</td>
                </tr>
                <tr>
	              	<td class="FieldTitle"><bean:message key="prodacs.job.operationgroupname"/>&nbsp;&nbsp; : &nbsp;&nbsp;
	              	<html:select property="selectJobOperations" styleClass="Combo">
		               <html:option value="0">-- Operation Group Name --</html:option>
		               <html:options collection="operationGrpName" property="key" labelProperty="value"/>
	               </html:select></td>
              	</tr>
              	</table>
				<br>
              	<table width="100%" cellspacing="0" cellpadding="0">
                <tr> 
	                 <td width="27" class="Header">
	                 <input type="checkbox" name="CheckAll" value="checkbox" onClick="chkAll1(document.forms[0]);"></td>
	                 <td width="110" class="Header"><bean:message key="prodacs.job.operationsno"/></td>	                 
	                 <td class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
	                 <td width="172" class="Header"><bean:message key="prodacs.job.operationname"/></td>
	            </tr>
	            </table>
	            <table width="100%" cellspacing="0" cellpadding="0" id="tblIncntvYes">
                <tr> 
                  <td width="24" class="TableItems"><input type="checkbox" name="CheckValue"></td>
                  <td width="107" class="TableItems"><input readonly name="serial" class="TextBoxFull" size="9" maxlength="10" value="1"></td>
                  <td class="TableItems"><input readonly name="opnGrpName" class="TextBoxFull" size="10" maxlength="50"></td>
                  <td width="169" class="TableItems"><html:text property="jobOperationName" styleClass="thinTbox" onfocus="opnNameIncntnvYes(this);"/></td>
                </tr>
                </table>
                <table width="100%" cellpadding="0" cellspacing="0" id="BtnBg">
                <tr>
                  <td><html:button property="saveOperations" styleClass="Button" onclick="submitItemIncntvYes();">Save</html:button></td>
                </tr>
                </table>
                </fieldset>
			<%
				}
			%>
			</td>
         </tr>
		 </table>
		 <table>
		 <tr>
 		 <table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
		 <tr>
			 <td width="40" valign="top" onClick="showHide(document.getElementById('transInfo'),document.getElementById('transInfoImg'));hideNshow(temp.showCount.value = '');"><img src='<bean:message key="context"/>/images/hide.gif' id="transInfoImg"></td>
		 </tr>
		 </table>

		<table width="100%" cellspacing="0" cellpadding="0" id="transInfo">
       	<tr>
			<td class="FieldTitle">
			<fieldset id="FieldSet"><legend><bean:message key="prodacs.job.transferoperations"/></legend>
			<br>
					<table width="100%" cellspacing="0" cellpadding="0">
                	<tr> 
	                  <td width="118" class="FieldTitle"><bean:message key="prodacs.customer.customertype"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="FieldTitle">
	                  	<html:select property="transCustomerType" styleClass="Combo" onchange="trCustType();">
	                     	<html:option value="0">-- Customer Type --</html:option>
	                      	<html:options collection="customerType" property="key" labelProperty="value"/>
	                    	</html:select></td>
	                  <td width="118" class="FieldTitle"><bean:message key="prodacs.customer.customername"/></td>
	                  <td width="1" class="FieldTitle">:</td>
	                  <td width="150" class="FieldTitle">
	                  	<html:select property="transCustomerName" styleClass="Combo" onchange="trCustName();">
	                     	<html:option value="0">-- Customer Name --</html:option>
	                      	<html:options collection="transCustName" property="key" labelProperty="value"/>
	                    	</html:select></td>
                  	<td width="110" class="FieldTitle"><bean:message key="prodacs.job.generalname"/></td>
                  	<td width="1" class="FieldTitle">:</td>
                  	<td class="FieldTitle">
                  		<html:select property="transGeneralName" styleClass="Combo" onchange="trGenName();">
                      		<html:option value="0">-- General Name --</html:option>
                      		<html:options collection="transGenName" property="key" labelProperty="value"/>
                    		</html:select></td>
                		</tr>
					</table>
              		<br>
					<table width="100%" cellpadding="0" cellspacing="0" id="ShowHide">
					<tr>
					<td width="40" valign="top" onClick="showHide(document.getElementById('fetching'), document.getElementById('tblFetchImg'));hideNshow(temp.showCount.value = '3');"><img src='<bean:message key="context"/>/images/hide.gif' id="tblFetchImg"></td>
					</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="0" id="fetching">
					<tr>
					<td>
						<table width="100%" cellspacing="0" cellpadding="3" id="tblFetch">
						<tr> 
						  <td width="30" class="Header"><bean:message key="prodacs.job.jobsno"/></td>
						  <td class="Header"><bean:message key="prodacs.job.jobname"/></td>
						  <td width="120" class="Header"><bean:message key="prodacs.job.drawingno"/></td>
						  <td width="120" class="Header"><bean:message key="prodacs.job.revisionno"/></td>
						  <td width="120" class="Header"><bean:message key="prodacs.job.materialtype"/></td>
						</tr>
						<%
							for (int i = 0; i < vecJbDet.size(); i++)
							{
								objJbDet = (JobDetails) vecJbDet.get(i);
						%>			
						<tr> 
						  <td class="TableItems2"><input type = "radio" name = "trRadio" value = "<%= objJbDet.getJob_Id() %>"></td>
						  <td class="TableItems2"><%= objJbDet.getJob_Name() %></td>
						  <td class="TableItems2"><%= objJbDet.getJob_Dwg_No() %></td>
						  <td class="TableItems2"><%= objJbDet.getJob_Rvsn_No() %></td>
						  <td class="TableItems2"><%= objJbDet.getJob_Matl_Type() %></td>
						</tr>
						<%
							}
						%>
						</table>
						<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
						<tr>
							<td><html:button property="Fetch" styleClass="Button" value="Fetch" onclick="listAllJobs();"/></td>
						</tr>
						</table>
					</td>
					</tr>
					</table>
	            <br>
				<table cellspacing="0" cellpadding="0" id="jbSelected" style="display:none">
				<tr>
					<td width="150" class="FieldTitle" nowrap><bean:message key="prodacs.job.jobname"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td width="150" class="ViewData">&nbsp;</td>

					<td width="150" class="FieldTitle" nowrap><bean:message key="prodacs.job.drawingno"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td width="150" class="ViewData">&nbsp;</td>
				</tr>
				<tr>
					<td width="150" class="FieldTitle" nowrap><bean:message key="prodacs.job.revisionno"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td width="150" class="ViewData">&nbsp;</td>

					<td width="150" class="FieldTitle" nowrap><bean:message key="prodacs.job.materialtype"/></td>
					<td width="1" class="FieldTitle">:</td>
					<td width="150" class="ViewData">&nbsp;</td>
				</tr>
				</table>
				<br>
			  <%
				if (objStdHours.isOpnlevelstdhrs())
				{
			  %>
  	       		<table width="100%" cellspacing="0" cellpadding="3" id="transOprns">
              		<tr>
							<td width="25" class="Header"><input type="checkbox" name="trCheckHead" value="checkbox" onclick="trCheckAll(document.forms[0])"></td>
	                  <td width="70" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
	                  <td width="160" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
	                  <td width="225" class="Header"><bean:message key="prodacs.job.operationname"/></td>
	                  <td width="90" class="Header"><bean:message key="prodacs.job.standardhrs"/></td>
					  <td class="Header"><bean:message key="prodacs.job.incentive"/></td>
	                </tr>
					<%
						for (int i = 0; i < vecTrJbDet.size(); i++)
						{
							objTrOperDet = (OperationDetails) vecTrJbDet.get(i);
					%>		               
					 <tr> 
						<td class="TableItems2"><input type = "checkbox" name = "checkValue" id = "CheckValue" value="<%= objTrOperDet.getOpnGpId() %>"/></td>
						<td class="TableItems2"><%= i+1 %></td>
						<td class="TableItems2"><input readonly name="trOpnCode" class="TextBoxFull" size="9" value="<%= objTrOperDet.getOpnGpCode() %>"></td>
						<td class="TableItems2"><input readonly name="trOpnName" class="TextBoxFull" size="9" maxlength="10" value="<%= objTrOperDet.getOpnName() %>"></td>
						<td class="TableItems2"><input readonly name="trStdHrs" class="TextBoxFull" size="9" maxlength="10" value="<%= objTrOperDet.getOpnStdHrs() %>"></td>
						<%
							if (BuildConfig.DMODE)
							{
								System.out.println("objTrOperDet.isOpnIncentive() :"+objTrOperDet.isOpnIncentive());
							}
							if (objTrOperDet.isOpnIncentive())
							{
						%>
							<td class="TableItems2"><input type="checkbox" readonly name="trOpnIncntv" checked></td>
						<%
							}
							else
							{
						%>
							<td class="TableItems2"><input type="checkbox" readonly name="trOpnIncntv"></td>
						<%
							}
						%>
					 </tr>
					<%
						}
					%>
					<table width = "100%" cellspacing = "0" cellpadding="0" id = "BtnBg">
						<tr> 
						  <td><html:button property="transfer" styleClass="Button" value = "Transfer" onclick="transOperations(document.forms[0]);hideNshow(temp.showCount.value = '2');"/></td>
						</tr>
					</table>
				</table>
			<%
			}
			else
			{
			%>
	       		<table width="100%" cellspacing="0" cellpadding="3" id="transOprns1">
              		<tr>
							<td width="25" class="Header"><input type="checkbox" name="trCheckHead" value="checkbox" onclick="trCheckAll1(document.forms[0])"></td>
	                  <td width="70" class="Header"><bean:message key="prodacs.job.operationsno"/></td>
	                  <td width="160" class="Header"><bean:message key="prodacs.job.operationgroupname"/></td>
	                  <td class="Header"><bean:message key="prodacs.job.operationname"/></td>
	               </tr>
					<%
						for (int i = 0; i < vecTrJbDet.size(); i++)
						{
							objTrOperDet = (OperationDetails) vecTrJbDet.get(i);
					%>		               
					 <tr> 
						<td class="TableItems2"><input type = "checkbox" name = "checkValue" id = "CheckValue" value="<%= objTrOperDet.getOpnGpId() %>"/></td>
						<td class="TableItems2"><%= i+1 %></td>
						<td class="TableItems2"><input readonly name="trOpnCode" class="TextBoxFull" size="9" value="<%= objTrOperDet.getOpnGpCode() %>"></td>
						<td class="TableItems2"><input readonly name="trOpnName" class="TextBoxFull" size="9" maxlength="10" value="<%= objTrOperDet.getOpnName() %>"></td>
					 </tr>
					<%
						}
					%>
					<table width = "100%" cellspacing = "0" cellpadding="0" id = "BtnBg">
						<tr> 
						  <td><html:button property="transfer" styleClass="Button" value = "Transfer" onclick="transOperations1(document.forms[0]);"/></td>
						</tr>
					</table>
				</table>
			<%
			}
			%>
			</td></tr>
         </table>
  </table>
<INPUT TYPE="hidden" NAME="txtRowCount" value="<%= strDefault %>"/>
<input type="hidden" name="trRadioCheck"/>
<INPUT TYPE="hidden" NAME="focusPoint" />
<input type="hidden" name="jbName"/>
<input type="hidden" name="dwgNo"/>
<input type="hidden" name="revNo"/>
<input type="hidden" name="matlTyp"/>
</html:form>
</body>
</html:html>
