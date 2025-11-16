🌤️ App de Previsão do Tempo

Este é um aplicativo web completo de previsão do tempo, construído com um backend Java (Spring Boot) e um frontend moderno em HTML, CSS e JavaScript.

📸 Tela

<img width="1918" height="865" alt="image" src="https://github.com/user-attachments/assets/8e54e51c-b6ec-4b71-a323-83378c3afbc7" />


✨ Funcionalidades Principais

Busca em Tempo Real: Encontre o clima de qualquer cidade do mundo.

Fundo Dinâmico: O plano de fundo da página muda automaticamente para refletir o clima atual da cidade pesquisada (céu limpo, chuva, nuvens, etc.).

Design Responsivo: O layout se ajusta perfeitamente a desktops, tablets e dispositivos móveis.

Previsão Completa: Mostra o clima atual, sensação térmica, umidade e a previsão para os próximos 8 dias.

Gráfico de Temperatura: Exibe um gráfico com a variação das temperaturas mínimas e máximas.

💻 Tecnologias Utilizadas

O projeto é dividido em duas partes principais:

Backend (Java / Spring Boot)

Java 17+

Spring Boot: Framework principal para a criação da API REST.

Spring Web: Para criar os endpoints @RestController.

RestTemplate: Para consumir as APIs externas da OpenWeather.

Jackson: Para desserializar (parse) as respostas JSON das APIs.

Arquitetura SOLID: O projeto segue os princípios SOLID, separando responsabilidades em:

Controller: Recebe as requisições web.

Service: Orquestra a lógica de negócio.

Client: Comunica-se com as APIs externas.

Parser: Converte os DTOs da API para os DTOs da aplicação.

ExceptionHandler: Trata erros de forma global (ex: "Cidade não encontrada").

Frontend (HTML / CSS / JS)

HTML5: Estrutura semântica do aplicativo.

CSS3: Estilização moderna, incluindo Flexbox, variáveis CSS e design responsivo.

JavaScript (ES6+):

fetch: Para fazer chamadas assíncronas à API backend.

async/await: Para lidar com a programação assíncrona.

Manipulação do DOM: Para atualizar a tela dinamicamente com os dados do clima.

Chart.js: Biblioteca utilizada para renderizar o gráfico de variação de temperatura.

🚀 Como Rodar o Projeto

Pré-requisitos

Java JDK 17 ou superior.

Maven 3.8 ou superior.

Uma chave de API (API Key) do OpenWeatherMap.

Passos

Clone o repositório:

git clone [https://github.com/seu-usuario/nome-do-repositorio.git](https://github.com/seu-usuario/nome-do-repositorio.git)
cd nome-do-repositorio


Configure a API Key:

Vá até src/main/resources/application.properties.

Adicione sua chave de API do OpenWeatherMap ao arquivo:

# URLs da API (já devem estar no arquivo)
weather.api.geo.url=[http://api.openweathermap.org/geo/1.0/direct](http://api.openweathermap.org/geo/1.0/direct)
weather.api.onecall.url=[https://api.openweathermap.org/data/2.5/onecall](https://api.openweathermap.org/data/2.5/onecall)

# Adicione sua chave de API aqui
weather.api.key=SUA_CHAVE_DE_API_AQUI


Compile e Rode o Backend (Java):

Abra o projeto em sua IDE (como IntelliJ ou VS Code).

Rode a classe principal ApptempoApplication.java.

Ou, pelo terminal:

mvn spring-boot:run


O servidor backend estará rodando em http://localhost:8080.

Acesse o Frontend:

O frontend é servido automaticamente pelo backend (a partir da pasta src/main/resources/static).

Abra seu navegador e acesse:

http://localhost:8080


Comece a pesquisar por cidades!

👥 Autores

Este projeto foi desenvolvido com dedicação por:

Cleyton Santos

Luis Fernando

José Anderson

Matheus Gomes
