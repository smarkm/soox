package org.smark.soox.eclipse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SooxPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	private Table table;
	private TableViewer tableViewer;

	/**
	 * @wbp.parser.constructor
	 */
	public SooxPreferencePage() {
		setMessage("Search Engine Config");
		
	}

	public SooxPreferencePage(String title) {
		super(title);
	}

	public SooxPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		composite.setLayout(new GridLayout(2, false));
		
		tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Engine");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setText("Suffix");
		tblclmnNewColumn_1.setWidth(100);
		
		Composite actionsComp = new Composite(composite, SWT.NONE);
		actionsComp.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		actionsComp.setLayout(new GridLayout(1, false));
		
		Button btnAdd = new Button(actionsComp, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				EditEnginDialog dialog = new EditEnginDialog(getShell());
				if (dialog.open()==IDialogConstants.OK_ID) {
					SearchEngine newEngine = dialog.getEngine();
					if (newEngine!=null) {
						Engines.addEngine(newEngine);
						tableViewer.setInput(Engines.engines);
					}
				};
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		
		Button btnEdit = new Button(actionsComp, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnEdit.setText("Edit");
		btnEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				SearchEngine engine = (SearchEngine) selection.getFirstElement();
				EditEnginDialog dialog = new EditEnginDialog(getShell(),engine);
				if (dialog.open()== IDialogConstants.OK_ID) {
					SearchEngine newEngine = dialog.getEngine();
					if (newEngine != null) {
						Engines.updateEngine(newEngine);
						tableViewer.setInput(Engines.engines);
					}
				};
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		
		Button btnDelete = new Button(actionsComp, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDelete.setText("Delete");
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				SearchEngine engine = (SearchEngine) selection.getFirstElement();
				if (engine!=null) {
					Engines.deleteEngine(engine);
					tableViewer.setInput(Engines.engines);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		tableViewer.setLabelProvider(new TableLableProvider());
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setInput(Engines.engines);
		return null;
	}
	
	
	
	@Override
	public void applyData(Object data) {
		System.out.println(Engines.engines.size());
		super.applyData(data);
	}



	public class TableLableProvider extends  LabelProvider implements ITableLabelProvider{

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			SearchEngine engine = (SearchEngine) element;
			switch (columnIndex) {
			case 0:
				return engine.getEngine();
			case 1:
				return engine.getSuffix();
			}
			return "";
		}

	}
	public class ContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
			
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			
		}

		@Override
		public Object[] getElements(Object inputElement) {
			Map<String, String> ngs = (Map<String, String>) inputElement;
			List<SearchEngine> rs = new ArrayList<>();
			for (String key : ngs.keySet()) {
				rs.add(new SearchEngine(ngs.get(key),key ));
			}
			return rs.toArray();
		}
	}
	

}
