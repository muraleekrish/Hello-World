<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="java.io.InputStream" %>
<%@ page import="com.savantit.prodacs.infra.util.Mail"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.WoReferenceDetails"%>
<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="net.sf.jasperreports.engine.JasperReport" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="net.sf.jasperreports.engine.data.JRBeanArrayDataSource" %>
<useradmin:userrights resource="40"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("WO Reference Email Starts.");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	WoReferenceDetails[] objWoReferenceDetails;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String mailAttachmentName = "";

	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String custTypName = request.getParameter("custTypName");
	String custName = request.getParameter("custName");
	String customerName = request.getParameter("customerName"); /* This is Customer ID */

	if ((startDate != "") && (endDate != "") && (custTypName != "") && (custName != ""))
	{
		/* For Start Date */
		StringTokenizer st = new StringTokenizer(startDate,"-");
		int yr = 0;
		int mnth = 0;
		int day = 0;
		if(st.hasMoreTokens())
		{
			yr = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{
			mnth = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{		
			day = Integer.parseInt(st.nextToken().trim());
		}
		GregorianCalendar ge = new GregorianCalendar(yr,mnth-1,day);

		/* To End Date */
		StringTokenizer st1 = new StringTokenizer(endDate,"-");
		yr = 0;
		mnth = 0;
		day = 0;
		if(st1.hasMoreTokens())
		{
			yr = Integer.parseInt(st1.nextToken().trim());
		}
		if(st1.hasMoreTokens())
		{
			mnth = Integer.parseInt(st1.nextToken().trim());
		}
		if(st1.hasMoreTokens())
		{		
			day = Integer.parseInt(st1.nextToken().trim());
		}
		GregorianCalendar ge1 = new GregorianCalendar(yr,mnth-1,day);

		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome woRefHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager woRefObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(woRefHomeObj.create(),SessionReportsDetailsManager.class);

			objWoReferenceDetails = woRefObj.fetchWoReferenceDetails(ge.getTime(),ge1.getTime(),Integer.parseInt(customerName));

			dataSource = new JRBeanArrayDataSource(objWoReferenceDetails);
			String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			StringTokenizer frmDate = new StringTokenizer(startDate.trim(),"-");
			String tempFrmDate[] = new String[frmDate.countTokens()];
			int i = 0;
			while(frmDate.hasMoreTokens())
			{
				tempFrmDate[i] = frmDate.nextToken();
				if (BuildConfig.DMODE)
					System.out.println("Frm: "+tempFrmDate[i]);
				i++;
			}
			
			StringTokenizer toDate = new StringTokenizer(endDate.trim(),"-");
			String tempToDate[] = new String[toDate.countTokens()];
			i = 0;
			while (toDate.hasMoreTokens())
			{
				tempToDate[i] = toDate.nextToken();
				if (BuildConfig.DMODE)
					System.out.println("Frm: "+tempToDate[i]);
				i++;
			}
			
			String stDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
			String edDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
			if (BuildConfig.DMODE)
			{
				System.out.println("Start Date: "+ stDate);
				System.out.println("End Date: "+ edDate);
				System.out.println("cust Id: "+ customerName);
				System.out.println("cust Typ Name: "+ custTypName);
				System.out.println("cust Name: "+ custName);
			}
			
			mailAttachmentName = "WorkOrder Reference For the Client "+custName+" from "+stDate+" to "+edDate+".pdf";
				parameters.put("Title", "WorkOrder Reference For the Client "+custName+" from "+stDate+" to "+edDate);
				parameters.put("startDate",stDate);
				parameters.put("endDate",edDate);
				parameters.put("customerId",customerName);
				
				is = this.getClass().getClassLoader().getResourceAsStream("/reports/WOReference.jasper");
				jr = (JasperReport)JRLoader.loadObject(is);
		}
		catch (Exception e)
		{
			if (BuildConfig.DMODE)
				System.out.println("Error: "+ e.toString());
		}
	}
%>
	<html>
	<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
	<script>
	  function isValidate()
	  {
		 if(document.forms[0].mailTo.value=="")
		  {
		 	  alert("Please enter To address!")
		 	  return false;
		  }	  
	  	  return true;
	  }
	  
	  function sendMailTo()
	  {
	  	if(isValidate())
		{
			document.forms[0].sendMail.value="yes";
			document.forms[0].action='<bean:message key="context"/>/Reports/WOReference/WOReferenceEmail.jsp';
			document.forms[0].submit();
		}
	  }
	   
	  function closeReport()
	  {
		document.forms[0].action='<bean:message key="context"/>/Reports/WOReference/WOReference.jsp';
		document.forms[0].submit();
	  }
	   
	   
	  </script>
	<form name="frmWrkOrdRefEmail">
<%
	if (BuildConfig.DMODE)
		System.out.println("Send Mail: "+request.getParameter("sendMail"));
	if (request.getParameter("sendMail") == null)
	{
		System.out.println("After send Mail");
%>		
		<input type='hidden' name='startDate' value='<%=request.getParameter("startDate")%>'>
		<input type='hidden' name='endDate' value='<%=request.getParameter("endDate")%>'>
		<input type='hidden' name='custTypName' value='<%=request.getParameter("custTypName") %>'>
		<input type='hidden' name='custName' value='<%=request.getParameter("custName")%>'>
		<input type='hidden' name='customerName' value='<%=request.getParameter("customerName")%>'>
		<input type='hidden' name='sendMail'>	  
		<table width="100%" cellspacing="0" cellpadding="10" >
		<tr>
		<td valign="top">
		<table width="100%" cellspacing="0" cellpadding="0" id="PageTitle">
		    <tr>
		      <td class="PageTitle">Email</td>
		    </tr>
		</table>
		  <br>
		<table width="100%" cellspacing="0" cellpadding="0">
		    <tr> 
		      <td width="65" class="FieldTitle">To</td>
		      <td width="1" class="FieldTitle">:</td>
		      <td class="FieldTitle"><input name="mailTo" type="text" class="TextBox" size="50" maxlength="250"></td>
		    </tr>
		    <tr> 
		      <td class="FieldTitle">CC</td>
		      <td class="FieldTitle">:</td>
		      <td class="FieldTitle"><input name="mailCc" type="text" class="TextBox" size="50" maxlength="250"></td>
		    </tr>
		    <tr> 
		      <td class="FieldTitle">Subject</td>
		      <td class="FieldTitle">:</td>
		      <td class="FieldTitle"><input name="mailSubject" type="text" class="TextBox" size="50" maxlength="250"></td>
		    </tr>
		    <tr>
		      <td valign="top" class="FieldTitle">Message</td>
		      <td valign="top" class="FieldTitle">:</td>
		      <td class="FieldTitle"><textarea name="mailMessage" rows="10" cols="50" wrap="VIRTUAL" class="TextBox"  maxlength="250"></textarea></td>
		    </tr>
		</table>
		  <br>
		<table width="100%" cellspacing="0" cellpadding="0" id="BtnBg">
		    <tr>
		      <td class="ButtonBg"><input name="butSend" type="button" class="Button" value="Send Mail" onclick="sendMailTo()"></td>
		    </tr>
		</table>
	<td>
	</tr>
	</table>
<%
	}
	else
	{
	   String mailSubject = request.getParameter("mailSubject");
	   String mailMessage = request.getParameter("mailMessage");
	   String[] mailTo = new String[0];
	   String[] mailCc = new String[0];
	   
	   Vector vectTo = new Vector();
	   if(request.getParameter("mailTo")!=null)
	   {
	    StringTokenizer toSt = new StringTokenizer(request.getParameter("mailTo"),",");
   		 while(toSt.hasMoreTokens())
   		 {
			vectTo.add(toSt.nextToken());
   		 }
	   }
	   
	   mailTo = new String[vectTo.size()];
	   vectTo.copyInto(mailTo);
	   
	   Vector vectCc = new Vector();
	   if(request.getParameter("mailCc")!=null)
	   {
	    StringTokenizer ccSt = new StringTokenizer(request.getParameter("mailCc"),",");
   		 while(ccSt.hasMoreTokens())
   		 {
			vectCc.add(ccSt.nextToken());
   		 }
	   }
	mailCc = new String[vectCc.size()];
	vectCc.copyInto(mailCc);
	ResourceBundle bundle = null;
	bundle = ResourceBundle.getBundle("ApplicationResources");
	String mailFrom = bundle.getString("defaultSender");
	System.out.println("Mail From :"+bundle.getString("mail.user"));
	
	byte[] bytes = 	JasperRunManager.runReportToPdf(jr,parameters,dataSource);
	
   	boolean isMailSend = false;
	try
	{
		Mail mail = new Mail();
		mail.setFrom(mailFrom);
		mail.addRecipients(mailTo,"to");
		mail.addRecipients(mailCc,"cc");
		if(mailSubject.trim().equals(""))
		{
			mail.setSubject(mailAttachmentName);
		}
		else
		{
			mail.setSubject(mailSubject);
		}
		if(mailSubject.trim().equals(""))
		{
			mail.setBodyText(mailAttachmentName);
		}
		else
		{
			mail.setBodyText(mailMessage);
		}
		mail.addAttachment(bytes,mailAttachmentName);
		
		isMailSend = mail.send();
	}
	catch(Exception e)
	{
		System.out.println("Error While Sending Mail: "+ e.toString());
	}
	System.out.println("Mail Sent: "+ isMailSend);
	if (isMailSend)
	{
%>
	<jsp:forward page='WOReference.jsp'>	
		<jsp:param name="mesg" value="success" />
	</jsp:forward>
<%
	}
	else
	{
%>
	<jsp:forward page='WOReference.jsp'>	
		<jsp:param name="mesg" value="failure" />
	</jsp:forward>
<%
	}
}
%>
</form>
</body>
</html>