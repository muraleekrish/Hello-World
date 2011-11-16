/*
 * Created on Feb 28, 2005
*/
package com.savantit.prodacs.businessimplementation.securityadmin;


/**
 * @author sduraisamy
 */
public class SecurityAdminException extends Exception 
{
    private String exceptionMessage;
	private String exceptionCode;
	private String exceptionGeneral;
	public SecurityAdminException(String exceptionCode,String exceptionMssage,String exceptionGeneral)
	{
		this.exceptionMessage = exceptionMssage;
		this.exceptionCode = exceptionCode;
		this.exceptionGeneral=exceptionGeneral;
	}
	



    /**
     * @return Returns the exceptionCode.
     */
    public String getExceptionCode()
    {
        return exceptionCode;
    }
    /**
     * @param exceptionCode The exceptionCode to set.
     */
    public void setExceptionCode(String exceptionCode)
    {
        this.exceptionCode = exceptionCode;
    }
    /**
     * @return Returns the exceptionGeneral.
     */
    public String getExceptionGeneral()
    {
        return exceptionGeneral;
    }
    /**
     * @param exceptionGeneral The exceptionGeneral to set.
     */
    public void setExceptionGeneral(String exceptionGeneral)
    {
        this.exceptionGeneral = exceptionGeneral;
    }
    /**
     * @return Returns the exceptionMessage.
     */
    public String getExceptionMessage()
    {
        return exceptionMessage;
    }
    /**
     * @param exceptionMessage The exceptionMessage to set.
     */
    public void setExceptionMessage(String exceptionMessage)
    {
        this.exceptionMessage = exceptionMessage;
    }
}

/***
$Log: SecurityAdminException.java,v $
Revision 1.2  2005/06/16 11:41:42  kduraisamy
constructor changed.

Revision 1.1  2005/03/23 06:22:06  kduraisamy
initial commit.

Revision 1.1  2005/03/02 06:33:34  sduraisamy
SecurityException renamed as SecurityAdminException

Revision 1.1  2005/02/28 12:13:50  sduraisamy
Initial commit

*/
