# t1comp
INE5426 lexical analysis

<hr>

### Compiler GRAMMAR:


**PROGRAM** -> (**CLASSLIST**) ? <br>
**CLASSLIST** -> (**CLASSDECL**)+ <br>
**CLASSDECL** -> <span style="color: purple; font-weight: bold">class</span>
                <span style="color: purple; font-weight: bold">ident</span>
                {<span style="color: purple; font-weight: bold">extends</span> <span style="color: purple; font-weight: bold">ident</span> }
                ? **CLASSBODY**<br>
**CLASSBODY** -> <span style="color: purple; font-weight: bold">{</span>
                (**CLASSLIST**)?(**VARDECL**<span style="color: purple; font-weight: bold">;</span> )\*(**CONSTRUCTDECL**)*
                (**METHODDECL**)*<span style="color: purple; font-weight: bold">}</span>  <br>
**VARDECL** -> (<span style="color: purple; font-weight: bold">int</span>
                | <span style="color: purple; font-weight: bold">string</span>
                | <span style="color: purple; font-weight: bold">ident</span> )
                <span style="color: purple; font-weight: bold">ident</span>
                (<span style="color: purple; font-weight: bold">[]</span>)
                \*(<span style="color: purple; font-weight: bold">,</span> <span style="color: purple; font-weight: bold">ident</span>
                 (<span style="color: purple; font-weight: bold">[]</span>)\*)\* <br>
**CONSTRUCTDECL** -> <span style="color: purple; font-weight: bold">constructor</span> **METHODBODY** <br>
**METHODDECL** -> (<span style="color: purple; font-weight: bold">int</span>
                                  | <span style="color: purple; font-weight: bold">string</span>
                                  | <span style="color: purple; font-weight: bold">ident</span> )
                  ([]\* <span style="color: purple; font-weight: bold">ident</span> **METHODBODY**) <br>
**METHODBODY** -> <span style="color: purple; font-weight: bold">(</span>
                    **PARAMLIST**
                    <span style="color: purple; font-weight: bold">)</span>
                     **STATEMENT** <br>
**PARAMLIST** -> ((<span style="color: purple; font-weight: bold">int</span>
                                  | <span style="color: purple; font-weight: bold">string</span>
                                  | <span style="color: purple; font-weight: bold">ident</span> )
                   <span style="color: purple; font-weight: bold">ident</span>
                   (<span style="color: purple; font-weight: bold">[]</span>)\*
                   (<span style="color: purple; font-weight: bold">,</span>
                   (<span style="color: purple; font-weight: bold">int</span>
                                                     | <span style="color: purple; font-weight: bold">string</span>
                                                     | <span style="color: purple; font-weight: bold">ident</span> )
                    <span style="color: purple; font-weight: bold">ident</span>
                    (<span style="color: purple; font-weight: bold">[]</span>)\*)\*)? <br>
****STATEMENT**** -> (
            **VARDECL**<span style="color: purple; font-weight: bold">;</span>
            | **ATRIBSTAT**<span style="color: purple; font-weight: bold">;</span>
            | **PRINTSTAT**<span style="color: purple; font-weight: bold">;</span>
            | **READSTAT**<span style="color: purple; font-weight: bold">;</span>
            | **RETURNSTAT**<span style="color: purple; font-weight: bold">;</span>
			|**IFSTAT** | **FORSTAT**
			| <span style="color: purple; font-weight: bold">{</span>
			    **STATLIST**
			  <span style="color: purple; font-weight: bold">}</span>
			|<span style="color: purple; font-weight: bold">break;</span>
			| <span style="color: purple; font-weight: bold">;</span>) <br>
**ATRIBSTAT** -> LVALUE <span style="color: purple; font-weight: bold">=</span> (**EXPRESSION** | **ALOCEXPRESSION**) <br>
**PRINTSTAT** -> <span style="color: purple; font-weight: bold">print</span> **EXPRESSION** <br>
**READSTAT** -> <span style="color: purple; font-weight: bold">read</span> **LVALUE** <br>
**RETURNSTAT** -> <span style="color: purple; font-weight: bold">return</span> (**EXPRESSION**)? <br>
**SUPERSTAT** -> <span style="color: purple; font-weight: bold">super</span>
                <span style="color: purple; font-weight: bold">(</span>
                    **ARGLIST**
                <span style="color: purple; font-weight: bold">)</span>     <br>
