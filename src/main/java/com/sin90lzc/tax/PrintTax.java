package com.sin90lzc.tax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//import com.sin90lzc.tax.constant.Alignment;
//import com.sin90lzc.tax.util.StringUtil;
import com.sin90lzc.util.FormatManager;
import com.sin90lzc.util.enu.Alignment;
import com.sin90lzc.util.enums.DateFormatPattern;
import com.sin90lzc.util.str.StringUtil;
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

	private static final int BLANK_LINE_SIZE = 14;

	private static int onBoardHour = 0;
	private static int onBoardMin = 0;
	private static boolean swt=false;

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			throw new Exception("please at least provide excel file path!");
		}
		String filePath = null;
		int sheetIndex = 0;
		filePath = args[0];
		if (!new File(filePath).exists()) {
			throw new Exception("not exists file " + filePath);
		}
		if (args.length > 1) {
			sheetIndex = Integer.parseInt(args[1]);
		}
		InputStream is = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		boolean isMore = false;
		try {
			is = new FileInputStream(filePath);
			Workbook workbook = WorkbookFactory.create(is);
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			Iterator<Row> rowIt = sheet.iterator();
			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				Cell c0 = row.getCell(0);
				if (c0.getCellType() != Cell.CELL_TYPE_NUMERIC) {
					throw new Exception("第一列不是日期类型！");
				}
				Cell c1 = row.getCell(1);
				if (c1.getCellType() != Cell.CELL_TYPE_NUMERIC) {
					throw new Exception("第二列不是数字类型！");
				}
				Date taxDate = c0.getDateCellValue();
				double money = c1.getNumericCellValue();
				String receipt = getOneReceipt(money, taxDate);
				System.out.println(receipt);
//				if (!isMore) {
//					System.out.println("continue(Y/N/MORE)?");
//				} else {
//					continue;
//				}
//				String oper=reader.readLine();
//				if ("Y".equals(oper)) {
//					continue;
//				} else if ("MORE".equals(oper)) {
//					isMore = true;
//					continue;
//				} else {
//					break;
//				}
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * 获取一张的士发票的打印文本
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
		receiptBuffer.append(getOffTaxTime(money,taxDate)).append(
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
		
		for (int i = 0; i < BLANK_LINE_SIZE; i++) {
			receiptBuffer.append(getBlankLine()).append(
					SystemProperty.WRAP_LINE_CHAR);
		}
		swt=!swt;
		if(swt){
			receiptBuffer.append(getBlankLine()).append(
					SystemProperty.WRAP_LINE_CHAR);
		}
		return receiptBuffer.toString();
	}

	/**
	 * 获取空行
	 * 
	 * @return
	 */
	private static final String getBlankLine() {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, " ",
				APPEND_CHAR);
	}

	/**
	 * 获取----
	 * 
	 * @return
	 */
	private static final String getHyper() {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, "----",
				APPEND_CHAR);
	}

	/**
	 * 获取金额
	 * 
	 * @param money
	 * @return
	 */
	private static final String getTotalPrice(double money) {
		return StringUtil
				.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
						"$" + FormatManager.formatDecimal("0.00", money) + "元",
						APPEND_CHAR);
	}

	/**
	 * 候时
	 * 
	 * @return
	 * @throws Exception
	 */
	private static final String getWaitTime() throws Exception {
		int waitSecond = random.nextInt(60);
		String waitTime = "00:00:"
				+ FormatManager.formatDecimal("00", waitSecond);
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, waitTime,
				APPEND_CHAR);
	}

	/**
	 * 里程
	 * 
	 * @param money
	 * @return
	 * @throws Exception
	 */
	private static final String getDistance(double money) throws Exception {
		double distance = 2.5 + (money - 10) / 2.6;
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				FormatManager.formatDecimal("0.0", distance) + "km",
				APPEND_CHAR);
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(taxDate);
		String onBoardTime = null;
		if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0
				&& cal.get(Calendar.SECOND) == 0) {
			if (random.nextBoolean()) {
				onBoardHour = 21;
			} else {
				onBoardHour = 22;
			}
			onBoardMin = random.nextInt(60);
			onBoardTime = FormatManager.formatDecimal("00", onBoardHour) + ":"
					+ FormatManager.formatDecimal("00", onBoardMin);
		} else {
			double distance = 2.5 + (money - 10) / 2.6;
			double speed = (50 - random.nextInt(10)) / 60.0;
			double min = distance / speed;
			cal.add(Calendar.MINUTE, -(int) min);
			onBoardTime = FormatManager.format(DateFormatPattern.HH_MI, cal);
		}
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, onBoardTime,
				APPEND_CHAR);
	}

	/**
	 * 下车时间
	 * 
	 * @param taxDate
	 * @return
	 */
	private static final String getOffTaxTime(double money,Date taxDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(taxDate);
		//String offTaxTime = null;
		if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0
				&& cal.get(Calendar.SECOND) == 0) {
			cal.set(Calendar.HOUR_OF_DAY, onBoardHour);
			cal.set(Calendar.MINUTE, onBoardMin);
			double distance = 2.5 + (money - 10) / 2.6;
			double speed = (50 - random.nextInt(10)) / 60.0;
			double min = distance / speed;
			cal.add(Calendar.MINUTE, (int) min);
		}
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				FormatManager.format(DateFormatPattern.HH_MI, cal),
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
	 * 电话
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
	 * 的士号码
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
	 * 证号
	 * 
	 * @return
	 */
	private static final String getCred() {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN, "111111",
				APPEND_CHAR);
	}

	/**
	 * 日期
	 * 
	 * @return
	 */
	private static final String getTaxDate(Date taxDate) {
		return StringUtil.alignTo(Alignment.RIGHT_ALIGN, MAX_LEN,
				FormatManager.format(DateFormatPattern.YY_MM_DD, taxDate),
				APPEND_CHAR);
	}

}
