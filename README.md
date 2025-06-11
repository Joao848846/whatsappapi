# Projeto WhatsApp API (Zentry API)

Este projeto é uma API em Java com Spring Boot projetada para integrar-se a uma plataforma de comunicação (Evolution API) e gerenciar o envio e recebimento de mensagens do WhatsApp, agendamentos, e dados de empresas e usuários. Ele serve como um backend robusto para automação de comunicação via WhatsApp, utilizando mensageria Kafka para resiliência e WebSockets para comunicação em tempo real.

## 🚀 Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.4.4
* **Banco de Dados:** MongoDB (com Spring Data MongoDB)
* **Mensageria:** Apache Kafka (com Spring Kafka)
* **WebSockets:** Spring WebSocket (STOMP)
* **Segurança:** Spring Security com JWT (JSON Web Tokens)
* **Construção e Gerenciamento de Dependências:** Apache Maven
* **Conteinerização:** Docker e Docker Compose
* **Outros:** Lombok, OpenCSV, RestTemplate para comunicação HTTP.

## ✨ Funcionalidades

* **Gerenciamento de Agendamentos:**
    * Leitura e persistência de dados de agendamentos via upload de arquivo CSV.
    * Busca de agendamentos por documento, telefone, nome e status de pagamento.
    * Estatísticas de agendamentos por status de pagamento.
    * Agendamento automático de lembretes de pagamento com base em datas de vencimento, evitando envios duplicados e verificando o status do lembrete.
* **Mensageria WhatsApp:**
    * Envio de mensagens de texto, mídia (fotos, vídeos) e reações via integração com a Evolution API.
    * Processamento assíncrono de mensagens de saída via Apache Kafka para maior resiliência e escalabilidade.
    * Recebimento e armazenamento de eventos de webhook do WhatsApp (mensagens recebidas, atualizações de contato, status de entrega de mensagens).
    * Atualização do status de lembretes enviados com base nos eventos de entrega do WhatsApp.
* **Comunicação em Tempo Real:**
    * Utilização de WebSockets para broadcasting de novas mensagens e eventos para o frontend em tempo real.
* **Gerenciamento de Instâncias (Evolution API):**
    * Criação, listagem e exclusão de instâncias da Evolution API.
    * Verificação do status de conexão das instâncias.
* **Autenticação e Autorização:**
    * Implementação de segurança com Spring Security e JWT para autenticação de usuários.
* **Gerenciamento de Usuários e Empresas:**
    * Criação e listagem de usuários e empresas com persistência em MongoDB.

## 🏗️ Estrutura do Projeto

O projeto segue uma arquitetura baseada em camadas (possivelmente Domain-Driven Design simplificado ou hexagonal), visando separação de responsabilidades:

* `adapter.in.controller`: Camada de entrada, onde ficam os controladores REST e WebSockets que recebem as requisições externas.
* `application.service`: Contém a lógica de negócio principal da aplicação.
* `domain.model`: Define as entidades de negócio e a lógica de domínio.
* `infrastructure.repository`: Camada de persistência de dados, responsável pela interação com o banco de dados (MongoDB).
* `infrastructure.config`: Configurações gerais da aplicação (segurança, WebSockets, etc.).
* `infrastructure.kafka`: Classes relacionadas à integração com Kafka.

## 🚀 Como Rodar o Projeto

Para configurar e rodar o projeto localmente, siga os passos abaixo. Você precisará ter o Docker e Docker Compose instalados em sua máquina.

1.  **Clone o Repositório:**
    ```bash
    git clone
    cd whatsappapi
    ```

2.  **Configurar Variáveis de Ambiente:**
    Crie um arquivo `.env` na raiz do projeto (o mesmo diretório do `docker-compose.yaml`) e adicione a variável `MONGODB_URI` para a conexão com o MongoDB.
    Exemplo de `.env`:
    ```
    MONGODB_URI=mongodb://root:root@localhost:27017/zentrydb?authSource=admin
    ```
    *(Atenção: A Evolution API e o Kafka geralmente exigem mais configurações, como as chaves no `client.properties` e `application.properties`. Certifique-se de que estão corretas para sua instalação ou ambiente de nuvem.)*

3.  **Iniciar os Serviços com Docker Compose:**
    Este projeto utiliza Docker Compose para orquestrar o banco de dados (PostgreSQL/Redis, embora o Spring Data MongoDB esteja configurado, o `docker-compose` mostra PostgreSQL), Redis, a Evolution API e a própria `zentry-api`.
    ```bash
    docker-compose up --build
    ```
    Este comando irá construir as imagens Docker (se necessário), iniciar todos os serviços definidos no `docker-compose.yaml` em segundo plano e manter os logs no terminal.

4.  **Acessar a Aplicação:**
    * A API principal estará rodando em: `http://localhost:3000`
    * A Evolution API estará rodando em: `http://localhost:8080`

## 💡 Exemplos de Uso da API

A API expõe diversos endpoints para gerenciar usuários, empresas, agendamentos e mensagens. Abaixo, alguns exemplos usando `curl` (ou Postman/Insomnia):

### Autenticação (JWT)

**Obter Token JWT:**
```bash
curl -X POST http://localhost:3000/auth/token \
-H "Content-Type: application/json" \
-d '{
    "username": "ADMIN",
    "password": "ADMIN"
}'
