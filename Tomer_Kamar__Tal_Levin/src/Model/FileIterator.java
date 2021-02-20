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
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;


public class FileIterator implements Iterator<Entry<String, Product>> {
	private RandomAccessFile raf;
	private int size;
	private int current = 0;
	private int last = -1;

	public FileIterator() throws FileNotFoundException, IOException {
		this.raf = new RandomAccessFile("products.txt", "rw");
		size = (int) this.raf.length();
	}

	@Override
	public boolean hasNext() {
		return current < size;
		//		try {
		//			if(oIn.available() != 0)
		//				return true;
		//		} catch (IOException e) {
		//			System.out.print(e.getMessage());	
		//		}
		//		return false;
	}

	@Override
	public Entry<String, Product> next() {
		try {
			if(!hasNext())
				return null;
			String catalog = raf.readUTF();
			String prodName = raf.readUTF();
			int storePrice = raf.readInt();
			int customerPrice = raf.readInt();
			String customerName = raf.readUTF();
			String phone = raf.readUTF();
			boolean promotions = raf.readBoolean();

			last = current;
			current = (int) raf.getFilePointer();

			return new java.util.AbstractMap.SimpleEntry<String, Product>(catalog, new Product(prodName, storePrice, customerPrice, new Customer(customerName, phone, promotions)));

		} catch (IOException | IllegalStateException e) {
			e.printStackTrace();
		}

		return null;

		//		String catalog = null;
		//		Product product = null;
		//		try {
		//			catalog = this.oIn.readUTF();
		//			product = (Product) this.oIn.readObject(); 
		//		} catch (IOException | ClassNotFoundException e) {System.out.print(e.getMessage());}
		//		return new java.util.AbstractMap.SimpleEntry<String, Product>(catalog, product);
		////		return catalog;
	}

	@Override
	public void remove() {
		if (last == -1)
			throw new IllegalStateException();
		try {
			int index = 1;
			int before = last;
			Entry<String, Product>  entry = null;
			while(hasNext()) {
				for(int i = 0; i < index; i++) {
					entry = next();
				}
				index = 2;
				raf.seek(before);
				raf.writeUTF(entry.getKey());
				writeProduct(this, entry.getValue());
				before = (int) raf.getFilePointer();
				
			}
			raf.setLength(last);
			last =-1;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RandomAccessFile getRaf() {
		return this.raf;
	}

	public TreeMap<String, Product> readAllProducts() throws ClassNotFoundException, IOException {
		FileIterator iterator = new FileIterator();
		if(iterator.hasNext()) {
			TreeMap<String, Product> allProductsMap = null;
			int sortOpt = iterator.getRaf().readInt();

			iterator.current = (int) iterator.getRaf().getFilePointer();

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

			Entry<String, Product> e;
			while(iterator.hasNext()) {
				e = iterator.next();
				allProductsMap.put(e.getKey(), e.getValue());
			}
			iterator.getRaf().close();
			return allProductsMap;
		}
		iterator.getRaf().close();
		return null;
	}

	//		TreeMap<String, Product> allProductsMap = null;
	//		this.oIn = new ObjectInputStream(new FileInputStream(new File("products.txt")));
	//
	//		if(hasNext()) {
	//			int sortOpt = this.oIn.readInt();
	//
	//			switch(sortOpt) {
	//			case 1:
	//				allProductsMap = new TreeMap<>(new ascendingComparator());
	//				break;
	//			case 2:
	//				allProductsMap = new TreeMap<>(new descendingComparator());
	//				break;
	//			case 3:
	//				allProductsMap = new TreeMap<>(new insertionOrderComparator());
	//				break;
	//			}
	//
	//			Entry<String, Product> e;
	//			while(hasNext()) {
	////				String key = next();
	////				Product p = nextProduct();//(Product)this.oIn.readObject();
	////
	////				allProductsMap.put(key, p);
	//				e = next();
	//				allProductsMap.put(e.getKey(), e.getValue());
	//			}
	//			this.oIn.close();
	//			return allProductsMap;
	//		}
	//		this.oIn.close();
	//		return null;
	//}

	public void writeProducts(TreeMap<String, Product> treeMap) throws IOException {
		FileIterator iterator = new FileIterator();
		iterator.getRaf().setLength(0);
		
		Comparator<String> comparator = (Comparator<String>) treeMap.comparator();
		if(comparator.getClass() == ascendingComparator.class)
			iterator.getRaf().writeInt(1);
		else if(comparator.getClass() == descendingComparator.class)
			iterator.getRaf().writeInt(2);
		else
			iterator.getRaf().writeInt(3);

		for(Entry<String, Product> entry : treeMap.entrySet()) {
			iterator.getRaf().writeUTF(entry.getKey());
			//			this.raf.writeObject(entry.getValue());
			writeProduct(iterator, entry.getValue());
		}
		
		iterator.getRaf().close();


		//		this.oOut = new ObjectOutputStream(new FileOutputStream(new File("products.txt")));
		//		//		this.oOut.reset();
		//		Comparator<String> comparator = (Comparator<String>) treeMap.comparator();
		//
		//		if(comparator.getClass() == ascendingComparator.class)
		//			this.oOut.writeInt(1);
		//		else if(comparator.getClass() == descendingComparator.class)
		//			this.oOut.writeInt(2);
		//		else
		//			this.oOut.writeInt(3);
		//
		//
		//		for(Entry<String, Product> entry : treeMap.entrySet()) {
		//			this.oOut.writeUTF(entry.getKey());
		//			this.oOut.writeObject(entry.getValue());
		//		}
		//		this.oOut.close();
	}

	public void writeProduct(FileIterator iterator, Product product) throws IOException {
		iterator.getRaf().writeUTF(product.getName());
		iterator.getRaf().writeInt(product.getStorePrice());
		iterator.getRaf().writeInt(product.getCustomerPrice());
		iterator.getRaf().writeUTF(product.getCustomer().getName());
		iterator.getRaf().writeUTF(product.getCustomer().getPhoneNumber());
		iterator.getRaf().writeBoolean(product.getCustomer().isAcceptPromotions());
	}

	public void deleteAllContent() throws FileNotFoundException, IOException {
		FileIterator iterator = new FileIterator();
		iterator.getRaf().setLength(4);
		iterator.getRaf().close();
		//		this.oIn = new ObjectInputStream(new FileInputStream(new File("products.txt")));
		//		int sortOpt = this.oIn.readInt();
		//		this.oIn.close();
		//
		//		this.oOut = new ObjectOutputStream(new FileOutputStream(new File("products.txt")));
		//		this.oOut.writeInt(sortOpt);
		//		this.oOut.close();
	}

	public boolean deleteProduct(String catalog) throws FileNotFoundException, IOException {
		//		this.oIn = new ObjectInputStream(new FileInputStream(new File("products.txt")));
		//		oIn.readInt();

		FileIterator iterator = new FileIterator();
		iterator.getRaf().readInt();
		iterator.current = (int) iterator.getRaf().getFilePointer();

		//		String tempCatalog;
		Entry<String, Product> entry;
		while(iterator.hasNext()) {
			//			tempCatalog = this.next();
			entry = iterator.next();
			//			if(catalog.equals(tempCatalog)) {
			if(entry.getKey().equals(catalog)) {
				iterator.remove();
				//				this.nextProduct();
				//				this.remove();
				iterator.getRaf().close();
				return true;
			}			
		}
		
		iterator.getRaf().close();
		return false;
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
