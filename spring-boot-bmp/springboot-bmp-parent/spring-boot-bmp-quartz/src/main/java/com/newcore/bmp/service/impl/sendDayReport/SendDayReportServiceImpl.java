package com.newcore.bmp.service.impl.sendDayReport;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newcore.bmp.dao.api.sendDayReport.SendDayReportDao;
import com.newcore.bmp.models.email.Email;
import com.newcore.bmp.models.email.EmailSubscription;
import com.newcore.bmp.models.errorJudge.ErrorTrail;
import com.newcore.bmp.service.api.email.EmailService;
import com.newcore.bmp.service.api.sendDayReport.SendDayReportService;

import lombok.extern.slf4j.Slf4j;


@Service("sendDayReportService")
@Slf4j
public class SendDayReportServiceImpl implements SendDayReportService {
	
	@Autowired
	SendDayReportDao sendDayReportDao;

	//使用email service
	@Autowired
	EmailService emailService;
			
	//邮件附件存放位置
	@Value("${attachFileLocation}")
	String attachFileLocation;
	
	@Override
	public void sendDayReportEmail(String system) throws Exception {

		String systemEnglishName = sendDayReportDao.SelectSystemEnglishName(system);
		System.out.println("systemEnglishName = " +systemEnglishName);
		String errReasonDetail = "批作业当日未重新启动";	
		
		// 获得当前年月日
		String today = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());

		// 邮件主题
		String subject = systemEnglishName + "批作业监控早报" + today;

		// 邮件正文
		String rizhong_content = this.calcEmailContent(system, "日终作业", errReasonDetail, systemEnglishName);
		String rijian_content = this.calcEmailContent(system, "日间作业", errReasonDetail, systemEnglishName);

		String content = today + ",<br>" + "<blockquote> " + rizhong_content + "<br>" + "</blockquote>"
				+ "<blockquote> " + rijian_content + "</blockquote>";
	
		//附件名称
		String attachFile = attachFileLocation + systemEnglishName+"_Batch_Details" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";
		
		// 生成邮件附件的excel文件,写入文件系统
		// 找出要写的记录
		List<ErrorTrail> etList = sendDayReportDao.SelectErrorTrail(system, errReasonDetail);

		// 将excel写入文件系统
		this.writeExcelToFS(etList, attachFile);

		//获取收件人订阅表
		List<EmailSubscription> emailSubscription = emailService.SelectEmailSubscriptionOfSystem(system);			
		
