<h1>Seção 01 - A grande variedade de impostos e o padrão Strategy</h1>

<h2>Design Patterns</h2>
Classes e métodos gigantes? Cinco minutos para entender o que aquele método faz ou onde está o código que faz uma alteração simples? Diversas variáveis e diversos ifs e fors no mesmo método? Código complexo e obscuro? Toda vez que uma alteração aparece, você precisa mudar em 20 classes diferentes? Sim, problemas muito comuns do nosso dia-a-dia. Mas por que isso acontece?

Um fato que é conhecido sobre todo software é que ele, mais cedo ou mais tarde, vai mudar: novas funcionalidades aparecerão, outras deverão ser alteradas etc. O problema é que, geralmente, essas mudanças não são feitas de forma muito planejada. Por esse motivo, durante o desenvolvimento de um projeto de software, é bem comum a criação de código onde as responsabilidades se misturam e espalham por várias classes, fazendo com que a manutenção do código fique cada vez mais difícil, já que uma simples mudança pode obrigar o desenvolvedor a alterar diversas classes. Nesse cenário, temos uma situação onde o 'Design' das classes não está bom e é possível melhorá-lo.

Para atingir o objetivo da melhora do 'Design' e diminuir o custo de manutenção, existem algumas técnicas, onde podemos aplicar a orientação a objetos de uma maneira a simplificar o código escrito.

Resolveremos diversos dos problemas que vemos em códigos, como classes e métodos gigantes que fazem muita coisa, classes que precisam conhecer mais 10 outras classes para fazer seu trabalho, métodos complicados com muitos ifs e condições, mudanças que se propagam por várias classes e assim por diante.

Algumas das principais técnicas para atingir um bom 'Design' são tão comuns que foram catalogadas em um conjunto de alternativas para solucionar problemas de Design de código, chamados de Design Patterns, mais conhecido em português como os Padrões de Projetos, os quais, durante esse curso, aprenderemos a utilizar em um projeto. Um padrão de projeto nada mais é do que uma solução elegante para um problema que é muito recorrente em nosso dia-a-dia.

Mais importante do que entender como é a implementação de um padrão de projeto, é entender a motivação do padrão: em quais casos ele faz sentido e deve ser aplicado. Durante os próximos capítulos, observe o cenário do exemplo dado com atenção e o leve para o seu mundo e para o contexto de sua aplicação. Aquele cenário acontece? Se sim, então talvez o padrão de projeto ensinado naquele capítulo seja uma boa saída para controlar a crescente complexidade daquele código.

Durante todos os outros capítulos, faremos muitos exercícios. Todas as explicações serão baseadas em problemas do mundo real e você, ao final, utilizará os conhecimentos adquiridos para resolver os exercícios, que são desafiadores! Não se esqueça de prestar muita atenção ao cenário e ao problema proposto. Entenda a motivação de cada padrão para que você consiga levá-lo para os problemas do seu mundo!

Muitas regras e código complexo
Tomando como exemplo uma aplicação cujo objetivo é a criação de orçamentos, temos uma regra de negócio na qual os valores dos orçamentos podem ser submetidos à alguns impostos, como ISS, ICMS e assim por diante. Com isso, temos a simples classe que representa o orçamento, recebendo via construtor o seu valor: 

```java
public class Orcamento { 
	private double valor; 

	public Orcamento(double valor) { 
		this.valor = valor; 
	} 

	public double getValor() { 
		return valor; 
	} 
} 

```
Com isso, podemos criar novos orçamentos, instanciando objetos do respectivo tipo e caso queiramos calcular um imposto sobre seu valor, basta utilizarmos o atributo valor para isso. Assim, podemos estipular que o ICMS valha 10% e precisamos calculá-lo, baseado no valor do orçamento. Para isso, podemos ter a seguinte classe com um simples método para realizar o cálculo: 

```java
public class CalculadorDeImpostos { 
	public void realizaCalculo(Orcamento orcamento) { 
		double icms = orcamento.getValor() * 0.1; System.out.println(icms); // imprimirá 50.0 
	} 
} 
```

