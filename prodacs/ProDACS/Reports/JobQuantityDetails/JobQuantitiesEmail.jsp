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
<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="net.sf.jasperreports.engine.JasperReport" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.infra.beans.Filter"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.JobTimeDetails" %>
<%@ page import="net.sf.jasperreports.engine.data.JRBeanArrayDataSource" %>
<useradmin:userrights resource="56"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Job Quantities Email Starts.");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	JobTimeDetails[] objJbTimeDets;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String mailAttachmentName = "JobQuantities.pdf";

	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String jobName = request.getParameter("jobName");
	String dwgNo = request.getParameter("dwgNo");
	boolean sortOrder = true;
	String sortFieldName = "JB_NAME";
	String dates = "";
	String stDate = "";
	String edDate = "";

	int startIndex = 0;
	try
	{
		if ((!startDate.trim().equalsIgnoreCase("")) && (!endDate.trim().equalsIgnoreCase("")))
		{
			String[] month = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
			StringTokenizer frmDate = new StringTokenizer(startDate.trim(),"-");

			String tempFrmDate[] = new String[frmDate.countTokens()];
			int i = 0;
			while(frmDate.hasMoreTokens())
			{
				tempFrmDate[i] = frmDate.nextToken();
				i++;
			}

			StringTokenizer toDate = new StringTokenizer(endDate.trim(),"-");
			String tempToDate[] = new String[toDate.countTokens()];
			i = 0;
			while (toDate.hasMoreTokens())
			{
				tempToDate[i] = toDate.nextToken();
				i++;
			}

			stDate = tempFrmDate[2] + "-" + month[Integer.parseInt(tempFrmDate[1])-1] + "-" + tempFrmDate[0];
			edDate = tempToDate[2] + "-" + month[Integer.parseInt(tempToDate[1])-1] + "-" + tempToDate[0];
			dates = stDate + "$" + edDate;

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
		}
		/* 	Setting the JNDI name and Environment 	*/
		obj.setJndiName("SessionReportsDetailsManager");
		obj.setEnvironment();

		/* 	Creating the Home and Remote Objects 	*/
		SessionReportsDetailsManagerHome jobQtyHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
		SessionReportsDetailsManager jbQtyObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(jobQtyHomeObj.create(),SessionReportsDetailsManager.class);

		Vector filter = new Vector();
		Filter objFilter[] = new Filter[0];
		if ((!dates.equalsIgnoreCase("")) || (!jobName.equalsIgnoreCase("")) || (!dwgNo.equals("")))
		{
			if (!dates.equalsIgnoreCase(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("WO_DATE");
				temp.setFieldValue(dates);
				temp.setSpecialFunction("DateBetween");
				filter.add(temp);
			}
			if (!jobName.equalsIgnoreCase(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("JB_NAME");
				temp.setFieldValue(jobName);
				filter.add(temp);
			}
			if (!dwgNo.equalsIgnoreCase(""))
			{
				Filter temp = new Filter();
				temp.setFieldName("JB_DWG_NO");
				temp.setFieldValue(dwgNo);
				filter.add(temp);
			}
			objFilter = new Filter[filter.size()];
			filter.copyInto(objFilter);
			session.setAttribute("objFilter",objFilter);
			
			if ((!stDate.equalsIgnoreCase("")) && (!edDate.equalsIgnoreCase("")) && (!jobName.equalsIgnoreCase("")) && (!dwgNo.equalsIgnoreCase("")))
			{
				parameters.put("Title", "Job Quantity Details for "+jobName+" "+dwgNo+" from "+stDate+" to "+edDate);
			}
			else if ((!stDate.equalsIgnoreCase("")) && (!edDate.equalsIgnoreCase("")) && (jobName.equalsIgnoreCase("")) && (dwgNo.equalsIgnoreCase("")))
			{
				parameters.put("Title", "Job Quantity Details from "+stDate+" to "+edDate);
			}
			else if ((!stDate.equalsIgnoreCase("")) && (!edDate.equalsIgnoreCase("")) && (jobName.equalsIgnoreCase("")) && (!dwgNo.equalsIgnoreCase("")))
			{
				parameters.put("Title", "Job Quantity Details for "+dwgNo+" from "+stDate+" to "+edDate);
			}
			else if ((!stDate.equalsIgnoreCase("")) && (!edDate.equalsIgnoreCase("")) && (!jobName.equalsIgnoreCase("")) && (dwgNo.equalsIgnoreCase("")))
			{
				parameters.put("Title", "Job Quantity Details for "+jobName+" from "+stDate+" to "+edDate);
			}
		}
		else
		{
			parameters.put("Title", "Job Quantity Details");
			session.setAttribute("objFilter",objFilter);
		}

		objJbTimeDets = jbQtyObj.fetchJobTimeDetails(objFilter,sortFieldName,sortOrder,startIndex);
		dataSource = new JRBeanArrayDataSource(objJbTimeDets);

		is = this.getClass().getClassLoader().getResourceAsStream("JobQuantitiesReport.jasper");
		jr = (JasperReport)JRLoader.loadObject(is);
		System.out.println("JR: "+jr);
	}
	catch(Exception e)
	{
		if (BuildConfig.DMODE)
			e.printStackTrace();
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
			document.forms[0].action='<bean:message key="context"/>/Reports/JobQuantityDetails/JobQuantitiesEmail.jsp';
			document.forms[0].submit();
		}
	}
   
	function closeReport()
	{
		document.forms[0].action='<bean:message key="context"/>/Reports/JobQuantityDetails/JobQuantities.jsp';
		document.forms[0].submit();
	}
</script>
<form name="frmJobQtyEmail">
<%
	System.out.println("Send Mail: "+request.getParameter("sendMail"));
	if (request.getParameter("sendMail") == null)
	{
		System.out.println("After send Mail");
%>		
		<input type='hidden' name='startDate' value='<%=request.getParameter("startDate")%>'>
		<input type='hidden' name='endDate' value='<%=request.getParameter("endDate")%>'>
		<input type='hidden' name='jobName' value='<%=request.getParameter("jobName") %>'>
		<input type='hidden' name='dwgNo' value='<%=request.getParameter("dwgNo")%>'>
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
		e.printStackTrace();
	}
	System.out.println("Mail Sent: "+ isMailSend);
	if (isMailSend)
	{
%>
	<jsp:forward page='JobQuantities.jsp'>	
		<jsp:param name="mesg" value="success" />
	</jsp:forward>
<%
	}
	else
	{
%>
	<jsp:forward page='JobQuantities.jsp'>	
		<jsp:param name="mesg" value="failure" />
	</jsp:forward>
<%
	}
}
%>
</form>
</body>
</html>