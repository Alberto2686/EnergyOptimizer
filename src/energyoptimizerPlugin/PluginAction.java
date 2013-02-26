package energyOptimizerPlugin;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import energyOptimizer.EnergyOptimizer;


public class PluginAction implements IObjectActionDelegate {

	private Shell shell;
	private ISelection currentSelection;
	private IFile file;
	
	/**
	 * Constructor for Action1.
	 */
	public PluginAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		IStructuredSelection iss = (IStructuredSelection)currentSelection;
		file = (IFile)iss.getFirstElement();
		EnergyOptimizer.start(file.getLocation().toString());
		MessageDialog.openInformation(
			shell,
			"EnergyOptimizer",
			"Analysis completed - new files created:\n-bestEP.uml: UML Deployment diagram illustrating the best system based on Energy Point analysis\n-bestW.uml: UML Deployment diagram illustrating the best system based on actual Watt consuption analysis\n-log.txt: log of analysis process\n-results.txt: a summary of best systems and characteristics.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.currentSelection = selection;
	}

}