Podemos ainda querer calcular outro imposto, como o ISS, que é 6% do valor do orçamento. Com isso, adicionamos a nova regra ao código anterior. Mas devemos escolher qual o imposto que será calculado. Portanto, o método realizaCalculo deverá receber uma informação, indicando qual o imposto terá o cálculo realizado: 

```java
public class CalculadorDeImpostos { 
	public void realizaCalculo(Orcamento orcamento, String imposto) { 
		if( "ICMS".equals(imposto) ) { 
			double icms = orcamento.getValor() * 0.1; 
			System.out.println(icms); // imprimirá 50.0 
		} else if( "ISS".equals(imposto) ) { 
			double iss = orcamento.getValor() * 0.06; 
			System.out.println(iss); // imprimirá 30.0 
		} 
	} 
} 
```

Note que uma das consequências do código que acabamos de criar, é que espalhamos os cálculos e nossas regras de negócio. Dessa maneira, não temos nenhum encapsulamento de nossas regras de negócio e elas se tornam bastante suscetíveis a serem replicadas em outros pontos do código da aplicação. Por que não encapsulamos as regras dos cálculos em uma classe especializada para cada imposto?
Encapsulando o comportamento
Ao invés de mantermos as regras espalhadas pela nossa aplicação, podemos encapsulá-las em classes cujas responsabilidades sejam realizar os cálculos. Para isso, podemos criar as classes ICMS e ISS cada um com seu respectivo método para calcular o valor do imposto de acordo com o orçamento.

```java

      public class ICMS {

          public double calculaICMS(Orcamento orcamento) {
              return orcamento.getValor() * 0.1;
          }

      }
      public class ISS {

          public double calculaISS(Orcamento orcamento) {
              return orcamento.getValor() * 0.06;
          }

      }
```

Agora temos as duas classes que separam a responsabilidade dos cálculos de impostos, com isso, podemos utilizá-las na classe CalculadorDeImpostos da seguinte maneira:

```java
      public class CalculadorDeImpostos {

          public void realizaCalculo(Orcamento orcamento, String imposto) {

          if( "ICMS".equals(imposto) ) {

            double icms = new ICMS().calculaICMS(orcamento);
            System.out.println(icms); // imprimirá 50.0

          } else if( "ISS".equals(imposto) ) {

            double iss = new ISS().calculaISS(orcamento);
            System.out.println(iss); // imprimirá 30.0

            }
          }
      }

```
Agora o código está melhor, mas não significa que esteja bom. Um ponto extremamente crítico desse código é o fato de que quando quisermos adicionar mais um tipo diferente de cálculo de imposto em nosso calculador, teremos que alterar essa classe adicionando mais um bloco de if, além de criarmos a classe que encapsulará o cálculo do novo imposto. Parece bastante trabalho.

Eliminando os condicionais com polimorfismo e o pattern Strategy
O que queremos em nosso código é não realizar nenhum condicional, ou seja, não termos mais que fazer ifs dentro do CalculadorDeImpostos. Dessa forma, não devemos mais receber a String com o nome do imposto, no qual realizamos os ifs. Mas como escolheremos qual o imposto que deve ser calculado?

Uma primeira possibilidade é criar dois métodos separados na classe CalculadorDeImpostos. Um para o ICMS e outro para o ISS, dessa forma teremos:
```java

      public class CalculadorDeImpostos {

          public void realizaCalculoICMS(Orcamento orcamento) {

            double icms = new ICMS().calculaICMS(orcamento);
            System.out.println(icms);

          }

          public void realizaCalculoISS(Orcamento orcamento) {

            double iss = new ISS().calculaISS(orcamento);
            System.out.println(iss);

          }

      }

```
No entanto, agora só transferimos o problema dos vários ifs para vários métodos. O que não resolve o problema. O próximo passo para conseguirmos melhorar essa solução é termos um único método, genérico, que consegue realizar o cálculo para qualquer imposto, sem fazer nenhum if dentro dele.

