package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;


public class FileIterator implements Iterator<Entry<String, Product>> {
	private RandomAccessFile raf;
	private long size;
	private long current = 0;
	private long last = -1;

	public FileIterator() throws FileNotFoundException, IOException {
		this.raf = new RandomAccessFile("products.txt", "rw");
		size = this.raf.length();
	}

	@Override
	public boolean hasNext() {
		return current < size;
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
			current = raf.getFilePointer();

			return new java.util.AbstractMap.SimpleEntry<String, Product>(catalog, new Product(prodName, storePrice, customerPrice, new Customer(customerName, phone, promotions)));

		} catch (IOException | IllegalStateException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void remove() {
		if (last == -1)
			throw new IllegalStateException();
		try {
			long before = last;
			Entry<String, Product>  entry = null;
			
			
			while(hasNext()) {
				entry = next();
				raf.seek(before);
				raf.writeUTF(entry.getKey());
				writeProduct(this, entry.getValue());
				before = raf.getFilePointer();
				raf.seek(current);
				
			}
			raf.setLength(before);
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
			writeProduct(iterator, entry.getValue());
		}
		
		iterator.getRaf().close();
	}

	public void writeProduct(FileIterator iterator, Product product) throws IOException {		
		iterator.getRaf().writeUTF(product.getName());
		iterator.getRaf().writeInt(product.getStorePrice());
		iterator.getRaf().writeInt(product.getCustomerPrice());
		iterator.getRaf().writeUTF(product.getCustomer().getName());
		iterator.getRaf().writeUTF(product.getCustomer().getPhoneNumber());
		iterator.getRaf().writeBoolean(product.getCustomer().isAcceptPromotions());
	}

//	public void deleteAllContent() throws FileNotFoundException, IOException {
//		FileIterator iterator = new FileIterator();
//		iterator.getRaf().setLength(4);
//		iterator.getRaf().close();
//	}

	public boolean deleteProduct(String catalog) throws FileNotFoundException, IOException {
		FileIterator iterator = new FileIterator();
		iterator.getRaf().readInt();
		iterator.current = (int) iterator.getRaf().getFilePointer();

		Entry<String, Product> entry;
		while(iterator.hasNext()) {
			entry = iterator.next();
			if(entry.getKey().equals(catalog)) {
				iterator.remove();
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
