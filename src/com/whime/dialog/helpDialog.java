package com.whime.dialog;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
/*帮助选项按钮，弹出一个窗口，提供链接复制功能
 * @author whime
 * 2018/11/23
 */
import java.awt.datatransfer.StringSelection;

import javax.swing.JOptionPane;

public class helpDialog {
	public helpDialog() {
		String msg="for more help about the program please visit:\n"
				+ "https://github.com/whime/ChineseSchoolInfo_database\n"
				+ "or contact with 511770592@qq.com";
		String url="https://github.com/whime/ChineseSchoolInfo_database";
		Object[] options=new Object[] {"copy url","close"};
		StringSelection selection=new StringSelection(url);
		//获取剪贴板
		Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
        int optionSelected = JOptionPane.showOptionDialog(
                null,
                msg,
                "HELP",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,    // 如果传null, 则按钮为 optionType 类型所表示的按钮（也就是确认对话框）
                options[0]
        );
        if(optionSelected==0)
        {
        	clipboard.setContents(selection,null);
        }
        else
        {
//        	啥也不做。。。
        }
	}
}