```java
      public class CalculadorDeImpostos {

          public void realizaCalculo(Orcamento orcamento) {

            double icms = new ICMS().calculaICMS(orcamento); 
            // Mas e se quisermos outro imposto?

            System.out.println(icms);

          }

      }

```
Agora estamos presos ao ICMS. Precisamos que nosso código fique flexível o bastante para utilizarmos diferentes impostos na realização do cálculo. Uma possibilidade para resolvermos esse problema é, ao invés de instanciarmos o imposto que desejamos dentro do método, recebermos uma instância do Imposto que queremos utilizar, como no código seguinte:

```java

      public class CalculadorDeImpostos {

          public void realizaCalculo(Orcamento orcamento, Imposto imposto) {

            double valor = imposto.calcula(orcamento); 

            System.out.println(valor);

          }

      }

```
No entanto, não temos o tipo Imposto em nossa aplicação e além disso, nesse tipo precisamos passar uma instância de ISS e ICMS. Para isso, podemos criar uma interface chamada Imposto e fazermos as classes ISS e ICMS a implementar.
```java

      public interface Imposto {
          double calcula(Orcamento orcamento);
      }

      public class ICMS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.1;
          }

      }

      public class ISS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.06;
          }

      }

```
E agora o nosso CalculadorDeImpostos está pronto para ser utilizado e flexível o bastante para receber diferentes tipos (ou "estratégias") de impostos. Um código que demonstra essa flexibilidade é o seguinte:

```java
      public class TesteDeImpostos {

          public static void main(String[] args) {
              Imposto iss = new ISS();
              Imposto icms = new ICMS();

              Orcamento orcamento = new Orcamento(500.0);

          CalculadorDeImpostos calculador = new CalculadorDeImpostos();

          // Calculando o ISS
          calculador.realizaCalculo(orcamento, iss);

          // Calculando o ICMS        
          calculador.realizaCalculo(orcamento, icms);
          }
      }

```
Agora, com um único método em nosso CalculadorDeImpostos, podemos realizar o cálculo de diferentes tipos de impostos, apenas recebendo a estratégia do tipo do imposto que desejamos utilizar no cálculo.

Quando utilizamos uma hierarquia, como fizemos com a interface Imposto e as implementações ICMS e ISS, e recebemos o tipo mais genérico como parâmetro, para ganharmos o polimorfismo na regra que será executada, simplificando o código e sua evolução, estamos usando o Design Pattern chamado Strategy.

Repare que a criação de uma nova estratégia de cálculo de imposto não implica em mudanças no código escrito acima! Basta criarmos uma nova classe que implementa a interface Imposto, que nosso CalculadorDeImpostos conseguirá calculá-lo sem precisar de nenhuma alteração!

<h2>O que é um padrão de projeto?</h2>
Um padrão de projeto é uma solução elegante para um problema que é recorrente no dia-a-dia do desenvolvedor.

Ou seja, por mais que desenvolvamos projetos diferentes, muitos dos problemas se repetem. Padrões de projeto são soluções elegantes e flexíveis para esses problemas.

<h2>Estudando a motivação dos padrões</h2>

O que é mais importante ao estudar um padrão de projetos?
R: Entender a motivação do padrão de projeto e qual problema ele resolve.

O mais importante ao estudar padrões de projeto é entender qual a real motivação do padrão, e quando ele deve ser aplicado.

As implementações são menos importantes, pois eles podem variar. O importante é resolver o problema de maneira elegante, usando a ideia por trás do padrão como um guia na implementação. Uma afirmação muito comum sobre padrões de projeto é que você os aplica mil vezes, e as mil vezes você termina com uma implementação diferente do mesmo padrão.

<h2>O que é um padrão de projeto?</h2>

Você já conhece padrões de projeto? Em sua opinião, o que é um padrão de projeto?
<br/ >
R: Um padrão de projeto é uma solução elegante para um problema que é recorrente no dia-a-dia do desenvolvedor.
Ou seja, por mais que desenvolvamos projetos diferentes, muitos dos problemas se repetem. Padrões de projeto são soluções elegantes e flexíveis para esses problemas.

