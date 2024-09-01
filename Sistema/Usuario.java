package sistema;

/**
 * Classe pai para o cliente e empresa
 */
public class Usuario {
	private String nome, email;
	private double saldo;

	/**
	 * 
	 * Getters e setters
	 */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * Construtor
	 */
	public Usuario(String nome, String email, double saldo) {
		this.nome = nome;
		this.email = email;
		this.saldo = saldo;
	}
}
