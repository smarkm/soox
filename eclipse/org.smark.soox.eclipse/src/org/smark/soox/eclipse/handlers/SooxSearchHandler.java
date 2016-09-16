package org.smark.soox.eclipse.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.smark.soox.eclipse.Engines;
import org.smark.soox.eclipse.SooxInpurtDialog;

/**
 */
public class SooxSearchHandler extends AbstractHandler {
	static Set<String> searchHistory = new HashSet<>();
	public SooxSearchHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String tipText = "hello g";
		if (searchHistory.size()>0) {
			tipText = (String)searchHistory.toArray()[0];
		}
		SooxInpurtDialog dialog = new SooxInpurtDialog(window.getShell(), "Soox", "Seach easy use soox", tipText, null);
		int rs = dialog.open();
		if (rs==InputDialog.OK) {
			String value = dialog.getValue();
			String[] keys = value.trim().split(" ");
			StringBuilder validKeys = new StringBuilder("");
			String engin = "";
			if (keys.length>0) {
				engin = keys[keys.length-1];
				if (Engines.engines.containsKey(engin)) {
					validKeys.append(value.substring(0, value.length()-1));
				}else{
					validKeys.append(value);
				}
			}
			String command = "cmd /c start "+Engines.getEngine(engin)+validKeys.toString().trim().replaceAll(" ", "%20");
			System.out.println(command);
			try {
				Runtime.getRuntime().exec(command);
				searchHistory.add(validKeys.toString().trim());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[]args) throws IOException{
		Runtime.getRuntime().exec("cmd /c start https://www.google.com/#q=eclipse%20s");
	}
	
	
}
