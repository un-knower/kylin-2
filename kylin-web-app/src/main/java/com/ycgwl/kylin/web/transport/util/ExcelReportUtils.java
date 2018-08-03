package com.ycgwl.kylin.web.transport.util;

import com.ycgwl.kylin.web.transport.entity.ExcelForm;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Description: excel导出工具.
 * @author <a href="mailto:109668@ycgwl.com">lihuixia</a>
 * @date 2017年8月15日 上午10:25:02
 * @version 1.3
 *
 */
public class ExcelReportUtils {

	/**
	 * 导出execle
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
	 * @param excelForm
	 * @param response
	 * @throws Exception
	 */
	public static void exportExcel(ExcelForm excelForm, HttpServletResponse response) throws Exception {
		SimpleDateFormat sbf = new SimpleDateFormat("yyMMddHHmmss");
		response.reset();
		response.setContentType("application/x-msdownload;charset=UTF-8");
		String fileName = URLEncoder.encode(excelForm.getFileName(), "UTF-8") + "(" + sbf.format(new Date())+ ")";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		ServletOutputStream out = response.getOutputStream();
		// 创建工作文档对象
		Workbook book = new SXSSFWorkbook();
		// 创建sheet对象
		Sheet wsheet = (Sheet) book.createSheet(excelForm.getSheetName());
		detailContent(excelForm, wsheet, book);
		book.write(out);
		out.close();
		book.close();
	}

	/**
	 *  根据传入的数据生成excel.
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
	 * @param excelForm
	 * @param wsheet
	 * @param book
	 * @throws Exception
	 */
	public static void detailContent(ExcelForm excelForm, Sheet wsheet, Workbook book) throws Exception {
		int titleRow = 0;
		if (excelForm.getTotal() != null) {
			titleRow = titleRow + 2;
			createTitle(excelForm, wsheet, book);
		}

		if (excelForm.getImplParam() != null) {
			titleRow = titleRow + 1;
			createSubTitle(excelForm, wsheet, book);
		}
		
		Row row = (Row) wsheet.createRow(0 + titleRow);

		// 循环写入列标题
		String showName[] = excelForm.getShowColumnName();
		for (int i = 0; i < showName.length; i++) {
			row.createCell(i).setCellValue(showName[i]);
		}
		// 设置列宽
		Integer columnWidth[] = excelForm.getShowColumnWidth();
		for (int i = 0; i < columnWidth.length; i++) {
			wsheet.setColumnWidth(i, columnWidth[i] * 256);
		}
		List<String[]> list = excelForm.getList();
		for (int i = 0; list!=null && i < list.size(); i++) {
			String rowDetail[] = list.get(i);
			row = (Row) wsheet.createRow(i + titleRow + 1);
			for (int j = 0; j < rowDetail.length; j++) {// 每行数据是一个对象
				row.createCell(j).setCellValue(rowDetail[j]);
			}
		}
	}

	/**
	 * 增加第一行大标题
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
	 * @param excelForm
	 * @param wsheet
	 * @param book
	 */
	@SuppressWarnings("deprecation")
	private static void createTitle(ExcelForm excelForm, Sheet wsheet, Workbook book) {
		Row title = (Row) wsheet.createRow(0);

		wsheet.addMergedRegion(new CellRangeAddress(0, 1, 0, excelForm.getShowColumnName().length - 1));

		Cell titleCell = title.createCell(0);
		titleCell.setCellValue(excelForm.getTotal());

		CellStyle createCellStyle = book.createCellStyle();
		createCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Font createFont = book.createFont();
		createFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		createFont.setFontName("黑体");
		createFont.setFontHeightInPoints((short) 20);
		createCellStyle.setFont(createFont);
		titleCell.setCellStyle(createCellStyle);
	}

	/**
	 * 增加第二行大标题
	 * <p>
	 * @developer Create by <a href="mailto:86756@ycgwl.com">yanxf</a> at 2017年12月5日
	 * @param excelForm
	 * @param wsheet
	 * @param book
	 */
	@SuppressWarnings("deprecation")
	private static void createSubTitle(ExcelForm excelForm, Sheet wsheet, Workbook book) {
		int firstRow = 0;
		if (excelForm.getTotal() != null) {
			firstRow = 2;
		}
		Row title = (Row) wsheet.createRow(firstRow);
		wsheet.addMergedRegion(new CellRangeAddress(firstRow, firstRow, 0, excelForm.getShowColumnName().length - 1));

		Cell titleCell = title.createCell(0);
		titleCell.setCellValue(excelForm.getImplParam());

		CellStyle createCellStyle = book.createCellStyle();
		createCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Font createFont = book.createFont();
		createFont.setFontName("黑体");
		createFont.setFontHeightInPoints((short) 16);
		createCellStyle.setFont(createFont);
		titleCell.setCellStyle(createCellStyle);
	}

}
