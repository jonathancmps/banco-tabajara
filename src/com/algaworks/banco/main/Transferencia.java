package com.algaworks.banco.main;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.algaworks.banco.modelo.Conta;

public class Transferencia {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoTabajara");
		EntityManager em = emf.createEntityManager();

		Scanner entrada = new Scanner(System.in);

		Conta conta1 = em.find(Conta.class, 1L);
		if (conta1 == null) {
			System.out.println("Digite o saldo inicial da conta1: ");
			Double saldoInicialConta1 = entrada.nextDouble();
			conta1 = new Conta();
			conta1.setSaldo(saldoInicialConta1);
		}

		Conta conta2 = em.find(Conta.class, 2L);
		if (conta2 == null) {
			System.out.println("Digite o saldo inicial da conta2: ");
			Double saldoInicialConta2 = entrada.nextDouble();
			conta2 = new Conta();
			conta2.setSaldo(saldoInicialConta2);
		}

		em.getTransaction().begin();
		em.persist(conta1);
		em.persist(conta2);
		em.getTransaction().commit();
		System.out.println("Saldo da conta1 :" + conta1.getSaldo() 
			+ ". Saldo da conta2: " + conta2.getSaldo());
		
		System.out.println("------------------------");
		System.out.println("Digite o valor a retirar da conta1 e depositar na conta");
		Double valorTransferencia = entrada.nextDouble();
		
		em.getTransaction().begin();
		conta1.setSaldo(conta1.getSaldo() - valorTransferencia);
		conta2.setSaldo(conta2.getSaldo() + valorTransferencia);
		
		if(conta1.getSaldo() > 0) {
			em.getTransaction().commit();
			System.out.println("Transferencia realizada com sucesso!");
		}else {
			em.getTransaction().rollback();
			System.out.println("Transferencia não realizada, saldo insuficiente!");
		}
		
	}
}