<h2>Estudando a motivação dos padrões</h2>
O que e mais importante ao estudar um padrão de projeto?
R: Entender a motivação do padrão de projeto e qual problema ele resolve.
As implementações são menos importantes, pois eles podem variar. O importante é resolver o problema de maneira elegante, usando a ideia por trás do padrão como um guia na implementação. Uma afirmação muito comum sobre padrões de projeto é que você os aplica mil vezes, e as mil vezes você termina com uma implementação diferente do mesmo padrão.


<h2>Implementando um Strategy</h2>
Crie todo o mecanismo para flexibilizar a criação de diferentes estratégias de impostos, igual visto no vídeo. Crie a interface Imposto, e as estratégias ICMS e ISS. O ISS deve ser 6% do valor do orçamento, e o ICMS deve ser 5% do valor do orçamento mais o valor fixo de R$ 50,00.

Crie a classe Orcamento, que tem como atributo um valor. Crie um construtor que recebe esse valor, e um getter para devolvê-lo.

Crie a classe CalculadorDeImpostos, que recebe um Orcamento e um Imposto. Essa classe calcula o imposto usando a estratégia recebida e imprime o resultado na tela.

Vamos começar com a classe Orçamento.

```java
    public class Orcamento {

        private double valor;

        public Orcamento(double valor) {
            this.valor = valor;
        }

        public double getValor() {
            return valor;
        }

    }
```
Em seguida, vamos criar a abstração Imposto. Todo imposto deverá implementar essa interface, já que a interface é a que será utilizada para fazermos o Strategy.

```java
      public interface Imposto {
          double calcula(Orcamento orcamento);
      }
```
Agora vamos fazer as implementações concretas dos impostos ICMS e ISS, ambos implementando a interface Imposto, cada um com sua regra específica:

```java
      public class ICMS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.05 + 50;
          }

      }

      public class ISS implements Imposto {

          public double calcula(Orcamento orcamento) {
              return orcamento.getValor() * 0.06;
          }

      }
```
Vamos criar agora uma classe cliente, que receberá a estratégia, e a utilizará. Veja a classe abaixo: ela recebe a estratégia, e a utiliza para calcular o imposto; em seguida, apenas exibe o resultado na tela. Em uma implementação mais real, essa classe poderia fazer algo mais útil com o resultado.


```java
      public class CalculadorDeImposto {

        public void calcula(Orcamento orcamento, Imposto estrategiaDeImposto) {
          double resultado = estrategiaDeImposto.calcula(orcamento);
          System.out.println(resultado);
        }
      }
```

<h2>Estratégia para o imposto ICCC</h2>
Implemente mais uma estratégia de cálculo de imposto.

Crie o imposto que se chama ICCC, que retorna 5% do valor total caso o orçamento seja menor do que R$ 1000,00 reais, 7% caso o valor esteja entre R$ 1000 e R$ 3000,00 com os limites inclusos, ou 8% mais 30 reais, caso o valor esteja acima de R$ 3000,00.

Escreva um método main que testa sua implementação.

Basta criar a classe ICCC, que implementa a interface Imposto. Em seguida, uma classe de teste que invocará essa estratégia, passando uma orçamento qualquer de exemplo.

```java
     public class ICCC implements Imposto {

        public double calcula(Orcamento orcamento) {
          if(orcamento.getValor() < 1000) {
            return orcamento.getValor() * 0.05;
          }
          else if (orcamento.getValor() <= 3000) {
            return orcamento.getValor() * 0.07;
          }
          else {
            return orcamento.getValor() * 0.08 + 30;
          }
        }
      }

     public  class Teste {
        public static void main(String[] args) {
          Orcamento reforma = new Orcamento(500.0);

          Imposto novoImposto = new ICCC();
          System.out.println(novoImposto.calcula(reforma));
        }
      }

```

<h2>Utilidade da classe CalculadoraDeImpostos</h2>

A classe CalculadoraDeImpostos agora tem pouco código. Ela simplesmente invoca a estratégia de imposto e imprime através de um System.out.println() na tela.

