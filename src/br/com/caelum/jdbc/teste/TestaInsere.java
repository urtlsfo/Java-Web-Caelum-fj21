package br.com.caelum.jdbc.teste;

import java.util.Calendar;

import br.com.caelum.jdbc.modelo.Contato;
import br.com.caelum.jdbc.dao.ContatoDao;

public class TestaInsere {
	
	public static void main(String[] args) {
		
		//cria objeto contato e inicializa atributos
		Contato contato = new Contato();
		contato.setNome("Fabricio");
		contato.setEmail("email@email.com");
		contato.setEndereco("Rua qualquer numero 123");
		contato.setDataNascimento(Calendar.getInstance());
		
		//abre conexao para gravar através do Data Acess Object
		ContatoDao dao = new ContatoDao();
		
		//utiliza metodo criado no DAO para adicionar na tabela SQL
		dao.adiciona(contato);
		
		System.out.println("Gravado");
		
	}
}
