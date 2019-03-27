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
import t1comp.view.View;

public final class App {

    private View view;
    private AnalisadorLexico lex; 
    public static void main(String[] args) {
       new App();
    }
    
    public App() {
        this.view = new View(this);
        lex = new AnalisadorLexico();
        analyzeSourceCode(view.getSourceCode());
        
        this.view.show(true);
    }
    
    public void analyzeSourceCode(String sourceCode) {
        lex.analyze(sourceCode);
    }
}
