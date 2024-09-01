package sistema;

/**
 * Classe representando um Cliente no sistema. Um Cliente possui um CPF.
 */
public class Cliente extends Usuario{
	private String cpf;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	/**
	 * Método para validar o CPF.
	 * 
	 * É importante tratar o CPF 
	 * como string para manter e preservar suas características
	 */
	private boolean validarCPF(String cpf) {
		 // Remover caracteres não numéricos
	    cpf = cpf.replaceAll("[^\\d]", "");

	    // Verificar se o CPF tem 11 dígitos ou se todos os dígitos são iguais
	    if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
	        return false;
	    }

	    // Calcular o primeiro dígito verificador
	    int soma = 0;
	    for (int i = 0; i < 9; i++) {
	        soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
	    }
	    int resto = soma % 11;
	    int primeiroDigito = (resto < 2) ? 0 : 11 - resto;

	    // Calcular o segundo dígito verificador
	    soma = 0;
	    for (int i = 0; i < 9; i++) {
	        soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
	    }
	    soma += primeiroDigito * 2;
	    resto = soma % 11;
	    int segundoDigito = (resto < 2) ? 0 : 11 - resto;
	    return (primeiroDigito == Character.getNumericValue(cpf.charAt(9)) &&
	            segundoDigito == Character.getNumericValue(cpf.charAt(10)));
	}
	
	public Cliente(String nome, String email, String cpf) {
        super(nome, email, 0);
        this.cpf = validarCPF(cpf) ? cpf : "CPF inválido";
    }
	
	/**
	 * 
	 * métodos para sacar e depositar do cliente
	 */
	public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        this.setSaldo(this.getSaldo() + valor);
    }
	public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        if (this.getSaldo() < valor) {
            throw new IllegalStateException("Saldo insuficiente para realizar o saque.");
        }
        this.setSaldo(this.getSaldo() - valor);
    }
}
