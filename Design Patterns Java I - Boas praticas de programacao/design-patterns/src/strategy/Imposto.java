package strategy;

import chainofresponsibility.Orcamento;

public interface Imposto {
	
	double calcula(Orcamento orcamento);
	
}
