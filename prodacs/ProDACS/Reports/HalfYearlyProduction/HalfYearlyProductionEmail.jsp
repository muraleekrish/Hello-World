<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="net.sf.jasperreports.engine.JasperReport" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="java.util.ResourceBundle"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.HalfYearlyProductionReportDetail"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Vector"%>
<%@ page import="net.sf.jasperreports.engine.data.JRBeanArrayDataSource" %>
<%@ page import="com.savantit.prodacs.infra.util.Mail"%>
<useradmin:userrights resource="33"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Monthly Production Starts...");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	HalfYearlyProductionReportDetail[] objHalfYearlyProductionReportDetail;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String month = request.getParameter("month");
	String year = request.getParameter("year");
	String machineCode = request.getParameter("machineCode");
	String mailAttachmentName = "";
	if (BuildConfig.DMODE)
		System.out.println("Month&Year : "+ month+"&"+year);
	
	if ((month != "") && (year != ""))
	{
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome monthlyHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager monthlyObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(monthlyHomeObj.create(),SessionReportsDetailsManager.class);
			
			objHalfYearlyProductionReportDetail = monthlyObj.fetchHalfYearlyProductionReport(machineCode,Integer.parseInt(month), Integer.parseInt(year));
			
			dataSource = new JRBeanArrayDataSource(objHalfYearlyProductionReportDetail);
			
			String[] mon = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			String strMonth = mon[Integer.parseInt(month)-1];
			if (BuildConfig.DMODE)
				System.out.println("Str Month: "+ strMonth);

			mailAttachmentName = "Half Yearly Production of Machine "+machineCode+" from "+strMonth+", "+year+".pdf";
			parameters.put("Title", "Half Yearly Production of Machine "+machineCode+" from "+strMonth+", "+year);
			parameters.put("month",month);
			parameters.put("year",year);
			parameters.put("machineCode",machineCode);
			
			is = this.getClass().getClassLoader().getResourceAsStream("/reports/HalfYearlyProduction.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
		}catch (Exception e)
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
			document.forms[0].action='<bean:message key="context"/>/Reports/HalfYearlyProduction/HalfYearlyProductionEmail.jsp';
			document.forms[0].submit();
		}
	  }
	   
	  function closeReport()
	  {
		document.forms[0].action='<bean:message key="context"/>/Reports/HalfYearlyProduction/HalfYearlyProduction.jsp';
		document.forms[0].submit();
	  }
	   
	   
	  </script>
	<form name="frmHalfYearlyProductionEmail">
<%
	if (BuildConfig.DMODE)
		System.out.println("Send Mail: "+request.getParameter("sendMail"));
	if (request.getParameter("sendMail") == null)
	{
		if (BuildConfig.DMODE)
			System.out.println("After send Mail");
%>		
		<input type='hidden' name='month' value='<%=request.getParameter("month")%>'>
		<input type='hidden' name='year' value='<%=request.getParameter("year")%>'>
		<input type='hidden' name='machineCode' value='<%=request.getParameter("machineCode")%>'>
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
	
	byte[] bytes = 	JasperRunManager.runReportToPdf(jr,parameters, dataSource);
	
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
		if (BuildConfig.DMODE)
			System.out.println("Error While Sending Mail: "+ e.toString());
	}
	if (BuildConfig.DMODE)
		System.out.println("Mail Sent: "+ isMailSend);
	if (isMailSend)
	{
%>
	<jsp:forward page='HalfYearlyProduction.jsp'>
		<jsp:param name="mesg" value="success" />
	</jsp:forward>
<%
	}
	else
	{
%>
	<jsp:forward page='HalfYearlyProduction.jsp'>
		<jsp:param name="mesg" value="failure" />
	</jsp:forward>
<%
	}
}
%>
</form>
</body>
</html>