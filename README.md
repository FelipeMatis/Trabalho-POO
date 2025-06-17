**Autores:** pedropiasecki, FelipeMatis

---

## Sobre o Jogo

Este é um jogo de aventura inspirado em Pokémon. Você é o treinador, escolhe seu Pokémon inicial e enfrenta batalhas enquanto captura novos Pokémon, compra itens na loja e cuida de seu time.

---

## Como Rodar

### Compilação

Para compilar os arquivos `.java` localizados na pasta `src`, execute o seguinte comando no terminal:

```bash
javac -d out (Get-ChildItem -Recurse -Filter *.java).FullName
```

### Execução

Após a compilação, execute o programa com:

```bash
java -cp out jogo.Main
```

---

## Funcionalidades do Jogo

### Escolha do Treinador e Pokémon Inicial

- Defina o nome do seu treinador.
- Escolha seu Pokémon inicial, visualizando seus atributos e tipos.

### Menu Principal

A partir do menu principal, você pode acessar as seguintes ações:

- **Batalhas:**
  - Enfrente Pokémon inimigos em um sistema de turnos.
  - Ao enfraquecer um adversário, você poderá tentar capturá-lo.
  - **Captura bem-sucedida:** Ganha moedas.
  - **Captura recusada ou derrota do inimigo:** Recebe experiência e moedas
  - Pokémon capturados podem ser adicionados à sua equipe para futuras batalhas.

- **Loja:**
  - Compre itens essenciais para sua jornada, como Pokébolas, poções e pokémons.
  - Caso não tenha moedas suficientes, você será notificado.

- **Minhas Pokébolas:**
  - Visualize as Pokébolas em seu inventário e veja se possuem Pokémon dentro.

- **Minhas Poções:**
  - Consulte suas poções e escolha qual Pokémon será curado ou aumentando o nível.

- **Desistir:**
  - Encerra o programa a qualquer momento.

---

## Fonte dos Dados dos Pokémon

Os dados dos Pokémon utilizados no jogo foram obtidos em:
(https://pokemondb.net/pokedex/all)
