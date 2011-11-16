/*
 * Created on Jan 10, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.reworklog;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.savantit.prodacs.businessimplementation.production.ProductionException;
import com.savantit.prodacs.infra.beans.Filter;
import com.savantit.prodacs.infra.dbtools.DBConnection;
import com.savantit.prodacs.infra.dbtools.DBConnectionFactory;
import com.savantit.prodacs.infra.dbtools.SQLMaster;
import com.savantit.prodacs.infra.util.BuildConfig;
import com.savantit.prodacs.infra.util.LoggerOutput;
import com.savantit.prodacs.infra.util.ProdacsConfig;
import com.savantit.prodacs.infra.util.SortString;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkLogDetailsManager
{
	static Logger logger = Logger.getLogger(ReworkLogDetailsManager.class);
	public ReworkLogDetailsManager()
	{
		logger.addAppender(LoggerOutput.getAppender());
		logger.setLevel(ProdacsConfig.LOGGER_LEVEL);
	}
	public boolean addReworkLogDetails(ReworkLogDetails objReworkLogDetails) throws ProductionException, SQLException
	{
		boolean addRESULT = false;
		Vector vec = new Vector();
		Vector vec_Opns = new Vector();
		Hashtable ht_ProdId_Get = new Hashtable();
		Hashtable ht_EmpDet_Get = new Hashtable();
		Hashtable ht_RwkLog_Det = new Hashtable();
		Hashtable ht_RwkLog_Emp = new Hashtable();
		Hashtable ht_WoJbId_Get = new Hashtable();
		DBConnection con = null;
		int woJbOpnId = 0;
		int jbOpnSno = 0;
		int woJbId = 0;
		int jbQtySno  = 0;
		int rwkId = 0;
		int prodId = 0;
		int radlId = 0;
		int jbId = 0;
		int woId = 0;
		String authorizedBy = objReworkLogDetails.getAuthorizedBy();
		vec = objReworkLogDetails.getVecRwkLogJbQtyDetails();
		woId = objReworkLogDetails.getWoId();
		jbId = objReworkLogDetails.getJbId();
		rwkId = objReworkLogDetails.getRwkId();
		try
		{
			con = DBConnectionFactory.getConnection();
			//con.startTransaction();

			ht_WoJbId_Get.put("WO_ID",new Integer(woId));
			ht_WoJbId_Get.put("JB_ID",new Integer(jbId));

			PreparedStatement ps_WoJbId_Get = con.executeStatement(SQLMaster.GET_WOJB_ID_SELECT_SQL_QUERY,ht_WoJbId_Get);
			ResultSet rs_WoJbId_Get = ps_WoJbId_Get.executeQuery();
			if(rs_WoJbId_Get.next())
			{
			    woJbId = rs_WoJbId_Get.getInt("WOJB_ID");
			}

			rs_WoJbId_Get.close();
			ps_WoJbId_Get.close();

			ResultSet rs_ProdId_Get = null;
			PreparedStatement ps_ProdId_Get = null;
			for(int i = 0;i<vec.size();i++)
			{
				RwkLogJbQtyDetails  objRwkLogJbQtyDetails = new RwkLogJbQtyDetails();
				objRwkLogJbQtyDetails = (RwkLogJbQtyDetails)vec.elementAt(i);
				jbQtySno = objRwkLogJbQtyDetails.getJbQtySno();
				vec_Opns = objRwkLogJbQtyDetails.getVecRwkLogJbOpnDetails();
				for(int j = 0;j<vec_Opns.size();j++)
				{
					RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
					objRwkLogJbOpnDetails = (RwkLogJbOpnDetails)vec_Opns.elementAt(j);
					jbOpnSno = objRwkLogJbOpnDetails.getOpnSno();
					woJbOpnId = objRwkLogJbOpnDetails.getWoJbOpnId();

					//to get PROD_ID from PROD.
					ht_ProdId_Get.put("WOJB_ID",new Integer(woJbId));
					System.out.println("WOJB_ID :"+woJbId);
					ps_ProdId_Get = con.executeStatement(SQLMaster.GET_PRODN_DETAILS_SQL_QUERY,ht_ProdId_Get);
					rs_ProdId_Get = ps_ProdId_Get.executeQuery();
					while(rs_ProdId_Get.next())
					{

						String qtySnos = "";
						qtySnos = rs_ProdId_Get.getString("PROD_QTY_SNOS");
						System.out.println("PROD_QTY_SNOS :"+qtySnos);
						System.out.println("Jb QtySno :"+jbQtySno);
						StringTokenizer st = new StringTokenizer(qtySnos,",");
						while(st.hasMoreTokens())
						{
							int sno = Integer.parseInt(st.nextToken().trim());
							if(sno == jbQtySno)
							{
								int stOpn = rs_ProdId_Get.getInt("PROD_START_OPN");
								int endOpn = rs_ProdId_Get.getInt("PROD_END_OPN");
								for(int k = stOpn;k<=endOpn;k++)
								{
									if(k==jbOpnSno)
										prodId = rs_ProdId_Get.getInt("PROD_ID");
								}

							}
						}

					}

					rs_ProdId_Get.close();
					ps_ProdId_Get.close();

					if(BuildConfig.DMODE)
					System.out.println("PROD_ID"+prodId);
					PreparedStatement ps_RadlId_Get = con.executeStatement(SQLMaster.GET_RADL_DETAILS_SQL_QUERY,ht_ProdId_Get);
					ResultSet rs_RadlId_Get = ps_RadlId_Get.executeQuery();
					while(rs_RadlId_Get.next())
					{
						String qtySnos = "";
						qtySnos = rs_RadlId_Get.getString("RADL_QTY_SNOS");
						StringTokenizer st = new StringTokenizer(qtySnos,",");
						if(BuildConfig.DMODE)
						System.out.println("RADL_QTY_SNOS :"+qtySnos);
						while(st.hasMoreTokens())
						{
							int sno = Integer.parseInt(st.nextToken().trim());
							if(sno == jbQtySno)
							{
								int stOpn = rs_RadlId_Get.getInt("RADL_START_OPN");
								int endOpn = rs_RadlId_Get.getInt("RADL_END_OPN");
								for(int k = stOpn;k<=endOpn;k++)
								{
									if(k==jbOpnSno)
										radlId = rs_RadlId_Get.getInt("RADL_ID");
								}

							}
						}

					}

					rs_RadlId_Get.close();
					ps_RadlId_Get.close();

					if(BuildConfig.DMODE)
					System.out.println("RADL_ID"+radlId);
					//to insert values into RWK_LOG..
					ht_RwkLog_Det.put("RWK_ID",new Integer(rwkId));
					ht_RwkLog_Det.put("RWKLOG_AUTHRZDBY",authorizedBy);
					ht_RwkLog_Det.put("WOJBOPN_ID",new Integer(woJbOpnId));

					int result  = con.executeUpdateStatement(SQLMaster.ADD_RWKLOG_DETAILS_SQL_QUERY,ht_RwkLog_Det);
					int rwkLogId = 0;
					if(result>0)
					{

						PreparedStatement ps_RwkLogId_Select = con.executeStatement(SQLMaster.SELECT_RWKLOGID_SQL_QUERY);
					    ResultSet rs_RwkLogId_Select = ps_RwkLogId_Select.executeQuery();
						if(rs_RwkLogId_Select.next())
						{
							rwkLogId = rs_RwkLogId_Select.getInt(1);
						}

						rs_RwkLogId_Select.close();
						ps_RwkLogId_Select.close();

						//to get Employees from PROD_EMP table ..
						if(prodId!=0)
						{
							ht_EmpDet_Get.put("PROD_ID",new Integer(prodId));
							PreparedStatement ps_EmpDet_Get = con.executeStatement(SQLMaster.GET_EMP_DETAILS_SQL_QUERY,ht_EmpDet_Get);
							ResultSet rs_EmpDet_Get = ps_EmpDet_Get.executeQuery();
							int empId = 0;
							while(rs_EmpDet_Get.next())
							{
								empId = rs_EmpDet_Get.getInt("EMP_ID");
								ht_RwkLog_Emp.put("RWK_LOG_ID",new Integer(rwkLogId));
								ht_RwkLog_Emp.put("EMP_ID",new Integer(empId));

								con.executeUpdateStatement(SQLMaster.ADD_RWKLOG_EMP_SQL_QUERY,ht_RwkLog_Emp);
							}
							rs_EmpDet_Get.close();
							ps_EmpDet_Get.close();
						}
						//to get Employees from RADL_EMP table ..

						if(radlId!=0)
						{
							ht_EmpDet_Get.put("RADL_ID",new Integer(radlId));
							PreparedStatement ps_EmpDet_Get = con.executeStatement(SQLMaster.GET_RADL_EMP_DETAILS_SQL_QUERY,ht_EmpDet_Get);
							ResultSet rs_EmpDet_Get = ps_EmpDet_Get.executeQuery();
							int empId = 0;
							while(rs_EmpDet_Get.next())
							{
								empId = rs_EmpDet_Get.getInt("EMP_ID");
								ht_RwkLog_Emp.put("RWK_LOG_ID",new Integer(rwkLogId));
								ht_RwkLog_Emp.put("EMP_ID",new Integer(empId));

								con.executeUpdateStatement(SQLMaster.ADD_RWKLOG_EMP_SQL_QUERY,ht_RwkLog_Emp);

							}

							rs_EmpDet_Get.close();
							ps_EmpDet_Get.close();

						}
						//to change operation status to 'R' in WO_JB_OPN table..
						result = con.executeUpdateStatement(SQLMaster.PRODN_OPN_STATUS_CHANGE_TO_R_SQL_QUERY,ht_RwkLog_Det);

						if(result>0)
						{
							PreparedStatement ps_OpnStatus_Check = con.executeStatement(SQLMaster.PRODN_OPN_STATUS_CHECK_R_SQL_QUERY,ht_RwkLog_Det);
						    ResultSet rs_OpnStatus_Check = ps_OpnStatus_Check.executeQuery();
							if(rs_OpnStatus_Check.next())
							{
								int count = rs_OpnStatus_Check.getInt(1);
								if(count==0)
								{
									//to change qty status to 'R' in WO_JB_STAT table..
									result = con.executeUpdateStatement(SQLMaster.PRODN_QTY_STATUS_CHANGE_TO_R_SQL_QUERY,ht_RwkLog_Det);
									if(result>0)
									{
										PreparedStatement ps_QtyStatus_Check = con.executeStatement(SQLMaster.PRODN_QTY_STATUS_CHECK_R_SQL_QUERY,ht_RwkLog_Det);
									    ResultSet rs_QtyStatus_Check = ps_QtyStatus_Check.executeQuery();
										if(rs_QtyStatus_Check.next())
										{
											count = rs_QtyStatus_Check.getInt(1);
											if(count==0)
											{
												//to change job status to 'R' in WO_JB_HEADER table..
												result = con.executeUpdateStatement(SQLMaster.PRODN_JOB_STATUS_CHANGE_TO_R_SQL_QUERY,ht_RwkLog_Det);
											}
											else
											{
//												to change job status to 'A' in WO_JB_HEADER table..
												result = con.executeUpdateStatement(SQLMaster.PRODN_JOB_STATUS_CHANGE_TO_A_SQL_QUERY,ht_RwkLog_Det);

												//to change wo status to 'A' in WO_HEADER table..
												result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGE_TO_A_SQL_QUERY,ht_RwkLog_Det);

											}
										}
									}

								}
								else
								{
									//to change qty status to 'A' in WO_JB_STAT table..
									result = con.executeUpdateStatement(SQLMaster.PRODN_QTY_STATUS_CHANGE_TO_A_SQL_QUERY,ht_RwkLog_Det);

									//to change job status to 'A' in WO_JB_HEADER table..
									result = con.executeUpdateStatement(SQLMaster.PRODN_JOB_STATUS_CHANGE_TO_A_SQL_QUERY,ht_RwkLog_Det);

									//to change wo status to 'A' in WO_HEADER table..
									result = con.executeUpdateStatement(SQLMaster.PRODN_WO_STATUS_CHANGE_TO_A_SQL_QUERY,ht_RwkLog_Det);

								}

							}
							rs_OpnStatus_Check.close();
							ps_OpnStatus_Check.close();
						}


					}
				}
			}
			addRESULT = true;
		}
		catch(SQLException sqle)
		{
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			throw new ProductionException("RLEC000","EXCEPTION WHILE CLOSING CONNECTION",sqle.toString());
		}
		finally
		{
			try
			{
            	if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
            }
			catch(SQLException ex)
			{
				if(BuildConfig.DMODE)
				{
					logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				}
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}


		return addRESULT;
	}
	public Vector getRwkJobOperationDetails(int woId,int jbId) throws ProductionException,SQLException
	{
		Hashtable ht_RwkJbOpn_Get = new Hashtable();
		Hashtable ht_JbStatSno_Get = new Hashtable();
		PreparedStatement ps_RwkJbOpn_Get = null;
		ResultSet rs_RwkJbOpn_Get = null;
		DBConnection con=null;
		Vector vecResult = new Vector();
		SortString ss = new SortString();
		String unpostedOpn_Snos = "";
		int woJbId = 0;

		logger.info("GET REWORK JOB OPERATION DETAILS STARTS");

		try
		{
			con = DBConnectionFactory.getConnection();
			ht_RwkJbOpn_Get.put("WO_ID",new Integer(woId));
			ht_RwkJbOpn_Get.put("JB_ID",new Integer(jbId));
			PreparedStatement ps_WoJbId_Get = con.executeStatement(SQLMaster.GET_WOJB_ID_SELECT_SQL_QUERY,ht_RwkJbOpn_Get);
			ResultSet rs_WoJbId_Get = ps_WoJbId_Get.executeQuery();
			if(rs_WoJbId_Get.next())
			{
			    woJbId = rs_WoJbId_Get.getInt("WOJB_ID");
			}

			rs_WoJbId_Get.close();
			ps_WoJbId_Get.close();

			ht_RwkJbOpn_Get.put("WOJB_ID",new Integer(woJbId));

			try
			{
				ps_RwkJbOpn_Get = con.executeStatement(SQLMaster.GET_RWK_JOBSTATSNO_SQL_QUERY,ht_RwkJbOpn_Get);
			    rs_RwkJbOpn_Get = ps_RwkJbOpn_Get.executeQuery();
			    ResultSet rs_Prod_Det  = null;
			    PreparedStatement ps_Prod_Det = null;
				while(rs_RwkJbOpn_Get.next())
				{
					RwkLogJbQtyDetails objRwkLogJbQtyDetails = new RwkLogJbQtyDetails();
					objRwkLogJbQtyDetails.setJbStatId(rs_RwkJbOpn_Get.getInt("WOJBSTAT_ID"));
					objRwkLogJbQtyDetails.setJbQtySno(rs_RwkJbOpn_Get.getInt("WOJBSTAT_SNO"));
					int jbQtySno = objRwkLogJbQtyDetails.getJbQtySno();
					ht_JbStatSno_Get.put("WOJBSTAT_ID",new Integer(objRwkLogJbQtyDetails.getJbStatId()));

					ps_Prod_Det = con.executeStatement(SQLMaster.GET_PRODDETAILS_SQL_QUERY,ht_RwkJbOpn_Get);
					rs_Prod_Det = ps_Prod_Det.executeQuery();
					String totOpns = "";
					while(rs_Prod_Det.next())
					{
						String qtySnos = "";
						qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
						StringTokenizer st = new StringTokenizer(qtySnos,",");
						while(st.hasMoreTokens())
						{
							int sno = Integer.parseInt(st.nextToken().trim());
							if(sno == jbQtySno)
							{
								int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
								int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
								for(int j = stOpn ;j<=endOpn ; j++)
								{
									if(totOpns.equals(""))
									{
										totOpns = j+"";
									}
									else
									{
										totOpns = totOpns +","+j;
									}
								}
							}

						}
					}
					rs_Prod_Det.close();
					ps_Prod_Det.close();
					//to take all Operations from RADL table for this particular Quantity...
					PreparedStatement ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_RADL_PRODDETAILS_SQL_QUERY,ht_RwkJbOpn_Get);
					ResultSet rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
					while(rs_RadlProd_Det.next())
					{
						String qtySnos = "";
						qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
						StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
						while(st.hasMoreTokens())
						{
							int sno = Integer.parseInt(st.nextToken().trim());
							if(sno == jbQtySno)
							{
								int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
								int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
								for(int j = stOpn ;j<=endOpn ; j++)
								{
									if(totOpns.equals(""))
									{
										totOpns = j+"";
									}
									else
									{
										totOpns = totOpns +","+j;
									}

								}

							}
						}

					}
					rs_RadlProd_Det.close();
					ps_RadlProd_Det.close();

					totOpns = ss.sortString(totOpns);
					PreparedStatement ps_Unposted_OpnSnos = con.executeStatement(SQLMaster.GET_OPERATION_SQL_QUERY,ht_JbStatSno_Get);
					ResultSet rs_Unposted_OpnSnos = ps_Unposted_OpnSnos.executeQuery();
					unpostedOpn_Snos = "";

					Vector vec = new Vector();
					while(rs_Unposted_OpnSnos.next())
					{

						int woJbOpnSno = rs_Unposted_OpnSnos.getInt("WOJBOPN_OPN_SNO");
						StringTokenizer st = new StringTokenizer(totOpns,",");
						while(st.hasMoreTokens())
						{
							if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
							{
								if(unpostedOpn_Snos.equals(""))
								{
									unpostedOpn_Snos = woJbOpnSno+"";

								}
								else
								{
									unpostedOpn_Snos = unpostedOpn_Snos + ","+woJbOpnSno;
								}
								RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
								objRwkLogJbOpnDetails.setOpnSno(woJbOpnSno);
								objRwkLogJbOpnDetails.setOpnName(rs_Unposted_OpnSnos.getString("WOJBOPN_OPN_NAME"));
								objRwkLogJbOpnDetails.setWoJbOpnId(rs_Unposted_OpnSnos.getInt("WOJBOPN_ID"));
								objRwkLogJbOpnDetails.setProdnEntered(true);
								vec.addElement(objRwkLogJbOpnDetails);

							}
						}


					}
					rs_Unposted_OpnSnos.close();
					ps_Unposted_OpnSnos.close();

					ps_Prod_Det = con.executeStatement(SQLMaster.GET_REWORK_PRODDETAILS_SQL_QUERY,ht_RwkJbOpn_Get);
					rs_Prod_Det = ps_Prod_Det.executeQuery();
					String sumOpns = "";
					while(rs_Prod_Det.next())
					{
						String qtySnos = "";
						qtySnos = rs_Prod_Det.getString("PROD_QTY_SNOS");
						StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
						while(st.hasMoreTokens())
						{
							int sno = Integer.parseInt(st.nextToken().trim());
							if(sno == jbQtySno)
							{
								int stOpn = rs_Prod_Det.getInt("PROD_START_OPN");
								int endOpn = rs_Prod_Det.getInt("PROD_END_OPN");
								for(int j = stOpn ;j<=endOpn ; j++)
								{
									if(sumOpns.equals(""))
									{
									    sumOpns = j+"";
									}
									else
									{
									    sumOpns = sumOpns +","+j;
									}
								}
							}

						}
					}
					rs_Prod_Det.close();
					ps_Prod_Det.close();

					ps_RadlProd_Det = con.executeStatement(SQLMaster.GET_REWORK_RADL_PRODDETAILS_SQL_QUERY,ht_RwkJbOpn_Get);
					rs_RadlProd_Det = ps_RadlProd_Det.executeQuery();
					while(rs_RadlProd_Det.next())
					{
						String qtySnos = "";
						qtySnos = rs_RadlProd_Det.getString("RADL_QTY_SNOS");
						StringTokenizer st = new StringTokenizer(qtySnos.trim(),",");
						while(st.hasMoreTokens())
						{
							int sno = Integer.parseInt(st.nextToken().trim());
							if(sno == jbQtySno)
							{
								int stOpn = rs_RadlProd_Det.getInt("RADL_START_OPN");
								int endOpn = rs_RadlProd_Det.getInt("RADL_END_OPN");
								for(int j = stOpn ;j<=endOpn ; j++)
								{
									if(sumOpns.equals(""))
									{
										sumOpns = j+"";
									}
									else
									{
										sumOpns = sumOpns +","+j;
									}

								}

							}
						}

					}

					rs_RadlProd_Det.close();
					ps_RadlProd_Det.close();

					PreparedStatement ps_Rework_OpnSnos = con.executeStatement(SQLMaster.GET_PENDING_PRODN_JOBSTAT_REWORK_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
					ResultSet rs_Rework_OpnSnos = ps_Rework_OpnSnos.executeQuery();
					while(rs_Rework_OpnSnos.next())
					{
					    int woJbOpnSno = rs_Rework_OpnSnos.getInt("WOJBOPN_OPN_SNO");
					    StringTokenizer st = new StringTokenizer(sumOpns,",");
					    while(st.hasMoreTokens())
					    {

					        if(woJbOpnSno==Integer.parseInt(st.nextToken().trim()))
					        {
					            if(unpostedOpn_Snos.equals(""))
					            {
					                unpostedOpn_Snos = woJbOpnSno+"";

					            }
					            else
					            {
					                unpostedOpn_Snos = unpostedOpn_Snos + ","+woJbOpnSno;
					            }

					    		RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
								objRwkLogJbOpnDetails.setOpnSno(woJbOpnSno);
								objRwkLogJbOpnDetails.setOpnName(rs_Rework_OpnSnos.getString("WOJBOPN_OPN_NAME"));
								objRwkLogJbOpnDetails.setWoJbOpnId(rs_Rework_OpnSnos.getInt("WOJBOPN_ID"));
								objRwkLogJbOpnDetails.setProdnEntered(true);
								vec.addElement(objRwkLogJbOpnDetails);

					        }
					    }

					}
					rs_Rework_OpnSnos.close();
					ps_Rework_OpnSnos.close();



					objRwkLogJbQtyDetails.setUnPostedProdnSnos(ss.sortString(unpostedOpn_Snos));

					PreparedStatement ps_Posted_Opns = con.executeStatement(SQLMaster.GET_POSTED_PRODN_OPERATION_SNOS_SQL_QUERY,ht_JbStatSno_Get);
					ResultSet rs_Posted_Opns = ps_Posted_Opns.executeQuery();

					String postedOpn_Snos = "";
					while(rs_Posted_Opns.next())
					{
						RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
						int woJbOpnSno = rs_Posted_Opns.getInt("WOJBOPN_OPN_SNO");
						if(postedOpn_Snos.equals(""))
						{
							postedOpn_Snos = woJbOpnSno+"";
						}
						else
						{
							postedOpn_Snos = postedOpn_Snos + ","+woJbOpnSno;
						}
						objRwkLogJbOpnDetails.setOpnSno(woJbOpnSno);
						objRwkLogJbOpnDetails.setOpnName(rs_Posted_Opns.getString("WOJBOPN_OPN_NAME"));
						objRwkLogJbOpnDetails.setWoJbOpnId(rs_Posted_Opns.getInt("WOJBOPN_ID"));
						objRwkLogJbOpnDetails.setProdnEntered(false);
						vec.addElement(objRwkLogJbOpnDetails);
					}
					rs_Posted_Opns.close();
					ps_Posted_Opns.close();

					objRwkLogJbQtyDetails.setVecRwkLogJbOpnDetails(vec);
					objRwkLogJbQtyDetails.setPostedProdnSnos(ss.sortString(postedOpn_Snos));
					if(!(objRwkLogJbQtyDetails.getPostedProdnSnos()+objRwkLogJbQtyDetails.getUnPostedProdnSnos()).equals(""))
					vecResult.addElement(objRwkLogJbQtyDetails);
				}
				rs_RwkJbOpn_Get.close();
				ps_RwkJbOpn_Get.close();
			}
			catch(SQLException sqle)
			{
				if(BuildConfig.DMODE)
				{
					sqle.printStackTrace();
				}
				throw new ProductionException("RLEC000","GENERAL SQL ERROR",sqle.toString());
			}
		}
		catch(SQLException ex)
		{
			if(BuildConfig.DMODE)
			{
				ex.printStackTrace();
			}
			throw new ProductionException("RLEC000","GENERAL SQL ERROR",ex.toString());
		}
		finally
		{
			try
			{
            	if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
            }
			catch(SQLException ex)
			{
				if(BuildConfig.DMODE)
				{
					logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				}
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		if(BuildConfig.DMODE)
		{
			logger.info("GET REWORK JOB OPERATION DETAILS ENDS");
		}
		return vecResult;
	}
	public ReworkLogDetails getReworkLogDetails(int rwkLogId) throws ProductionException, SQLException
	{
	    DBConnection con = null;
	    Hashtable ht_RwkLogId = new Hashtable();
	    ReworkLogDetails objReworkLogDetails = new ReworkLogDetails();
	    try
	    {
	        con = DBConnectionFactory.getConnection();
	        ht_RwkLogId.put("RWK_LOG_ID",new Integer(rwkLogId));
	        PreparedStatement ps_RwkDetails_Get = con.executeStatement(SQLMaster.GET_RWKLOG_DETAILS_SQL_QUERY,ht_RwkLogId);
	        ResultSet rs_RwkDetails_Get = ps_RwkDetails_Get.executeQuery();
			if(rs_RwkDetails_Get.next())
			{
			    objReworkLogDetails.setRwkCategory(rs_RwkDetails_Get.getString("RWK_CATEGORY"));
			    objReworkLogDetails.setRwkReason(rs_RwkDetails_Get.getString("RWK_RSN"));
			    objReworkLogDetails.setAuthorizedBy(rs_RwkDetails_Get.getString("RWKLOG_AUTHRZDBY"));
			    objReworkLogDetails.setRwkDate(rs_RwkDetails_Get.getTimestamp("RWK_DATE"));
			    objReworkLogDetails.setWoNo(rs_RwkDetails_Get.getString("WO_NO"));
			    objReworkLogDetails.setJbName(rs_RwkDetails_Get.getString("JB_NAME"));
			    objReworkLogDetails.setRwkIsvalid(rs_RwkDetails_Get.getString("RWKLOG_ISVALID").equals("1"));

			}
			rs_RwkDetails_Get.close();
			ps_RwkDetails_Get.close();

			PreparedStatement ps_RwkEmpDetails_Get = con.executeStatement(SQLMaster.GET_RWKLOG_EMPLOYEE_DETAILS_SQL_QUERY,ht_RwkLogId);
			ResultSet rs_RwkEmpDetails_Get = ps_RwkEmpDetails_Get.executeQuery();
			Vector vecResult = new Vector();
			while(rs_RwkEmpDetails_Get.next())
			{
			    ReworkLogEmployeeDetails objReworkLogEmployeeDetails = new ReworkLogEmployeeDetails();
			    objReworkLogEmployeeDetails.setEmpId(rs_RwkEmpDetails_Get.getInt("EMP_ID"));
			    objReworkLogEmployeeDetails.setEmpName(rs_RwkEmpDetails_Get.getString("EMP_NAME"));
			    objReworkLogEmployeeDetails.setEmpTypId(rs_RwkEmpDetails_Get.getInt("EMP_TYP_ID"));
			    objReworkLogEmployeeDetails.setEmpTypName(rs_RwkEmpDetails_Get.getString("EMP_TYP_NAME"));
			    vecResult.add(objReworkLogEmployeeDetails);
			}
			rs_RwkEmpDetails_Get.close();
			ps_RwkEmpDetails_Get.close();

			objReworkLogDetails.setVecRwkLogEmpDetails(vecResult);
			PreparedStatement ps_RwkOpnDetails_Get = con.executeStatement(SQLMaster.GET_RWKLOG_OPN_DETAILS_SQL_QUERY,ht_RwkLogId);
			ResultSet rs_RwkOpnDetails_Get = ps_RwkOpnDetails_Get.executeQuery();
			Vector vectResult = new Vector();
			while(rs_RwkOpnDetails_Get.next())
			{
			    RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
			    objRwkLogJbOpnDetails.setOpnGpCode(rs_RwkOpnDetails_Get.getString("OPN_GP_CODE"));
			    objRwkLogJbOpnDetails.setOpnSno(rs_RwkOpnDetails_Get.getInt("WOJBOPN_OPN_SNO"));
			    objRwkLogJbOpnDetails.setOpnName(rs_RwkOpnDetails_Get.getString("WOJBOPN_OPN_NAME"));
			    objRwkLogJbOpnDetails.setOpnStdHrs(rs_RwkOpnDetails_Get.getFloat("WOJBOPN_OPN_STDHRS"));
			    vectResult.add(objRwkLogJbOpnDetails);
			}
			rs_RwkOpnDetails_Get.close();
			ps_RwkOpnDetails_Get.close();

			PreparedStatement ps_RwkQtyDetails_Get = con.executeStatement(SQLMaster.GET_RWKLOG_JBQTY_DETAILS_SQL_QUERY,ht_RwkLogId);
			ResultSet rs_RwkQtyDetails_Get = ps_RwkQtyDetails_Get.executeQuery();
			RwkLogJbQtyDetails objRwkLogJbQtyDetails = new RwkLogJbQtyDetails();
			if(rs_RwkQtyDetails_Get.next())
			{
			    int woJbQtySno = rs_RwkQtyDetails_Get.getInt("WOJBSTAT_SNO");
			    objRwkLogJbQtyDetails.setJbQtySno(woJbQtySno);
			}
			rs_RwkQtyDetails_Get.close();
			ps_RwkQtyDetails_Get.close();

			objRwkLogJbQtyDetails.setVecRwkLogJbOpnDetails(vectResult);
			Vector vec_QtyDet = new Vector();
			vec_QtyDet.addElement(objRwkLogJbQtyDetails);

			objReworkLogDetails.setVecRwkLogJbQtyDetails(vec_QtyDet);

	    }
	    catch(SQLException sqle)
	    {
	        if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
				logger.info("GENERAL EXCEPTION");
			}
			throw new ProductionException("RLEC000","GENERAL SQL ERROR",sqle.toString());
	    }
	    finally
		{
			try
			{
            	if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
            }
			catch(SQLException ex)
			{
				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}
		return objReworkLogDetails;
	}
	public HashMap getAllReworkLogDetails(Filter[] filters,String sortBy,boolean ascending,int startIndex,int displayCount) throws ProductionException, SQLException
	{

		HashMap hm_Result = new HashMap();
		Vector vec_RwkLog_Det = new Vector();
		DBConnection con=null;

		logger.info("GET ALL REWORK LOG DETAILS FILTER STARTS");

		if((filters == null)||( sortBy == null))
		{
			logger.error("FILTER VALUES ARE NULL");

			throw new ProductionException("RLEC006","FILTER VALUES ARE NULL","");
		}

		con = DBConnectionFactory.getConnection();

		int tot_Rec_Cnt = 0;


		try
		{

		    //filters and tableName are passed to the function and receives Total Record Count
			tot_Rec_Cnt = con.getRowCountWithComplexFilters(filters,SQLMaster.GET_ALL_REWORK_DETAILS_FILTER_QUERY);

			//Finding end index for the query
			int eIndex = startIndex + displayCount;

				// filters,tablename,startindex,endindex,sorting column name and sortorder are passed to the function and  query will be received
			String Query = con.buildDynamicQuery(filters,startIndex,eIndex,sortBy,ascending,SQLMaster.GET_ALL_REWORK_DETAILS_FILTER_QUERY);

			if(BuildConfig.DMODE)
			{
				// total records and query
				System.out.println("TOTAL NUMBER OF RECORDS : " + tot_Rec_Cnt);
				System.out.println("QUERY : " + Query);
			}

			logger.info("Total Records : " + tot_Rec_Cnt);
			logger.info("Query : " + Query);


			ResultSet rs_RwkLog_Details_Get =  con.executeRownumStatement(Query);
			while(rs_RwkLog_Details_Get.next())
			{
				ReworkLogDetails objReworkLogDetails = new ReworkLogDetails();

				objReworkLogDetails.setRwkLogId(rs_RwkLog_Details_Get.getInt("RWK_LOG_ID"));
				objReworkLogDetails.setRwkCategory(rs_RwkLog_Details_Get.getString("RWK_CATEGORY"));
				objReworkLogDetails.setRwkReason(rs_RwkLog_Details_Get.getString("RWK_RSN"));
				objReworkLogDetails.setWoNo(rs_RwkLog_Details_Get.getString("WO_NO"));
				objReworkLogDetails.setJbName(rs_RwkLog_Details_Get.getString("JB_NAME"));
				objReworkLogDetails.setRwkDate(rs_RwkLog_Details_Get.getTimestamp("RWK_DATE"));
				objReworkLogDetails.setRwkIsvalid((rs_RwkLog_Details_Get.getString("RWKLOG_ISVALID")).equals("1")?true:false);
				objReworkLogDetails.setRwkDateStamp(rs_RwkLog_Details_Get.getTimestamp("RWKLOG_DATESTAMP"));

				vec_RwkLog_Det.add(objReworkLogDetails);
			}
			hm_Result.put("TotalRecordCount",new Integer(tot_Rec_Cnt));
			hm_Result.put("ReworkLogDetails",vec_RwkLog_Det);
			rs_RwkLog_Details_Get.getStatement().close();
			rs_RwkLog_Details_Get.close();
		}
		catch(SQLException sqle)
		{
			if(BuildConfig.DMODE)
			{
				sqle.printStackTrace();
			}
			logger.info("GENERAL EXCEPTION");
			throw new ProductionException("RLEC000","GENERAL SQL ERROR",sqle.toString());
		}
		finally
		{
			try
			{
            	if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
            }
			catch(SQLException ex)
			{
				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		logger.info("GET ALL REWORK LOG DETAILS FILTER ENDS");


		return hm_Result;
	}
	//MAKE REWORK LOG VALID
	public HashMap makeReworkLogValid(Vector reworkLogIds) throws SQLException,ProductionException
	{
		DBConnection con = null;
		int res = 0;
		HashMap hmp_RwkLog_MkValid = new HashMap();


		logger.info("MAKE REWORK LOG VALID STARTED");


		if(reworkLogIds == null)
		{

			logger.error("REWORK LOG ID VECTOR IS NULL");
			throw new ProductionException("RLMEC001","REWORK LOG ID VECTOR OBJECT IS NULL","");
		}


		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();
			for(int i=0;i<reworkLogIds.size();i++)
			{
				Hashtable ht_ReworkLogId = new Hashtable();

				//put the RWK_LOG_ID into Hashtable
				ht_ReworkLogId.put("RWK_LOG_ID",(Integer)reworkLogIds.get(i));

					res = con.executeUpdateStatement(SQLMaster.REWORK_LOG_MAKE_VALID_QUERY,ht_ReworkLogId);
				if(res>=1)
				{
				    hmp_RwkLog_MkValid.put(reworkLogIds.get(i),new Integer(0));
					if(BuildConfig.DMODE)
					{
						System.out.println("RWK LOG RECORD VALIDATED");
					}

					logger.debug("RWK LOG RECORD VALIDATED");
					con.commitTransaction();
				}
				else
				{
				    hmp_RwkLog_MkValid.put(reworkLogIds.get(i),new Integer(1));
					con.rollBackTransaction();
					if(BuildConfig.DMODE)
					{
						System.out.println("REWORK LOG RECORD NOT VALIDATED");

					}

					logger.debug("REWORK LOG RECORD NOT VALIDATED");


				}

			}
		}
		catch(SQLException e)
		{

			logger.error("GENERAL SQL ERROR",e);

			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new ProductionException("RLMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{
            	if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
            }
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		return hmp_RwkLog_MkValid;

	}

	//	MAKE REWORK LOG INVALID
	public HashMap makeReworkLogInValid(Vector reworkLogIds) throws SQLException,ProductionException
	{
		DBConnection con = null;
		int res = 0;
		HashMap hmp_RwkLog_MkValid = new HashMap();


		logger.info("MAKE REWORK LOG INVALID STARTED");


		if(reworkLogIds == null)
		{

			logger.error("REWORK LOG ID VECTOR IS NULL");
			throw new ProductionException("RLMEC001","REWORK LOG ID VECTOR OBJECT IS NULL","");
		}


		try
		{
			con = DBConnectionFactory.getConnection();
			con.startTransaction();
			for(int i=0;i<reworkLogIds.size();i++)
			{
				Hashtable ht_ReworkLogId = new Hashtable();

				//put the RWK_LOG_ID into Hashtable
				ht_ReworkLogId.put("RWK_LOG_ID",(Integer)reworkLogIds.get(i));

					res = con.executeUpdateStatement(SQLMaster.REWORK_LOG_MAKE_INVALID_QUERY,ht_ReworkLogId);
				if(res>=1)
				{
				    hmp_RwkLog_MkValid.put(reworkLogIds.get(i),new Integer(0));
					if(BuildConfig.DMODE)
					{
						System.out.println("RWK LOG RECORD INVALIDATED");
					}

					logger.debug("RWK LOG RECORD INVALIDATED");
					con.commitTransaction();
				}
				else
				{
				    hmp_RwkLog_MkValid.put(reworkLogIds.get(i),new Integer(1));
					con.rollBackTransaction();
					if(BuildConfig.DMODE)
					{
						System.out.println("REWORK LOG RECORD NOT INVALIDATED");
					}

					logger.debug("REWORK LOG RECORD NOT INVALIDATED");

				}

			}
		}
		catch(SQLException e)
		{

			logger.error("GENERAL SQL ERROR",e);

			if(BuildConfig.DMODE)
			{
				e.printStackTrace();
				System.out.println("GENERAL EXCEPTION");
			}
			throw new ProductionException("RLMEC000","GENERAL SQL ERROR",e.toString());
		}
		finally
		{
			try
			{
            	if(con != null)
					if(con.getConnection() != null )
						if(!con.getConnection().isClosed())
						{
							con.closeConnection();
						}
            }
			catch(SQLException ex)
			{

				logger.error("EXCEPTION WHILE CLOSING CONNECTION");
				throw new SQLException("EXCEPTION WHILE CLOSING CONNECTION" + ex.toString());
			}
		}

		return hmp_RwkLog_MkValid;

	}

	public static void main(String args[]) throws ProductionException, SQLException{

	   ReworkLogDetailsManager objReworkLogDetailsManager = new ReworkLogDetailsManager();
	 /*
	   Vector v = objReworkLogDetailsManager.getRwkJobOperationDetails(340,94);
	    System.out.println("v.size() :"+v.size());
	    RwkLogJbQtyDetails objRwkLogJbQtyDetails = new RwkLogJbQtyDetails();
	    objRwkLogJbQtyDetails = (RwkLogJbQtyDetails)v.elementAt(0);
	    System.out.println("posted :"+objRwkLogJbQtyDetails.getPostedProdnSnos());
	    System.out.println("Unposted :"+objRwkLogJbQtyDetails.getUnPostedProdnSnos());
	    Vector v1 = objRwkLogJbQtyDetails.getVecRwkLogJbOpnDetails();


	    RwkLogJbOpnDetails objRwkLogJbOpnDetails = new RwkLogJbOpnDetails();
	    objRwkLogJbOpnDetails = (RwkLogJbOpnDetails)v1.elementAt(0);
	    System.out.println(objRwkLogJbOpnDetails.getOpnSno()+","+objRwkLogJbOpnDetails.getOpnName()+","+objRwkLogJbOpnDetails.isProdnEntered());
	    objRwkLogJbOpnDetails = (RwkLogJbOpnDetails)v1.elementAt(1);
	    System.out.println(objRwkLogJbOpnDetails.getOpnSno()+","+objRwkLogJbOpnDetails.getOpnName()+","+objRwkLogJbOpnDetails.isProdnEntered());
	    objRwkLogJbOpnDetails = (RwkLogJbOpnDetails)v1.elementAt(2);
	    System.out.println(objRwkLogJbOpnDetails.getOpnSno()+","+objRwkLogJbOpnDetails.getOpnName()+","+objRwkLogJbOpnDetails.isProdnEntered());
	    objRwkLogJbOpnDetails = (RwkLogJbOpnDetails)v1.elementAt(3);
	    System.out.println(objRwkLogJbOpnDetails.getOpnSno()+","+objRwkLogJbOpnDetails.getOpnName()+","+objRwkLogJbOpnDetails.isProdnEntered());
	 */
	   ReworkLogDetails objReworkLogDetails = objReworkLogDetailsManager.getReworkLogDetails(34);
	   //System.out.println(objReworkLogDetai);
	}
}

