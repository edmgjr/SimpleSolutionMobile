package br.com.simplesolution;

public class UsuariosList {
    public String nome, tuser;
    public Boolean status;



    public UsuariosList()
    {
            //Necessário construtor vazio.
    }

    public UsuariosList(String nome, String tuser, Boolean status){

        this.nome = nome;
        this.status = status;
        this.tuser = tuser;
    }

    public String getNome() { return nome; }

    public void setNome(String nome){this.nome = nome;}

    public Boolean getStatus(){return status;}

    public void setStatus(Boolean status){this.status = status;}

    public String getTuser(){return tuser;}

    public void setTuser(String tuser){this.tuser = tuser;}

}
