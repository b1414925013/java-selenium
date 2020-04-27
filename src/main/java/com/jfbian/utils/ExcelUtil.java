/**
 * @Title:  ExcelUtil.java
 * @Package com.jfbian.util
 * @Description:    描述
 * @author: bianjianfneg
 * @date:   2020年3月24日 上午12:17:54
 * @version V1.0
 */
package com.jfbian.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @ClassName:  ExcelUtil
 * @Description:操作excel的工具类
 * @author: bianjianfeng
 * @date:   2020年3月24日 上午12:17:54
 */
@SuppressWarnings("all")
public class ExcelUtil {
    // 获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        // 判断是否为null或空串
        if (cell == null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_STRING: // 字符串类型
                cellValue = cell.getStringCellValue().trim();
                cellValue = StringUtils.isEmpty(cellValue) ? "" : cellValue;
                break;
            case Cell.CELL_TYPE_BOOLEAN: // 布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC: // 数值类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) { // 判断日期类型
                    if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                        cellValue = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        cellValue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                    }
                    break;
                }
                if (cell.getCellStyle().getDataFormatString().contains("%")) {
                    cellValue =
                        String.valueOf(new DecimalFormat(cell.getCellStyle().getDataFormatString().replace("%", ""))
                            .format(Double.valueOf(cell.getNumericCellValue() * 100))).concat("%");
                } else if (cell.getCellStyle().getDataFormatString().contains("General")) {
                    cell.setCellType(1);
                    cellValue = cell.getStringCellValue();
                } else { // 否
                         // cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                    cellValue = new DecimalFormat(
                        cell.getCellStyle().getDataFormatString().replaceAll("#", "").replaceAll(",", ""))
                            .format(cell.getNumericCellValue());
                }
                break;
            default: // 其它类型，取空串吧
                cellValue = "";
                break;
        }
        return cellValue;
    }
}
