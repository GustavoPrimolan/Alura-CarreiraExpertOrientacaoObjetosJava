package templatemethod;

public class Main {
	
	public static void main(String[] args) {
		Orcamento orcamento = new Orcamento(500.00);
		TemplateDeImpostoCondicional ikcv = new IKCV();
		TemplateDeImpostoCondicional icpp = new ICPP();
		
		System.out.println(ikcv.calcula(orcamento));
		
	}
}
