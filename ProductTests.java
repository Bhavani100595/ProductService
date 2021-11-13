package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductTests {

	@Autowired
	private ProductRepository repo;
	
	@Test
	@Rollback(false)
	public void testCreateProduct() {
		Product product = new Product("iphone9",789);
		Product savedProduct = repo.save(product);
		assertNotNull(savedProduct);
	}
	
	@Test
	public void testFindProductByNameExist() {
		String name="iphone9";
		Product product = repo.findByName(name);
		assertThat(product.getName()).isEqualTo(name);
	}
	
	@Test
	@Rollback(false)
	public void testFindProductByNameNotExist() {
		String name="iphone 11";
		Product product = repo.findByName(name);
		assertNull(product);
	}
	
	@Test
	@Rollback(false)
	public void testUpdateProduct() {
		String productName = "Kindle Reader";
		Product product = new Product(productName,789);
		product.setId(2);
		repo.save(product);
		Product updateProduct = repo.findByName(productName);
		assertThat(updateProduct.getName()).isEqualTo(productName);
		
	}

	@Test
	public void testListProduct() {
		List<Product> products = (List<Product>) repo.findAll();
		for(Product product : products) {
			System.out.println(product);
		}
		assertThat(products).size().isGreaterThan(0);
	}
	
	@Test
	public void testDeleteProduct() {
		Integer id=2;
		boolean isExistBeforeDelete = repo.findById(id).isPresent();
		repo.deleteById(id);
		boolean isNotExistBeforeDelete = repo.findById(id).isPresent();
		assertTrue(isExistBeforeDelete);
		assertFalse(isNotExistBeforeDelete);
	}
}
