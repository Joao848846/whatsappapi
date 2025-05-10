package com.zentry.whatsappapi.domain.model.scheduling;

public class scheduling {

    private String nomeCliente;
    private String telefoneCliente;
    private String dataConsulta;
    private String horaConsulta;
    private String doutor;


    public scheduling(String nomeCliente, String telefoneCliente, String dataConsulta, String horaConsulta, String doutor) {
        this.nomeCliente = nomeCliente;
        this.telefoneCliente = telefoneCliente;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
        this.doutor = doutor;
    }

    public scheduling() {

    }

    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public String getTelefoneCliente() {
        return telefoneCliente;
    }
    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }
    public String getDataConsulta() {
        return dataConsulta;
    }
    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
    public String getHoraConsulta() {
        return horaConsulta;
    }
    public void setHoraConsulta(String horaConsulta) {
        this.horaConsulta = horaConsulta;
    }
    public String getDoutor() {
        return doutor;
    }
    public void setDoutor(String doutor) {
        this.doutor = doutor;
    }
}