Será que ela precisa continuar existindo?

<br/>
Nesse exemplo, como a CalculadoraDeImpostos apenas exibe uma mensagem na tela, ela não é necessária. Mas agora imagine que, além de calcular o imposto, essa classe precisasse fazer mais alguma coisa, como por exemplo, alterar o status do Orçamento, ou notificar algum outro objeto desse valor calculado. Nesse caso, precisaríamos de uma classe para conter essa regra de negócios, e a classe CalculadoraDeImpostos seria uma boa candidata.

Repare que não há resposta correta; tudo sempre depende do contexto, do problema que estamos resolvendo.

<h2>Estratégias para Investimentos</h2>

Muitas pessoas optam por investir o dinheiro das suas contas bancárias. Existem diversos tipos de investimentos, desde investimentos conservadores até mais arrojados.

Independentemente do investimento escolhido, o titular da conta recebe apenas 75% do lucro do investimento, pois 25% é imposto.

Implemente um mecanismo que invista o valor do saldo dela em um dos vários tipos de investimento e, dado o retorno desse investimento, 75% do valor é adicionado no saldo da conta.

Crie a classe RealizadorDeInvestimentos que recebe uma estratégia de investimento, a executa sobre uma conta bancária, e adiciona o resultado seguindo a regra acima no saldo da conta.

Os possíveis tipos de investimento são:

"CONSERVADOR", que sempre retorna 0.8% do valor investido;
"MODERADO", que tem 50% de chances de retornar 2.5%, e 50% de chances de retornar 0.7%;
"ARROJADO", que tem 20% de chances de retornar 5%, 30% de chances de retornar 3%, e 50% de chances de retornar 0.6%.
Para verificar se a chance é maior que 30%, por exemplo, use:

  boolean escolhido = new java.util.Random().nextDouble() > 0.30;

Vamos começar modelando nossa classe Conta, com um saldo e um método deposita():

```java
      class Conta {
        private double saldo;

        public void deposita(double valor) {
          this.saldo += valor;
        }

        public double getSaldo() {
          return this.saldo;
        }
      }
```

Em seguida vamos criar a interface Investimento, e suas implementações concretas:

```java
      interface Investimento {
        double calcula(Conta conta);
      }

      class Conservador implements Investimento{
        public double calcula(Conta conta) {
          return conta.getSaldo() * 0.008;
        } 
      }

      class Moderado implements Investimento {
        private Random random;

        public Moderado() {
          this.random = new Random();
        }

        public double calcula(Conta conta) {
          if(random.nextInt(2) == 0) return conta.getSaldo() * 0.025;
          else return conta.getSaldo() * 0.007;
        }
      }

      class Arrojado implements Investimento {
        private Random random;

        public Arrojado() {
          this.random = new Random();
        }

        public double calcula(Conta conta) {
          int chute = random.nextInt(10);
          if(chute >= 0 && chute <= 1) return conta.getSaldo() * 0.05;
          else if (chute >= 2 && chute <= 4) return conta.getSaldo() * 0.03;
          else return conta.getSaldo() * 0.006;
        }
      }
```

Agora vamos fazer a classe RealizadorDeInvestimentos, que receberá uma conta e um investimento, e depositará o valor do investimento na conta:
```java
      class RealizadorDeInvestimentos {
        public void realiza(Conta conta, Investimento investimento) {
          double resultado = investimento.calcula(conta);

          conta.deposita( resultado * 0.75 );
          System.out.println ( "Novo saldo: " + conta.getSaldo());
        }
      }
```

<h2>Strategy o tempo todo?</h2>

Se eu tenho apenas uma única estratégia, faz sentido implementar o Strategy?

Depende do problema. Lembre-se que códigos simples são mais fáceis de manter sempre. Se você só tem uma estratégia, talvez faça mais sentido você não usar o Strategy, já que você estaria flexibilizando algo sem necessidade.

Mas, se é nítido que novas estratégias aparecerão, com certeza um Strategy é mais limpo do que um conjunto de ifs, conforme discutimos nesse capítulo.

