Neste projeto, implementaremos um framework de automação de testes para APIs utilizando Java em conjunto com a biblioteca RestAssured, integrando com o Maven para gerenciamento de dependências e o TestNG como framework de testes. O objetivo é criar uma solução modular e eficiente que permita realizar testes de integração e validação de APIs RESTful de maneira automatizada, garantindo qualidade e estabilidade no desenvolvimento de sistemas.

RestAssured é uma biblioteca popular em Java que facilita a escrita de testes para APIs RESTful, permitindo que os desenvolvedores façam requisições HTTP (GET, POST, PUT, DELETE) e validem as respostas de forma intuitiva. Combinado com TestNG, que oferece uma rica estrutura de execução de testes, controle de fluxos e relatórios detalhados, esse projeto oferece uma base robusta para a automação de APIs. O uso do Maven como ferramenta de build permite o gerenciamento centralizado das dependências e facilita a integração contínua com outras ferramentas do ecossistema de desenvolvimento, como Jenkins e Git.

A estrutura deste projeto permitirá a criação de testes escaláveis e fáceis de manter, permitindo que novas funcionalidades sejam validadas automaticamente, reduzindo o tempo de testes manuais e minimizando a ocorrência de falhas em produção. Ao longo do projeto, serão abordados aspectos como boas práticas de design de testes, manuseio de dados de teste, validação de esquemas de resposta, e geração de relatórios de execução.


Cenários de Testes Automatizados em Gherkin


TC01: Cadastrar um novo pet

Dado a requisição do tipo POST para cadastro de pet
E que seja informado um corpo com dados pet
Quando solicitado o cadastro
Então o pet deve ser cadastrado com sucesso.
E que seja retornado um código 200.


TC02: Atualizar o pet cadastrado

Dado a requisição do tipo PUT para atualização do cadastro
E que seja informado os novos dados para o pet
Quando solicitado o alteração do cadastro
Então os dados do pet devem ser atualizados com sucesso.
E que seja retornado um código 200.


TC03: Buscar um pet cadastrado por ID

Dado a requisição do tipo GET para buscar um pet
E que seja informado um ID válido de um pet
Quando solicitado a busca
Então o pet correto deve ser retornado com sucesso
E que seja retornado um código 200.


TC04: Buscar um pet com ID inválido

Dado a requisição do tipo GET para buscar um pet
E que seja informado um ID inválido
Quando solicitado a busca
Então deve ser retornando que o ID é inválido
E que seja retornado um código 400