package decorator;

import decorator.Orcamento;

public abstract class Imposto {
	
	protected Imposto outroImposto;
	
	public Imposto(Imposto outroImposto) {
		this.outroImposto = outroImposto;
	}
	
	public Imposto() {
	
	}
	
	public abstract double calcula(Orcamento orcamento);
	
}
