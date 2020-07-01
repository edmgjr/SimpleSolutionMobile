package br.com.simplesolution;

public class IdiomasList {
    public String idioma, nivel;

    public IdiomasList(){

        //Necessário construtor vazio.

    }

    public IdiomasList(String idioma, String nivel){
        this.idioma = idioma;
        this.nivel = nivel;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getNivel() {
        return nivel;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
