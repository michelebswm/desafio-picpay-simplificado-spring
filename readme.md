# Desafio Back-end PicPay

https://github.com/PicPay/picpay-desafio-backend/tree/master

Este projeto é uma API construída usando Java, Java Spring, JPA, H2.

### Objetivo: PicPay Simplificado

Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam transferências entre eles. Vamos nos atentar **somente** ao fluxo de transferência entre dois usuários.

Requisitos:

-   Para ambos tipos de usuário, precisamos do Nome Completo, CPF, e-mail e Senha. CPF/CNPJ e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail.

-   Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários.

-   Lojistas **só recebem** transferências, não enviam dinheiro para ninguém.

-   Validar se o usuário tem saldo antes da transferência.

-   Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock para simular (https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc).

-   A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia.

-   No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um serviço de terceiro e eventualmente este serviço pode estar indisponível/instável. Use este mock para simular o envio (https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6).

-   Este serviço deve ser RESTFul.

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/michelebswm/desafio-picpay-simplificado-spring.git
```

2. Instale as dependências do Maven.

## Uso

1. Inicie o aplicativo com Maven

2. A API estará acessível em http://localhost:8080

## API Endpoints
A API fornece os seguintes endpoints:

**API USER**
```markdown
POST /api/users - Cria um novo usuário
GET /api/users - Recupera todos os usuários
GET /api/users/{id} - Recupera um usuário
```

**BODY**
```json
{
    "name": "Nome do usuário",
    "email": "email@gmail.com",
    "password": "123456",
    "numeroDocumento": "33191546005146",
    "tipoPessoa": "JURIDICA",
    "typeUser": "RETAILER"
}
```

**ENUMS**

tipoPessoa:
- Este enum representa o tipo de pessoa associada a um usuário.
- Os valores possíveis são: 'JURIDICA' (pessoa jurídica) e 'FISICA' (pessoa física).

typeUser:
- Este enum representa o tipo de usuário.
- Os valores possíveis são: 'COMOM' (usuário comum) e 'RETAILER' (varejista).


**API WALLET**
```markdown
POST /api/wallets - Cria uma nova carteira
GET /api/wallets - Recupera todas as carteiras
GET /api/wallets/{id} - Recupera uma carteira
```

**BODY**
```json
{
    "account": "33564749",
    "user": {
        "id": 2
    },
    "balance": 0.0
}
```

**API TRANSACTION**
```markdown
POST /api/transactions - Cria uma nova transferencia
GET /api/transactions - Recupera todas as transferencias
```

**BODY**
```json
{
    "payer": {
            "id": 1
        },
    "receiver":{
            "id": 2
        },
    "amount": 15.0
}
```