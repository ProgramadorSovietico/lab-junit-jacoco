# Relatório do Laboratório: JUnit 5

**Dupla:** _Nome 1_ e _Nome 2_
**Link do fork:** _https://github.com/..._

## 1. Resultado final

Cole aqui o resumo que aparece no final de `java scripts/Rodar.java`:

```
>> Compilando o codigo...
>> Compilando os testes...
>> Rodando os testes...

.
+-- JUnit Platform Suite [OK]
+-- JUnit Jupiter [OK]
| +-- ContaTest [OK]
| | +-- saqueValidoDeveDiminuirOSaldo() [OK]
| | +-- contaNovaDeveGuardarTitularESaldoInicial() [OK]
| | +-- saqueMaiorQueOSaldoDeveSerRecusado() [OK]
| | +-- depositoValidoDeveAumentarOSaldo() [OK]
| | +-- depositoComValorNegativoDeveSerRecusado() [OK]
| | '-- saqueDoSaldoExatoDeveSerPermitidoEZerarAConta() [OK]
| '-- ServicoTransferenciaTest [OK]
|   +-- saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta() [OK]
|   +-- transferenciaAcimaDe1000PagaTarifaDe1Porcento() [OK]
|   +-- transferenciaPequenaDeveSerAceitaSemTarifa() [OK]
|   +-- mesmaContaEValorInvalidoDevemSerRecusados() [OK]
|   +-- transferenciaAcimaDoLimiteDeveSerRecusada() [OK]
|   '-- transferenciaDeExatamente1000NaoPagaTarifa() [OK]
'-- JUnit Vintage [OK]

Test run finished after 71 ms
[         5 containers found      ]
[         0 containers skipped    ]
[         5 containers started    ]
[         0 containers aborted    ]
[         5 containers successful ]
[         0 containers failed     ]
[        12 tests found           ]
[         0 tests skipped         ]
[        12 tests started         ]
[         0 tests aborted         ]
[        12 tests successful      ]
[         0 tests failed          ]
```

## 2. Nível 1: `if`s de `Conta`

Para cada `if` de `Conta.java`, diga qual teste faz o `if` ser **verdadeiro** e qual faz ser **falso**
(escreva o nome do método de teste).

| `if` (condição) | Teste que torna **verdadeiro** | Teste que torna **falso** |
|---|---|---|
| saldo inicial negativo (construtor) | Não há | Não há |
| valor do depósito `<= 0` |  | depositoComValorNegativoDeveSerRecusado |
| valor do saque `<= 0` |  | |
| saque maior que o saldo | | |

## 3. Nível 2: regras de `ServicoTransferencia`

| Regra | Teste(s) que a exercita(m) |
|---|---|
| R1 mesma conta | mesmaContaEValorInvalidoDevemSerRecusados |
| R2 valor <= 0 | mesmaContaEValorInvalidoDevemSerRecusados |
| R3 acima do limite de 5.000 (e 5.000 exatos permitido) | transferenciaAcimaDoLimiteDeveSerRecusada |
| R4 saldo insuficiente para valor + tarifa (nada é alterado); tarifa de 1% acima de 1.000 (1.000 exatos sem tarifa) | saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta ; transferenciaAcimaDe1000PagaTarifaDe1Porcento e transferenciaDeExatamente1000NaoPagaTarifa|
| R5 transferência OK: saldos da origem e do destino | transferenciaDeExatamente1000NaoPagaTarifa, transferenciaAcimaDe1000PagaTarifaDe1Porcento e transferenciaPequenaDeveSerAceitaSemTarifa |

## 4. Nível 3: regras de `AnalisadorEmprestimo`

| Regra | Cenário escolhido (entradas → decisão esperada) | Método de teste |
|---|---|---|
| 1. entradas inválidas → DADOS_INVALIDOS (cada uma das 6 condições) | | |
| 2. score < 300 → NEGADO | | |
| 3. taxa por faixa de score (800 / 600) | | |
| 4. parcela (Price) | | |
| 5. comprometimento > 50% → NEGADO; > 30% → ANÁLISE MANUAL | | |
| 6. comprometimento <= 30%: score >= 600 aprovado; abaixo, análise manual | | |

## 5. Reflexão (2 a 4 linhas cada)

1. **Os dois lados do `if`:** qual `if` ficou sem teste de um dos lados no começo? Que bug passaria
   despercebido por isso?
      O saldo inicial negativo não foi testado, gerando bugs caso a conta fosse gerada com valores negativos, impedindo que depósitos fossem realizados (por terem valor sempre superior ao saldo). O caso não ocorre pois as regras implementadas pela Conta.java consideram isso, tornando saldo negativo, na geração da conta, no valor 0, mas se alterações fossem feitas, sem o teste, erros poderiam surgir sem serem percebidos por isso.

2. **Valor-limite:** qual teste de fronteira (ex.: 1.000,00 / 5.000,00 / score 600) vocês acham
   mais importante? Que bug ele pegaria se o código usasse `>=` no lugar de `>`?
      Os bugs seriam provenientes de não aceitar o valor de limite exato, que deveriam ser aceitos. Os testes que deveriam ser válidos seriam inválidos, graças a um erro do código (>= ao invés de >); o que indicaria uma necessidade de mudança do código.

3. **Nível 3:** como vocês decidiram quais cenários testar? (classes de equivalência, fronteiras,
   leitura das regras...)
      Não deu tempo de fazer.

4. **Teste que passa mas não protege:** descrevam um teste que executa o código e passa, mas não
   verificaria um erro (dica: um teste sem `assert`).
5. **Divisão do trabalho:** quem escreveu o quê? (uma linha)
      Eu escrevi tudo.
