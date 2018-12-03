package com.whime.tool;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
 
/**
 *来自https://blog.csdn.net/cy96151/article/details/52320024
 * @author 96151
 */
public class outExcel {
 
    public outExcel(String[] title, String[][] context) {
        File f = FileChooser();
        if (f != null) {
            try {
                WritableWorkbook book = Workbook.createWorkbook(f);
                WritableSheet sheet = book.createSheet("School", 0);
                //创建字体，7个参数分别是字体名称，字号，是否粗体，是否斜体，下划线，颜色，上下标
                WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK, ScriptStyle.NORMAL_SCRIPT);
                WritableCellFormat format = new WritableCellFormat(font);
                format.setAlignment(Alignment.CENTRE); //对齐方
                WritableCellFormat format2 = new WritableCellFormat();
                format2.setAlignment(Alignment.CENTRE);
                for (int i = 0; i < title.length; i++) //title
                {
                    sheet.setColumnView(i, 15);
                    sheet.addCell(new Label(i, 0, title[i], format));
                }
                for (int i = 0; i < context.length; i++) //context
                {
                    for (int j = 0; j < context[i].length; j++) {
                        sheet.addCell(new Label(j, i + 1, context[i][j], format2));
                    }
                }
                book.write();
                //关闭文件
                book.close();
                JOptionPane.showMessageDialog(null, "导出成功");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "导出失败");
            }
        }
    }
 
    private File FileChooser() {
        JFileChooserDemo fc = new JFileChooserDemo("C:");
        //是否可多选  
        fc.setMultiSelectionEnabled(false);
        //选择模式，可选择文件和文件夹  
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
        //设置是否显示隐藏文件  
        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(false);
        //设置文件筛选器  
        // fc.setFileFilter(new MyFilter("java"));  
        fc.setFileFilter(new FileNameExtensionFilter("Excel文件(*.xls)", "xls"));
        int returnValue = fc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String ftype = file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4);
            if (!ftype.equals(".xls")) {
                //如果用户没有填写扩展名，自动添加扩展名.xls
                file = new File(file.getAbsolutePath() + ".xls");
            }
            return file;
        }
        return null;
    }
}
 
class JFileChooserDemo extends javax.swing.JFileChooser {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -5956762658103773311L;

	public JFileChooserDemo(String currentDirectoryPath) {
        super(currentDirectoryPath);
    }
 
    @Override
    public void approveSelection() {
        File file = new File(getSelectedFile() + ".xls");
        if (file.exists()) {
            JOptionPane.showMessageDialog(getParent(), "文件名已被占用");
            return;
        } else {
            super.approveSelection();
        }
    }
}

