import java.io.*;
//-----------------------------------------------------
//Assignment 3
//Written by: Evan Teboul and 40238390
//-----------------------------------------------------
/**
* Evan Teboul and 40238390
* COMP249
* Assignment 2
* March 27 2023
* @author evanteboul
*
*/
@SuppressWarnings("serial")
public class Book implements Serializable{
//	private static final long serialVersionUID = 1L;
	private String title;
	private String authors;
	private double price;
	private String isbn;
	private String genre;
	private int year;
	
	

	/**
	 * This is the parameterized constructor.
	 * @param authors the book authors
	 * @param price double that represents the book cost
	 * @param isbn String isbn that can be converted to int 
	 * @param genre string for the genre code
	 * @param year integer that represents the year.
	 * @param title String that represents the book title
	 */
	public Book(String title, String authors, double price, String isbn, String genre, int year) {
		super();
		this.title=title;
		this.authors = authors;
		this.price = price;
		this.isbn = isbn;
		this.genre = genre;
		this.year = year;
	}
	
	
	/**
	 * default constructor
	 */
	public Book() {
		super();
	}
	// Order: title, authors, price, isbn,genre, year
	/**
	 * This method takes a string array and converts to a book object, this is to be used on strings that are syntaxically and semantically correct.
	 * @param arr, the String array which is composed of the information of each book record.
	 * @return new book object which is composed of the information of each correct book record.
	 */
	public static Book toBook(String[] arr) {
		Book book = new Book(arr[0],arr[1],Double.parseDouble(arr[2]),arr[3],arr[4],Integer.parseInt((arr[5])));
		return book;
	}
	/**
	 * getter
	 * @return title field
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 
	 * @return authors field
	 */
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	/**
	 * 
	 * @return price field
	 */
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * 
	 * @return isbn field
	 */
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	/**
	 * 
	 * @return genre field
	 */
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * 
	 * @return year field
	 */
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * copy constructor
	 * @param book object of the book class.
	 */
	public Book(Book book) {
		title=book.title;
		price=book.price;
		isbn=book.isbn;
		authors=book.authors;
		genre=book.genre;
		year=book.year;
	}
	// Order: title, authors, price, isbn,genre, year
	
	@Override 
	public String toString() {
		return title+","+ authors+","+price+","+isbn+","+genre+","+year;
	}
	
	@Override
	public boolean equals(Object x) {
		if(x==null||x.getClass()!=this.getClass())
			return false;
		Book book = (Book) x;
		return title.equals(book.getTitle()) && authors.equals(book.authors)&&price==book.price&&
				isbn.equals(book.isbn)&&genre.equals(book.isbn)&&year==book.year;
	}
	
}