Novamente, avalie o contexto e veja se o padrão de projeto vai trazer benefícios para aquele cenário.

<h2>Quando usar o Strategy?</h2>
Quando devemos aplicar Strategy? Cite um exemplo que você viveu que poderia ter utilizado esse padrão.
O padrão Strategy é muito útil quando temos um conjunto de algoritmos similares, e precisamos alternar entre eles em diferentes pedaços da aplicação. No exemplo do vídeo, temos diferentes maneiras de calcular o imposto, e precisamos alternar entre elas.

O Strategy nos oferece uma maneira flexível para escrever diversos algoritmos diferentes, e de passar esses algoritmos para classes clientes que precisam deles. Esses clientes desconhecem qual é o algoritmo "real" que está sendo executado, e apenas mandam o algoritmo rodar. Isso faz com que o código da classe cliente fique bastante desacoplado das implementações concretas de algoritmos, possibilitando assim com que esse cliente consiga trabalhar com N diferentes algoritmos sem precisar alterar o seu código.

------------------------------------------------------------------------------------
<h1>Seção 02 - Muitos Descontos e o Chain of Responsibility</h1>

Nosso orçamento pode receber um desconto de acordo com o tipo da venda que será efetuada. Por exemplo, se o cliente comprou mais de 5 ítens, ele recebe 10% de desconto; se ele fez uma compra casada de alguns produtos, recebe 5% de desconto, e assim por diante.

Em uma implementação tipicamente procedural, teríamos algo do tipo:

```java
    public class CalculadorDeDescontos {
      public double calcula(Orcamento orcamento) {
        // verifica primeira regra de possível desconto
        if(orcamento.getItens().size() > 5) {
          return orcamento.getValor() * 0.1;
        }

        // verifica segunda regra de possível desconto
        else if(orcamento.getValor() > 500.0) {
          return orcamento.getValor() * 0.07;
        }

        // verifica terceira, quarta, quinta regra de possível desconto ...
        // um monte de ifs daqui pra baixo
      }
    }
```

Ver esse monte de ifs em sequência nos lembra o capítulo anterior, na qual extraímos cada um deles para uma classe específica. Vamos repetir o feito, já que com classes menores, o código se torna mais simples de entender:
```java
    public class DescontoPorMaisDeCincoItens {
      public double desconta(Orcamento orcamento) {
        if(orcamento.getItens().size() > 5) {
          return orcamento.getValor() * 0.1;
        }
        else {
          return 0;
        }
      }
    }
    public class DescontoPorMaisDeQuinhentosReais {
      public double desconta(Orcamento orcamento) {
        if(orcamento.getValor() > 500) {
          return orcamento.getValor() * 0.07;
        }
        else {
          return 0;
        }
      }
    }
```

Veja que tivemos que sempre colocar um return 0;, afinal o método precisa sempre retornar um double. Vamos agora substituir o conjunto de ifs na classe CalculadorDeDesconto. Repare que essa classe procura pelo desconto que deve ser aplicado; caso o anterior não seja válido, tenta o próximo.
```java
    public class CalculadorDeDescontos {
      public double calcula(Orcamento orcamento) {
        // vai chamando os descontos na ordem até que algum deles dê diferente de zero...
        double desconto = new DescontoPorMaisDeCincoItens().desconta(orcamento);
        if(desconto == 0) 
          desconto = new DescontoPorMaisDeQuinhentosReais().desconta(orcamento);
        if(desconto == 0) 
          desconto = new ProximoDesconto().desconta(orcamento);
        // ...

        return desconto;
      }
    }
```

O código já está um pouco melhor. Cada regra de negócio está em sua respectiva classe. O problema agora é como fazer essa sequência de descontos ser aplicada na ordem, pois precisamos colocar mais um if sempre que um novo desconto aparecer.

