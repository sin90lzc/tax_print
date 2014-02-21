package com.sin90lzc.tax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.sin90lzc.tax.constant.Alignment;
import com.sin90lzc.tax.util.StringUtil;

public class PrintTax {
	
	//private static final String TAX_PHONE="";
	private static final String[] TAX_PHONE_PREFIX={"363","843","342","393"};
	
	private static final String TAX_NO_PREFIX="A";
	
	private static final int MAX_LEN=16;
	
	
	private static final char APPEND_CHAR=' ';
	
	private static final Random random=new Random(37);

	private static String taxDate="2014-02-14";
	
	
	public static void main(String[] args) {
		Collection<String> col=new ArrayList<String>();
		for(int i=0;i<10000;i++){
			String taxPhone=getTaxPhone();
			System.out.println(taxPhone);
			if(col.contains(taxPhone)){
				System.out.println(i);
				throw new RuntimeException("error!");
			}else{
				col.add(taxPhone);
			}
		}
	}

	
	private static final String getTaxPhone(){
		int preRandom=random.nextInt(4*999);
		int preIndex=preRandom%4;
		StringBuffer phone=new StringBuffer(TAX_PHONE_PREFIX[preIndex]);
		for(int i=0;i<5;i++){
			phone.append(random.nextInt(10));
		}
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, phone.toString(), APPEND_CHAR);
	}
	
	/**
	 * 获取的士车牌
	 * @return
	 */
	private static final String getTaxNo(){
		StringBuffer taxNo=new StringBuffer(TAX_NO_PREFIX);
		boolean slid=random.nextBoolean();
		char nextChar=(char)(random.nextInt('Z'-'A')+'A');
		if(slid){
			taxNo.append(nextChar);
			taxNo.append(random.nextInt(10000));
		}else{
			taxNo.append(random.nextInt(1000));
			taxNo.append(nextChar);
			taxNo.append(random.nextInt(10));
		}
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, taxNo.toString(), APPEND_CHAR);
	}
	/**
	 * 获取证件号
	 * @return
	 */
	private static final String getCred(){
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, "0000", APPEND_CHAR);
	}
	
	/**
	 * 获取日期
	 * @return
	 */
	private static final String getTaxDate(){
		return taxDate;
	}
	
}
