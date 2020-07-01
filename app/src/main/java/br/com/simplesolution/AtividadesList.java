package br.com.simplesolution;

public class AtividadesList {
    public String descrip, turmanome, idioma;


    public AtividadesList(){

        //Necessário construtor vazio.
    }

    public AtividadesList(String descrip, String turmanome, String idioma){

        this.descrip = descrip;
        this.idioma = idioma;
        this.turmanome = turmanome;

    }

    public String getDescrip() {
        return descrip;
    }


    public String getIdioma() {
        return idioma;
    }

    public String getTurmanome() {
        return turmanome;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }


    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setTurmanome(String turmanome) {
        this.turmanome = turmanome;
    }
}
