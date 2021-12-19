package Pack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.VariableElement;

import CodIntermedio.IntermediateStatement;
import CodIntermedio.Quadruple;

public class FinalCode {
    public static final String $v0 = "$v0";

    public static final String $a0 = "$a0";
    public static final String $a1 = "$a1";
    public static final String $a2 = "$a2";
    public static final String $a3 = "$a3";

    public static final String $t0 = "$t0";
    public static final String $t1 = "$t1";
    public static final String $t2 = "$t2";
    public static final String $t3 = "$t3";
    public static final String $t4 = "$t4";
    public static final String $t5 = "$t5";
    public static final String $t6 = "$t6";
    public static final String $t7 = "$t7";

    public static final String $s0 = "$s0";
    public static final String $s1 = "$s1";
    public static final String $s2 = "$s2";
    public static final String $s3 = "$s3";
    public static final String $s4 = "$s4";
    public static final String $s5 = "$s5";
    public static final String $s6 = "$s6";
    public static final String $s7 = "$s7";

    public static final String $sp = "$sp";
    public static final String $fp = "$fp";
    public static final String $ra = "$ra";

    private enum OperationType {

        INTEGER_OPERATION, BOOLEAN_OPERATION
    }
    
    private class Info {
        public String reg;
        public OperationType type;

        public Info(String reg, OperationType type) {
            this.reg = reg;
            this.type = type;
        }
    }

    private SemanticAnalysis semanticTable;
    private BufferedWriter out;
    private HashMap<String, Boolean> avalibleTemps;
    private HashMap<String, Info> finalTemps;
    private IntermediateStatement intermediateForm;
    private List<String> stringsTable;
    private List<String> scopesTable;

    public FinalCode(SemanticAnalysis semanticTable, File out, IntermediateStatement intermediateForm, List<String> stringsTable,List<String> scopeTable) throws IOException {
        this.semanticTable = semanticTable;
        this.out = new BufferedWriter(new FileWriter(out));
        this.avalibleTemps = new HashMap();
        this.finalTemps = new HashMap();
        this.stringsTable = stringsTable;
        this.scopesTable = scopeTable;


        // Filling hashmap
        avalibleTemps.put($v0, true);

        avalibleTemps.put($a0, true);
        avalibleTemps.put($a1, true);
        avalibleTemps.put($a2, true);
        avalibleTemps.put($a3, true);

        avalibleTemps.put($t0, true);
        avalibleTemps.put($t1, true);
        avalibleTemps.put($t2, true);
        avalibleTemps.put($t3, true);
        avalibleTemps.put($t4, true);
        avalibleTemps.put($t5, true);
        avalibleTemps.put($t6, true);
        avalibleTemps.put($t7, true);

        avalibleTemps.put($s0, true);
        avalibleTemps.put($s1, true);
        avalibleTemps.put($s2, true);
        avalibleTemps.put($s3, true);
        avalibleTemps.put($s4, true);
        avalibleTemps.put($s5, true);
        avalibleTemps.put($s6, true);
        avalibleTemps.put($s7, true);
        this.intermediateForm = intermediateForm;
    }

    private String getAvailableTemp() {
        for (int i = 0; i < 8; i++) {
            if (avalibleTemps.get("$t" + i)) {
                avalibleTemps.put("$t" + i, false);
                return "$t" + i;
            }
        }

        return null;
    }
    
    private String getAvailableArguments() {
        for (int i = 0; i < 4; i++) {
            if (avalibleTemps.get("$a" + i)) {
                avalibleTemps.put("$a" + i, false);
                return "$a" + i;
            }
        }
        return null;
    }

    private String getAvailableSaved() {
        for (int i = 0; i < 4; i++) {
            if (avalibleTemps.get("$s" + i)) {
                avalibleTemps.put("$s" + i, false);
                return "$s" + i;
            }
        }
        return null;
    }

    private void setAvailable(String reg) {
        avalibleTemps.put(reg, true);
    }

    public void writeFinalCode(String code) throws IOException {
        this.out.append(code);
        out.flush();
        out.close();
    }

