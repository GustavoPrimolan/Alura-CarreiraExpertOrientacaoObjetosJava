package strategy;

public class CalculadorDeImpostos {
	
	public void realizaCalculoICMS(Orcamento orcamento, Imposto impostoQualquer) {
			double icms = impostoQualquer.calcula(orcamento);
			System.out.println(icms);
	}
	
	
}