Precisávamos fazer com que um desconto qualquer, caso não deva ser executado, automaticamente passe para o próximo, até encontrar um que faça sentido. Algo do tipo:
```java
      public class DescontoPorMaisDeQuinhentosReais {
        public double desconta(Orcamento orcamento) {
          if(orcamento.getValor() > 500) {
            return orcamento.getValor() * 0.07;
          }
          else {
            return PROXIMO DESCONTO;
          }
        }
      }
```
Todos os descontos têm algo em comum. Todos eles calculam o desconto dado um orçamento. Podemos criar uma abstração para representar um desconto genérico. Por exemplo:

```java
      public interface Desconto {
        double desconta(Orcamento orcamento);
      }

      public class DescontoPorMaisDeCincoItens implements Desconto { ... }
      public class DescontoPorMaisDeQuinhentosReais implements Desconto { ... }
```
Para fazer aquele código funcionar agora, basta fazer com que todo desconto receba um próximo desconto! Observe o código abaixo:

```java
      public interface Desconto {
        double desconta(Orcamento orcamento);
        void setProximo(Desconto proximo);
      }

      public class DescontoPorMaisDeCincoItens implements Desconto {
        private Desconto proximo;

        public void setProximo(Desconto proximo) {
          this.proximo = proximo;
        }

        public double desconta(Orcamento orcamento) {
          if(orcamento.getItens().size() > 5) {
            return orcamento.getValor() * 0.1;
          }
          else {
            return proximo.desconta(orcamento);
          }
        }
      }
      public class DescontoPorMaisDeQuinhentosReais implements Desconto {
        private Desconto proximo;

        public void setProximo(Desconto proximo) {
          this.proximo = proximo;
        }

        public double desconta(Orcamento orcamento) {
          if(orcamento.getValor() > 500) {
            return orcamento.getValor() * 0.07;
          }
          else {
            return proximo.desconta(orcamento);
          }
        }
      }
```

Ou seja, se o orçamento atende a regra de um desconto, o mesmo já calcula o desconto. Caso contrário, ele passa para o "próximo" desconto, qualquer que seja esse próximo desconto.

Basta agora plugarmos todas essas classes juntas. Veja que um desconto recebe um "próximo". Para o desconto, pouco importa qual é o próximo desconto. Eles estão totalmente desacoplados um do outro!

```java
      public class CalculadorDeDescontos {
        public double calcula(Orcamento orcamento) {
          Desconto d1 = new DescontoPorMaisDeCincoItens ();
          Desconto d2 = new DescontoPorMaisDeQuinhentosReais();

          d1.setProximo(d2);

          return d1.desconta(orcamento);
        }
      }

      public class TestaDescontos {
        public static void main(String[] args) {
          CalculadorDeDescontos calculador = new CalculadorDeDescontos();

              Orcamento orcamento = new Orcamento(500.0);
              orcamento.adicionaItem(new Item("CANETA", 250.0));
              orcamento.adicionaItem(new Item("LAPIS", 250.0));

              double desconto = calculador.calcula(orcamento);

              System.out.println(desconto);

        }
      }
```
Esses descontos formam como se fosse uma "corrente", ou seja, um ligado ao outro. Daí o nome do padrão de projeto: Chain of Responsibility. A ideia do padrão é resolver problemas como esses: de acordo com o cenário, devemos realizar alguma ação. Ao invés de escrevermos código procedural, e deixarmos um único método descobrir o que deve ser feito, quebramos essas responsabilidades em várias diferentes classes, e as unimos como uma corrente.

Nosso problema é só fazer o algoritmo parar agora. Se ele não encontrar nenhum desconto válido, o valor deve ser 0. Vamos criar a classe SemDesconto, que será o fim da corrente.

```java
      public class SemDesconto implements Desconto {

          public double desconta(Orcamento orcamento) {
              return 0;
          }

          public void setProximo(Desconto desconto) {
              // nao tem!
          }
      }

      public class CalculadorDeDescontos {
        public double calcula(Orcamento orcamento) {
          Desconto d1 = new DescontoPorMaisDeCincoItens();
          Desconto d2 = new DescontoPorMaisDeQuinhentosReais();
          Desconto d3 = new SemDesconto();

          d1.setProximo(d2);
          d2.setProximo(d3);

          return d1.desconta(orcamento);
        }
      }
```

