package org.smark.soox.eclipse;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EditEnginDialog extends TitleAreaDialog {

	SearchEngine engine;
	private Text txtEngine;
	private Text txtSuffix;
	private Button btnOK;

	/**
	 * @wbp.parser.constructor
	 */
	public EditEnginDialog(Shell parentShell) {
		super(parentShell);
	}

	public EditEnginDialog(Shell parentShell, SearchEngine engine) {
		super(parentShell);
		this.engine = engine;
	}
	
	public SearchEngine getEngine() {
		return engine;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Edit SearchEngine");
		boolean ok = false;
		Composite container = (Composite) super.createDialogArea(parent);
		
		Group grpSearchEngin = new Group(container, SWT.NONE);
		grpSearchEngin.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpSearchEngin.setText("Search Engine");
		grpSearchEngin.setLayout(new GridLayout(1, false));
		
		txtEngine = new Text(grpSearchEngin, SWT.BORDER);
		txtEngine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtEngine.setBounds(0, 0, 73, 26);
		txtEngine.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				validate();
			}
		});
		
		Group grpSuffix = new Group(container, SWT.NONE);
		grpSuffix.setText("Suffix");
		grpSuffix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpSuffix.setLayout(new GridLayout(1, false));
		
		txtSuffix = new Text(grpSuffix, SWT.BORDER);
		txtSuffix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtSuffix.setBounds(0, 0, 73, 26);
		txtSuffix.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		
		if (engine!=null) {
			txtEngine.setText(engine.getEngine());
			txtSuffix.setText(engine.getSuffix());
		}

		return container;
	}

	private void validate() {
		if (checkSearchEngine()&&checkValidSuffix()) {
			engine = new SearchEngine(txtEngine.getText().trim(), txtSuffix.getText().trim());
			btnOK.setEnabled(true);
		}else {
			btnOK.setEnabled(false);
		}
	}
	private boolean checkSearchEngine() {
		boolean valid = false;
		String engine = txtEngine.getText().trim();
		if (engine.length()>0) {
			valid = true;
		}
		return valid;
	}

	private boolean checkValidSuffix() {
		boolean valid = false;
		String suff = txtSuffix.getText().trim();
		if (suff.length() != 1) {
			setMessage("Suffix is 1 char current is "+suff.length());
		}else {
			setMessage("");
			valid = true;
			String engine = Engines.engines.get(suff);
			if (engine!=null) {
				setMessage("'"+suff+"' have exsist");
			}
		}
		return valid;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnOK =	createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}
}
