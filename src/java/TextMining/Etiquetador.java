/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TextMining;

import dominio.Palavra;
import java.util.ArrayList;
import java.io.BufferedReader; import java.io.BufferedWriter;import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 import java.io.FileReader; import java.io.FileWriter; import java.io.IOException;import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
 import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author lsiqueira
 */
public class Etiquetador {
    private static ArrayList<String> adverbios = new ArrayList<String>();
    private static ArrayList<String> palavras = new ArrayList<String>();
    private static ArrayList<String> tags = new ArrayList<String>();
    private static ArrayList<String> termoscompostos = new ArrayList<String>();
    
    private static ArrayList<Palavra> Palavras = new ArrayList<Palavra>();
    
    private static String input = "/home/lsiqueira/POSTagger/input.txt";
    private static String output = "/home/lsiqueira/POSTagger/output.txt";
    private static final Logger log = Logger.getLogger(Etiquetador.class.getName());

    public static ArrayList<Palavra> getPalavras() {
        return Palavras;
    }

    public static void setPalavras(ArrayList<Palavra> Palavras) {
        Etiquetador.Palavras = Palavras;
    }
    
    

    public static ArrayList<String> getAdverbios(String entrada) throws IOException {
        adverbios.clear();
        String saida = etiqueta(entrada);
        String[] palavras = saida.split(" ");
        for(int i=0; i<palavras.length; i++){
            if (palavras[i].contains("/ADV")){
                adverbios.add(palavras[i]);
            }
                    
        }
        System.out.println(saida);
        return adverbios;
    }
    
    public static String processaTexto(String entrada) throws IOException{
       /* palavras.clear();
        tags.clear();
        termoscompostos.clear();
       
        String saida = etiqueta(entrada);
        String[] palavrasT = saida.split(" ");
        for (int i = 0; i<palavrasT.length; i++){
            int posicao = palavrasT[i].indexOf("/");
            String tag = palavrasT[i].substring(posicao);
            String palavra = palavrasT[i].substring(0, posicao);
            palavras.add(palavra);
            tags.add(tag);
        }
        String retorno = "";
        
        for (int i = 0; i<palavras.size(); i++){
            retorno += palavras.get(i) + " - " + tags.get(i) + "\n";
            
        }*/
        
        // tentando etiquetar e contas frequencia da palavras - 20-09-2015
        
        String retorno = "";
        boolean achou = false;
        String saida = etiqueta(entrada);
        String[] palavrasT = saida.split(" ");
        
         for (int i = 0; i<palavrasT.length; i++){
            int m = 0;
            int contTC = 0;
            int posicao = palavrasT[i].indexOf("/");
            String tag = palavrasT[i].substring(posicao);
            String palavra = palavrasT[i].substring(0, posicao);
            
            //Verifica se a Palavra � um Nome, Verbo, para radicalizar
            if(tag.equals("/ADJ") || tag.equals("/CN") || tag.equals("/GER") || tag.equals("/GERAUX") || tag.equals("/INF") || tag.equals("/V")){
                palavra = Minerador.Radicalizar(palavra);
            }
            
            //Verifica se a palavra � Parte de Nome Composto (Termo Composto)
            if (tag.equals("/PNM")){
                m = i + 1;
                
                while (palavrasT[m].contains("/PNM") || palavrasT[m].contains("/PREP")){
                    posicao = palavrasT[m].indexOf("/");
                    //String tag2 = palavrasT[m].substring(posicao);
                    String palavra2 = palavrasT[m].substring(0, posicao);
                    palavra += " " + palavra2;
                    m++;
               }
               i = m; 
            }
           // else{
                if (i > 0){

                    achou = false;
                    for (int j = 0; j < Palavras.size(); j++){
                        //Verifica se a palavra j� est� no array, caso esteja, conta mais um
                        if(Palavras.get(j).getDescricao().equals(palavra)){
                            Palavras.get(j).contaMaisUm();
                            achou = true;
                            break;
                        }
                    }
                    if(!achou){
                        Palavra palavrao = new Palavra(i, palavra, tag);
                       // System.out.println(palavrao);
                        Palavras.add(palavrao);
                    }

                }
                else{
                    Palavra palavrao = new Palavra(i, palavra, tag);
                    Palavras.add(palavrao);
                }
           // }
        }
        
         for (int i = 0; i<Palavras.size(); i++){
            retorno += Palavras.get(i).toString() + "\n";
            
        }
        return retorno;
    }
    
    public static boolean verificaNegacao(){
//        String adv = "/ADV";
        boolean resultado = false;
        for (int i = 0; i<palavras.size(); i++){
           /// if (tags.get(i).equals("/ADV")){
                //System.out.println("entrou");
                //System.out.println(palavras.get(tags.indexOf("/ADV")));
                if      ((palavras.get(i).equals("n�o")) || 
                        (palavras.get(i).equals("tampouco")) || 
                        (palavras.get(i).equals("nem")) || 
                        (palavras.get(i).equals("nunca")) || 
                        (palavras.get(i).equals("jamais"))){
                    return true;
                }
                else if (palavras.get(i).equals("de")){
                    if ((palavras.get(i+1).equals("modo") && (palavras.get(i+2).equals("algum"))) || 
                            palavras.get(i+1).equals("jeito") && palavras.get(i+2).equals("nenhum") ||
                            palavras.get(i+1).equals("jeito") && palavras.get(i+2).equals("algum") ||
                            palavras.get(i+1).equals("forma") && palavras.get(i+2).equals("nenhuma") ||
                            palavras.get(i+1).equals("forma") && palavras.get(i+2).equals("alguma"))
                        return true;
                }
                
           // }
            
        }
        return resultado;
    }
    /*
    public static List verificaTermosCompostos(){
        int j = 0;
        int contTC = 0;//contador de termos compostos
        for (int i = 0; i<palavras.size(); i++){
             if (tags.get(i).equals("/PNM")){
                 j = i + 1;
              
                 termoscompostos.add(palavras.get(i));
                 while (tags.get(j).equals("/PNM") || tags.get(j).equals("/PREP")){
                     termoscompostos.set(contTC, (termoscompostos.get(contTC) + " " + palavras.get(j)));
                     j++;
                 }
                 contTC++;
                 i = j;
             }
             //if ((palavras.get(i).equals("de")) && (tags.get(i).equals("/PREP"))) 
             //    termoscompostos.add(palavras.get(i-1) + " " + palavras.get(i) + " " + palavras.get(i+1));
        }
        return termoscompostos;
    }*/
    
