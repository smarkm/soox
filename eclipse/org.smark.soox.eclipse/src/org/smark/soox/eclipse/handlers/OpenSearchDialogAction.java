package org.smark.soox.eclipse.handlers;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.smark.soox.eclipse.Engines;
import org.smark.soox.eclipse.SooxInpurtDialog;

public class OpenSearchDialogAction implements IWorkbenchWindowActionDelegate {
	static Set<String> searchHistory = new HashSet<>();
	@Override
	public void run(IAction action) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
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
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}
	public static void main(String[]args) throws IOException{
		Runtime.getRuntime().exec("cmd /c start https://www.google.com/#q=eclipse%20s");
	}

}
