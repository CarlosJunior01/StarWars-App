# StarWars-App
StarWars movie and people listing application.

![GitHub top language](https://img.shields.io/github/languages/top/Carlosjr01/Filmes-App) 
[![MVVM](https://img.shields.io/badge/Architecture-MVVM_+_CLEAN_ARCHITECTURE-black)](https://www.youtube.com/watch?v=tIPxSWx5qpk) 
[![Flow](https://img.shields.io/badge/Kotlin_Flow-Asynchronous-black)](https://developer.android.com/kotlin/coroutines) 
[![Coroutines](https://img.shields.io/badge/Coroutines-1.6.0-black.svg)]()
[![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-2.4.2-black.svg)]()
[![Lottie](https://img.shields.io/badge/Lottie-5.2.0-black.svg)]()
[![Retrofit](https://img.shields.io/badge/Retrofit-2.9.0-black.svg)]()
[![OkHttp](https://img.shields.io/badge/Okhttp-4.9.0-black.svg)]()
[![Glide](https://img.shields.io/badge/Glide-4.12.0-black.svg)]()
[![Mockito](https://img.shields.io/badge/Mockito-2.2.0-black.svg)]()
[![Espresso](https://img.shields.io/badge/Espresso-3.4.0-black.svg)]()
[![Paging 3](https://img.shields.io/badge/Paging_3-3.1.1-black.svg)]()
![API](https://img.shields.io/badge/API-Swapi-lightgrey)
![Testes](https://img.shields.io/badge/Testes_Unitários_+_Ui_Testes-lightgrey)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)]()

*******
![image](https://user-images.githubusercontent.com/9430430/233276350-e2160d47-e82d-4465-95fe-e23ecb7b0bef.png)
![image](https://user-images.githubusercontent.com/9430430/233276633-109b8752-3c11-4124-9bb6-878980b4039b.png)
![image](https://user-images.githubusercontent.com/9430430/233276917-c04ba963-8250-48b8-970a-81fd754c844f.png)
![image](https://user-images.githubusercontent.com/9430430/233277311-80d2607e-eb85-4531-b572-a059677251c1.png)
*******

Aplicativo Android Nativo escrito em Kotlin, consumindo api Rest Swapi para listagem de personagens e filmes do universo StarWars, processando resposta com retrofit e tratamento de concorrência com Kotlin Flow, utilizando padrão de arquitetura MVVM + Clean Architecture em multi-módulos, com divisão de responsabilidades e separação de conceitos e desacoplamento de camadas. Injeção de dependência com Dagger Hilt. Cache de imagens e da Api. Funcionalidade de compartilhamento, listagem de favoritos e listagem de últimos vistos, tratamento de erro e retry. Testes unitários e Testes de interface utilizando JUnit, Mockito e Espresso.

**MVVM:** Tem como principal objetivo separar responsabilidades entre Views e Modelos
Aqui temos a View que responde somente para a ViewModel, e a ViewModel não comunica diretamente com a View. A ViewModel é então uma classe intermediaria entre a View e a Model que conecta uma com a outra fazendo assim intermediação entre ambas.

**Modelo (Model):**
A Model caracteriza um conjunto de classes para descrever a lógica de negócios. Ela também descreve as regras de negócios para dados sobre como os dados podem ser manipulados ou alterados.

**Visão (View):**
A View representa componentes da interface do usuário. Ela também exibe os modelos na interface do usuário a partir do retorno da Presenter e da ViewModel. Assim como no MVP, a View aqui também tende a possuir o mínimo de regra de negócio possível, ela também é "burra", quem vai definir o que ela vai exibir é a ViewModel.

**ViewModel:**
A ViewModel é responsável por apresentar funções, métodos e comandos para manter o estado da View, operar a Model e ativar os eventos na View.
O ViewModel expõe fluxos de dados relevantes para o View. Mesmo neste caso, é a ponte entre o repositório e a View e contém toda a lógica de negócios.

### Principais benefícios da arquitetura Android
**Manutenção**
É possível entrar em partes menores e focadas do código e fazer alterações facilmente por causa da separação de diferentes tipos de códigos de maneira mais limpa. Isso ajudará a lançar novas versões rapidamente e a manter a agilidade.

**Testabilidade:**
No caso da arquitetura MVVM, cada parte do código permanece granular. Caso seja implementada da maneira adequada, todas as dependências internas e externas permanecerão do código que contém a lógica principal do aplicativo que você estava planejando testar.
Portanto, se você planeja escrever testes unitários com a lógica principal, fica muito mais fácil. Lembre-se de verificar se funciona bem quando escrito e continue executando, principalmente quando houver qualquer tipo de alteração no código, por menor que seja.

**Extensibilidade:**
Devido ao aumento de partes granulares de código e limites de separação, às vezes ele se mistura com a capacidade de manutenção. Se você planeja reutilizar qualquer uma dessas partes terá mais chances de fazê-lo.
Ele também vem com o recurso no qual você pode adicionar novos trechos de código ou substituir os existentes que executam trabalhos semelhantes em alguns locais da estrutura da arquitetura.
Sem dúvida, o principal objetivo de escolher a arquitetura MVVM é abstrair as Views para que o código por trás da lógica de negócios possa ser reduzido a uma extensão.

*******
* **Desenho da Arquitetura multi-módulos desse Projeto:** 
*******
![image](https://user-images.githubusercontent.com/9430430/166218310-f3a3e559-e2cf-40b5-a6e1-d79d7f9ff90e.png)
*******
* **Explicação de frameworks utilizados no projeto:**
*******

[![Retrofit](https://img.shields.io/badge/retrofit-2.9.0-black.svg)]()

Retrofit: É uma biblioteca desenvolvida pela Square que é utilizada como um REST Client no Android.
Utiliza a biblioteca OkHttp para fazer os Http Requests.
O Retrofit torna mais fácil recuperar e fazer upload de JSON através de uma Web service REST.
Com o Retrofit podemos escolher que conversor usar para a serialização de dados, como por exemplo o Moshi e GSON.

[![Glide](https://img.shields.io/badge/glide-4.12.0-black.svg)]()

Glide: É um gerenciador de mídia de código aberto rápido e eficiente e estrutura de carregamento de imagem para Android que envolve decodificação de mídia, armazenamento em cache de memória e disco e pool de recursos em uma interface simples e fácil de usar.
   
[![Kotlin_Flow](https://img.shields.io/badge/Kotlin_Flow-Asynchronous-black)](https://developer.android.com/kotlin/coroutines) 

Kotlin Flow: Utilizada para fluxos assíncronos, com isso podemos retornar vários valores calculados de forma assíncrona ao contrário de ma função de suspensão que retorna de forma assíncrona um único valor.

[![Lottie](https://img.shields.io/badge/lottie-5.2.0-black.svg)]()

Lottie: Framework para adicionar, renderizar e controlar Animações dentro do app. Lottie é um formato de arquivo de animação de código aberto que é pequeno, de alta qualidade, interativo e pode ser manipulado em tempo de execução.

[![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-2.42-black.svg)]()

Hilt: É uma biblioteca de injeção de dependência que reduz a injeção manual de código no projeto, criando e gerenciando as instâncias provendo os módulos dentro da aplicação.

[![Mockito](https://img.shields.io/badge/Mockito-2.2.0-black.svg)]()

Mockito: É uma ferramenta para mocking quando escrevemos testes para aplicações Kotlin. Nos ajuda a escrever código limpo e conciso ao testar aplicações Java e Kotlin.

*******
* **Conclusão:** 
*******
Para este projeto foi escolhido o padrão de Arquitetura MVVM com Clean Architecture em multi-módulos justamente por fazer uso um padrão de divisão de responsabilidades, com separação de conceitos, e camadas diferentes, nele temos o desacoplamento da camada de "Network" da camada de "Apresentação", Repository: Utilizado para centralizar funções e não repetir códigos centraliza o acesso aos dados. UseCases com responsabilidade única, possuindo as regras de negócio da aplicação e fazendo o meio de campo entre as duas camadas "ViewModel" e "Repository" separando ainda mais as responsabilidades da aplicação, separando a camada de apresentação da camada de dados.
Com essas duas arquiteturas implementadas melhoramos a clareza e entendimento de cada parte do projeto, facilitando e possibilitando o trabalho em diferentes frentes de camadas desacopladas em um projeto mais organizado, expandível, testável e flexível de forma padronizada de desenvolvimento para que a aplicação venha a ser escalável e testável com maior separação de conceitos e responsabilidades. 

*******
![image](https://user-images.githubusercontent.com/9430430/233280732-ad221db2-1f9b-467d-aca6-73571fe56625.png)
*******