/***
$Log: ReworkLogDetailsManager.java,v $
Revision 1.35  2005/09/15 07:36:10  kduraisamy
rs.getStatement().close() added in Filters.

Revision 1.34  2005/06/29 12:43:58  smurugesan
if con ! = null added in finally

Revision 1.33  2005/05/26 05:35:46  kduraisamy
unused variables removed.

Revision 1.32  2005/04/22 10:11:09  kduraisamy
logger class changed.

Revision 1.31  2005/04/19 13:13:34  kduraisamy
resultset properly closed.

Revision 1.30  2005/04/18 12:28:28  kduraisamy
executeStatement() return type changed.

Revision 1.29  2005/04/07 09:20:43  kduraisamy
MODE IS CHANGED TO DMODE.

Revision 1.28  2005/04/07 07:36:49  kduraisamy
if(BuildConfig.DMODE) ADDED. AND LEVEL INCLUDED PROPERLY.

Revision 1.27  2005/03/30 08:25:29  kduraisamy
excecuteRownumStatement() called for filters.

Revision 1.26  2005/03/26 11:50:58  smurugesan
ERROR CODES ARE ALTERED.

Revision 1.25  2005/03/26 10:09:10  smurugesan
ROWNUM RESET QUERY REMOVED.

Revision 1.24  2005/03/23 12:15:14  kduraisamy
getInt(1) added instead of getInt(Id).

Revision 1.23  2005/03/11 06:20:47  kduraisamy
RESET ROWNUM QUERY ADDED BEFORE FILTER METHODS..

Revision 1.22  2005/03/10 07:27:36  kduraisamy
CHECKING BEFORE THE FILTER METHODS(buildDynamicQuery(),etc) added.

getDate is changed to getTimestamp().

Revision 1.21  2005/03/04 08:15:21  kduraisamy
DBConnection Changes made.

Revision 1.20  2005/02/14 11:10:05  kduraisamy
unwanted cast removed.

Revision 1.19  2005/02/11 11:47:21  kduraisamy
isvalid field added in getMethod().

Revision 1.18  2005/02/11 06:55:57  kduraisamy
unused variables removed.

Revision 1.17  2005/02/11 05:16:19  kduraisamy
make valid , invalid added.

Revision 1.16  2005/02/05 09:15:52  sponnusamy
Errors corrected.

Revision 1.15  2005/02/05 06:10:15  kduraisamy
jbQtySno added in entity Object.

Revision 1.14  2005/02/04 19:07:19  kduraisamy
rework entered operations get code included.

Revision 1.13  2005/02/03 14:42:59  kduraisamy
getReworkLogDetails() added.

***/


