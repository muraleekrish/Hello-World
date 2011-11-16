/*
 * Created on Dec 17, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.infra.util;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class SortString 
{
	public String sortString(String actual)
	{
		String sorted = "";
		StringTokenizer st = new StringTokenizer(actual,",");
		int array[];
		array = new int[st.countTokens()];
		int i = 0;
		
		while(st.hasMoreTokens())
		{
			array[i] = Integer.parseInt(st.nextElement().toString().trim());
			i++;
		}
	
		Arrays.sort(array);
		
		/*for(i = 0;i<array.length-1 && flg ;i++)
		{
			flg = false;
		    for(int j = 0;j<array.length-i-1;j++)
			{
				if(array[j]>array[j+1])
				{
					flg = true;
				    array[j]=array[j]+array[j+1]-(array[j+1]=array[j]);
				}
			}
		}
		*/for(int k = 0;k<array.length;k++)
		{
			if(k==0)
			sorted = array[k]+"";
			else
			sorted = sorted+","+array[k];
		}
		return sorted;
	}
	
	public static void main(String args[])
	{
	    SortString ss = new SortString();
	    System.out.println(ss.sortString("3,12,1,24,14"));
	}
}