A classe SemDesconto não atribui o próximo desconto, pois ela não possui um próximo. Na realidade, ela é o ponto final da nossa cadeia de responsabilidades.

Note também que nossa classe Orcamento cresceu, e agora recebe ítens também. A mudança é simples:

```java
      public class Orcamento {

          private double valor;
          private List<Item> itens;

          public Orcamento(double valor) {
              this.valor = valor;
              this.itens = new ArrayList<Item>();
          }

          public double getValor() {
              return valor;
          }

          public List<Item> getItens() {
              return Collections.unmodifiableList(itens);
          }

          public void adicionaItem(Item item) {
              itens.add(item);
          }

      }
      public class Item {

          private String nome;
          private double valor;

          public Item(String nome, double valor) {
              this.nome = nome;
              this.valor = valor;
          }

          public String getNome() {
              return nome;
          }

          public double getValor() {
            return valor;
          }

      }
```

<h2>Desconto por Venda Casada</h2>

Implemente mais uma estratégia de desconto: 5% se tivermos LAPIS e CANETA na mesma compra.

Para isso, crie a classe DescontoPorVendaCasada. Adicione essa classe na cadeia, para suceder o DescontoPorMaisDeQuinhentosReais.

Para verificar se um item está no orçamento você pode usar uma função como a seguinte:

```java
        private boolean existe(String nomeDoItem, Orcamento orcamento) {
            for (Item item : orcamento.getItens()) {
                if(item.getNome().equals(nomeDoItem)) return true;
            }
            return false;
        }
```
Parecido com o exercício anterior, nosso desconto implementa a interface Desconto e passa para o próximo desconto caso o orçamento não deva receber esse desconto.


```java
    public class DescontoPorVendaCasada implements Desconto {

        private Desconto proximo;

        public double desconta(Orcamento orcamento) {
            if(aconteceuVendaCasadaEm(orcamento)) return orcamento.getValor() * 0.05;
            else return proximo.desconta(orcamento);
        }

        private boolean aconteceuVendaCasadaEm(Orcamento orcamento) {
            return existe("CANETA", orcamento) && existe("LAPIS", orcamento);
        }

        private boolean existe(String nomeDoItem, Orcamento orcamento) {
            for (Item item : orcamento.getItens()) {
                if(item.getNome().equals(nomeDoItem)) return true;
            }
            return false;
        }

        public void setProximo(Desconto proximo) {
            this.proximo = proximo;
        }

    }

    public class TestaCorrente {
      public static void main(String[] args) {
        Desconto d1 = new DescontoPorCincoItens();
        Desconto d2 = new DescontoPorMaisDeQuinhentosReais();
        Desconto d3 = new DescontoPorVendaCasada();
        Desconto d4 = new SemDesconto();

        d1.setProximo(d2);
        d2.setProximo(d3);
        d3.setProximo(d4);

    Item lapis = new Item("LAPIS", 15.00);
        Item caneta = new Item("CANETA", 15.00);
        Item borracha = new Item("borracha", 15.00);

        Orcamento orcamento = new Orcamento(500.0);
        orcamento.adiciona(caneta);
        orcamento.adiciona(borracha);
        orcamento.adiciona(lapis);        

        double desconto = d1.desconta(orcamento);
        System.out.println(desconto);
      }
    }
```

<h2>Utiidade da classe CalculadorDeDescontos</h2>

Será que a classe CalculadorDeDescontos é realmente necessária? Discuta a utilidade dela.

Agora que implementamos o Chain of Responsibility, temos cada uma das responsabilidades separadas em uma classe, e uma forma de unir essa corrente novamente. Veja a flexibilidade que o padrão nos deu: podemos montar a corrente da forma como quisermos, e sem muitas complicações.

Mas precisamos de uma classe que monte essa corrente na ordem certa, com todos os descontos necessários. Por isso que optamos pela classe CalculadorDeDescontos. Ela poderia ter qualquer outro nome como CorrenteDeDescontos, e assim por diante, mas fato é que em algum lugar do seu código você precisará montar essa corrente.