/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smark.soox.netbeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "Edit",
        id = "org.smark.soox.netbeans.SooxAction"
)
@ActionRegistration(
        iconBase = "org/smark/soox/netbeans/soox.png",
        displayName = "#CTL_SooxAction"
)
@ActionReferences({
    @ActionReference(path = "Menu/Edit", position = 0)
    ,
  @ActionReference(path = "Toolbars/File", position = 0)
    ,
  @ActionReference(path = "Shortcuts", name = "F12")
})
@Messages("CTL_SooxAction=Soox Search")
public final class SooxAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SearchDialog dialog = new SearchDialog(new JFrame("Soox Search"), true);
        dialog.setTitle("Soox Search");
        dialog.setLocationRelativeTo(null);
        dialog.show();
    }
}
