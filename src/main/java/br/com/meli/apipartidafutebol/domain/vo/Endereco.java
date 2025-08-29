package br.com.meli.apipartidafutebol.domain.vo;

public final class Endereco {
    private final String logradouro;
    private final String bairro;
    private final String cidade;
    private final String uf;
    private final String cep;
    public Endereco(String logradouro, String bairro, String cidade, String uf, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    }
    public String getLogradouro() { return logradouro; }
    public String getBairro()     { return bairro; }
    public String getCidade()     { return cidade; }
    public String getUf()         { return uf; }
    public String getCep()        { return cep; }
}
