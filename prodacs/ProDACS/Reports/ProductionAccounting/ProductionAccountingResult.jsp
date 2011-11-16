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
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionAccountingDateDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionAccountingMachineDetails"%>
<%@ page import="com.savantit.prodacs.businessimplementation.production.ProductionAccountingShiftDetails"%>
<%@ page import="com.savantit.prodacs.util.EJBLocator"%>
<%@ page import="javax.rmi.PortableRemoteObject" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<useradmin:userrights resource="44"/>
<%
	if (BuildConfig.DMODE)
		System.out.println("Production Accounting Starts...");
	EJBLocator obj = new EJBLocator(); // Create an Object for the EJBLocator
	ProductionAccountingDateDetails[] objProdAcctngDets;
	ProductionAccountingMachineDetails[] objProdAcctngMachDets;
	ProductionAccountingShiftDetails[] objProdAcctngSftDets;
	JRBeanArrayDataSource dataSource = null;
	JasperReport jr = null;
	InputStream is = null;
	Map parameters = new HashMap();
	String isPdf = (request.getParameter("pdf")==null)?"":request.getParameter("pdf");
	String startDate = request.getParameter("startDate");
	if (BuildConfig.DMODE)
		System.out.println("Start Date: "+ startDate);
	
	if (startDate != "")
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
		try
		{
			/* 	Setting the JNDI name and Environment 	*/
			obj.setJndiName("SessionReportsDetailsManager");
	   		obj.setEnvironment();
	   		
			/* 	Creating the Home and Remote Objects 	*/
			SessionReportsDetailsManagerHome reportsHomeObj = (SessionReportsDetailsManagerHome)PortableRemoteObject.narrow(obj.getHome(),SessionReportsDetailsManagerHome.class);
			SessionReportsDetailsManager reportsObj = (SessionReportsDetailsManager)PortableRemoteObject.narrow(reportsHomeObj.create(),SessionReportsDetailsManager.class);
			
			objProdAcctngMachDets = reportsObj.fetchProductionAccountingDateDetails(ge.getTime());

			dataSource = new JRBeanArrayDataSource(objProdAcctngMachDets);
			parameters.put("Title", "Production Accounting Report For "+startDate);
			parameters.put("date",startDate);
			
			is = this.getClass().getClassLoader().getResourceAsStream("ProductionAccounting.jasper");
			jr = (JasperReport)JRLoader.loadObject(is);
		}
		catch (Exception e)
		{
			if (BuildConfig.DMODE)
				System.out.println("Error: "+ e.toString());
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
				document.forms[0].action='<bean:message key="context"/>/Reports/ProductionAccounting/ProductionAccountingResult.jsp';
				document.forms[0].submit();
			}
			
			function closeReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/ProductionAccounting/ProductionAccounting.jsp';
				document.forms[0].submit();
			}
			
			function emailReport()
			{
				document.forms[0].action='<bean:message key="context"/>/Reports/ProductionAccounting/ProductionAccountingEmail.jsp';
				document.forms[0].submit();		  
			}
		</script>
		<form name="frmProductionAccountingResult">
			<input type='hidden' name='startDate' value='<%=request.getParameter("startDate")%>'>
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
