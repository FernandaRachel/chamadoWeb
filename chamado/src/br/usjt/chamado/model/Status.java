package br.usjt.chamado.model;

public enum Status {
	ABERTO("ABERTO"), FECHADO("FECHADO"), EMATENDIMENTO("EM ATENDIMENTO"),
	PENDENTE("PENDENTE"), CANCELADO("CANCELADO"), ATRASADO("ATRASADO");

    private String tipo;

    private Status(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoUsuario() {
        return tipo;
    }

}
