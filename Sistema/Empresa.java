package sistema;
/**
 * Classe que representa uma Empresa no sistema. Uma empresa tem CNPJ e uma taxa
 */
public class Empresa extends Usuario{
	private String cnpj;
    private double taxa;
    
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public double getTaxa() {
		return taxa;
	}
	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}
    
	/**
	 * M�todo para validar o CNPJ.
	 * 
	 * � importante tratar o CNPJ 
	 * como string para manter e preservar suas caracter�sticas
	 */
	public boolean validarCNPJ(String cnpj) {
	    // Remover caracteres n�o num�ricos
	    cnpj = cnpj.replaceAll("[^\\d]", "");

	    // Verificar se o CNPJ tem 14 d�gitos
	    if (cnpj.length() != 14) {
	        return false;
	    }

	    // Calcular o primeiro d�gito verificador
	    int soma = 0;
	    int[] pesos = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	    for (int i = 0; i < 12; i++) {
	        soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
	    }
	    int resto = soma % 11;
	    int primeiroDigito = (resto < 2) ? 0 : 11 - resto;

	    // Calcular o segundo d�gito verificador
	    soma = 0;
	    int[] pesos2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
	    for (int i = 0; i < 12; i++) {
	        soma += Character.getNumericValue(cnpj.charAt(i)) * pesos2[i];
	    }
	    soma += primeiroDigito * pesos2[12];
	    resto = soma % 11;
	    int segundoDigito = (resto < 2) ? 0 : 11 - resto;
	    return (primeiroDigito == Character.getNumericValue(cnpj.charAt(12)) &&
	            segundoDigito == Character.getNumericValue(cnpj.charAt(13)));
	}
	
	public Empresa(String nome, String email, String cnpj, double taxa) {
        super(nome, email, 0); // O saldo inicial pode ser zero
        this.cnpj = validarCNPJ(cnpj) ? cnpj : "CNPJ inv�lido";
        this.taxa = taxa;
    }
	
	public void realizarTransacao(Cliente cliente, double valor, String tipo) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transa��o deve ser positivo.");
        }

        double valorTaxa = valor * taxa;
        double valorFinal = valor - valorTaxa;

        switch (tipo.toLowerCase()) {
            case "deposito":
                // Cliente faz um dep�sito na empresa
                cliente.sacar(valor); // Deduz o valor da conta do cliente
                this.depositar(valorFinal); // Adiciona o valor com a taxa deduzida ao saldo da empresa
                break;

            case "saque":
                // Cliente faz um saque da empresa
                if (this.getSaldo() < valorFinal) {
                    throw new IllegalStateException("Saldo insuficiente na empresa para realizar o saque.");
                }
                this.sacar(valorFinal); // Deduz o valor com a taxa deduzida do saldo da empresa
                cliente.depositar(valor); // Adiciona o valor total � conta do cliente
                break;

            default:
                throw new IllegalArgumentException("Tipo de transa��o inv�lido. Use 'deposito' ou 'saque'.");
        }

        //callback para a empresa e notifica��o para o cliente
        callback();
        NotifiCliente(cliente);
    }
	
    private void callback() {
        System.out.println("Callback enviado para a empresa.");
    }
    private void NotifiCliente(Cliente cliente) {
        System.out.println("Notifica��o enviada para o cliente: " + cliente.getNome());
    }
    
    // M�todos de dep�sito e saque no saldo da empresa
    public void depositar(double valor) {
        this.setSaldo(this.getSaldo() + valor);
    }

    public void sacar(double valor) {
        this.setSaldo(this.getSaldo() - valor);
    }
}
