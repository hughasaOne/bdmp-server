package com.rhy.bdmp.system.modules.assets.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class DownloadExcelBoxHandler implements SheetWriteHandler {
    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet sheet = workbook.getSheetAt(0);
        Row row2 = sheet.createRow(0);
        Cell cell1 = row2.createCell(0);
        //强制换行,设置之后需要开启setWrapText=true;否则需要双击才生效
        String cnt = "\r\n";
        HSSFRichTextString wrap = new HSSFRichTextString(cnt);

        cell1.setCellValue("Tips：" +wrap+
                "  1、*为必填项，必填字段为空时设备不予以导入；" +wrap+
                "  2、导入设备时以设备ip为标识，仅当平台存在相同标识的设备时才能导入成功；" +wrap);
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeight((short)240);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cell1.setCellStyle(cellStyle);
        sheet.addMergedRegionUnsafe(new CellRangeAddress(0, 3, 0, 4));
    }
}
