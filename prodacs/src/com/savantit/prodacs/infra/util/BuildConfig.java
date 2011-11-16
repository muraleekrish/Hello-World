/*
 * Created on Mar 2, 2005
 *
 * ClassName	:  	BuildConfig.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.infra.util;

/**
 * @author kduraisamy
 * 
 */
public class BuildConfig {
	public static final int ORACLE = 1;
	public static final int MYSQL = 2;
	public static final int DBASE = MYSQL;
	public static final boolean USECONNECTIONPOOL = false;
	public static final boolean USE_INFO_LOGGER = true;
	public static final boolean USE_WARN_LOGGER = true;
	public static final boolean USE_ERROR_LOGGER = true;
	public static final boolean USE_FATAL_LOGGER = true;
	public static final boolean USE_DEBUG_LOGGER = true;

	public static final boolean DMODE = false; // to check is it development
												// mode

}

/*
 * $Log: BuildConfig.java,v $Revision 1.37 2005/11/18 09:02:52 kduraisamyDBASE =
 * ORACLE ADDED.
 * 
 * Revision 1.36 2005/10/24 07:16:28 vkrishnamoorthyDMODE set to false.
 * 
 * Revision 1.35 2005/10/24 06:11:53 vkrishnamoorthyDMODE set to true.
 * 
 * Revision 1.23 2005/08/22 08:51:57 vkrishnamoorthyDMODE SET TO TRUE.
 * 
 * Revision 1.22 2005/08/22 07:25:02 vkrishnamoorthyDMODE SET TO FALSE.
 * 
 * Revision 1.19 2005/07/28 09:36:53 kduraisamyDMODE IS SET TO TRUE.
 * 
 * Revision 1.18 2005/07/27 07:28:47 kduraisamydmode is set to false.
 * 
 * Revision 1.17 2005/07/27 06:52:26 kduraisamydmode is set to true.
 * 
 * Revision 1.16 2005/07/26 15:59:00 kduraisamydmode is set to false.
 * 
 * Revision 1.15 2005/07/12 09:38:00 kduraisamydmode set to true.
 * 
 * Revision 1.14 2005/07/07 07:22:46 kduraisamydmode set to false.
 * 
 * Revision 1.13 2005/06/11 09:52:01 kduraisamydmode is true.
 * 
 * Revision 1.12 2005/06/08 14:27:45 kduraisamydmode set to false.
 * 
 * Revision 1.11 2005/05/27 07:33:32 kduraisamydmode is set to false
 * 
 * Revision 1.10 2005/05/20 09:35:28 kduraisamydmode is set to false.
 * 
 * Revision 1.9 2005/05/17 04:44:25 kduraisamydmode is set to true.
 * 
 * Revision 1.8 2005/05/11 10:56:41 kduraisamydmode set to false.
 * 
 * Revision 1.7 2005/05/05 04:43:00 vkrishnamoorthysunday added instead of
 * thursday in select shiftDefnDetails sql query.
 * 
 * Revision 1.6 2005/04/22 15:23:47 kduraisamydevelopment mode set as false.
 * 
 * Revision 1.5 2005/04/16 07:52:57 kduraisamyDMODE SET TO TRUE.
 * 
 * Revision 1.4 2005/04/07 09:19:02 kduraisamyFIELD DMODE INCLUDED.
 * 
 * Revision 1.3 2005/04/07 07:49:28 kduraisamyDMODE FIELD ADDED.
 * 
 * Revision 1.2 2005/04/07 07:41:28 kduraisamyMODE FIELD INCLUDED AND SET FALSE.
 * 
 * Revision 1.1 2005/03/04 07:23:45 kduraisamyinitial commit.
 */