		//调用emailService发送邮件
		emailService.sendEmail(emailSubscription, subject, content, attachFile);

	}

	//生成邮件内容
	private String calcEmailContent(String system, String batchTypeDetail, String errReasonDetail,
			String systemEnglishName) {
		String content = "";

		// 计算当天每个省应运行日终批作业数量
		int everyprovinceBatchNums = sendDayReportDao.calcBatchNums(system, batchTypeDetail);
		log.info("everyprovinceBatchNums=" + everyprovinceBatchNums);

		// 计算共有多少个省份
		int provinceNums = sendDayReportDao.calcprovinceNums();
		log.info("provinceNums=" + provinceNums);

		// 计算当天应运行日终批作业总数
		int batchSums = everyprovinceBatchNums * provinceNums;
		log.info("batchSums=" + batchSums);

		// 计算当天未运行日终批作业个数
		int unRunBatchNums = sendDayReportDao.calcUnRunBatchNums(system, batchTypeDetail, errReasonDetail);
		log.info("unRunBatchNums=" + unRunBatchNums);

		// 计算当天实际运行的日终批作业个数
		int runedBatchNums = batchSums - unRunBatchNums;
		log.info("runedBatchNums=" + runedBatchNums);

		// 计算当天未运行的日终批作业涉及的分公司个数
		int RelativeProvinceNums = sendDayReportDao.calcRelativeProvinceNums(system, batchTypeDetail, errReasonDetail);
		log.info("RelativeProvinceNums=" + RelativeProvinceNums);

		// 获得当前年月日
		String today = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
		log.info("today=" + today);

		// 拼出邮件正文
		content = systemEnglishName + "全国" + "应运行" + batchTypeDetail + "批作业" + batchSums + "个，" + "实际运行"
				+ runedBatchNums + "个，" + "未运行" + unRunBatchNums + "个，" + "未运行批作业涉及分公司" + RelativeProvinceNums + "家。";
		return content;
	}

	// 生成Excel给outStream
	public void writeExcelToOutputStream(OutputStream outputStream, List<ErrorTrail> etList) throws SQLException {

		// 初始一个workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建单个sheet
		HSSFSheet sheet = workbook.createSheet("未运行批作业详情");

		// 冻结首行
		sheet.createFreezePane(0, 1, 0, 1);

		// 单元格文字居中设置
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		// style.setWrapText(true);//自动换行

		// 设置总列数
		int maxColumnNum = 11;

		// 创建多行
		// 创建第一行，设置列名
		HSSFRow row0 = sheet.createRow(0);

		for (int cellIndex = 0; cellIndex < maxColumnNum; cellIndex++) {

			// 设置列宽自适应
			sheet.autoSizeColumn(cellIndex);

			HSSFCell cell = row0.createCell(cellIndex);

			// 文字居中
			cell.setCellStyle(style);

			switch (cellIndex) {
			case 0:
				cell.setCellValue("序号");
				break;
			case 1:
				cell.setCellValue("省份名称");
				break;
			case 2:
				cell.setCellValue("省份编号");
				break;
			case 3:
				cell.setCellValue("批作业名称");
				break;
			case 4:
				cell.setCellValue("批作业编号");
				break;
			case 5:
				cell.setCellValue("类型");
				break;
			case 6:
				cell.setCellValue("异常原因");
				break;
			case 7:
				cell.setCellValue("所属系统");
				break;
			case 8:
				cell.setCellValue("异常首次判定时间");
				break;
			case 9:
				cell.setCellValue("异常最新判定时间");
				break;
			case 10:
				cell.setCellValue("应当启动日期");
				break;
			default:
				break;
			}
		}
		// 创建剩余行
		for (int rowIndex = 1; rowIndex <= etList.size(); rowIndex++) {
			HSSFRow row = sheet.createRow(rowIndex);
			ErrorTrail et = etList.get(rowIndex - 1);
			// 创建多列
			for (int cellIndex = 0; cellIndex < maxColumnNum; cellIndex++) {
				HSSFCell cell = row.createCell(cellIndex);

				// 文字居中
				cell.setCellStyle(style);

				switch (cellIndex) {
				case 0:
					// 序号
					cell.setCellValue(rowIndex);
					break;
				case 1:
					// 省份名称
					cell.setCellValue(et.getProvBranchName());
					break;

				case 2:
					// 省份编号
					cell.setCellValue(et.getProvBranchCode());
					break;
				case 3:
					// 批作业名称
					cell.setCellValue(et.getBatchName());
					break;
				case 4:
					// 批作业编号
					cell.setCellValue(et.getBatchTxNo());
					break;
				case 5:
					// 类型
					cell.setCellValue(et.getBatchTypeDetail());
					break;
				case 6:
					// 异常原因
					cell.setCellValue(et.getErrReasonDetail());
					break;
				case 7:
					// 所属系统
					cell.setCellValue(et.getSystem());
					break;
				case 8:
					// 异常首次判定时间
					cell.setCellValue(et.getErrFirstJudgeTime());
					break;
				case 9:
					// 异常首次判定时间
					cell.setCellValue(et.getErrRecentJudgeTime());
					break;
				case 10:
					// 应当启动日期
					cell.setCellValue(et.getStartExecTime());
					break;
				default:
					break;
				}
			}
		}

		// 设置自适应列宽
		columnLengthSelfAdapt(sheet, maxColumnNum);

		try {
			workbook.write(outputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 设置自适应列宽
	private void columnLengthSelfAdapt(HSSFSheet sheet, int maxColumnNum) {
		for (int columnNum = 0; columnNum <= maxColumnNum; columnNum++) {
			int columnWidth = sheet.getColumnWidth(columnNum) / 256;
			for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
				HSSFRow currentRow;
				// 当前行未被使用过
				if (sheet.getRow(rowNum) == null) {
					currentRow = sheet.createRow(rowNum);
				} else {
					currentRow = sheet.getRow(rowNum);
				}

				if (currentRow.getCell(columnNum) != null) {
					HSSFCell currentCell = currentRow.getCell(columnNum);
					if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						int length = currentCell.getStringCellValue().getBytes().length;
						if (columnWidth < length) {
							columnWidth = length;
						}
					}

				}
			}
			sheet.setColumnWidth(columnNum, columnWidth * 256);
		}
	}

	// 将excel写入文件系统
	private void writeExcelToFS(List<ErrorTrail> etList, String attachFile) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(attachFile));
			this.writeExcelToOutputStream(out, etList);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
