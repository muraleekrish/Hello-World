<%@ page errorPage="error.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/menuconfig.tld" prefix="menuconfig"%>
<%@ taglib uri="/WEB-INF/useradmin.tld" prefix="useradmin"%>

<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.StringTokenizer"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page import="net.sf.jasperreports.engine.JasperRunManager" %>
<%@ page import="net.sf.jasperreports.engine.JasperReport" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %>
<%@ page import="net.sf.jasperreports.engine.export.*" %>
<%@ page import="net.sf.jasperreports.engine.JasperPrint" %>
<%@ page import="net.sf.jasperreports.engine.JasperFillManager" %>
<%@ page import="net.sf.jasperreports.engine.JRExporterParameter" %>
<%@ page import="net.sf.jasperreports.engine.data.JRBeanArrayDataSource" %>
<%@ page import="javax.servlet.ServletOutputStream"%>
<%@ page import="com.savantit.prodacs.infra.util.BuildConfig"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManager"%>
<%@ page import="com.savantit.prodacs.facade.SessionReportsDetailsManagerHome"%>
<%@ page import="com.savantit.prodacs.businessimplementation.reports.ProductionMachineWiseDetails"%>

<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<useradmin:userrights resource="46"/>

<%
	if (BuildConfig.DMODE)
		System.out.println("Machinewise Monthly Report Starts...");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	ProductionMachineWiseDetails[] objProdMachMonthDets;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String mailAttachmentName = "";
	if (BuildConfig.DMODE)
	{
		System.out.println("Start Date: "+ startDate);
		System.out.println("End Date: "+ endDate);
	}
	
	if ((startDate != "") && (endDate != ""))
	{
		/* For Start Date */
		StringTokenizer st = new StringTokenizer(startDate,"-");
		int yr = 0;
		int month = 0;
		int day = 0;
		if(st.hasMoreTokens())
		{
			yr = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{
			month = Integer.parseInt(st.nextToken().trim());
		}
		if(st.hasMoreTokens())
		{		
			day = Integer.parseInt(st.nextToken().trim());
		}
		GregorianCalendar ge = new GregorianCalendar(yr,month-1,day);
		
		/* For End Date */
		StringTokenizer edSt = new StringTokenizer(endDate,"-");
		int y = 0;
		int m = 0;
		int d= 0;
		if(edSt.hasMoreTokens())
		{
			y = Integer.parseInt(edSt.nextToken().trim());
		}
		if(edSt.hasMoreTokens())
		{
			m = Integer.parseInt(edSt.nextToken().trim());
		}
		if(edSt.hasMoreTokens())
		{		
			d = Integer.parseInt(edSt.nextToken().trim());
		}
		GregorianCalendar gc = new GregorianCalendar(y,m-1,d);

		mailAttachmentName = "Machinewise_Monthly_Report_From_"+startDate+"_to_"+endDate+".pdf";
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
			
			objProdMachMonthDets = reportsObj.fetchProductionMachineWiseDetails(ge.getTime(),gc.getTime());

			dataSource = new JRBeanArrayDataSource(objProdMachMonthDets);
			parameters.put("Title", "Production Accounting Report From "+startDate+" to "+endDate);
			parameters.put("startDate",startDate);
			parameters.put("endDate",endDate);
			
			is = this.getClass().getClassLoader().getResourceAsStream("MachinewiseMonthly.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
		}
		catch(Exception e)
		{
			if (BuildConfig.DMODE)
				System.out.println("Error :"+e.toString());
		}
	}


	if (isPdf.equals("1"))
	{
		byte[] bytes = JasperRunManager.runReportToPdf(jr,parameters, dataSource);
		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(bytes, 0, bytes.length);
		ouputStream.flush();
		ouputStream.close();
	}
	else
	{
%>
		<html>
		<link href='<bean:message key="context"/>/styles/pmac.css' rel="stylesheet" type="text/css">
		<script>
			function printReport()
			{
				printTab.style.display="none";
				window.print();
				printTab.style.display="block";
			}
			
			function pdfReport()
			{
				document.forms[0].pdf.value = "1";
				document.forms[0].action='<bean:message key="context"/>/Reports/MachinewiseMonthly/MachinewiseMonthlyResult.jsp';
				document.forms[0].submit();
			}
			
			function closeReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/MachinewiseMonthly/MachinewiseMonthly.jsp';
				document.forms[0].submit();
			}
			
			function emailReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/MachinewiseMonthly/MachinewiseMonthlyEmail.jsp';
				document.forms[0].submit();		  
			}
		</script>
		<form name="frmMachMonthlyResult">
			<input type='hidden' name='startDate' value='<%=request.getParameter("startDate")%>'>
			<input type='hidden' name='endDate' value='<%=request.getParameter("endDate")%>'>
			<input type='hidden' name='pdf'>
			<br>
			<table width="100%" cellpadding="0" cellspacing="0" id="printTab">
				<tr>
					<td class="TopLnk"><a href="javascript:printReport()">Print</a> | 
					<a href="javascript:emailReport()">Email</a> | 
					<a href="javascript:pdfReport()">PDF</a> | <a href="javascript:closeReport()">Close</a></td>
					<td>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</form>
		</html>    
<%
	try
	{
		JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parameters,dataSource);
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.exportReport(); 
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	}
%>
