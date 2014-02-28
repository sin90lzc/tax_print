package com.sin90lzc.tax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import com.sin90lzc.tax.constant.Alignment;
import com.sin90lzc.tax.util.StringUtil;
import com.sin90lzc.util.FormatManager;
import com.sin90lzc.util.enums.DateFormatPattern;
import com.sin90lzc.util.system.SystemProperty;

public class PrintTax {

	// private static final String TAX_PHONE="";
	private static final String[] TAX_PHONE_PREFIX = { "363", "843", "342",
			"393" };

	private static final String TAX_NO_PREFIX = "A";

	private static final int MAX_LEN = 16;

	private static final String UNIT_PRICE = "2.60元";

	private static final char APPEND_CHAR = ' ';

	private static final Random random = new Random(37);
	
	private static final int BLANK_LINE_SIZE=8;

	public static void main(String[] args) {
		Collection<String> col = new ArrayList<String>();
		for (int i = 0; i < 10000; i++) {
			String taxPhone = getTaxPhone();
			System.out.println(taxPhone);
			if (col.contains(taxPhone)) {
				System.out.println(i);
				throw new RuntimeException("error!");
			} else {
				col.add(taxPhone);
			}
		}
	}

	/**
	 * 根据费用和时间，生成一张发票的打印文本
	 * 
	 * @param money
	 * @param taxDate
	 * @return
	 * @throws Exception
	 */
	private static final String getOneReceipt(double money, Date taxDate)
			throws Exception {
		StringBuffer receiptBuffer = new StringBuffer();
		receiptBuffer.append(getTaxPhone()).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getTaxNo()).append(SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getCred()).append(SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getTaxDate(taxDate)).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getOnBoardTime(money, taxDate)).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getOffTaxTime(taxDate)).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getUnitPrice()).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getDistance(money)).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getWaitTime()).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getTotalPrice(money)).append(
				SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getHyper()).append(SystemProperty.WRAP_LINE_CHAR);
		receiptBuffer.append(getHyper()).append(SystemProperty.WRAP_LINE_CHAR);
		for(int i=0;i<BLANK_LINE_SIZE;i++){
			receiptBuffer.append(getBlankLine()).append(SystemProperty.WRAP_LINE_CHAR);
		}
		return null;
	}

	/**
	 * 获取 空行
	 * @return
	 */
	private static final String getBlankLine(){
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, " ",
				APPEND_CHAR);
	}
	
	/**
	 * 获取----的串
	 * 
	 * @return
	 */
	private static final String getHyper() {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, "----",
				APPEND_CHAR);
	}

	/**
	 * 金额
	 * 
	 * @param money
	 * @return
	 */
	private static final String getTotalPrice(double money) {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, "$"+FormatManager.formatDecimal("0.00", money)+"元",
				APPEND_CHAR);
	}

	/**
	 * 候时
	 * 
	 * @return
	 * @throws Exception
	 */
	private static final String getWaitTime() throws Exception {
		return null;
	}

	/**
	 * 根据费用计算出里程
	 * 
	 * @param money
	 * @return
	 * @throws Exception
	 */
	private static final String getDistance(double money) throws Exception {
		return null;
	}

	/**
	 * 上车时间
	 * 
	 * @param money
	 * @param taxDate
	 * @return
	 * @throws Exception
	 */
	private static final String getOnBoardTime(double money, Date taxDate)
			throws Exception {
		return null;
	}

	/**
	 * 下车时间
	 * 
	 * @param taxDate
	 * @return
	 */
	private static final String getOffTaxTime(Date taxDate) {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				FormatManager.format(DateFormatPattern.HH_MI, taxDate),
				APPEND_CHAR);
	}

	/**
	 * 单价
	 * 
	 * @return
	 */
	private static final String getUnitPrice() {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, UNIT_PRICE,
				APPEND_CHAR);
	}

	/**
	 * 随机获取的士手机号码
	 * 
	 * @return
	 */
	private static final String getTaxPhone() {
		int preRandom = random.nextInt(4 * 999);
		int preIndex = preRandom % 4;
		StringBuffer phone = new StringBuffer(TAX_PHONE_PREFIX[preIndex]);
		for (int i = 0; i < 5; i++) {
			phone.append(random.nextInt(10));
		}
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				phone.toString(), APPEND_CHAR);
	}

	/**
	 * 获取的士车牌
	 * 
	 * @return
	 */
	private static final String getTaxNo() {
		StringBuffer taxNo = new StringBuffer(TAX_NO_PREFIX);
		boolean slid = random.nextBoolean();
		char nextChar = (char) (random.nextInt('Z' - 'A') + 'A');
		if (slid) {
			taxNo.append(nextChar);
			taxNo.append(random.nextInt(10000));
		} else {
			taxNo.append(random.nextInt(1000));
			taxNo.append(nextChar);
			taxNo.append(random.nextInt(10));
		}
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				taxNo.toString(), APPEND_CHAR);
	}

	/**
	 * 获取证件号
	 * 
	 * @return
	 */
	private static final String getCred() {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, "0000",
				APPEND_CHAR);
	}

	/**
	 * 获取日期
	 * 
	 * @return
	 */
	private static final String getTaxDate(Date taxDate) {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				FormatManager.format(DateFormatPattern.YY_MM_DD, taxDate),
				APPEND_CHAR);
	}

}
