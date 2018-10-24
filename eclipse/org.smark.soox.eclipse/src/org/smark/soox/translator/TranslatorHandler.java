package org.smark.soox.translator;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.smark.soox.eclipse.Engines;
import org.smark.soox.eclipse.SooxInpurtDialog;
import org.smark.soox.eclipse.StringUtil;

/**
 */
public class TranslatorHandler extends AbstractHandler {
	static Map<String, String> engineMap = new HashMap<>();
	static final String defaultKey = "default";
	String defaultEngine = "";
	
	static {
		engineMap.put("g", "https://translate.google.com/#auto/en/");
		engineMap.put("d", "https://fanyi.baidu.com/#zh/en/");
		engineMap.put(defaultKey, engineMap.get("g"));
	}
	public TranslatorHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String tipText = "hello g";
		SooxInpurtDialog dialog = new SooxInpurtDialog(window.getShell(), "Multi Translator", "", tipText, null);
		int rs = dialog.open();
		if (rs==InputDialog.OK) {
			String value = dialog.getValue();
			String[] keys = value.trim().split(" ");
			if (keys.length>0) {
				String eKey = keys[keys.length-1];
				String world = StringUtil.joinFirstN(keys, keys.length-1, " ");
				String command = "cmd /c start "+getEngine(eKey)+world.trim().replaceAll(" ", "%20");
				try {
					Runtime.getRuntime().exec(command);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return null;
	}
	public String getEngine(String key) {
		String engine = engineMap.get(key);
		if (engine==null) {
			engineMap.get(defaultKey);
		}
		return engine;
	}
	
	public static void main(String[]args) throws IOException{
		Runtime.getRuntime().exec("cmd /c start https://www.google.com/#q=eclipse%20s");
	}
	
	
}
