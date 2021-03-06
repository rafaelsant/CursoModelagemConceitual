package com.rafaelsantiago.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rafaelsantiago.cursomc.domain.Categoria;
import com.rafaelsantiago.cursomc.domain.Cidade;
import com.rafaelsantiago.cursomc.domain.Cliente;
import com.rafaelsantiago.cursomc.domain.Endereco;
import com.rafaelsantiago.cursomc.domain.Estado;
import com.rafaelsantiago.cursomc.domain.ItemPedido;
import com.rafaelsantiago.cursomc.domain.Pagamento;
import com.rafaelsantiago.cursomc.domain.PagamentoComBoleto;
import com.rafaelsantiago.cursomc.domain.PagamentoComCartao;
import com.rafaelsantiago.cursomc.domain.Pedido;
import com.rafaelsantiago.cursomc.domain.Produto;
import com.rafaelsantiago.cursomc.domain.enums.EstadoPagamento;
import com.rafaelsantiago.cursomc.domain.enums.TipoCliente;
import com.rafaelsantiago.cursomc.repositories.CategoriaRepository;
import com.rafaelsantiago.cursomc.repositories.CidadeRepository;
import com.rafaelsantiago.cursomc.repositories.ClienteRepository;
import com.rafaelsantiago.cursomc.repositories.EnderecoRepository;
import com.rafaelsantiago.cursomc.repositories.EstadoRepository;
import com.rafaelsantiago.cursomc.repositories.ItemPedidoRepository;
import com.rafaelsantiago.cursomc.repositories.PagamentoRepository;
import com.rafaelsantiago.cursomc.repositories.PedidoRepository;
import com.rafaelsantiago.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository catRepo;
	@Autowired
	private ProdutoRepository prodRep;
	@Autowired
	private CidadeRepository cidRep;
	@Autowired
	private EstadoRepository estRep;
	@Autowired
	private EnderecoRepository endRep;
	@Autowired
	private ClienteRepository cliRep;
	@Autowired
	private PedidoRepository pedRep;
	@Autowired
	private PagamentoRepository pagRep;
	@Autowired
	private ItemPedidoRepository itemPedRep;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm");
		
		Categoria cat1 = new Categoria(null,"informática");
		Categoria cat2 = new Categoria(null,"escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",20.00);
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","11111111111",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 203","Jardim","38777034",cli1,c1);
		Endereco e2 = new Endereco(null,"Av Matos","105","Sala 800","Centro","38777012",cli1,c2);
		
		Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:32"),cli1,e1);
		Pedido ped2 = new Pedido(null,sdf.parse("10/10/2020 10:12"),cli1,e1);
		
		ItemPedido ip1 = new ItemPedido(ped1,p1,0.00,1,2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		Pagamento pagto1 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped1,6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE,ped2,sdf.parse("20/10/2017 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		System.out.println("*****************************************************************");
		System.out.println(est1.getCidades());
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		System.out.println("*****************************************************************");
		System.out.println(cat1.getProdutos());
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat2));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		catRepo.saveAll(Arrays.asList(cat1,cat2));
		prodRep.saveAll(Arrays.asList(p1,p2,p3));
		estRep.saveAll(Arrays.asList(est1,est2));
		cidRep.saveAll(Arrays.asList(c1,c2,c3));
		cliRep.saveAll(Arrays.asList(cli1));
		endRep.saveAll(Arrays.asList(e1,e2));
		pedRep.saveAll(Arrays.asList(ped1,ped2));
		pagRep.saveAll(Arrays.asList(pagto1,pagto2));
		itemPedRep.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
