package br.com.caelum.jdbc.teste;

import br.com.caelum.jdbc.modelo.Contato;
import br.com.caelum.jdbc.dao.ContatoDao;

public class TestaPesquisaPorID {

	public static void main(String[] args) {

		ContatoDao dao = new ContatoDao();
		Contato contato = dao.pesquisar(1);
		System.out.println("Nome: " + contato.getNome());
		System.out.println("Email: " + contato.getEmail());
		System.out.println("Endereco: " + contato.getEndereco());
		System.out.println("Data de Nascimento: " +
						contato.getDataNascimento().getTime() + "\n");
	}

}