    public String build(){
        StringBuilder final_code = new StringBuilder();
        StringBuilder scopeFixer = new StringBuilder();
        StringBuilder idFixer = null;
        scopeFixer.append(scopesTable.get(0)).deleteCharAt(0);
        ArrayList<VariableTableNode> primerScope = this.semanticTable.getSymbolTable().getAllVars(scopeFixer.toString());
        final_code.append(".data\n");
        for (VariableTableNode node : primerScope) {
            if (node.getType().equals("Integer")) {
                final_code.append("_"+node.getId()+":\t.word\t0");
            }else if(node.getType().equals("Boolean")){
                final_code.append("_"+node.getId()+":\t.byte\t' '");
            }
            final_code.append('\n');
        }
        for (int i = 0; i < stringsTable.size(); i++) {
            final_code.append("_msg"+ i + ":\t.asciiz\t\"" + stringsTable.get(i) + "\"\n");
        }
        final_code.append("\n.text\n.globl main\n\n");
        final_code.append("main: \n\n");

        StringBuilder body = new StringBuilder();
        for (int i = 0; i < intermediateForm.operations.size(); i++) {
            Quadruple quad = intermediateForm.operations.elementAt(i);
            switch (quad.getType()) {
                case MIN:
                case MUL:
                case DIV:
                case ADD:
                    String t1 = null;
                    String t3 = null;
                    OperationType type = null;
                    if (this.finalTemps.get(quad.getOp1()) !=null) {
                        t1 = finalTemps.get(quad.getOp1()).reg;
                        type = finalTemps.get(quad.getOp1()).type;
                    }else{
                        if (quad.getOp1().matches("[0-9]*")) {
                            t1 = getAvailableTemp();
                            body.append("li "+ t1 + "," + quad.getOp1() + "\n");
                            type = OperationType.INTEGER_OPERATION;
                        } else{
                            String identifier = quad.getOp1();
                            idFixer = new StringBuilder();
                            idFixer.append(identifier).deleteCharAt(0);
                            VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString() );

                            if (var.getType().equals("Integer")){
                                if (!var.getCurrent_reg().equals("")) {
                                    t1 = getAvailableTemp();
                                    body.append("move " + t1 + ", " + var.getCurrent_reg() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                } else if (!var.getFinal_direction().equals("")) {
                                    t1 = getAvailableTemp();
                                    body.append("lw " + t1 + ", " + var.getFinal_direction() + "\n");
                                    type = OperationType.INTEGER_OPERATION;                            
                                }else{
                                    t1 = getAvailableTemp();
                                    body.append("lw " + t1 + ", " + quad.getOp1() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                }
                            }
                        }
                    }
                    String t2= null;
                    if (finalTemps.get(quad.getOp2()) !=null) {
                        t2 = finalTemps.get(quad.getOp2()).reg;
                        type = finalTemps.get(quad.getOp2()).type;
                    } else{
                        if (quad.getOp2().matches("[0-9]*")) {
                            t2 = getAvailableTemp();
                            body.append("li " + t2 + ", " + quad.getOp2() + "\n");
                            type = OperationType.INTEGER_OPERATION;
                        } else if (quad.getOp2().contains("_")) {
                            String identifier = quad.getOp2();
                            idFixer = new StringBuilder();
                            idFixer.append(identifier).deleteCharAt(0);
                            VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString() );
                            if (var.getType().equals("Integer")){
                                if (!var.getCurrent_reg().equals("")) {
                                    t2 = getAvailableTemp();
                                    body.append("move " + t2 + ", " + var.getCurrent_reg() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                } else if (!var.getFinal_direction().equals("")) {
                                    t2 = getAvailableTemp();
                                    body.append("lw " + t2 + ", " + var.getFinal_direction() + "\n");
                                    type = OperationType.INTEGER_OPERATION;                            
                                }else{
                                    t2 = getAvailableTemp();
                                    body.append("lw " + t2 + ", " + quad.getOp2() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                }
                            }
                        }else{
                            t2=$t0;
                        }
                    }
                    String ope = "";
                    switch (quad.getType()) {
                        case ADD:
                            ope = "add ";
                            break;
                        case MIN:
                            ope = "sub ";
                            break;
                        case DIV:
                            ope = "div ";
                            break;
                        case MUL:
                            ope = "mul ";
                            break;
                        default:
                            break;
                    }

                    if (type == OperationType.INTEGER_OPERATION) {
                        t3 = getAvailableTemp();
                        body.append(ope + t3 + ", " +t1 + ", " + t2 + "\n");
                        String var_result = quad.geteLugar();
                        if (var_result.contains("_")) {
                            String identifier = quad.geteLugar();
                            idFixer = new StringBuilder();
                            idFixer.append(identifier).deleteCharAt(0);
                            VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString() );
                            if (!var.getCurrent_reg().equals("")) {
                                body.append("move " + t3 + "," + var.getCurrent_reg() + "\n");
                            } else if (!var.getFinal_direction().equals("")) {
                                body.append("sw " + t3 + "," + var.getFinal_direction() + "\n");
                            }else{
                                body.append("sw " + t3 + "," + quad.geteLugar() + "\n");
                            }
                            setAvailable(t3);
                        }else{
                            this.finalTemps.put(quad.geteLugar(), new Info(t3,type));
                            if (this.finalTemps.get(quad.getOp1()) != null) {
                                this.finalTemps.remove(quad.getOp1());
                            }

                            if (this.finalTemps.get(quad.getOp2()) != null) {
                                this.finalTemps.remove(quad.getOp2());
                            }
                        }
                    }

                    setAvailable(t1);
                    setAvailable(t2);
                    break;                
                case IF_GEQ:
                case IF_LEQ:
                case IF_GT:
                case IF_EQ:
                case IF_LT:
                case IF_NEQ:
                    String condType = "";
                    switch(quad.getType()){
                        case IF_GEQ:
                            condType = "bge ";
                            break;
                        case IF_LEQ:
                            condType = "ble ";
                            break;
                        case IF_GT:
                            condType = "bgt ";
                            break;
                        case IF_LT:
                            condType = "blt ";
                            break;
                        case IF_NEQ:
                            condType = "bne ";
                            break;
                        case IF_EQ:
                            condType = "beq ";
                            break;
                    }
                    t1 = null;
                    t2 = null;
                    type = null;

                    if (this.finalTemps.get(quad.getOp1()) != null) {
                        t1 = this.finalTemps.get(quad.getOp1()).reg;
                        type = this.finalTemps.get(quad.getOp1()).type;    
                    } else {
                        if (quad.getOp1().matches("[0-9]+")) {
                            t1 = getAvailableTemp();
                            body.append("li " + t1 + ","+quad.getOp1() + "\n");
                            type = OperationType.INTEGER_OPERATION;
                        } else {
                            String identifier = quad.getOp1();
                            idFixer = new StringBuilder();
                            idFixer.append(identifier).deleteCharAt(0);
                            VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString() );
                            if (var.getType().equals("Integer")){
                                if (!var.getCurrent_reg().equals("")) {
                                    t1 = getAvailableTemp();
                                    body.append("move " + t1 + ", " + var.getCurrent_reg() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                } else if (!var.getFinal_direction().equals("")) {
                                    t1 = getAvailableTemp();
                                    body.append("lw " + t1 + ", " + var.getFinal_direction() + "\n");
                                    type = OperationType.INTEGER_OPERATION;                            
                                }else{
                                    t1 = getAvailableTemp();
                                    body.append("lw " + t1 + ", " + quad.getOp1() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                }
                            }
                        }
                    }

                    if (this.finalTemps.get(quad.getOp2()) != null) {
                        t2 = this.finalTemps.get(quad.getOp2()).reg;
                        type = this.finalTemps.get(quad.getOp2()).type;
                    } else {
                        if (quad.getOp2().matches("[0-9]+")) {
                            t2 = getAvailableTemp();
                            body.append("li " + t2 + "," + quad.getOp2() + "\n");
                            type = OperationType.INTEGER_OPERATION;
                        } else {
                            String identifier = quad.getOp2();
                            idFixer = new StringBuilder();
                            idFixer.append(identifier).deleteCharAt(0);
                            VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString() );
                            if (var.getType().equals("Integer")){
                                if (!var.getCurrent_reg().equals("")) {
                                    t2 = getAvailableTemp();
                                    body.append("move " + t2 + ", " + var.getCurrent_reg() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                } else if (!var.getFinal_direction().equals("")) {
                                    t2 = getAvailableTemp();
                                    body.append("lw " + t2 + ", " + var.getFinal_direction() + "\n");
                                    type = OperationType.INTEGER_OPERATION;                            
                                }else{
                                    t2 = getAvailableTemp();
                                    body.append("lw " + t2 + ", " + quad.getOp2() + "\n");
                                    type = OperationType.INTEGER_OPERATION;
                                }
                            }
                        }
                    }

                    if (type == OperationType.INTEGER_OPERATION) {
                        body.append(condType + t1 + ", " + t2 + ", " + quad.getLabel().toString() + "\n");
                    }

                    setAvailable(t1);
                    setAvailable(t2);

                    break;
                
                case ASSIGN:
                    type = null;
                    t1 = null;
                    if (quad.getOp1().matches("[0-9]+")) {
                        t1 = getAvailableTemp();
                        body.append("li " + t1 + ", " + quad.getOp1() + "\n");
                    } else if (this.finalTemps.get(quad.getOp1()) != null){
                        t1 = this.finalTemps.get(quad.getOp1()).reg;
                        type = this.finalTemps.get(quad.getOp1()).type;
                    } else if(quad.getOp1().contains("_")){
                        String identifier = quad.getOp1();
                        idFixer = new StringBuilder();
                        idFixer.append(identifier).deleteCharAt(0);
                        VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString());
                        if (!var.getCurrent_reg().equals("")) {
                            t1 = getAvailableTemp();
                            body.append("move " + t1 + ", " + var.getCurrent_reg() + "\n");
                        } else if (!var.getFinal_direction().equals("")) {
                            t1 = getAvailableTemp();
                            body.append("lw " + t1 + ", " + var.getFinal_direction() + "\n");
                        }else{
                            t1 = getAvailableTemp();
                            body.append("lw " + t1 + ", " + quad.getOp1() + "\n");
                        }
                    } else if(quad.getOp1().equals("RET")){
                        t1 = $v0;
                    }else {
                        t1 = $t0;
                        setAvailable($t0);
                    }

                    String identifier = quad.geteLugar();

                    if(identifier.equals("RET")){
                        body.append("move " + $v0 + ", " + t1 + "\n");
                        body.append("b _fin" + quad.getLabel().toString() + "\n");
                    }else if(identifier.contains("_")){
                        idFixer = new StringBuilder();
                        idFixer.append(identifier).deleteCharAt(0);
                        VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString() );
                        if (!var.getCurrent_reg().equals("")) {
                            body.append("move " + var.getCurrent_reg() + "," + t1 + "\n");
                        } else if (!var.getFinal_direction().equals("")) {
                            body.append("sw " + t1 + "," + var.getFinal_direction() + "\n");
                        }else{
                            body.append("sw " + t1 + "," + quad.geteLugar() + "\n");
                        }
                    }else{
                        t2 =getAvailableTemp();
                        body.append("move " + t2 + "," + t1 + "\n");
                    }

                    setAvailable(t1);

                    if (this.finalTemps.get(quad.getOp1()) != null) {
                        this.finalTemps.remove(quad.getOp1());
                    }

                    break;
                case PARAM:
                    if (quad.getOp1().matches("[0-9]+")){
                        t1 = getAvailableTemp();
                        body.append("li " + t1 + ", " + quad.getOp1() + "\n");
                    }else if (quad.getOp1().contains("_")){
                        identifier = quad.getOp1();
                        idFixer = new StringBuilder();
                        idFixer.append(identifier).deleteCharAt(0);
                        VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString());
                        t1= getAvailableTemp();
                        if (var.getType().equals("Integer")) {
                            if (!var.getCurrent_reg().equals("")) {
                                body.append("move " + t1 + ", " + var.getCurrent_reg() + "\n");
                            } else if (!var.getFinal_direction().equals("")) {
                                body.append("lw " + t1 + ", " + var.getFinal_direction() + "\n");
                            }else{
                                body.append("lw " + t1 + ", " + quad.getOp1() + "\n");
                            }
                        } else if (var.getType().equals("Boolean")) {
                            body.append("li " + $v0 + ", 1\n");
                            body.append("lw " + $a0 + ", " + quad.getOp1() + "\n");
                            body.append("syscall\n");
                        }
                    } else {
                        t1 = this.finalTemps.get(quad.getOp1()).reg;
                        type = this.finalTemps.get(quad.getOp1()).type;
                    }

                    String argument = getAvailableArguments();
                    if (argument != null) {
                        body.append("move " + argument + ", " + t1 + "\n");
                    }

                    setAvailable(t1);
                    break;
                case CALL:
                    body.append("jal " + quad.getOp1() + "\n");
                    for (int j = 0; j <= 3; j++) {
                        setAvailable("$a" + j);
                    }
                    break;
                
                case GOTO:
                    body.append("b " + quad.getLabel() + "\n");
                    break;
                
                case PRINT:
                    if (quad.getOp1().matches("[\"][\\w\\W]*[\"]")){
                        body.append("li " + $v0 + ", 4\n");
                        body.append("la " + $a0 + ", _msg" + Integer.toString(stringsTable.indexOf(quad.getOp1().replaceAll("\"",""))) + "\n");
                        body.append("syscall\n");
                    }else if (quad.getOp1().matches("[0-9]+")){
                        t1 = getAvailableTemp();
                        body.append("li " + $v0 + ", 4\n");
                        body.append("li " + t1 + ", " + quad.getOp1() + "\n");
                        body.append("move " + $a0 + ", " + t1 + "\n");
                        body.append("syscall\n");
                        setAvailable(t1);
                    }else if (quad.getOp1().contains("_")){
                        identifier = quad.getOp1();
                        idFixer = new StringBuilder();
                        idFixer.append(identifier).deleteCharAt(0);
                        VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString());
                        if (var.getType().equals("Integer")) {
                            if (!var.getCurrent_reg().equals("")) {
                                body.append("li " + $v0 + ", 1\n");
                                body.append("move " + $a0 + ", " + var.getCurrent_reg() + "\n");
                                body.append("syscall\n");
                            } else if (!var.getFinal_direction().equals("")) {
                                body.append("li " + $v0 + ", 1\n");
                                body.append("lw " + $a0 + ", " + var.getFinal_direction() + "\n");
                                body.append("syscall\n");
                            }else{
                                body.append("li " + $v0 + ", 1\n");
                                body.append("lw " + $a0 + ", " + quad.getOp1() + "\n");
                                body.append("syscall\n");
                            }
                        } else if (var.getType().equals("Boolean")) {
                            body.append("li " + $v0 + ", 1\n");
                            body.append("lw " + $a0 + ", " + quad.getOp1() + "\n");
                            body.append("syscall\n");
                        }
                    }
                    break;
                case READ:
                    identifier = quad.getOp1();
                    idFixer = new StringBuilder();
                    idFixer.append(identifier).deleteCharAt(0);
                    VariableTableNode var = (VariableTableNode) semanticTable.getSymbolTable().findSymbol(idFixer.toString(),scopeFixer.toString());
                    if (var.getType().equals("Integer")){
                        if (!var.getCurrent_reg().equals("")) {
                            body.append("li " + $v0 + ", 5\n");
                            body.append("syscall\n");
                            body.append("move " + $v0 + ", " + var.getCurrent_reg() + "\n");
                        } else if (!var.getFinal_direction().equals("")) {
                            body.append("li " + $v0 + ", 5\n");
                            body.append("syscall\n");
                            body.append("sw " + $v0 + ", " + var.getFinal_direction() + "\n");                            
                        }else{
                            body.append("li " + $v0 + ", 5\n");
                            body.append("syscall\n");
                            body.append("sw " + $v0 + ", " + quad.getOp1() + "\n");
                        }
                    }
                    break;
                case LABEL:
                    body.append(quad.getLabel() + ": \n");
                    if (quad.getLabel().toString().contains("_")) {
                        if (quad.getLabel().toString().contains(".")) {
                            scopeFixer = new StringBuilder();
                            scopeFixer.append(quad.getLabel().toString()).deleteCharAt(0);
                            ArrayList<VariableTableNode> vars = this.semanticTable.getSymbolTable().getAllVars(scopeFixer.toString());
                            int stackcount = 8;
                            int varinside = 0;

                            body.append("sw $fp, -4($sp) \n");
                            body.append("sw $ra, -8($sp) \n");
                            body.append("move $fp, $sp \n");
                            for (int j = 0; j < vars.size(); j++) {
                                if (vars.get(j).isParam()) {
                                    String reg = getAvailableSaved();
                                    vars.get(j).setCurrent_reg(reg);
                                    stackcount+=4;
                                    body.append("sw " + reg + " , -" + stackcount + "($sp)\n");
                                    String number = "" + reg.charAt(reg.length() - 1);
                                    body.append("move " + reg + ", " + "$a"+number+"\n");
                                } else {
                                    varinside +=4;
                                    int direction = stackcount + varinside;
                                    vars.get(j).setFinal_direction("-" + direction + "($fp)");
                                }
                            }
                            body.append("sub $sp, $sp, " + (stackcount + varinside) + "\n");
                        }
                    }
                    for (int j = 0; j < 4; j++) {
                        setAvailable("$s"+j);
                    }
                    break;
                case FUNCTION_END: 
                    body.append("_fin" + quad.getOp1() + ":\n");
                    body.append("move $sp,$fp \n");
                    ArrayList<VariableTableNode> vars = semanticTable.getSymbolTable().getAllVars(scopeFixer.toString());
                    int stackcount=8;
                    int varinside =0;
                    int contador_s = 0;

                    for (int j = 0; j < vars.size(); j++) {
                        if (vars.get(j).isParam()) {
                            stackcount +=4;
                            body.append("lw $s" + contador_s + " , -"+stackcount + "($fp)\n");
                            contador_s++;
                        } else {
                            varinside+=4;
                        }
                    }

                    body.append("lw $ra, -8($fp) \n");
                    body.append("lw $fp, -4($fp) \n");
                    body.append("jr $ra\n");
                    break;

                case CLOSE: {
                    body.append("li " + $v0 + ", 10\n");
                    body.append("syscall\n");
                    break;
                }
                default:
                    break;
            }
        }
        return final_code.append(body.toString()).toString();
    }
}
