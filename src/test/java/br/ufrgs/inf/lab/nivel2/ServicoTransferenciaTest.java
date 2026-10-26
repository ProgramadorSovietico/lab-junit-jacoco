package br.ufrgs.inf.lab.nivel2;

import static org.junit.jupiter.api.Assertions.*;

import br.ufrgs.inf.lab.nivel1.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * NÍVEL 2 (semi-guiado): ServicoTransferencia.
 *
 * Aqui NÃO há os asserts: cada teste abaixo diz o CENÁRIO e o que deve
 * ser verificado. Você decide como escrever (assertEquals, assertTrue...).
 * Leia as regras R1..R5 em ServicoTransferencia.java.
 *
 * Remova o @Disabled e o fail(...) de cada teste à medida que o implementar.
 * Sinta-se livre para ACRESCENTAR testes (e usar @ParameterizedTest).
 */
class ServicoTransferenciaTest {

    private static final double DELTA = 0.001;

    private ServicoTransferencia servico;
    private Conta origem;
    private Conta destino;

    // Roda ANTES de cada teste: garante contas "limpas" (testes independentes)
    @BeforeEach
    void preparar() {
        servico = new ServicoTransferencia();
        origem = new Conta("Origem", 10_000.0);
        destino = new Conta("Destino", 0.0);
    }

    // ---------------------------------------------------------------
    // PRONTO (exemplo)
    // ---------------------------------------------------------------

    @Test
    void transferenciaPequenaDeveSerAceitaSemTarifa() {
        StatusTransferencia status = servico.transferir(origem, destino, 200.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(9_800.0, origem.getSaldo(), DELTA);
        assertEquals(200.0, destino.getSaldo(), DELTA);
    }

    // ---------------------------------------------------------------
    // PARA VOCÊ ESCREVER
    // ---------------------------------------------------------------

    @Test
    void transferenciaAcimaDe1000PagaTarifaDe1Porcento() {
        StatusTransferencia status = servico.transferir(origem, destino, 2000.0);

        assertEquals(StatusTransferencia.OK, status);
        boolean tarifa = servico.calcularTarifa(2000.0) == 20.0;
        assertTrue(tarifa);
        assertEquals(7980.0, origem.getSaldo(), DELTA);
        assertEquals(2000.0, destino.getSaldo(), DELTA);
        // DADO   origem com 10.000 e destino com 0
        // QUANDO transfere 2.000
        // ENTÃO  status = ? OK  | calcularTarifa(2000) = ? 20 | saldo da origem = ? 7980 (10000 - 2000 - 20) | saldo do destino = ? 2000
        //        (calcule os valores esperados na mão ANTES de rodar!)

    }

    @Test
    void transferenciaDeExatamente1000NaoPagaTarifa() {
        StatusTransferencia status = servico.transferir(origem, destino, 1000.0);

        assertEquals(StatusTransferencia.OK, status);
        boolean tarifa = servico.calcularTarifa(1000.0) == 0.0;
        assertTrue(tarifa);
        assertEquals(9000.0, origem.getSaldo(), DELTA);
        assertEquals(1000.0, destino.getSaldo(), DELTA);
        // Valor-limite da regra R4: "ACIMA de 1.000" ... e 1.000 exatos?
        // Verifique o status, a tarifa e os saldos.

    }

    @Test
    void transferenciaAcimaDoLimiteDeveSerRecusada() {
        StatusTransferencia status = servico.transferir(origem, destino, 5000.01);

        assertEquals(StatusTransferencia.ACIMA_DO_LIMITE, status);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);
        // Qual status? ACIMA_DO_LIMITE Qual o valor mínimo que ultrapassa o limite (R3)? 5000,01
        // E um teste com EXATAMENTE o limite: o que deveria acontecer? DEVERIA TER STATUS OK E REALIZAR A TRANSFERÊNCIA
        // Verifique também que nenhuma conta foi alterada.

    }

    @Test
    void saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta() {
        Conta origem2k = new Conta("Origem2k", 2000.0);
        StatusTransferencia status = servico.transferir(origem2k, destino, 2000.0);

        assertEquals(StatusTransferencia.SALDO_INSUFICIENTE, status);
        assertEquals(2_000.0, origem2k.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);
        // DICA: crie uma conta de origem com saldo suficiente para o VALOR,
        //       mas NÃO suficiente para VALOR + TARIFA (ex.: saldo 2.000, valor 2.000).
        // Verifique o status e que origem E destino mantêm os saldos originais.
    }

    @Test
    void mesmaContaEValorInvalidoDevemSerRecusados() {
        StatusTransferencia status = servico.transferir(origem, origem, 0.0);

        assertEquals(StatusTransferencia.MESMA_CONTA, status);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);

        StatusTransferencia statusNegativo = servico.transferir(origem, destino, -100.0);

        assertEquals(StatusTransferencia.VALOR_INVALIDO, statusNegativo);
        assertEquals(10_000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);
        // Cubra R1 (mesma conta) e R2 (valor zero e valor negativo).
        // Quantos testes/asserts você precisa para exercitar os DOIS lados de cada "if"?     UM PARA CADA
        // Em todos os casos, confira o status E que os saldos não mudaram.
    }
}
