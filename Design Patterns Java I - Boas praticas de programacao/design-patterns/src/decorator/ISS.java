package decorator;

import decorator.Orcamento;

public class ISS extends Imposto{
	
	public ISS(Imposto outroImposto) {
		
	}
	
	@Override
	public double calcula(Orcamento orcamento) {
		return orcamento.getValor() * 0.06;
	}
	
}