**IFSTAT** -> <span style="color: purple; font-weight: bold">if</span>
                <span style="color: purple; font-weight: bold">(</span>
                    **EXPRESSION**
                <span style="color: purple; font-weight: bold">)</span>
                **STATEMENT** (<span style="color: purple; font-weight: bold">else</span> **STATEMENT**)? <br>
**FORSTAT** -> <span style="color: purple; font-weight: bold">for</span>
                <span style="color: purple; font-weight: bold">(</span>
                    (**ATRIBSTAT**)?<span style="color: purple; font-weight: bold">;</span>
                    (**EXPRESSION**)?<span style="color: purple; font-weight: bold">;</span>
                    (**ATRIBSTAT**)?
                 <span style="color: purple; font-weight: bold">)</span>
                 **STATEMENT** <br>
**STATLIST** -> **STATEMENT**(**STATLIST**)? <br>
**LVALUE** -> <span style="color: purple; font-weight: bold">ident</span>
              (
                <span style="color: purple; font-weight: bold">[</span>
                    **EXPRESSION**
                <span style="color: purple; font-weight: bold">]</span>
                | <span style="color: purple; font-weight: bold">.ident</span>
                (
                    <span style="color: purple; font-weight: bold">(</span>
                        **ARGLIST**
                    <span style="color: purple; font-weight: bold">)</span>
                )?) <br>
**ALOCEXPRESSION** -> <span style="color: purple; font-weight: bold">new</span>
            (<span style="color: purple; font-weight: bold">ident</span>
             <span style="color: purple; font-weight: bold">(</span>
                **ARGLIST**
             <span style="color: purple; font-weight: bold">)</span>
             | (<span style="color: purple; font-weight: bold">int</span>
                | <span style="color: purple; font-weight: bold">string</span>
                | <span style="color: purple; font-weight: bold">ident</span> )
                (
                    <span style="color: purple; font-weight: bold">[</span>
                        **EXPRESSION**
                    <span style="color: purple; font-weight: bold">]</span>
                )+) <br>
**EXPRESSION** -> **NUMEXPRESSION** (
            ( <span style="color: purple; font-weight: bold"><</span> |
              <span style="color: purple; font-weight: bold">></span> |
              <span style="color: purple; font-weight: bold"><=</span> |
                <span style="color: purple; font-weight: bold">>=</span> |
                 <span style="color: purple; font-weight: bold">==</span>|
                 <span style="color: purple; font-weight: bold">!=</span>)**NUMEXPRESSION**)? <br>
**NUMEXPRESSION** -> ****TERM****((<span style="color: purple; font-weight: bold">+</span>|<span style="color: purple; font-weight: bold">-</span>)**TERM**)\* <br>
**TERM** -> **UNARYEXPR**(
            (<span style="color: purple; font-weight: bold">\*</span>|
            <span style="color: purple; font-weight: bold">/</span>|
            <span style="color: purple; font-weight: bold">%</span>)**UNARYEXPR**)\* <br>
**UNARYEXPR** -> ((<span style="color: purple; font-weight: bold">+</span>|<span style="color: purple; font-weight: bold">-</span>))?FACTOR <br>
**FACTOR** -> (<span style="color: purple; font-weight: bold">int-constant</span>
                |<span style="color: purple; font-weight: bold">string-constant</span>
                | <span style="color: purple; font-weight: bold">null</span>
                 | **LVALUE**|
                 <span style="color: purple; font-weight: bold">(</span>
                    **EXPRESSION**
                <span style="color: purple; font-weight: bold">)</span>
                ) <br>
**ARGLIST** -> (**EXPRESSION**(<span style="color: purple; font-weight: bold">,</span> **EXPRESSION**)\*)? <br>

