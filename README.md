# API Shop-Orders
API para listagem de produtos e cadastro de pedidos.

## 📜 Sumário
1. [Detalhes do projeto](https://github.com/DanJusto/API-ControleEstoque#1--detalhes-do-projeto)
2. [Tecnologias usadas](https://github.com/DanJusto/API-ControleEstoque#2--tecnologias-usadas)
3. [Para rodar o projeto](https://github.com/DanJusto/API-ControleEstoque#3--para-rodar-o-projeto)
4. [Documentação](https://github.com/DanJusto/API-ControleEstoque#4--documenta%C3%A7%C3%A3o)
5. [Autor](https://github.com/DanJusto/API-ControleEstoque#5--autor)

## 1. 🔍 Detalhes do projeto
A API Shop-Orders tem como objetivo persistir dados para gerenciamento de pedidos com base em um estoque de produtos. Foi realizado academicamente durante o Bootcamp Java Academy, iniciativa da Ada em parceria com a Avanade.

#### Cenário:
* Sistema permite o cadastro de usuário, com validação de informações;
* Sistema recupera e armazena, caso a tabela de produtos esteja vazia, os produtos da API externa: https://dummyjson.com/products/search?q=phone
* Sistema permite apenas as requisições de listagem de produtos, detalhes de produto, cadastro de usuário e login sem autenticação por meio de JWT;
* Com usuário logado, o sistema permite a criação de pedido, validando o estoque de produtos antes de armazenar o pedido;
* Sistema atualiza o estoque automaticamente e encaminha e-mail de confirmação do pedido ao usuário;

Obs.: O sistema será aprimorado para suportar autenticação de adminsitrador para gerenciar alguns endpoints (user: get, delete / product: post, put, delete / order: get, get:id, delete).

## 2. 💻 Tecnologias usadas
<div align="center">

Languages, Frameworks & Librarys:   
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![SpringBoot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![JSON](https://img.shields.io/badge/json-5E5C5C?style=for-the-badge&logo=json&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)

Tests:  
![Insomnia](https://img.shields.io/badge/Insomnia-5849be?style=for-the-badge&logo=Insomnia&logoColor=white)
![JUnit](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

Database:  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)

IDE:  
![Intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

</div>

## 3. 🔌 Para rodar o projeto
1. Instale as dependências necessárias com o Maven para rodar a API (relacionadas no pom.xml):

    ```
    mvn dependency:copy-dependencies
    ```
2. A API utiliza o PostgreSQL como banco de dados, então se faz necessário que você tenha-o instalado em sua máquina.

3. Com ele instalado, crie um database e preencha o arquivo "application.proporties" com a url (colocando o nome do database criado no local indicado), username e password.

    ```
    spring.datasource.url=jdbc:mysql://localhost/{nomedatabase}
    spring.datasource.username={username}
    spring.datasource.password={password}
    ```

4. Rode a aplicação que o sistema já irá criar as tabelas e popular a tabela produtos automaticamente, deixando-as prontas para uso. Por padrão, a aplicação rodará na porta 8080.

5. Você precisará de uma ferramenta de teste de requisições como o [Insomnia](https://insomnia.rest/), devendo seguir as orientações da documentação abaixo para utilizar a API.

6. Você pode rodar os testes automatizados criados com JUnit, caso queira (mais testes em desenvolvimento).

## 4. 🔌 Documentação
### Endpoints

**Auth** <br/>
[`POST /auth/`](#post-auth) - Autenticação de usuário (login)
<br/><br/>

**User** <br/>
[`GET /user/`](#get-user) - Listagem de usuários <br/>
[`GET /user/:id`](#get-user-id) - Detalhamento de um usuário <br/>
[`POST /user/`](#post-user) - Criação de um novo usuário <br/>
[`PATCH /user/:id`](#patch-user) - Atualização de dados de um usuário <br/>
[`DELETE /user/:id`](#delete-user) - Deleção de um usuário
<br/><br/>

**Address** <br/>
[`GET /address/:id`](#get-address) - Detalhamento de um endereço <br/>
[`POST /address/`](#post-address) - Criação de um novo endereço <br/>
[`PUT /address/:id`](#put-address) - Atualização de dados de um endereço <br/>
[`DELETE /address/:id`](#delete-address) - Deleção de um endereço
<br/><br/>

**Product** <br/>
[`GET /product/`](#get-products) - Listagem de produtos com parâmetros <br/>
[`GET /product/:id`](#get-products-id) - Detalhamento um de produto <br/>
[`POST /product/`](#post-products) - Criação de um novo produto <br/>
[`PUT /product/:id`](#put-products) - Atualização de dados de um produto <br/>
[`DELETE /product/:id`](#delete-products) - Deleção de um produto
<br/><br/>

**Order** <br/>
[`GET /order/`](#get-order) - Listagem de pedidos <br/>
[`GET /order/:id`](#get-order-id) - Detalhamento de um pedido <br/>
[`POST /order/`](#post-order) - Criação de um novo pedido <br/>
[`DELETE /order/:id`](#delete-order) - Deleção de um pedido
<br/><br/>

###
#### POST auth

**Request**

| **Nome** |**Obrigatório**|**Tipo**| **Descrição**       |
|:---------| :------------ | :------------ |:--------------------|
| username |sim|`string`| Username do usuário |
| password |sim|`string`| Senha do usuário    |

<br />

> **_NOTA:_**  Não é necessário enviar Token JWT via Authorization Header.

<br />

**Response**

Sucesso
```json
{
  "type": "Bearer",
  "token": "abcdefghijklmnopqrstuvwxyz"
}
```
```status: 200```
<br /><br />
Erro comum

```json
{
    "message": "Authentication failed."
}
```
```status: 401```
<br/>

###
#### GET user

**Request**

Listar todos os usuários

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|-|-|-|Não é necessário enviar nenhum parâmetro|

<br />

> **_NOTA:_**  É necessário enviar Token JWT via Authorization Header (rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso
```json
[
   {
      "id": 1,
      "name": "Fulano",
      "username": "fulano",
      "cpf": "12345678910",
      "email": "fulano@email.com",
      "phone": "11999998888",
      "registerDate": "2023-08-25T21:00:00.000000",
      "addresses": null,
      "orders": null
   },
   {
      "id": 2,
      "name": "Beltrano",
      "username": "beltrano",
      "cpf": "98765432101",
      "email": "beltrano@email.com",
      "phone": "1133330000",
      "registerDate": "2023-08-25T21:30:00.000000",
      "addresses": null,
      "orders": null
   }
]
```
```status: 200```

<br /> <br /> 
Sucesso sem retorno de dados

```json
[]
```
```status: 200```
<br/>

###
#### GET user-id

**Request**

Detalhar um usuário

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|Enviar via parâmetro de rota|

<br />

> **_NOTA:_**  É necessário enviar Token JWT via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 1,
   "name": "Fulano",
   "username": "fulano",
   "cpf": "12345678910",
   "email": "fulano@email.com",
   "phone": "11999998888",
   "registerDate": "2023-08-25T21:00:00.000000",
   "addresses": [
      {
         "id": 1,
         "street": "Rua 1",
         "number": "111, apto 01",
         "postalCode": "12345678",
         "city": "Cidade",
         "state": "Estado",
         "country": "País",
         "userId": 1
      }
   ],
   "orders": [
      {
         "id": 1,
         "userId": 1,
         "orderDate": "2023-08-25T22:45:06.786814",
         "totalPrice": 99100,
         "orderItems": [
            {
               "id": 1,
               "orderId": 1,
               "productId": 2,
               "qty": 1
            },
            {
               "id": 2,
               "orderId": 1,
               "productId": 3,
               "qty": 2
            }
         ]
      }
   ]
}
```
```status: 200```

<br /><br />
Erro comum

```json
{
   "message": "User not found."
}
```
```status: 404```

<br/>

###
#### POST user

Criar um usuário

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**       |
|:---------|:----------------|:---------|:--------------------|
| name     | sim             | `string` | Nome para perfil    |
| username | sim             | `string` | Username do usuário |
| password | sim             | `string` | Senha do usuário    |
| cpf      | sim             | `string` | CPF do usuário      |
| email    | sim             | `string` | E-mail do usuário   |
| phone    | sim             | `string` | Telefone do usuário |
| address  | não             | `object` | Endereço do usuário |

Caso o endereço seja cadastrado junto com o registro do usuário, deve seguir os seguintes parâmetros:

| **Nome**   | **Obrigatório** | **Tipo** | **Descrição**          |
|:-----------|:----------------|:---------|:-----------------------|
| street     | não             | `string` | Rua/Avenida            |
| number     | sim             | `string` | Número com complemento |
| postalCode | sim             | `string` | CEP                    |
| city       | não             | `string` | Cidade                 |
| state      | não             | `string` | Estado                 |
| country    | não             | `string` | País                   |

Exemplo de requisição:

```json
{
   "name": "Fulano",
   "username": "fulano",
   "password": "password",
   "cpf": "12345678910",
   "email": "fulano@email.com",
   "phone": "11999998888",
   "address": {
      "street": "Rua 1",
      "number": "111 apto 11",
      "postalCode": "12345678",
      "city": "Cidade 1",
      "state": "Estado 1",
      "country": "País 1"
   }
}
```

<br />

> **_NOTA:_**  Não é necessário enviar Token JWT via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 1,
   "name": "Fulano",
   "username": "fulano",
   "cpf": "12345678910",
   "email": "fulano@email.com",
   "phone": "11999998888",
   "registerDate": "2023-08-25T21:00:00.000000",
   "addresses": [
      {
         "id": 1,
         "street": "Rua 1",
         "number": "111, apto 01",
         "postalCode": "12345678",
         "city": "Cidade",
         "state": "Estado",
         "country": "País",
         "userId": 1
      }
   ],
   "orders": []
}
```
```status: 201```
<br /><br />
Erros comuns

```json
{
   "message": "User already exists."
}
```
```status: 400```
```json
{
   "errors": [
      "invalid Brazilian individual taxpayer registry number (CPF)"
   ]
}
```
```status: 400```
```json
{
   "field": "password",
   "message": "The password must have at least 8 characters"
}
```
```status: 400```
<br/>

###
#### PATCH user

**Request**

Editar um usuário. Apenas nome, email e telefone poder ser editados.

| **Nome** |**Obrigatório**|**Tipo**| **Descrição**                                                       |
|:---------| :------------ | :------------ |:--------------------------------------------------------------------|
| id       |sim|`number`| ID do produto que será atualizado (recebido como parâmetro de rota) |
| name     |não|`string`| Nome do usuário                                                     |
| email    |não|`string`| Email do usuário                                                    |
| phone    |não|`string`| Telefone do usuário                                       |


<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 1,
   "name": "Fulano Editado",
   "username": "fulano",
   "cpf": "12345678910",
   "email": "fulano.editado@email.com",
   "phone": "11999998888",
   "registerDate": "2023-08-25T21:00:00.000000",
   "addresses": [
      {
         "id": 1,
         "street": "Rua 1",
         "number": "111, apto 01",
         "postalCode": "12345678",
         "city": "Cidade",
         "state": "Estado",
         "country": "País",
         "userId": 1
      }
   ],
   "orders": [
      {
         "id": 1,
         "userId": 1,
         "orderDate": "2023-08-25T22:45:06.786814",
         "totalPrice": 99100,
         "orderItems": [
            {
            "id": 1,
            "orderId": 1,
            "productId": 2,
            "qty": 1
            },
            {
            "id": 2,
            "orderId": 1,
            "productId": 3,
            "qty": 2
            }
        ]
      }
   ]
}
```
```status: 200```
<br/><br/>
Erros comuns

```json
{
   "message": "User not found."
}
```
```status: 404```
<br/>

###
#### DELETE user

Deletar um usuário

**Request**

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|ID do produto que será deletado (recebido como parâmetro de rota)|

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header (rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso  
```no body returned for response``` <br/>
```status: 204```
<br/> <br/>

Erro comum

```json
{
   "message": "User not found."
}
```
```status: 404```
<br/>

###
#### GET address

**Request**

Detalhar endereço

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|Enviar via parâmetro de rota|

<br />

> **_NOTA:_**  É necessário enviar Token JWT via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 1,
   "street": "Rua 1",
   "number": "111, apto 01",
   "postalCode": "12345678",
   "city": "Cidade",
   "state": "Estado",
   "country": "País",
   "userId": 1
}
```
```status: 200```

<br /><br />
Erro comum

```json
{
   "message": "Address not found."
}
```
```status: 404```
<br/>

###
#### POST address

Cadastrar um endereço

**Request**

| **Nome**   | **Obrigatório** | **Tipo** | **Descrição**                         |
|:-----------|:----------------|:---------|:--------------------------------------|
| userId     | sim             | `number` | Id do usuário que o endereço pertence |
| street     | não             | `string` | Rua/Avenida                           |
| number     | sim             | `string` | Número com complemento                |
| postalCode | sim             | `string` | CEP                                   |
| city       | não             | `string` | Cidade                                |
| state      | não             | `string` | Estado                                |
| country    | não             | `string` | País                                  |

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 2,
   "street": "Rua 2",
   "number": "222, apto 02",
   "postalCode": "87654321",
   "city": "Cidade",
   "state": "Estado",
   "country": "País",
   "userId": 1
}
```
```status: 201```
<br/><br/>
Erros comuns

```json
{
   "message": "User not found."
}
```
```status: 404```
```json
[
   {
      "field": "number",
      "message": "must not be blank"
   }
]
```
```status: 400```
<br/>

###
#### PUT address

Editar um endereço

**Request**

| **Nome**   | **Obrigatório** | **Tipo** | **Descrição**                          |
|:-----------|:----------------|:---------|:---------------------------------------|
| userId     | sim             | `number` | Id do usuário (recebido por parâmetro) |
| street     | não             | `string` | Rua/Avenida                            |
| number     | sim             | `string` | Número com complemento                 |
| postalCode | sim             | `string` | CEP                                    |
| city       | não             | `string` | Cidade                                 |
| state      | não             | `string` | Estado                                 |
| country    | não             | `string` | País                                   |

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 2,
   "street": "Rua 22",
   "number": "444, apto 04",
   "postalCode": "87654321",
   "city": "Cidade",
   "state": "Estado",
   "country": "País",
   "userId": 1
}
```
```status: 200```
<br/><br/>
Erros comuns

```json
{
   "message": "Address not found."
}
```
```status: 404```
```json
{
   "message": "User not found."
}
```
```status: 404```
<br/>

###
#### DELETE address

**Request**

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|ID do produto que será deletado (recebido por parâmetro)|

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header.

<br />

**Response**

Sucesso  
```no body returned for response``` <br/>
```status: 204```
<br/> <br/>

Erro comum

```json
{
   "message": "Address not found."
}
```
```status: 404```
<br/>

###
#### GET products

Listar produtos (pode receber filtros)

**Request**

| **Nome** | **Obrigatório** | **Tipo** | **Descrição**                         |
|:---------|:----------------|:---------|:--------------------------------------|
| title    | não             | `string` | Nome do produto a ser pesquisado      |
| brand    | não             | `string` | Marca do produto a ser pesquisado     |
| category | não             | `string` | Categoria do produto a ser pesquisado |

**Response**

Sucesso
```json
[
   {
      "id": 1,
      "title": "Produto 1",
      "description": "Qualquer coisa",
      "price": 2000,
      "stock": 50,
      "brand": "Marca 1",
      "category": "Eletronico"
   },
   {
      "id": 2,
      "title": "Produto 2",
      "description": "Qualquer coisa 2",
      "price": 4000,
      "stock": 70,
      "brand": "Marca 2",
      "category": "Vestuario"
   }
]
```
```status: 200```

<br /> <br /> 
Sucesso sem retorno de dados

```json
[]
```
```status: 200```
<br/>

###
#### GET products-id

Detalhar um produto

**Request**

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|Enviar via parâmetro de rota|

<br />

> **_NOTA:_**  É necessário enviar Token JWT via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 3,
   "title": "Produto 3",
   "description": "Qualquer coisa 3",
   "price": 1000,
   "stock": 10,
   "brand": "Marca 3",
   "category": "Eletronico"
}
```
```status: 200```

<br /><br />
Erro comum

```json
{
   "message": "Product not found."
}
```
```status: 404```

<br/>

###
#### POST products

Cadastrar um produto

**Request**

| **Nome**    |**Obrigatório**|**Tipo**| **Descrição**           |
|:------------| :------------ | :------------ |:------------------------|
| title       |sim|`string`| Nome do produto         |
| description |sim|`string`| Descrição do produto    |
| price       |sim|`number`| Preço do produto        |
| stock       |sim|`number`| Estoque do produto      |
| brand       |sim|`string`| Marca do produto        |
| category    |sim|`string`| Categoria do produto |

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header (rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso
```json
{
   "id": 4,
   "title": "Produto 4",
   "description": "Qualquer coisa",
   "price": 52000,
   "stock": 5,
   "brand": "Marca 4",
   "category": "Mobiliario"
}
```
```status: 201```
<br/><br/>
Erros comuns

```json
[
   {
      "field": "price",
      "message": "must be greater than or equal to 1"
   }
]
```
```status: 400```
```json
{
    "message": "Product already exist"
}
```
```status: 400```
<br/>

###
#### PUT products

Editar um produto

**Request**

| **Nome**    | **Obrigatório** |**Tipo**| **Descrição**           |
|:------------|:----------------| :------------ |:------------------------|
| title       | não             |`string`| Nome do produto         |
| description | não             |`string`| Descrição do produto    |
| price       | não             |`number`| Preço do produto        |
| stock       | não             |`number`| Estoque do produto      |
| brand       | não             |`string`| Marca do produto        |
| category    | não             |`string`| Categoria do produto |

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header (rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso
```json
{
   "id": 2,
   "title": "Produto 2",
   "description": "Qualquer coisa",
   "price": 2000,
   "stock": 50,
   "brand": "Marca 2",
   "category": "Eletronico"
}
```
```status: 200```
<br/><br/>

Erros comuns

```json
{
   "message": "Product not found."
}
```
```status: 404```
```json
[
   {
      "field": "price",
      "message": "must be greater than or equal to 1"
   }
]
```
<br/>

###
#### DELETE products

Deletar um produto

**Request**

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|ID do produto que será deletado (recebido por parâmetro)|

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header (rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso  
```no body returned for response``` <br/>
```status: 204```
<br/> <br/>

Erro comum

```json
{
   "message": "Product not found."
}
```
```status: 404```
<br/>

###
#### GET order

Listar pedidos

**Request**

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|-|-|-|Não é necessário enviar nenhum parâmetro|

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header (rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso
```json
[
   {
      "id": 1,
      "userId": 1,
      "orderDate": "2023-08-26T14:45:06.786814",
      "totalPrice": 50000,
      "orderItems": [
         {
            "id": 1,
            "orderId": 1,
            "productId": 2,
            "qty": 5
         },
         {
            "id": 2,
            "orderId": 1,
            "productId": 3,
            "qty": 1
         }
      ]
   },
   {
      "id": 2,
      "userId": 1,
      "orderDate": "2023-08-26T18:41:12.651864",
      "totalPrice": 100000,
      "orderItems": [
         {
            "id": 3,
            "orderId": 2,
            "productId": 5,
            "qty": 20
         },
         {
            "id": 4,
            "orderId": 2,
            "productId": 1,
            "qty": 8
         }
      ]
   }
]
```
```status: 200```

<br />
Sucesso sem retorno de dados

```json
[]
```
```status: 200```
<br/>

###
#### GET order-id

Detalhar um produto

**Request**

|**Nome**|**Obrigatório**|**Tipo**|**Descrição**|
| :------------ | :------------ | :------------ | :------------ |
|id|sim|`number`|Enviar via parâmetro de rota|

<br />

> **_NOTA:_**  É necessário enviar Token JWT via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 1,
   "userId": 1,
   "orderDate": "2023-08-26T23:59:38.468445729",
   "totalPrice": 94500,
   "orderItems": [
      {
         "id": 1,
         "orderId": 1,
         "productId": 2,
         "qty": 1
      },
      {
         "id": 2,
         "orderId": 1,
         "productId": 3,
         "qty": 1
      }
   ]
}
```
```status: 200```

<br /><br />
Erro comum

```json
{
   "message": "Order not found."
}
```
```status: 404```

<br/>

###
#### POST order

Criar um pedido

**Request**

| **Nome**   |**Obrigatório**| **Tipo** | **Descrição**                   |
|:-----------| :------------ |:---------|:--------------------------------|
| userId     |sim| `number` | Id do usuário                   |
| orderItems |sim| `object` | Lista de produtos e quantidades |
| productId  |sim| `number` | Id do produto                   |
| qty        |sim| `number` | Quantidade do produto           |

Exemplo de requisição:

```json
{
   "userId": 1,
   "orderItems": [
      {
         "productId": 2,
         "qty": 1
      },
      {
         "productId": 3,
         "qty": 1
      }
   ]
}
```

<br/>

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header.

<br />

**Response**

Sucesso
```json
{
   "id": 1,
   "userId": 1,
   "orderDate": "2023-08-26T23:59:38.468445729",
   "totalPrice": 94500,
   "orderItems": [
      {
         "id": 1,
         "orderId": 1,
         "productId": 2,
         "qty": 1
      },
      {
         "id": 2,
         "orderId": 1,
         "productId": 3,
         "qty": 1
      }
   ]
}
```
```status: 201```
<br/><br/>
Erros comuns

```json
{
   "field": null,
   "message": "No stock available"
}
```
```status: 400```
```json
{
   "message": "Product not found."
}
```
```status: 404```
```json
{
   "message": "USer not found."
}
```
```status: 404```
<br/>

###
#### DELETE products

**Request**

|**Nome**|**Obrigatório**|**Tipo**| **Descrição**                                           |
| :------------ | :------------ | :------------ |:--------------------------------------------------------|
|id|sim|`number`| ID do pedido que será deletado (recebido por parâmetro) |

<br />

> **_NOTA:_**  É necessário enviar Token JWT de *Admin* via Authorization Header ((rota que será exclusiva de administrador futuramente).

<br />

**Response**

Sucesso  
```no body returned for response``` <br/>
```status: 204```
<br/> <br/>

Erro comum

```json
{
   "message": "Order not found."
}
```
```status: 404```
<br/>

## 5. 👨‍💻 Autor
Criado por Daniel Justo

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/danielmjusto/)
[![github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/DanJusto)

Obrigado pela visita!