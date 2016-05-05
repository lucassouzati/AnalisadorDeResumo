/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TextMining;

import dominio.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 *
 * @author lefoly
 */
public class Comparador {

    public static ArrayList<String> termosTempP = new ArrayList<String>();
    public static ArrayList<String> termosTempR = new ArrayList<String>();
    public static ArrayList<String> conceitosTempP = new ArrayList<String>();
    public static ArrayList<String> conceitosTempR = new ArrayList<String>();
    public static ArrayList<String> stemsRelevantesP = new ArrayList<String>();
    public static ArrayList<String> stemsRelevantesR = new ArrayList<String>();
    public static ArrayList stemsFreqP = new ArrayList();
    public static ArrayList stemsFreqR = new ArrayList();
    public static float resultado;
    
    //Mudan�a no c�digo para o SCTI - 20-09-2015
    
    public static ArrayList<Palavra> Palavras = new ArrayList<Palavra>();
    

 public static void main(String args[]) throws SQLException, IOException {
        String pergTeste = "Conceitue: objetos, atributo, diagrama de componentes e caso de uso:";
        String respTeste = "Objeto é a instância de uma classe. " +
                           "Atributo é uma característica que os objetos possuem. " +
                           "Diagrama de Componentes mostra a interação entre os componentes em um sistema. " +
                           "E o caso de uso mostra a ação de um ator dentro de um sistema.";
        String pergTeste2 = "O que é um objeto?";
        String respTeste2 = "Não é a instância de uma não classe.";

        String pergTeste3 = "Explique a utilidade do Diagrama de Classes no processo de Projeto:";
        String respTeste3 = "O diagrama de classes mostra as classes, não somente conceituais, mas também as do software.";
        //String respTeste3 = "O diagrama de classes.";

        String pergTeste4 = "Conceitue Requisitos de Domínio:";
        String respTeste4 = "São características que refletem um dado domínio da aplicação. Considere um sistema de solução de sistemas lineares, pode haver o requisito de solução do sistema através de um método específico, como Gauss-Seidel.";
        
        String pergTeste5 = "O que é Comércio Eletrônico";
        String respTeste5 = "Pode-se dizer que não envolve qualquer transação eletronica de negócios eletronica eletronica eletronica.";
        
        String pergTeste6= "O que é um atributo?";
        String respTeste6 = "São características de um determinado objeto ou classe.";

        /*Processar(pergTeste2, respTeste2, 1);
        ListarTudo();
        System.out.printf("\nO aluno acertou %.2f%% da questão.\n", resultado);
        System.out.printf("A nota sugerida| é: %.1f\n\n", resultado/10);*/
        
        /* - Testando a negação
        System.out.println(Etiquetador.processaTexto("O projeto de pesquisa de forma alguma está em desenvolvimento."));
        if (Etiquetador.verificaNegacao()){
            System.out.println("\n É negação");
        }
        else
            System.out.println("\n Não é negação.");
        System.out.println("------------------------------------------------");
        System.out.println(Etiquetador.processaTexto("Por enquanto, de jeito algum chegamos aos resultados esperados."));
        
        if (Etiquetador.verificaNegacao()){
            System.out.println("\n É negação");
        }
        else
            System.out.println("\n Não é negação.");
        */
        
        /* Extraindo texto de pdf */
        
        PDDocument documento = new PDDocument();
        documento.load("0051.pdf");
        
        PDFTextStripper stripper = new PDFTextStripper();
       // stripper.setStartPage( 2 );
       // stripper.setEndPage( 3 );
        //;
        String texto = stripper.getText(documento.load("0051.pdf"));
        System.out.println(Etiquetador.processaTexto(stripper.getText(documento.load("0051.pdf"))));
        
        Palavras = Etiquetador.getPalavras();
        
        ordenaPorFrequencia();
        // Identificando preposições e termos compostos
        //System.out.println(Etiquetador.processaTexto("Essa string contem varios termpos compostos, como Sistemas de Informação, Engenharia de Software, Sistemas de Informação, Engenharia Computacional, Análise Orientada a Objetos."));
        //Essa string contem varios termpos compostos, como Sistemas de Informação, Engenharia de Software, engenharia Computacional, Análise Orientada a Objetos.
        //System.out.println(Etiquetador.verificaTermosCompostos());
    }
    
