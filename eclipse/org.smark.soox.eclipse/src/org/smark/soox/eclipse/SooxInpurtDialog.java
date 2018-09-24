package org.smark.soox.eclipse;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

public class SooxInpurtDialog extends InputDialog {
	static Set<String> searchHistory = new HashSet<>();

	public SooxInpurtDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
			IInputValidator validator) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
	}

	public static Set<String> getSearchHistory() {
		return searchHistory;
	}
	

}
