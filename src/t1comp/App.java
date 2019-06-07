/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1comp;

/**
 *
 * @author nathan
 */
import t1comp.model.AnalisadorLexico;
import t1comp.model.AnalisadorSintatico;
import t1comp.model.LL1Table;
import t1comp.model.SymbolsTable;
import t1comp.model.TableBuilder;
import t1comp.view.View;

public final class App {

    private View view;
    private AnalisadorLexico lex;
    private AnalisadorSintatico parser;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        this.view = new View(this);
        lex = new AnalisadorLexico();
        parser = new AnalisadorSintatico();
        analyzeSourceCode(view.getSourceCode());
        this.view.show(true);
    }

    public void analyzeSourceCode(String sourceCode) {
        SymbolsTable table = SymbolsTable.getInstance();
        lex.analyze(sourceCode);
        parser.parse(lex);
        view.cleanStatus();
        view.updateStatus("\n\nNEW ANALISIS\n");
        view.updateStatus(lex.statusMessage());
        view.updateStatus(parser.statusMessage());
    }
}
