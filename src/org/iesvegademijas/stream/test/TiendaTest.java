package org.iesvegademijas.stream.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class TiendaTest {
	
	@Test
	void testSkeletonFrabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
		
			
			//TODO STREAMS
			
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testAllFabricante() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
			assertEquals(9,listFab.size());
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	@Test
	void testAllProducto() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			assertEquals(11,listProd.size());
		
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		

	
	}
	
	/**
	 * 1. Lista los nombres y los precios de todos los productos de la tabla producto
	 */
	@Test
	void test1() {
	
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();
			
			List<Producto> listProd = prodHome.findAll(); //para conseguir la coleccion producto
			
			List <String> listaNombrePrecio = listProd.stream().map(p -> "Nombre: " + p.getNombre() + "  - Precio: " + p.getPrecio() + " euros \n").collect(toList());
			System.out.println(listaNombrePrecio);

			prodHome.commitTransaction();

			Set <String[]> setNombrePrecio = listProd.stream()
					.map(producto -> new String []{producto.getNombre(), Double.toString(producto.getPrecio())})
					.collect(toSet()); // mejor usar lista que set

			System.out.println("Tamaño de la lista : " + listProd.size());
			Assertions.assertEquals(11, listProd.size());

		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	
	}
	
	
	/**
	 * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
	 */
	@Test
	void test2() {
		
		ProductoHome prodHome = new ProductoHome();
		
		try {
			prodHome.beginTransaction();			
			List<Producto> listProd = prodHome.findAll();


			List <Double> listaPrecioEuros = listProd.stream().map(Producto::getPrecio).collect(toList());
			List <Double> listaPrecioDolares = listProd.stream().map(p -> p.getPrecio() * 1.2).collect(toList());

			Double precioDolaresPrimerProducto = listaPrecioDolares.get(0);
			Double precioEurosPrimerProducto = listaPrecioEuros.get(0);

			System.out.println("Precio en euros del primer producto: " + precioEurosPrimerProducto);
			System.out.println("Precio en dolares del primer producto: " + precioDolaresPrimerProducto);
			Assertions.assertEquals(precioDolaresPrimerProducto, precioEurosPrimerProducto * 1.2);



			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
	 */
	@Test
	void test3() {
		
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();


			List <String> listaNombrePrecioMayus = listProd.stream()
					.map(p -> "Nombre: " + p.getNombre().toUpperCase() + "  - Precio: " + p.getPrecio() + " euros \n")
					.collect(toList());
			System.out.println(listaNombrePrecioMayus);

			List<String> listaNombres = listProd.stream()
					.map(Producto::getNombre)
					.collect(toList());

			String nombre1normal = listaNombres.get(0);
			String nombre1mayus = listaNombres.get(0).toUpperCase();
			System.out.println(nombre1normal + ", " +  nombre1mayus);

			Assertions.assertEquals(nombre1normal.toUpperCase(), nombre1mayus);

			prodHome.commitTransaction();

		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
	 */
	@Test
	void test4() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();
			List<String> nombresFabricantes = listFab.stream().map(fab -> fab.getNombre().substring(0,2)).collect(toList());

			Assertions.assertEquals("As", nombresFabricantes.get(0));
			//Las dos primeras letras del fabricante Asus

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 5. Lista el código de los fabricantes que tienen productos.
	 */
	@Test
	void test5() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> listaProductosDeFabricante = listFab.stream()
					.filter(f -> !f.getProductos().isEmpty())
					.collect(toList());

			System.out.println("Lista de productos que tiene cada " +
							"fabricante: " + listaProductosDeFabricante);

			Assertions.assertEquals(1, listaProductosDeFabricante.get(0).getCodigo());
			//El fabricante con el código 1 es Asus

			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
	 */
	@Test
	void test6() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<String> listaNombres = listFab.stream()
							.sorted(comparing(Fabricante::getNombre).reversed())
							.map(Fabricante::getNombre)
							.collect(toList());
			fabHome.commitTransaction();

			System.out.println(listaNombres);
			Assertions.assertEquals("Xiaomi", listaNombres.get(0));
			//El primer fabricante es Xiaomi al ser ordenado en nombre descendente
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
	 */
	@Test
	void test7() {
	//en la misma lista
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			listProd.stream().sorted(comparing(Producto::getNombre).
					thenComparing(comparing(Producto::getPrecio).reversed()))
					.map(p -> p.getNombre() + " - " + p.getPrecio()).
					forEach(System.out::println);

			Assertions.assertEquals(86.99, listProd.get(0).getPrecio());

			prodHome.commitTransaction();


		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}


	}
	
	/**
	 * 8. Devuelve una lista con los 5 primeros fabricantes.
	 */
	@Test
	void test8() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> cincoPrimeros = listFab.stream()
							.limit(5)
							.collect(toList());

			System.out.println(cincoPrimeros);

			Assertions.assertEquals(5, cincoPrimeros.size());
			//Se comprueba tamaño
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
	 */
	@Test
	void test9() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> terceroYCuarto = listFab.stream()
					.limit(4)
					.skip(2)
					.collect(toList());

			System.out.println(terceroYCuarto);
			Assertions.assertEquals(4, terceroYCuarto.get(1).getCodigo());
			//se comprueba que el primero de la lista es el 4, al saltar los dos primeros
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 10. Lista el nombre y el precio del producto más barato
	 */
	@Test
	void test10() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
			List<Producto> productoMasBarato = listProd.stream()
							.sorted(comparing(Producto::getPrecio))
							.limit(1)
							.collect(toList());
			Assertions.assertEquals(59.99, productoMasBarato.get(0).getPrecio());

			System.out.println("El producto más barato es: "+ productoMasBarato.get(0).getNombre() +
							" con un precio de: " + productoMasBarato.get(0).getPrecio());

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 11. Lista el nombre y el precio del producto más caro
	 */
	@Test
	void test11() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			List<Producto> productoMasCaro = listProd.stream()
					.sorted(comparing(Producto::getPrecio).reversed())
					.limit(1)
					.collect(toList());

			Assertions.assertEquals(755.0, productoMasCaro.get(0).getPrecio());
			System.out.println("El producto más caro es: "+ productoMasCaro.get(0).getNombre() +
					" con un precio de: " + productoMasCaro.get(0).getPrecio());

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
	 * 
	 */
	@Test
	void test12() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			List<Producto> codigo2 = listProd.stream()
					.filter( p -> p.getFabricante().getCodigo() == 2)
					.collect(toList());

			System.out.println(codigo2);
			Assertions.assertEquals(2, codigo2.get(0).getFabricante().getCodigo());

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
	 */
	@Test
	void test13() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<Producto> menorA120 = listProd.stream()
							.filter(p-> p.getPrecio() <= 120)
									.collect(toList());

			System.out.println(menorA120);
			Assertions.assertNotEquals(121, menorA120.get(0).getPrecio());
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Lista los productos que tienen un precio mayor o igual a 400€.
	 */
	@Test
	void test14() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<Producto> mayorA400 = listProd.stream()
					.filter(p-> p.getPrecio() >= 400)
					.collect(toList());

			System.out.println(mayorA400);
			Assertions.assertNotEquals(399, mayorA400.get(0).getPrecio());
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Lista todos los productos que tengan un precio entre 80€ y 300€. 
	 */
	@Test
	void test15() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();


			List<Producto> entre80y300 = listProd.stream()
					.filter(p-> p.getPrecio() <= 300
							&& p.getPrecio() >= 80)
					.collect(toList());

			System.out.println(entre80y300);
			Assertions.assertNotEquals(79, entre80y300.get(0).getPrecio());
			Assertions.assertNotEquals(301, entre80y300.get(0).getPrecio());

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
	 */
	@Test
	void test16() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			List<Producto> mas200ycodFab6 = listProd.stream()
					.filter(p-> p.getFabricante().getCodigo() == 6
							&& p.getPrecio() >= 200)
					.collect(toList());

			System.out.println(mas200ycodFab6);
			//Crucial es el fabricante que tiene el código 6
			Assertions.assertEquals("Crucial", mas200ycodFab6.get(0).getFabricante().getNombre());


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
	 */
	@Test
	void test17() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
			final Set<Integer> setValidos = new HashSet<>(); //invariable - closura
			//closure = variable externa al lambda que queda atrapada en él
			setValidos.add(1); //se añaden los códigos válidos
			setValidos.add(3);
			setValidos.add(5);

			List<Producto> listProd = prodHome.findAll();
			var listaFabr = listProd.stream().filter(producto -> setValidos.contains(producto.getFabricante().getCodigo())).collect(toList());

			Assertions.assertEquals(5, listaFabr.get(0).getFabricante().getCodigo());
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 18. Lista el nombre y el precio de los productos en céntimos.
	 */
	@Test
	void test18() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		


//			List <String> setNombrePrecio = listProd.stream()
//					.map(producto -> producto.getNombre() + " - precio en centimos: " +  producto.getPrecio() * 100 + "\n")
//					.collect(toList()); // mejor usar lista que set

//			record NombrePrecioEnCents(String nombre, Double precio) {
//
//				public NombrePrecioEnCents {
//						precio= precio*100;
//				}
//
//			}

			List<Object[]>listArrayObj = listProd.stream().map(p-> new Object[]{p.getNombre(), p.getPrecio()}).collect(toList());
			listArrayObj.forEach(objects -> System.out.println((String)objects[0] + " " + ((Double) objects[1]) * 100)) ;

//			List<NombrePrecioEnCents> nombrePrecioEnCentsList = listProd.stream().map(p-> new NombrePrecioEnCents(p.getNombre(), p.getPrecio())).collect(toList());

			Assertions.assertEquals(8699.0,(Double)(listArrayObj.get(0)[1]) * 100);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
	 */
	@Test
	void test19() {
	
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> nombreS = listFab.stream().filter(fabricante -> fabricante.getNombre().indexOf("S") == 0).collect(toList());
			System.out.println(nombreS);
			Assertions.assertEquals(0, nombreS.get(0).getNombre().indexOf("S"));
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
	 */
	@Test
	void test20() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<Producto> nombresPortatil = listProd.stream().filter(p -> p.getNombre().contains("Portátil")
					|| p.getNombre().contains("Portatil"))
							.collect(toList());

			System.out.println(nombresPortatil);
			Assertions.assertTrue(nombresPortatil.get(0).getNombre().contains("Portátil"));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
	 */
	@Test
	void test21() {
	
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<Producto> nombresMonitor = listProd.stream().filter(p -> p.getNombre().contains("Monitor")
							|| p.getNombre().contains("monitor") && p.getPrecio() <= 215)
							.collect(toList());

			System.out.println(nombresMonitor);
			Assertions.assertTrue(nombresMonitor.get(0).getNombre().contains("Monitor"));
			Assertions.assertTrue(nombresMonitor.get(0).getPrecio() <= 215);


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
	 */
	@Test
	void test22() {
		
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<Producto> nombres180 = listProd.stream().filter(p -> p.getPrecio() >= 180)
					.sorted(comparing(Producto::getPrecio).reversed())
					.sorted(comparing(Producto::getNombre))
					.collect(toList());

			System.out.println(nombres180);
			Assertions.assertTrue(nombres180.get(0).getPrecio() >= 180);
				
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos. 
	 * Ordene el resultado por el nombre del fabricante, por orden alfabético.
	 */
	@Test
	void test23() {
		
		ProductoHome prodHome = new ProductoHome();
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List <Producto> prods = listProd.stream().sorted(comparing(p->p.getFabricante().getNombre()))
							.collect(toList());

			System.out.println(prods);
			assertEquals(0, prods.get(0).getFabricante().getNombre().indexOf("A"));
			// el primer producto tiene como nombre de fabricante una palabra que empieza por A (asus)


			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
	 */
	@Test
	void test24() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<Producto> prodCaro = listProd.stream()
					.sorted(comparing(Producto::getPrecio).reversed())
					.limit(1)
					.collect(toList());
			System.out.println(prodCaro);
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
	 */
	@Test
	void test25() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			
			List<Producto> crucial = listProd.stream()
					.filter(p->p.getFabricante().getNombre().equals("Crucial")  && p.getPrecio() > 200)
					.collect(toList());

			System.out.println(crucial);
			Assertions.assertTrue(crucial.get(0).getPrecio()>200
					&& crucial.get(0).getFabricante().getNombre().equals("Crucial"));
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
	 */
	@Test
	void test26() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();
			

			List<Producto> prods = listProd.stream().filter(p->p.getFabricante().getNombre().equals("Asus")
			|| p.getFabricante().getNombre().equals("Hewlett-Packard")
			|| p.getFabricante().getNombre().equals("Seagate"))
							.collect(toList());
			System.out.println(prods);

			Assertions.assertTrue(prods.get(0).getFabricante().getNombre().equals("Asus")
			|| prods.get(0).getFabricante().getNombre().equals("Hewlett-Packard")
					|| prods.get(0).getFabricante().getNombre().equals("Seagate"));

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€. 
	 * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
	 * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
	 * La salida debe quedar tabulada como sigue:

Producto                Precio             Fabricante
-----------------------------------------------------
GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
Portátil Yoga 520      |452.79            |Lenovo
Portátil Ideapd 320    |359.64000000000004|Lenovo
Monitor 27 LED Full HD |199.25190000000003|Asus

	 */
/* Función para usar en el test27 y comprobar si el precio tiene dos decimales
* Se tiene en cuenta para la salida en tabla */
	public boolean tieneDosDecimales(double numero) {
		String numeroStr = Double.toString(numero);
		String[] partes = numeroStr.split("\\.");

		if (partes.length == 2) {
			if (partes[1].length() == 2) {
				// Si se cumple, el número tiene dos decimales
				return true;
			}
		}

		return false;
	}
	@Test
	void test27() {


		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			List<Producto> prod = listProd.stream().filter(p->p.getPrecio() >= 180)
							.sorted(comparing(Producto::getPrecio))
									.sorted(comparing(Producto::getNombre))
											.collect(toList());


			System.out.println("Producto        " + "                  Precio     " + "Fabricante  ");
			System.out.println("--------------------------------------------------------------------");

            for (Producto producto : prod) {
				System.out.print(producto.getNombre());
				for (int i = producto.getNombre().length(); i <= 31 ; i++) {
					System.out.print(" ");
				}
				System.out.print("|");
				if (tieneDosDecimales(producto.getPrecio()))
				{
					System.out.print(" " + producto.getPrecio() + " | ");
				} else {
					System.out.print(" " + producto.getPrecio() + "  | ");

				}
				System.out.print(producto.getFabricante().getNombre());
				System.out.println();
			}

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos. 
	 * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados. 
	 * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
	 * La salida debe queda como sigue:
Fabricante: Asus

            	Productos:
            	Monitor 27 LED Full HD
            	Monitor 24 LED Full HD

Fabricante: Lenovo

            	Productos:
            	Portátil Ideapd 320
            	Portátil Yoga 520

Fabricante: Hewlett-Packard

            	Productos:
            	Impresora HP Deskjet 3720
            	Impresora HP Laserjet Pro M26nw

Fabricante: Samsung

            	Productos:
            	Disco SSD 1 TB

Fabricante: Seagate

            	Productos:
            	Disco duro SATA3 1TB

Fabricante: Crucial

            	Productos:
            	GeForce GTX 1080 Xtreme
            	Memoria RAM DDR4 8GB

Fabricante: Gigabyte

            	Productos:
            	GeForce GTX 1050Ti

Fabricante: Huawei

            	Productos:


Fabricante: Xiaomi

            	Productos:

	 */
	@Test
	void test28() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

					
			//Formatear salida: quitar comas y corchetes
//			Fabricante: Crucial
//
//			Productos:
//			GeForce GTX 1080 Xtreme
//			Memoria RAM DDR4 8GB

			List<String> fabs = listFab.stream()
					.map( fabricante -> "Fabricante: " + fabricante.getNombre() +
			 				"\n" + "Productos: " + "\n"
							+ fabricante.getProductos().stream().map(p->" -" + p.getNombre()
							+ " \n")
							.collect(toList())
							+ "\n")
							.collect(toList());




			System.out.println(fabs);
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
	 */
	@Test
	void test29() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
	
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> fabs = listFab.stream().filter(f -> f.getProductos().isEmpty()).collect(toList());
			System.out.println(fabs);

			Assertions.assertTrue(fabs.get(0).getProductos().isEmpty());
								
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
	 */
	@Test
	void test30() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		

			long prods = listProd.stream().filter(producto -> !producto.getNombre().isEmpty()).count();
			System.out.println("Numero total de productos que hay en la tabla productos" + prods);

            assertEquals(listProd.size(), prods);
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}

	
	/**
	 * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
	 */
	@Test
	void test31() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			//TODO STREAMS
			List<Producto> prods= listProd.stream().filter(p-> !p.getFabricante().getProductos().isEmpty()).collect(toList());
			System.out.println("Numero de fabricante con productos" + prods);
            assertFalse(prods.get(0).getFabricante().getProductos().isEmpty());
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 32. Calcula la media del precio de todos los productos
	 */
	@Test
	void test32() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();


			double media = listProd.stream().map(Producto::getPrecio).reduce(0.0, Double::sum) / listProd.size();
			System.out.println("Media del precio de todos los productos" + media);
						

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
	 */
	@Test
	void test33() {
	
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						

			Optional<Double> precioBarato = listProd.stream().map(Producto::getPrecio).reduce(Double::min);
			System.out.println("Precio mas barato: " + precioBarato);
			prodHome.commitTransaction();



		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 34. Calcula la suma de los precios de todos los productos.
	 */
	@Test
	void test34() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

			double suma = listProd.stream().map(Producto::getPrecio).reduce(0.0, Double::sum);
			System.out.println("Suma del precio de todos los productos: " + suma);
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 35. Calcula el número de productos que tiene el fabricante Asus.
	 */
	@Test
	void test35() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			long prodAsus = listProd.stream().filter(p -> p.getFabricante().getNombre().equals("Asus")).count();
			System.out.println("Numero de productos asus> " + prodAsus);

			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 36. Calcula la media del precio de todos los productos del fabricante Asus.
	 */
	@Test
	void test36() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();		
						
			List<Producto> prodAsus = listProd.stream().filter(p -> p.getFabricante().getNombre().equals("Asus")).collect(toList());
			double media = prodAsus.stream().map(Producto::getPrecio).reduce(0.0, Double::sum) / prodAsus.size();

			System.out.println("Hay " + prodAsus.size() + " Asus");
			System.out.println("La media de precio de los productos " + prodAsus.get(0).getNombre() + " y " + prodAsus.get(1).getNombre() + " es de  " + media);




			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial. 
	 *  Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 */
	@Test
	void test37() {
		
		ProductoHome prodHome = new ProductoHome();	
		try {
			prodHome.beginTransaction();
		
			List<Producto> listProd = prodHome.findAll();

//			//TODO STREAMS /*HACER!!!!!*/
//			Double[] statistics = listProd.stream()
//					.mapToDouble(Producto::getPrecio)
//					.collect(
//							() -> new Double[] { Double.MIN_VALUE, Double.MAX_VALUE, 0.0, 0.0 },
//							(acc, price) -> {
//								acc[0] = Math.max(acc[0], price); // Maximum price
//								acc[1] = Math.min(acc[1], price); // Minimum price
//								acc[2] += price; // Sum of prices
//								acc[3]++; // Count of products
//							},
//							(acc1, acc2) -> {
//								acc1[0] = Math.max(acc1[0], acc2[0]);
//								acc1[1] = Math.min(acc1[1], acc2[1]);
//								acc1[2] += acc2[2];
//								acc1[3] += acc2[3];
//							}
//					);
//
//			System.out.println(statistics);
			
			prodHome.commitTransaction();
		}
		catch (RuntimeException e) {
			prodHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 38. Muestra el número total de productos que tiene cada uno de los fabricantes. 
	 * El listado también debe incluir los fabricantes que no tienen ningún producto. 
	 * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene. 
	 * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
	 * La salida debe queda como sigue:
	 
     Fabricante     #Productos
-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
           Asus              2
         Lenovo              2
Hewlett-Packard              2
        Samsung              1
        Seagate              1
        Crucial              2
       Gigabyte              1
         Huawei              0
         Xiaomi              0

	 */
	@Test
	void test38() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
//			fabHome.commitTransaction();
//			System.out.println("Fabricante         #Productos");
//			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
//			long cantidad = 0;
////			long prods = listProd.stream().filter(producto -> !producto.getNombre().isEmpty()).count();
//			List<String> nombre = listFab.stream().map(Fabricante::getNombre).collect(toList());
//			for (int i = 0; i < nombre.size(); i++) {
//				String fabricante = nombre.get(i);
//
//				if (listFab.get(i).getNombre().equals(fabricante)){
//					cantidad = listFab.get(i).getProductos().size();
//				}
//
//
//				String formattedLine = String.format("%-15s%6d", fabricante, cantidad);
//				System.out.println(formattedLine);
//			}

		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes. 
	 * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
	 * Deben aparecer los fabricantes que no tienen productos.
	 */
	@Test
	void test39() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€. 
	 * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
	 */
	@Test
	void test40() {
	
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
	 */
	@Test
	void test41() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();

			List<Fabricante> dosOMas = listFab.stream().filter(f -> f.getProductos().size() >= 2).collect(toList());
			System.out.println("Fabricantes con dos o mas productos: " + dosOMas);

			Assertions.assertTrue(dosOMas.get(0).getProductos().size() >= 2);
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €. 
	 * Ordenado de mayor a menor número de productos.
	 */
	@Test
	void test42() {
		
		FabricanteHome fabHome = new FabricanteHome();
		ProductoHome prodHome = new ProductoHome();


		try {
			fabHome.beginTransaction();
			prodHome.beginTransaction();


			List<Fabricante> listFab = fabHome.findAll();
			List<Producto> listProd = prodHome.findAll();

			List<String> fabricantes = listProd.stream()
					.filter(p-> p.getPrecio() >= 200)
					.map(p -> p.getFabricante().getNombre())
					.distinct()
					.collect(toList());

			System.out.println("Fabricante     #Productos +200e");
			System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");

			for (int i = 0; i < fabricantes.size(); i++) {
				int finalI = i;
				long cantidad = listProd.stream()
						.filter(p -> p.getFabricante().getNombre().equals(fabricantes.get(finalI))).count();
				if (fabricantes.get(i).length() <=5) {
					System.out.println(fabricantes.get(i) + "   - " + cantidad);
				} else {
					System.out.println(fabricantes.get(i) + " - " + cantidad);
				}
			}
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 */
	@Test
	void test43() {
		
		FabricanteHome fabHome = new FabricanteHome();
		ProductoHome prodHome = new ProductoHome();


		try {
			fabHome.beginTransaction();
			prodHome.beginTransaction();


			List<Fabricante> listFab = fabHome.findAll();
			List<Producto> listProd = prodHome.findAll();


			//TODO STREAMS
			Double suma = listProd.stream()
					.map(Producto::getPrecio)
					.reduce(0.0, Double::sum);




			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
	 * Ordenado de menor a mayor por cuantía de precio de los productos.
	 */
	@Test
	void test44() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante. 
	 * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante. 
	 * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
	 */
	@Test
	void test45() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
	/**
	 * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
	 * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
	 */
	@Test
	void test46() {
		
		FabricanteHome fabHome = new FabricanteHome();
		
		try {
			fabHome.beginTransaction();
				
			List<Fabricante> listFab = fabHome.findAll();
				
			//TODO STREAMS															
		
			fabHome.commitTransaction();
		}
		catch (RuntimeException e) {
			fabHome.rollbackTransaction();
		    throw e; // or display error message
		}
			
	}
	
}

