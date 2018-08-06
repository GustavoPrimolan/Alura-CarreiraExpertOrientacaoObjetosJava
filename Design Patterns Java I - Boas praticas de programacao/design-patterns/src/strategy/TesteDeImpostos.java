package strategy;

//A CALCULADORA DE IMPOSTOS RECEBE O PADRÃO DE PROJETOS STRATEGY
public class TesteDeImpostos {
	
	public static void main(String[] args) {
		Imposto iss = new ISS();
		Imposto icms = new ICMS();
		
		Orcamento orcamento = new Orcamento(500.0);
		
		CalculadorDeImpostos calculador = new CalculadorDeImpostos();
		
		calculador.realizaCalculoICMS(orcamento, iss);
		calculador.realizaCalculoICMS(orcamento, icms);
	}
	
}
