package com.conversor.Model;

public class Model {
    String header =
            """
                <ImportacaoLancamentoDTO>
                    <ListaLancamentos>
                        <LancamentoImportacao>
                            <NROEMPRESA>VAR1</NROEMPRESA>
                            <DTACONTABIL>VAR2</DTACONTABIL>
                            <NROLOTE>12</NROLOTE>
                            <EXTEMPORANEO>N</EXTEMPORANEO>
                            <LancamentoContas>
                VAR3
                            </LancamentoContas>
                        </LancamentoImportacao>
                    </ListaLancamentos>
                </ImportacaoLancamentoDTO>
                """;
    String accountEntry =
            """
                    \t\t\t\t<LancamentoConta>
                    \t\t\t\t\t<CONTA>VAR1</CONTA>
                    \t\t\t\t\t<TIPO>VAR2</TIPO>
                    \t\t\t\t\t<VALOR>VAR3</VALOR>
                    \t\t\t\t\t<HISTORICOCOMPLETO>VAR4</HISTORICOCOMPLETO>
                    \t\t\t\t\t<LancamentoContaParams>
                    \t\t\t\t\t\t<LancamentoContaParam>
                    \t\t\t\t\t\t\t<PARAMETRO>E</PARAMETRO>
                    \t\t\t\t\t\t\t<SEQPARAMETROVALOR>VAR5</SEQPARAMETROVALOR>
                    \t\t\t\t\t\t</LancamentoContaParam>
                    VAR6
                    \t\t\t\t\t</LancamentoContaParams>
                    \t\t\t\t</LancamentoConta>""";
    String dynamicParam =
            """         
                    \t\t\t\t\t\t<LancamentoContaParam>
                    \t\t\t\t\t\t\t<PARAMETRO>VAR1</PARAMETRO>
                    \t\t\t\t\t\t\t<SEQPARAMETROVALOR>VAR2</SEQPARAMETROVALOR>
                    \t\t\t\t\t\t</LancamentoContaParam>""";

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAccountEntry() {
        return accountEntry;
    }

    public String getDynamicParam() {
        return dynamicParam;
    }
}