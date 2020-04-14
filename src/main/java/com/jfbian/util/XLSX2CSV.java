package com.jfbian.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XLSX2CSV {

    private static Logger logger = Logger.getLogger(XLSX2CSV.class);

    private List<List<String>> rows = null;

    private int rowCount = 0;

    private class SheetToCSV implements SheetContentsHandler {
        private List<String> cells = new ArrayList<>();
        private int currentRow = -1;
        private int currentCol = -1;

        private void outputMissingRows(int number) {
            for (int i = 0; i < number; i++) {
                cells.add("");
            }
        }

        @Override
        public void startRow(int rowNum) {
            // If there were gaps, output the missing rows
            outputMissingRows(rowNum - currentRow - 1);
            // Prepare for this row
            cells.clear();
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
            if (rowNum >= startRowNum && rowNum <= endRowNum) {
                rows.add(new ArrayList<>(cells));
            }
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            // gracefully handle missing CellRef here in a similar way as
            // XSSFCell does
            if (cellReference == null) {
                cellReference = new CellAddress(currentRow, currentCol).formatAsString();
            }

            // Did we miss any cells?
            int thisCol = new CellReference(cellReference).getCol();
            int missedCols = thisCol - currentCol - 1;
            for (int i = 0; i < missedCols; i++) {
                cells.add("");
            }
            currentCol = thisCol;
            cells.add(formattedValue);
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }

    /**
     * 获取excel行数
     */
    private class SheetToCSV2 implements SheetContentsHandler {

        @Override
        public void startRow(int rowNum) {}

        @Override
        public void endRow(int rowNum) {
            rowCount = rowNum;
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {}

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }

    ///////////////////////////////////////

    private final OPCPackage xlsxPackage;

    private final int startRowNum;

    private final int endRowNum;

    /**
     * Creates a new XLSX -> CSV examples
     *
     * @param pkg
     *            The XLSX package to process
     * @param output
     *            The PrintStream to output the CSV to
     * @param minColumns
     *            The minimum number of columns to output, or -1 for no minimum
     */
    public XLSX2CSV(OPCPackage pkg, int startRowNum, int endRowNum) {
        this.xlsxPackage = pkg;
        this.startRowNum = startRowNum;
        this.endRowNum = endRowNum;
    }

    /**
     * Parses and shows the content of one sheet using the specified styles and
     * shared-strings tables.
     *
     * @param styles
     *            The table of styles that may be referenced by cells in the
     *            sheet
     * @param strings
     *            The table of strings that may be referenced by cells in the
     *            sheet
     * @param sheetInputStream
     *            The stream to read the sheet-data from.
     *
     * @exception java.io.IOException
     *                An IO exception from the parser, possibly from a byte
     *                stream or character stream supplied by the application.
     * @throws SAXException
     *             if parsing the XML data fails.
    */
    public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler,
        InputStream sheetInputStream) throws IOException, SAXException {
        DataFormatter formatter = new DataFormatter();
        InputSource sheetSource = new InputSource(sheetInputStream);
        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            logger.error("SAX parser appears to be broken - " + e.getMessage());
        }
    }

    /**
     * Initiates the processing of the XLS workbook file to CSV.
     *
     * @throws IOException
     *             If reading the data from the package fails.
     * @throws SAXException
     *             if parsing the XML data fails.
     */
    public Map<String, List<List<String>>> process(String _sheetName)
        throws IOException, OpenXML4JException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        Map<String, List<List<String>>> result = new HashMap<>();
        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                rows = new ArrayList<>();
                String sheetName = iter.getSheetName();
                if (sheetName.equals(_sheetName)) {
                    processSheet(styles, strings, new SheetToCSV(), stream);
                    result.put(sheetName, rows);
                }
            }
        }

        return result;
    }

    public Map<String, List<List<String>>> process() throws IOException, OpenXML4JException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        Map<String, List<List<String>>> result = new HashMap<>();
        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                rows = new ArrayList<>();
                String sheetName = iter.getSheetName();
                processSheet(styles, strings, new SheetToCSV(), stream);
                result.put(sheetName, rows);
            }
        }

        return result;
    }

    public int getLastRowIndex(String _sheetName) throws IOException, OpenXML4JException, SAXException {
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
        XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator)xssfReader.getSheetsData();
        while (iter.hasNext()) {
            try (InputStream stream = iter.next()) {
                rows = new ArrayList<>();
                String sheetName = iter.getSheetName();
                if (sheetName.equals(_sheetName)) {
                    processSheet(styles, strings, new SheetToCSV2(), stream);
                    break;
                }
            }
        }

        return rowCount;
    }

    public static Map<String, List<List<String>>> read(String path, int startRowNum, int endRowNum, String sheetName) {
        logger.info("解析文件:" + path);
        File xlsxFile = new File(path);
        // The package open is instantaneous, as it should be.
        try (OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ)) {
            XLSX2CSV xlsx2csv = new XLSX2CSV(p, startRowNum, endRowNum);

            return xlsx2csv.process(sheetName);
        } catch (Exception e) {
            logger.error("SAX read - " + e.getMessage());
        }

        return null;
    }

    public static Map<String, List<List<String>>> read(String path, int startRowNum, int endRowNum) {
        // logger.info("解析文件:" + path);
        File xlsxFile = new File(path);

        // The package open is instantaneous, as it should be.
        try (OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ)) {
            XLSX2CSV xlsx2csv = new XLSX2CSV(p, startRowNum, endRowNum);

            return xlsx2csv.process();
        } catch (Exception e) {
            logger.error("SAX read - " + e.getMessage());
        }

        return null;
    }

    /**
     * 获取excel中最后一行数据的索引
     *
     * @param path
     * @param sheetName
     * @return
     */
    public static int getLastRowIndex(String path, String sheetName) {
        File xlsxFile = new File(path);

        // The package open is instantaneous, as it should be.
        try (OPCPackage p = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ)) {
            XLSX2CSV xlsx2csv = new XLSX2CSV(p, 0, 0);

            return xlsx2csv.getLastRowIndex(sheetName);
        } catch (Exception e) {
            logger.error("SAX read - " + e.getMessage());
        }

        return 0;
    }
}
