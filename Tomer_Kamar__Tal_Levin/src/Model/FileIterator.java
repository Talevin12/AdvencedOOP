package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;


public class FileIterator implements Iterator<String> {
	private ObjectOutputStream oOut;
	private ObjectInputStream oIn;

	public FileIterator() throws FileNotFoundException, IOException {
	}

	@Override
	public boolean hasNext() {
		try {
			if(oIn.available() != 0)
				return true;
		} catch (IOException e) {
			System.out.print(e.getMessage());	
		}
		return false;
	}

	@Override
	public String next() {
		String catalog = null;
		try {
			catalog = this.oIn.readUTF();
		} catch (IOException e) {System.out.print(e.getMessage());}
		return catalog;
	}

	public Product nextProduct() {
		Product product = null;
		try {
			product = (Product) this.oIn.readObject();
		} catch (IOException | ClassNotFoundException e) {System.out.print(e.getMessage());}
		return product;
	}

	public void closeOutputStreams() throws IOException {
		this.oOut.close();
	}

	public TreeMap<String, Product> readAllProducts() throws ClassNotFoundException, IOException {
		TreeMap<String, Product> allProductsMap = null;
		this.oIn = new ObjectInputStream(new FileInputStream(new File("products.txt")));

		if(hasNext()) {
			int sortOpt = this.oIn.readInt();

			switch(sortOpt) {
			case 1:
				allProductsMap = new TreeMap<>(new ascendingComparator());
				break;
			case 2:
				allProductsMap = new TreeMap<>(new descendingComparator());
				break;
			case 3:
				allProductsMap = new TreeMap<>(new insertionOrderComparator());
				break;
			}

			while(hasNext()) {
				String key = next();
				Product p = nextProduct();//(Product)this.oIn.readObject();

				allProductsMap.put(key, p);
			}
			this.oIn.close();
			return allProductsMap;
		}
		this.oIn.close();
		return null;
	}

	public void writeProducts(TreeMap<String, Product> treeMap) throws IOException {
		this.oOut = new ObjectOutputStream(new FileOutputStream(new File("products.txt")));
		//		this.oOut.reset();
		Comparator<String> comparator = (Comparator<String>) treeMap.comparator();

		if(comparator.getClass() == ascendingComparator.class)
			this.oOut.writeInt(1);
		else if(comparator.getClass() == descendingComparator.class)
			this.oOut.writeInt(2);
		else
			this.oOut.writeInt(3);


		for(Entry<String, Product> entry : treeMap.entrySet()) {
			this.oOut.writeUTF(entry.getKey());
			this.oOut.writeObject(entry.getValue());
		}
		this.oOut.close();
	}

	public void deleteAllContent() throws FileNotFoundException, IOException {
		this.oIn = new ObjectInputStream(new FileInputStream(new File("products.txt")));
		int sortOpt = this.oIn.readInt();
		this.oIn.close();

		this.oOut = new ObjectOutputStream(new FileOutputStream(new File("products.txt")));
		this.oOut.writeInt(sortOpt);
		this.oOut.close();
	}

	public void deleteProduct(String catalog) throws FileNotFoundException, IOException {
		this.oIn = new ObjectInputStream(new FileInputStream(new File("products.txt")));
		oIn.readInt();
		String tempCatalog;
		while(this.hasNext()) {
			tempCatalog = this.next();
			if(catalog.equals(tempCatalog)) {
				this.remove();
				this.nextProduct();
				this.remove();
				break;
			}			
		}
	}


	public static class ascendingComparator implements Comparator<String>{
		@Override
		public int compare(String catalog1, String catalog2) {
			return catalog1.compareTo(catalog2);
		}
	}

	public static class descendingComparator implements Comparator<String>{
		@Override
		public int compare(String catalog1, String catalog2) {
			return catalog2.compareTo(catalog1);
		}
	}

	public static class insertionOrderComparator implements Comparator<String>{
		@Override
		public int compare(String catalog1, String catalog2) {
			return 1;
		}
	}
}
