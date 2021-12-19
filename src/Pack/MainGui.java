package Pack;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import jflex.exceptions.SilentExit;

public class MainGui extends javax.swing.JFrame {


    private final String proyectDirectory = "C:\\Users\\pc\\Desktop\\git repositories\\Ada95-Compiler\\Ejemplos";
    private File archivoAbierto;

    /** Creates new form Interfaz */
    public MainGui()
    {
        initComponents();

        TextLineNumber tln = new TextLineNumber(this.inputArea);
        tln.setUpdateFont(true);
        this.jScrollPane3.setRowHeaderView(tln);

        archivoAbierto = null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        inputArea = new javax.swing.JTextPane();
        colLabel = new javax.swing.JLabel();
        abrirArchivo = new javax.swing.JButton();
        compilarCodigo = new javax.swing.JButton();
        botonGraficar = new javax.swing.JButton();
        limpiarOutput = new javax.swing.JButton();
        CompilarCup = new javax.swing.JButton();
        compilarJflex = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compilador Ada");
        setResizable(false);

        outputArea.setEditable(false);
        outputArea.setColumns(20);
        outputArea.setFont(new java.awt.Font("Lucida Console", 0, 12)); // NOI18N
        outputArea.setRows(5);
        outputArea.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jScrollPane2.setViewportView(outputArea);

        inputArea.setBorder(null);
        inputArea.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        inputArea.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                inputAreaCaretUpdate(evt);
            }
        });
        jScrollPane3.setViewportView(inputArea);

        colLabel.setText("columna:");

        abrirArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/imgs/folder_32.png"))); // NOI18N
        abrirArchivo.setToolTipText("Abrir Archivo");
        abrirArchivo.setContentAreaFilled(false);
        abrirArchivo.setFocusPainted(false);
        abrirArchivo.setMaximumSize(new java.awt.Dimension(42, 42));
        abrirArchivo.setMinimumSize(new java.awt.Dimension(42, 42));
        abrirArchivo.setPreferredSize(new java.awt.Dimension(42, 42));
        abrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirArchivoActionPerformed(evt);
            }
        });

        compilarCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/imgs/compile2.png"))); // NOI18N
        compilarCodigo.setToolTipText("Compilar");
        compilarCodigo.setContentAreaFilled(false);
        compilarCodigo.setFocusPainted(false);
        compilarCodigo.setFocusable(false);
        compilarCodigo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        compilarCodigo.setMaximumSize(new java.awt.Dimension(42, 42));
        compilarCodigo.setMinimumSize(new java.awt.Dimension(42, 42));
        compilarCodigo.setPreferredSize(new java.awt.Dimension(42, 42));
        compilarCodigo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        compilarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarCodigoActionPerformed(evt);
            }
        });

        botonGraficar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/imgs/graph.png"))); // NOI18N
        botonGraficar.setToolTipText("Graficar");
        botonGraficar.setFocusable(false);
        botonGraficar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGraficar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGraficarActionPerformed(evt);
            }
        });

        limpiarOutput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/compilador/imgs/clean.png"))); // NOI18N
        limpiarOutput.setToolTipText("Limpiar consola");
        limpiarOutput.setFocusable(false);
        limpiarOutput.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        limpiarOutput.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        limpiarOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarOutputActionPerformed(evt);
            }
        });

        CompilarCup.setText("C CUP");
        CompilarCup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompilarCupActionPerformed(evt);
            }
        });

        compilarJflex.setText("C JFLEX");
        compilarJflex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarJflexActionPerformed(evt);
            }
        });
        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(compilarJflex, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(CompilarCup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(abrirArchivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(compilarCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonGraficar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(limpiarOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(colLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(abrirArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(compilarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonGraficar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(limpiarOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(compilarJflex, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CompilarCup, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(colLabel)
                .addGap(3, 3, 3)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirArchivoActionPerformed

        // Crear File Chooser
        JFileChooser fc = new JFileChooser(proyectDirectory);
        
        int seleccion = fc.showOpenDialog(null);

        if ( seleccion == fc.APPROVE_OPTION )
        {
            // Escoger el archivo
            archivoAbierto = fc.getSelectedFile();
            
            // Agregarlo al textarea
            try
            {
                FileReader fr = new FileReader(archivoAbierto);
                BufferedReader reader = new BufferedReader(fr);
                
                String linea;
                String input = "";
                while ( (linea = reader.readLine()) != null )
                    input += linea + "\n";

                inputArea.setText(input);
                fr.close();
                reader.close();
                outputArea.append("Archivo abierto: " + archivoAbierto.getName() + "\n");
                //clean column
                
            }
            catch (Exception e )
            {
                outputArea.setText(e.getMessage());
            }
        }        
    }//GEN-LAST:event_abrirArchivoActionPerformed

    parser par;   
    
    private void compilarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarCodigoActionPerformed
        
                    
        
        
        String texto= inputArea.getText();
        
        
        System.out.println("iniciando analisis sintactico..\n");
        Lexer scan = new Lexer(new BufferedReader( new StringReader(texto)));
        par = new parser(scan);
      

        try {
            par.parse();
        } catch (Exception ex) {
            Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }//GEN-LAST:event_compilarCodigoActionPerformed
    
    
    public void graficar(Nodo raiz){
        FileWriter archivo = null;
        PrintWriter pw = null;
        String cadena = graficarNodo(raiz);
        
        try{
            archivo = new FileWriter("arbol.dot");
            pw = new PrintWriter(archivo);
            pw.println("digraph G {node[shape=box, style=filled, color=blanchedalmond]; edge[color=chocolate3];rankdir=UD \n");
            pw.println(cadena);
            pw.println("\n}");
            archivo.close();
        }catch (Exception e) {
            System.out.println(e +" 1");
        }
        
        try {
            String cmd = "dot -Tpng arbol.dot -o arbol.png";
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ioe) {
            System.out.println(ioe +" 2");
        }
        
    }
    
    public String graficarNodo(Nodo nodo){
        String cadena = "";
        for(Nodo hijos : nodo.getHijos())
        {
            cadena += "\"" + nodo.getNumNodo() + "_" + nodo.getNombre() + " -> " + nodo.getValor() + "\"->\"" + hijos.getNumNodo() + "_" + hijos.getNombre() + " -> " + hijos.getValor() + "\"\n";
            cadena += graficarNodo(hijos);
        }
        return cadena;
    }
    
    
    
    private void botonGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGraficarActionPerformed
        /*
        graficar
        
        String opciones[] = new String[5];
        opciones[0] = "-destdir";
        opciones[1] = "src\\compilador\\";
        opciones[2] = "-parser";
        opciones[3] = "parser";
        opciones[4] = "src\\compilador\\parser.cup";

        try
        {
            outputArea.setText(""); // Clear
            outputArea.setText("Compilando archivo .cup ... \n");
            java_cup.Main.main(opciones);
            outputArea.append("archivo .cup compilado exitosamente. \n");

        }
        catch ( Exception e )
        {
            outputArea.setText(e.getMessage());
        }
        
        */
        if(par != null){
            if(par.padre != null){
                graficar(par.padre);
                System.out.println("Se ha graficado con exito");
                SemanticAnalysis analysis = new SemanticAnalysis(new SymbolTable());
                analysis.Traverse(par.padre, "");
                
                
                File file = new File("./archivo.adb");
                File intermediateCodeFile = new File(file.getAbsolutePath().replace(".adb", "") + ".o");
                CodigoIntermedio initIntermedio = null;
                
                File finalCodeFile = new File(file.getAbsolutePath().replace(".adb","")+".asm");
                FinalCode initFinal = null;
                try {
                    initIntermedio = new CodigoIntermedio(intermediateCodeFile,analysis);
                } catch (IOException ex) {
                    Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //comenzar ejecucion codigo intermedio
                //codigo intermedio en memoria
                initIntermedio.GenerandoCod(par.padre);
                try {
                    initFinal = new FinalCode(initIntermedio.getInstance(),finalCodeFile,initIntermedio.ie,initIntermedio.getStringsTable(),initIntermedio.getScopesTable());
                } catch (IOException ex) {
                    Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    //codigo intermedio en un file
                    initIntermedio.createFile(initIntermedio.ie.buildIntermediateCode());
                   
                } catch (IOException ex) {
                    Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    //codigo intermedio en un file
                    initFinal.writeFinalCode(initFinal.build());
                   
                } catch (IOException ex) {
                    Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        /*no mostrar el frame por ahora
        //crear la imagen

        JframeImagen marco = new JframeImagen();
        marco.setExtendedState(MAXIMIZED_BOTH);
        marco.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        marco.setVisible(true);
        */
        
        else
            System.out.println("No se puede graficar!!!");
        

    }//GEN-LAST:event_botonGraficarActionPerformed

    private void limpiarOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarOutputActionPerformed
        // Limpiar el output
        outputArea.setText("");
    }//GEN-LAST:event_limpiarOutputActionPerformed

    private void inputAreaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_inputAreaCaretUpdate
        
        int line = 0, column =0, caretpos = inputArea.getCaretPosition();
        String texto = inputArea.getText();

        for( int i = 0; i < caretpos; i++)
            
            if( texto.charAt(i) == 10)
            {
                
                line++;
                column = 0;
            }
            else
                column++;
        
        colLabel.setText("Columna: " + column);


       
    }//GEN-LAST:event_inputAreaCaretUpdate

    String ruta = "src/Pack/"; //ruta donde tenemos los archivos con extension .jflex y .cup
    
    
    private void CompilarCupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompilarCupActionPerformed
        // TODO add your handling code here:
            System.out.println("------- start Cup --------");
            outputArea.setText(""); // Clear
            outputArea.setText("Compilando archivo .cup ... \n");
            
            String opcCUP[] = { "-destdir", ruta, "-parser", "parser", ruta + "parser.cup" };
        try {
            java_cup.Main.main(opcCUP);
        } catch (IOException ex) {
            Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            outputArea.append("archivo .cup compilado exitosamente. \n");
    }//GEN-LAST:event_CompilarCupActionPerformed

    private void compilarJflexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarJflexActionPerformed
        // TODO add your handling code here:
         //compilacion flex
            
            outputArea.setText(""); // clear
            outputArea.setText("Compilando archivo .flex ... \n");
            
            
            
            String opcFlex[] = { ruta + "lexer.flex", "-d", ruta };
        try {
            jflex.Main.generate(opcFlex);
        } catch (SilentExit ex) {
            Logger.getLogger(MainGui.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            outputArea.append("archivo .flex compilado exitosamente. \n");
            
            System.out.println("------- end Flex --------");
            
            
    }//GEN-LAST:event_compilarJflexActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[])
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainGui().setVisible(true);
            }

            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CompilarCup;
    private javax.swing.JButton abrirArchivo;
    private javax.swing.JButton botonGraficar;
    private javax.swing.JLabel colLabel;
    private javax.swing.JButton compilarCodigo;
    private javax.swing.JButton compilarJflex;
    protected static javax.swing.JTextPane inputArea;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton limpiarOutput;
    private javax.swing.JMenuBar menuBar;
    protected static javax.swing.JTextArea outputArea;
    // End of variables declaration//GEN-END:variables

}