    //Trabalhando com objeto Palavra
    
    public static List verificaTermosCompostos(){
        int j = 0;
        int contTC = 0;//contador de termos compostos
        for (int i = 0; i<palavras.size(); i++){
             if (tags.get(i).equals("/PNM")){
                 j = i + 1;
              
                 termoscompostos.add(palavras.get(i));
                 while (tags.get(j).equals("/PNM") || tags.get(j).equals("/PREP")){
                     termoscompostos.set(contTC, (termoscompostos.get(contTC) + " " + palavras.get(j)));
                     j++;
                 }
                 contTC++;
                 i = j;
             }
             //if ((palavras.get(i).equals("de")) && (tags.get(i).equals("/PREP"))) 
             //    termoscompostos.add(palavras.get(i-1) + " " + palavras.get(i) + " " + palavras.get(i+1));
        }
        return termoscompostos;
    }
    
    public static void setAdverbios(ArrayList<String> adverbios) {
        Etiquetador.adverbios = adverbios;
    }
    
    public static void escreveInput(String entrada) throws IOException{
        OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(input),"UTF-8");
        bufferOut.write(entrada + "\n");
        bufferOut.close();
        //BufferedWriter buffWrite = new BufferedWriter(new FileWriter(input)); 
        //buffWrite.append(entrada + "\n"); 
        //buffWrite.close();
    }
    
    public static String leOutput() throws FileNotFoundException, IOException{
        //String retorno = "";
        
        // abertura do arquivo com acento
        BufferedReader myBuffer = new BufferedReader(new InputStreamReader(new FileInputStream(output), "UTF-8")); 
        // loop que l� e imprime todas as linhas do arquivo 
        String retorno = "";
        String linha = "";
        while (linha != null) { 
            //System.out.println(linha); 
            linha = myBuffer.readLine(); 
            if (linha != null) { 
                //System.out.println(retorno); 
                retorno += linha; 
            } 
            else break; 
            
            
        } 
        myBuffer.close();

        /*Esta leitura n�o funcicona com acento
        //BufferedReader buffRead = new BufferedReader(new FileReader(output));
        BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(output), "UTF-8"));
        while (true) { 
            if (retorno != null) { 
                System.out.println(retorno); 
            } 
            else break; 
            retorno = buffRead.readLine(); 
        } 
        buffRead.close();*/

        return retorno;
    }
    
    public static String etiqueta(String entrada) throws IOException{
        String retorno = "";
        escreveInput(entrada);
        //colocar um if pra saber qual � o sistema operacional 19-05-2015
        executeCommand("cat /home/lsiqueira/POSTagger/input.txt | /home/lsiqueira/POSTagger/run-Tagger.sh > /home/lsiqueira/POSTagger/output.txt");
        //Runtime run = Runtime.getRuntime();  
        //run.exec("cat input.txt | /home/lsiqueira/POSTagger/run-Tagger.sh > output.txt");
        //run.exec("cat /home/lsiqueira/POSTagger/input.txt | /home/lsiqueira/POSTagger/run-Tagger.sh > /home/lsiqueira/POSTagger/output.txt");
        //Process proc = Runtime.getRuntime().exec("cat input.txt | /home/lsiqueira/POSTagger/run-Tagger.sh > output.txt");
        //BufferedReader read = new BufferedReader(new InputStreamReader(
        //            proc.getInputStream()));
        
        retorno = leOutput();
       
        return retorno;
    }
    
    public static void executeCommand(final String command) throws IOException { 
        final ArrayList<String> commands = new ArrayList<String>(); 
        commands.add("/bin/bash"); 
        commands.add("-c"); 
        commands.add(command); 
        BufferedReader br = null; 
        try { 
            final ProcessBuilder p = new ProcessBuilder(commands); 
            final Process process = p.start(); 
            final InputStream is = process.getInputStream(); 
            final InputStreamReader isr = new InputStreamReader(is); 
            br = new BufferedReader(isr); 
            String line; 
            while((line = br.readLine()) != null) {
                System.out.println("Retorno do comando = [" + line + "]"); 
            } 
        } 
        catch (IOException ioe) { 
            log.log(Level.SEVERE, "Erro ao executar comando shell{0}", ioe.getMessage()); 
            throw ioe; 
        } 
        finally { 
            secureClose(br); 
        } 
    }


    
    private static void secureClose(final Closeable resource) { 
        try { 
            if (resource != null) { 
                resource.close(); 
            } 
        } 
        catch (IOException ex) { 
            log.severe("Erro = " + ex.getMessage()); 
        } 
    }

    public static void main() throws IOException{
        getAdverbios("Testand o que acontece nessa classe.");
    }
}
