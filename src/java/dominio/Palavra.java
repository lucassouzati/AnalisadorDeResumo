/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author lsiqueira
 */
public class Palavra {
    
    private int id;
    private String descricao;
    private String tag;
    private int frequencia = 0;

    public Palavra() {
    }

    public Palavra(int id, String descricao, String tag) {
        this.id = id;
        this.descricao = descricao;
        this.tag = tag;
        this.frequencia = 1; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }
    
    public void contaMaisUm(){
        this.frequencia++;
    }

    @Override
    public String toString() {
        //return "Palavra{" + "id=" + id + ", descricao=" + descricao + ", tag=" + tag + ", frequencia=" + frequencia + '}';
        return "descricao=" + descricao + ", tag=" + tag + ", frequencia=" + frequencia + '}';

    }
    
    
}
