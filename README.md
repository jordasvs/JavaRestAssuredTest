## Tecnologias utilizadas no template

- Java JDK 8 - [Java JDK 8](https://www.oracle.com/br/java/technologies/javase/javase8-archive-downloads.html)
- IDE de desenvolvimento (Sugestão) - [JetBrains IntelliJ IDEA Community](https://www.jetbrains.com/pt-br/idea/download/#section=windows)
- Framework de testes automatizados de API - [RestAssured](https://rest-assured.io/)
- Relatório de teste - [Extent Reports](https://www.extentreports.com/)
- Orquestrador de testes - [TestNG](https://testng.org/doc/)
- Biblioteca de geração de dados falsos e randômicos - [Java Faker](https://github.com/DiUS/java-faker)
- Facilitador de criação de objetos Java - [Lombok](https://projectlombok.org/)

## Abrindo o projeto (Baseado no uso do IntelliJ)

1. Baixar o projeto
2. Abrir IntelliJ 
3. Clicar em "File"
4. Clicar em "Open"
5. Selecinar a pasta raiz do projeto
6. Clicar em "OK"
7. O projeto será carregado e as dependências serão baixadas automaticamente. Aguardar até o fim.

## Arquitetura:

A arquitetura padrão composta por:

- `bases`: Pacote que contém as classes pai, que definem comportamentos ou contém implementações que serão extendidas pelas classes filhas. As já existentes são:
  - **RequstRestBase:** Deve ser herdada por toda classe "request" que representará uma requisição de uma API REST
  - **RequstSoapBase:** Deve ser herdada por toda classe "request" que representará uma requisição de uma API SOAP
  - **RequstRestBase:** Deve ser herdada por toda classe "test". 
- `databasesteps`: Pacote que contém classes que contém métodos que executam alguma ação no banco de dados, tanto para retornar informações quanto para realizar alguma manipulação (UPDATE, DELETE ou execução de procedure);
- `enums`: Pacote que contém os enums utilizados no projeto. A já existente é:
  - **AuthenticationType:** Contém os tipos de autenticações utilizadas no projeto
- `jsonObjects`: Paconte que contém as classes que representam os objetos json que serão utilizados para serializar os contratos (body) das requisições;
- `jsons`: Pacote que contém os arquivos .json que serão utilizados para serializar os contratos (body) das requisições;
- `queries`: Pacote que contém as queries utilizdas nos DataBaseSteps para manipulação do bando de dados;
- `requests`: Pacote que contém as classes que representam as requisições (endpoints ou serviços) da API que será testada;
- `steps`: Paconte de classes que contém métodos que executam alguma request da API ou e uma API externa para serem utilizados como arranjo do teste;
- `tests`: Pacote de classes que contém os métodos de teste;
- `utils`: Pacote que contém classes com utilitários das teconlogias utilizadas no template, as já existentes são:
  - **DataBaseUtils:** Contém os métodos disponíveis para fazer conexão ao banco de dados (configurado para MS SQL Server) e executar ações no banco como "executeQuery" e "getQueryResults"
  - **ExtentReportsUtils:** Contém os métodos que implementam o relatório de testes.
  - **GeneralUtils:** Contém métodos que podem ser utilizados em qualquer ponto do código e não estão ligados à uma tecnologia específica, ex: métodos para formatar json, métodos para manipulação de datas, métodos para gerar cpf etc.
  - **RestAssuredUtils:** Contém os métodos que são o motor de execução das requisições através do RestAssured
- `GlobalParameters.java`: Classe que representa os parâmetros globais do projeto;
- `globalParameters.properties`: Arquivo de configuração dos parâmetros globais do projeto;
- `pom.xml`: Arquivo de configuração do Maven, utilizado para incluir bibliotecas no projeto e definir os procedimentos de build;
- `suiteTestes.xml`: Arquivo que representa a suíte de testes do projeto. São as classes e/ou tags de testes definidas neste arquivo que serão executadas pelo Maven (que será a forma de execução em um CI/CD);

## Configurando variáveis globais/de ambiente dos testes:
O arquivo globalParameters.properties contém as variáveis globais e de ambientes utilziadas pelos testes. 
Essas variáveis são aquelas que devem ser alteradas de acordo com o ambiente de execução do teste e ou alteração do projeto.
É um arquivo simples de chave e valor (chave=valor) que é um padrão de aplicações desenvolvidas em Java.
É essencial para facilitar a configuração dos testes dentro de ferramentas de integração/deploy contínuo.

**Pontos de atenção:**
- Toda variável que representa um parâmetro de ambiente deve ser criada para todos os ambientes disponíveis no projeto
- Ao adicionar uma nova variável no arquivo globalParameters.properties é necessário adicioanr também uma variável que represente na classe "GlobalParameters.java" e associá-la ao valor do arquivo .properties dentro do construtor da mesma (vide exemplos já implementados)
- A classe de GlobalParameters é carregada no hook "@BeforeSuite" da classe TestBase

## Mapeando as requisições:
Cada requsição/endpoint/serviço da API em teste é representado por uma classe. 
Nesta classe serão definidos os atributos específicos da requisição como: parãmetros, parâmetros de query, body, verbo (GET, POST, DELETE etc), headers e cookies.
Todas essas configurações padrões devem estar no construtor da classe, exceto o body que terá um método específico para configuração de acordo com a forma de serializaçaõ escolhida.
Para melhor exemplo vide classes "PostSalvarRequest" e "GetObterPorCPFRequest"

- Configurações de requests disponíveis:
  - **url** (String): usado somente na necessidade de sobrescrever a url default usada no projeto (configurada no globalParameters.properties)
  - **requestService** (String): request/endpoint/serviço da requisição. Sempre começa com "/"
  - **method** (io.restassured.http.Method): verbo que representa a requisição (GET, POST, DELETE etc)
  - **jsonBody** (Object): contém o corpo/contrato da requisição (quando existir). Pode ser de qualquer tipo de objeto Java (inclusive String) e deve ser configurado através do método "setJsonBody" contido na classe
  - **headers** (java.util.Map): configura headers específicos para a request através do método "put", passando chave e valor do header a ser adicionado, ex: headers.put("chave", "valor")
  - **cookies** (java.util.Map): configura cookies específicos para a request através do método "put", passando chave e valor do cookie a ser adicionado, ex: cookies.put("chave", "valor")
  - **queryParameters** (java.util.Map): configura parâmetros do tipo "query" da request através do método "put", passando chave e valor do parâmetro a ser adicionado, ex: queryParameters.put("chave", "valor")

- Parâmetros de URL
  - Os parâmetros de URL devem ser concatenados junto ao requestService, exemplo: **requestService = "/retornaPessoa/"+nome** (onde "nome" é uma variável recebida pelo construtor da Classe)

**Observações gerais**
- Não é responsabilidade da request manter parâmetros de teste, ou seja, todos os valores que serão passados para a request devem ser recebidos por uma variável no construtor e associados aos devidos parâmetros no corpo do construtor (exceto jsonBody).
- Em caso de dúvida consulte os exemplos disponíveis no pacote "requests"

## Serializando objetos json:
Serialização é o processo de transformar os dados do corpo da requisição no formato json requerido (contrato)
Existem várias abordágens para serialização de jsons. O template oferece suporte à todas, mas como exemplo, estão implementadas duas formas:

- **Serialização através de arquivo .json:** Nesta abordagem se utliza o arquivo json com o contrato da requisição (body), lendo esse arquivo para uma variável do tipo "String" e substiindo valores dos parâmetros através do método "replace()". Vide exemplo nas classes "PostSalvarRequest" e "PostSalvarTests".
*Existe uma limitação com essa abordagem, pois objetos e arrays dentro do json ficam estáticos sendo necessário um outro arquivo para representar um contrato com um ou mais objetos e/ou arrays. Recomenda-se utilizar só em casos de contratos com estruras mais simples e com poucos dados.*
- **Serialização através de objetos Java:** Nesta abordagem são utilizados objetos Java que representam a estrutura do contrato (body). Um contrato pode ser representado por um ou vários objetos. Se faz necessário mais de um objeto quando o body é composto por um ou mais objetos.
para facilitar a criação dos objetos Java que representam o contrato (body) utilizamos o "Lombok" que ajuda na criação dos contrutores, getters e setters das classes. 
Outro facilitador é o [Json2Java](https://codebeautify.org/json-to-java-converter) que já cria os objetos Java de acordo com o json passado como parâmetro. *OBS: O Json2Java tem como output também os getters e setters para as classes, que devem ser ignorados no template devido ao uso do Lombok*

## Configurando autenticações:
- **Autenticação com usuário e senha**
Existem alguns mecanismos que requerem somente usuário e senha para realizar autenticação. Alguns deles já estão implementados no template como "Basic authentication" e "Preempitive authentication".
Caso seja necesária algum outro tipo de autenticação deste tipo, esta deve ser implementada dentro do método "executeRequest()" da classe "RestAssuredUtils".
Para configurar autenticação com usuário e senha:
  - Alterar o parâmetro "authenticationType" na classe "TestBase" para o tipo desejado (o tipo é definido pelo enum "AuthenticationType")
  - Inserir valores para os parâmetros "authenticator.user" e "authenticator.password" no arquivo "globalParameters.properties"
  - Caso a autenticação seja necessária somente para requisições/endpoints/serviços específicos dentro da API em teste, o parâmetro "authenticationType" deve ser configurado no construtor da classe que representa a requisição.

- **Autenticação com token**
Autenticação com token normalmente é realizada da seguinte forma:
  - Se faz uma requisição à um serviço/endpoint informando usuário e senha
  - Na resposta da requisição existe o parâmetro "token"
  - Se extrai o parâmetro token da resposta e o utiliza como header das requisições que necessitam da authenticação.

   Para geração deste token, fazemos um passo (step) que executa a request e o chamamos na classe "TestBase" comforme premissa de expiração do token.
   Este step deve associar o valor do token à variável global (globalParameters.properties) "token" (exemplo na classe "AuthenticacaoSteps").
  - Caso token tenha um tempo de expiração muito curto (menor que o tempo de execução de toda bateria de teste), o passo de geração do token deve ser chamdo dentro do método "beforeSuíte()" da classe "TestBase"
  - Caso token tenha um tempo de expiração longo (maior que o tempo de execução de toda bateria de teste), o passo de geração do token deve ser chamdo dentro do método "beforeMethod()" da classe "TestBase"

  Deve-se também adicionar o token no header das requisições:
  - Caso seja necessário para todas as requisições da API em teste, deve ser adicionada a linha de código "headers.put("Authorization", GlobalParameters.TOKEN);" no construtor da classe "RequestRestBase"
  - Caso seja necessário para requisições específicas da API em teste, deve ser adicionada a linha de código "headers.put("Authorization", GlobalParameters.TOKEN);" no construtor da classe que representa a request
## Configurando headers globais:
Os headers globais, que se aplicam à todas requests/endpoints/serviços da API em teste (ex: "content-type"), podem ser adicionados diretamente no construtor da classe "RequestRestBase", através do método "headers.put("chave", "valor")" (vide exemplo)

## Configurando cookies globais:
Os cookies globais, que se aplicam à todas requests/endpoints/serviços da API em teste, podem ser adicionados diretamente no construtor da classe "RequestRestBase", através do método "cookies.put("chave", "valor")" (vide exemplo)

## Construindo um teste:
Os testes devem ser estruturados utilizando a ideia do [Padrão AAA](https://www.lambda3.com.br/2010/08/testando-com-aaa-arrange-act-assert/) (Arranjo, Ação e Asserção), onde:
- **Arranjo:** Parâmetros do teste, configurações de serialização (se for o caso), instâcia da classe de requisição e configuração da requisição.
- **Ação:** Execução da request
- **Assert:** Passos de asserção

Observações gerais:
- O retorno das execuções sempre devem ser alocadas em uma variável do tipo "io.restassured.response.ValidatableResponse"
- Sempre aferir primeiro se o status code é o esperado para a requisição visto que, caso não seja o status code esperado se evita que erro no teste por não encontrar o parâmetro no corpo da resposta.
- Os valores do body são extraídos através de expressões [Json Path](https://github.com/json-path/JsonPath).
- Todas as aferições de valores do corpo da requisição devem estar contidas em um único método "body()". Desta forma garantimos que todos os valores serão verificados mesmo que antes do fim das verificações exista algum parâmetro retornado errado.
- Exemplos de como realizar as asserções e montar os testes nas classes "GetObterPorCPFTests" e "PostSalvarTests"

Funções mais relevantes para realização de asserção de parâmetros do body através da biblioteca "org.hamcrest.Matchers":

- **equalTo(valor esperado):** verifica se o valor retornado é igual ao esperado
- **equalToIgnoringCase(valor esperado):** verifica se o valor retornado é igual ao esperado ignorando se contém caracteres em maiúsculo.
- **containsString(valor esperado):** verifica se o valor retornado comtém a string esperada
- **hasItem(item esperado):** verifica se objeto contém o item esperado
- **hasEntry(propriedade, valorEsperado):** verifica se propriedade contém o valor esperado (muito utilizado em conjunto com o hasItem()
- **hasSize(tamanho esperado):** verifica se o objeto tem o tamanho esperado (quantidade de objetos de um array de objetos)
- **hasKey(chave esperada):** verifica se o objeto contém o item esperado.
- **not(expressão matchers):** usada para negação de qualquer expressão da biblioteca “Matchers” ex: Verificar se um valor é diferente de 0 “not(equalTo(0))”
- **everyItem(valor esperado):** verifica se todos os itens do array de objetos contém um valor esperado

Existem também funções na biblioteca "Matches" que podem ser passadas como parâmetros para as funções de asserção:
- **empty():** define que o resultado esperado é “vazio”
- **graterThan(valor):** deine que resultado esperado é maior que “valor”
- **lessThan(valor):** define que resultado esperado é menor que “valor”
- **emptyArray():** define que o resultado esperado é que o array esteja “vazio”
- **emptyString():** define que o resultado esperado para o parâmetro que retorna uma string é “vazio”

## Relatório de testes:
Sempre que um ou mais testes são executados um relatório da execução é gerado na pasta indicada na variável "report.path" do arquivo "globalParameters.properties" (valor defaul é target/reports).
Para abrir o relatório, basta navegar para a pasta que contém o realtório da execução e abrir (através do browser) o arquivo .html com o nome do relatório

## Executando testes através do Maven:
Em um ambiente de CI/CD os testes serão executados através de comandos do Maven. O Maven do projeto está configurado para executar os testes incluídos no arquivo "suiteTestes.xml"
Execução via Maven através do IntelliJ IDEA:
- Clicar no combo-box de execuções
- Clicar em "Edit Configurations"
- Clicar em "Add new configuration"
- Clicar em "Maven"
- Preecher a o campo "Command Line" com "clean test"
- Clicar em "Apply"
- Clicar em "OK"
- A configuração de execução ficará disponível no combo-box de execuções
- Selecionar a configuração
- Clicar no "play" ao lado do combo-box de execuções

## Padrão escrita de código:
O padrão adotado para escrita é o “CamelCase” onde uma palavra é separada da outra através de letras maiúsculas. Este padrão é adotado para o nome de classes, métodos, variáveis e arquivos em geral exceto constantes. Constantes devem ser escritas com todas suas letras em maiúsculo separando as palavras com “_”.

Ex: setJsonBody(), nomeUsuario, GetTokenRequest etc.
Ex. constantes: URL_DEFAULT.

- **Pacotes:** sempre escritas todas as palavras em letra minúscula sem separação
- **Classes:** começam sempre com letra maiúscula
- **Arquivos de classes:** começam sempre com letra maiúscula
- **Arquivos que não são de classes:** começam sempre com letra minúscula
- **Métodos:** começam sempre com letra minúscula
- **Variáveis:** começam sempre com letra minúscula
- **Variáveis de Objetos:** começam sempre com letra minúscula

No caso de siglas, manter o padrão da primeira letra, de acordo com o padrão do tipo que será nomeado, ex:

- cpfField (variável)
- preencherCPF() (método)

No caso de palavras com uma letra, as duas devem ser escritas juntas de acordo com o padrão do tipo que será nomeado, ex:

- retornaSeValorEOEsperado()

Nomes de classes e arquivos devem terminar com o tipo de conteúdo que representam, em inglês, ex:

- GetTokenRequest (classe de Request)
- LoginTests (classe de testes)
- LoginTestData.csv (planilha de dados)

OBS: Atenção ao plural e singular! Se a classe contém um conjunto do tipo que representa, esta deve ser escrita no plural, caso seja uma entidade, deve ser escrita no singular.

Nomes dos objetos devem ser exatamente os mesmos nomes de suas classes, iniciando com letra minúscula, ex:

- GetTokenRequest (classe) getTokenRequest (objeto)

Geral:
- Nunca utilizar acentos, caracteres especiais e “ç” para denominar pacotes, classes, métodos, variáveis, objetos e arquivos.





