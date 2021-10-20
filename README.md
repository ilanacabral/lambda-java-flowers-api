<h2>Digital Innovation: Bootcamp GFT Java & AWS Developer</h2>

Objetivo é desenvolver uma API REST para o gerenciamento de uma floricultura. A linguagem usada é Java, os dados serão armazenados em uma tabela DynamoDB e o serviços serão atendidos por funções lambda na nuvem AWS.

Foram utilizados:

* Setup inicial de projeto com o Framework Serverless que provê infaestrutura como código
* Criação de modelo de dados para o mapeamento de entidades em bancos de dados não relacional DynamoDB
* Desenvolvimento de operações de gerenciamento (cadastro, leitura, atualização e remoção) através de funções lambda
* Relação de cada uma das operações acima com o padrão arquitetural REST
* Implantação do sistema na nuvem AWS


Endpoints:
```

  * [Listar todos]  https://xxxxxxxxxxxxxxxx.us-east-1.amazonaws.com/dev/
  * [Listar por id] https://xxxxxxxxxxxxxxxx.us-east-1.amazonaws.com/dev/flowers/{id}
  * [Incluir] https://xxxxxxxxxxxxxxxx.us-east-1.amazonaws.com/dev/flowers
  * [Alterar] https://xxxxxxxxxxxxxxxx.us-east-1.amazonaws.com/dev/flowers/{id}
  * [Excluir] https://xxxxxxxxxxxxxxxx.us-east-1.amazonaws.com/dev/flowers/{id}

```

Exemplo de paylod para inclusão :
```
{   
    "name": "cravo",
    "color": "blue",
    "water": "once",
    "price": "66.54"
}
```

Exemplo de paylod para alteração:
```
{   
    "id":"c6791576-b1e2-4d09-953a-b840d844e682",
    "name": "cravo",
    "color": "blue",
    "water": "once",
    "price": "66.54"
}
```

São necessários os seguintes pré-requisitos para a execução do projeto:

* Java 11 ou versões superiores
* Maven 3.6.3 ou versões superiores
* Visual Studio Code 1.61.0 ou versões superiores
* Serverless Framework 2.63.0 (standalone) ou versões superiores
* Conta no GitHub para o armazenamento do seu projeto na nuvem.
* Conta na AWS para o deploy do projeto na nuvem


Links úteis:

* [Referência do VSCode para download](https://code.visualstudio.com/)
* [Site oficial do Serverless](https://www.serverless.com/)
* [Site oficial do GitHub](http://github.com/)
* [Documentação oficial do Lombok](https://projectlombok.org/)
* [Referência para o padrão arquitetural REST](https://restfulapi.net/)
* [Referência para o DynamoDB](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/WorkingWithDynamo.html)
* [Guides & Tutorials](https://www.serverless.com/blog/how-to-create-a-rest-api-in-java-using-dynamodb-and-serverless)