package br.com.simplesolution;

public class TurmasList {
    public String descrip, profname, idioma;


    public TurmasList(){

        //Necessário construtor vazio.
    }

    public TurmasList(String descrip, String profname, String idioma){

        this.descrip = descrip;
        this.profname = profname;
        this.idioma = idioma;
    }


    public String getDescrip() {
        return descrip;
    }

    public String getProfname() {
        return profname;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public void setProfname(String profname) {
        this.profname = profname;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
}