    public static void ordenaPorFrequencia(){
        Palavra palavraux = new Palavra();
        int maiorfrequencia = 0;
        for(int i=0; i<Palavras.size(); i++){
            if(i==0)
                maiorfrequencia = Palavras.get(i).getFrequencia();
            else {
                if (Palavras.get(i).getFrequencia() > maiorfrequencia);
            }
        }
    }

   /* public static float Processar(String pergunta, String resposta, int disciplina) throws SQLException {
        termosTempP.clear();
        termosTempR.clear();
        conceitosTempP.clear();
        conceitosTempR.clear();
        stemsRelevantesP.clear();
        stemsRelevantesR.clear();
        stemsFreqP.clear();
        stemsFreqR.clear();
        resultado = 0;

        //int disciplina = 2;
//        obterTermosRelevantes("Pergunta", disciplina, pergunta);
   //     obterFrequenciaTermos("Pergunta");
    //    obterFrequenciaConceitos("Pergunta");
//
        //obterTermosRelevantes("Resposta", disciplina, resposta);

        //esse � o novo obterTErmosRelevantes("Resposta")
        String respostaNova = Minerador.processar(resposta);
        String respostaNovaV[] = respostaNova.split("\\s");
        for (int i = 0; i <= respostaNovaV.length-1; i++) {
            //stemsRelevantesR.add(respostaNovaV[i]);
            termosTempR.add(respostaNovaV[i]);
        }
   //     obterFrequenciaTermos("Resposta");
        //obterFrequenciaConceitos("Resposta");

        Comparar();

        return resultado;
    }

    private static void ListarTudo() {
        System.out.println("Pergunta");
        for (int i = 0; i < stemsRelevantesP.size(); i++) {
            System.out.println("      " + stemsRelevantesP.get(i) + " = " + stemsFreqP.get(i));
        }
        System.out.println("-----------------------------------");
        System.out.println("Resposta");
        for (int i = 0; i < stemsRelevantesR.size(); i++) {
            System.out.println("      " + stemsRelevantesR.get(i) + " = " + stemsFreqR.get(i));
        }
    }

    private static void Comparar() {

        ArrayList<String> termosEspelho = new ArrayList<String>();
        ArrayList<String> conceitosEspelho = new ArrayList<String>();
        ArrayList termosFreqEspelho = new ArrayList();
        ArrayList conceitosFreqEspelho = new ArrayList();
        int tamanhoP, tamanhoR, tamanhoE;

        tamanhoP = stemsRelevantesP.size();
        tamanhoR = stemsRelevantesR.size();

        System.out.println("tamanhoP = " + tamanhoP);
        System.out.println("tamanhoR = " + tamanhoR);

        for (int i = 0; i <= tamanhoP - 1; i++) {
            String termo = stemsRelevantesP.get(i);
            Object freq = stemsFreqP.get(i);
            int j = 0;
            if (tamanhoR > 0) {
                while ((j < tamanhoR - 1) && (! stemsRelevantesR.get(j).equals(termo))) {
                    j++;
                }
                if (stemsRelevantesR.get(j).equals(termo)) {
                    termosEspelho.add(termo);
                    termosFreqEspelho.add(freq);
                }
            } 
        }
        tamanhoE = termosEspelho.size();
        float somaPergunta = 0, somaEspelho = 0;
        for (int i = 0; i <= tamanhoP - 1; i++) {
            somaPergunta = somaPergunta + Integer.parseInt(stemsFreqP.get(i).toString());
        }
        for (int i = 0; i <= tamanhoE - 1; i++) {
            somaEspelho = somaEspelho + Integer.parseInt(termosFreqEspelho.get(i).toString());
        }
        System.out.println(somaEspelho);
        System.out.println(somaPergunta);
        resultado = (somaEspelho / somaPergunta)  * 100;
    }*/
}

