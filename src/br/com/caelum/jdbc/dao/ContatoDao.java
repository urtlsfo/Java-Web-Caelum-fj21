package br.com.caelum.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.jdbc.ConnectionFactory;
import br.com.caelum.jdbc.modelo.Contato;

public class ContatoDao {

	//a conexão com o banco de dados
	private Connection connection;
	
	public ContatoDao(){
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public void adiciona(Contato contato) {
		String sql = "insert into contatos " +
				"(nome,email,endereco,dataNascimento)" +
				" values (?,?,?,?)";
		try {
			//prepared statement para inserir
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			//seta os valores
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(
					contato.getDataNascimento().getTimeInMillis()));
			
			stmt.execute();
			stmt.close();
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public List<Contato> getLista(){
		try{
			List<Contato> contatos = new ArrayList<Contato>();
			PreparedStatement stmt = this.connection.
					prepareStatement("select * from contatos");
			ResultSet rs = stmt.executeQuery();
			
			//rs.next retorna false quando acabam os resultados
			while (rs.next()){
				//cria um objeto contato e set atributos a partir do resultset obtido
				Contato contato = new Contato();
				
				contato.setId(rs.getLong("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				
				//para data é necessario instanciar calendar primeiro
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("dataNascimento"));
				contato.setDataNascimento(data);
			
				//adiciona esse objeto à lista de objetos
				contatos.add(contato);
			}
			rs.close();
			stmt.close();
			return contatos;
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public Contato pesquisar(int id){
		
		try{
		//pega id e converte para long	
		Long idL = (long) id;
		
		//string da query para pegar apenas linha com determinado id
		String sql = "select * from contatos where id = ? ";
		
		//inicia o preparedstatement e set o valor de ? para idL
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, idL);
		ResultSet rs = stmt.executeQuery();
		
		//cria um objeto contato a ser retornado
		Contato contato = new Contato();
		
		//usa if pois espera apenas uma entrada com o id fornecido por ser chave primaria
		if (rs.next()){
			
			//set atributos a partir do resultset obtido
			contato.setId(rs.getLong("id"));
			contato.setNome(rs.getString("nome"));
			contato.setEmail(rs.getString("email"));
			contato.setEndereco(rs.getString("endereco"));
			
			//para data é necessario instanciar calendar primeiro
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("dataNascimento"));
			contato.setDataNascimento(data);
		}
		rs.close();
		stmt.close();
		return contato;	
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}

	public void altera(Contato contato) {
		String sql = "update contatos set nome=?, email=?,"+
					"endereco=?, dataNascimento=? where id=?";
		
		try{
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setDate(4, new Date(
					contato.getDataNascimento().getTimeInMillis()));
			stmt.setLong(5, contato.getId());
			stmt.execute();
			stmt.close();
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void remove(Contato contato){
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from contatos where id=?");
			stmt.setLong(1, contato.getId());
			stmt.execute();
			stmt.close();
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
}
