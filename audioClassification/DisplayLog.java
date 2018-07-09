package audioClassification;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayLog {

	public static String logStatement=null;
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	public static String getLogStatement() {
		return logStatement;
	}

	public static void setLogStatement(String logStatement) {
		DisplayLog.logStatement = DisplayLog.logStatement+"\n"+sdf.format(new Date().getTime())+":->"+logStatement;
	}

}
