package Vistas;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class VistaServer extends javax.swing.JFrame
{

	private JFileChooser fc;

	public VistaServer()
	{
		initComponents();
	}

	public File getFile() /*Obtiene un archivo del explorador, usado para
		asignar el archivo de propiedades*/
	{
		fc = new JFileChooser(System.getProperty("user.dir"));
		fc.showOpenDialog(fc);
		return fc.getSelectedFile();
	}

	public void Mensajes(String i) /*muestra un mensaje en pantalla*/
	{
		JOptionPane.showMessageDialog(null, i);
	}

	public void mensajepanel(String text) /*añade un mensaje al panel*/
	{
		panelText.append(text);
	}

	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        panelText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelText.setColumns(20);
        panelText.setRows(5);
        jScrollPane1.setViewportView(panelText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea panelText;
    // End of variables declaration//GEN-END:variables
}
